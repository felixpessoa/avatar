package domain.repositories;

import java.util.Map;

import domain.models.ProfilePhoto;

public interface ProfilePhotoRepository {

    void registerEntity(Map<String, ProfilePhoto> entites);

    void commit();

    void rollback();

}