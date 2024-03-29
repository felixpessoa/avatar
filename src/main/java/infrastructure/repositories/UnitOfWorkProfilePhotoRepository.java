package infrastructure.repositories;

import java.util.Map;

import org.jboss.logging.Logger;

import domain.models.ProfilePhoto;
import domain.repositories.ProfilePhotoRepository;
import infrastructure.rest.StableDiffusionService;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UnitOfWorkProfilePhotoRepository implements ProfilePhotoRepository {

    private final HibernateProfilePhotoRepository persistenceRepository;
    private final S3ProfilePhotoStorageRepository storageRepository;
    private final StableDiffusionService stableDiffusionService;

    private Map<String, ProfilePhoto> entities;

    public UnitOfWorkProfilePhotoRepository(HibernateProfilePhotoRepository persistenceRepository,
            S3ProfilePhotoStorageRepository storageRepository, StableDiffusionService stableDiffusionService) {
        this.persistenceRepository = persistenceRepository;
        this.storageRepository = storageRepository;
        this.stableDiffusionService = stableDiffusionService;
        this.entities = Map.of();
    }

    @Override
    public void registerEntity(Map<String, ProfilePhoto> entites) {
        this.entities = entites;
    }

    @Override
    public void commit() {
        entities.forEach((customerId, profilePhoto) -> {
            try {
                persistenceRepository.save(customerId, profilePhoto);

                var generated = stableDiffusionService.generate(profilePhoto).await().indefinitely();

                var originalS3 = storageRepository.store(customerId, profilePhoto).await().indefinitely();
                var generatedS3 = storageRepository.store(customerId, profilePhoto, generated).await().indefinitely();

                var updated = new ProfilePhoto(customerId, originalS3, generatedS3);

                persistenceRepository.save(customerId, updated);
            } catch (Exception e) {
                Logger.getLogger(getClass()).error(e);
            }
        });
    }

    @Override
    public void rollback() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rollback'");
    }

}