package guru.qa.niffler.model;

import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.UUID;

public record UserJson(
        UUID id,
        String username,
        CurrencyValues currency,
        String fullname,
        String firstname,
        String surname,
        byte[] photo,
        byte[] photoSmall) {

    public static UserJson fromEntity(UserEntity entity) {
        return new UserJson(
                entity.getId(),
                entity.getUsername(),
                entity.getCurrency(),
                entity.getFullname(),
                entity.getFirstname(),
                entity.getSurname(),
                entity.getPhoto(),
                entity.getPhotoSmall()
        );
    }
}
