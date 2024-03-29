package infrastructure.rest;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import domain.models.ProfilePhoto;
import infrastructure.rest.client.StableDiffusion;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StableDiffusionService {
    private final StableDiffusion stableDiffusion;

    public StableDiffusionService(@RestClient StableDiffusion stableDiffusion) {
        this.stableDiffusion = stableDiffusion;
    }

    @Transactional
    public Uni<String> generate(ProfilePhoto profilePhoto) throws IOException {
        var fileContent = FileUtils.readFileToByteArray(new File(profilePhoto.originalPhoto()));
        var encodedString = Base64.getEncoder().encodeToString(fileContent);

        return stableDiffusion.img2img(new StableDiffusion.Request(encodedString))
                .onItem()
                .transform(response -> response.images().stream().findFirst().orElseThrow());
    }
}
