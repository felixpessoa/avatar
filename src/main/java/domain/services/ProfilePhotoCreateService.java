package domain.services;

import java.util.Map;

import domain.models.ProfilePhoto;
import domain.repositories.ProfilePhotoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProfilePhotoCreateService {

    private final ProfilePhotoRepository repository;

    public ProfilePhotoCreateService(ProfilePhotoRepository repository) {
        this.repository = repository;
    }

    public void save(String customerId, ProfilePhoto profilePhoto) {
        repository.registerEntity(Map.of(customerId, profilePhoto));
        repository.commit();
    }
}
