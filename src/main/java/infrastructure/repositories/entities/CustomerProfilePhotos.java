package infrastructure.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity(name = "profile_photos")
public class CustomerProfilePhotos {
    @EmbeddedId
    CompositeKey CompositeKey;

    @Column(name = "original_photo")
    String originalPhoto;

    @Column(name = "generated_photo")
    String generatedPhoto;

    @Embeddable
    private class CompositeKey {
        @Column(name = "customer_id")
        String customerId;

        @Column(name = "id")
        String id;
    }
}
