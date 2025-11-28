package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserJson(
        UUID id,
        String username,
        CurrencyValues currency,
        String fullname,
        String firstname,
        String surname,
        byte[] photo,
        byte[] photoSmall,
        FriendshipStatus friendshipStatus,
        @JsonIgnore
        TestData testData) {

    public static UserJson fromEntity(UserEntity entity, FriendshipStatus friendshipStatus) {
        return new UserJson(
                entity.getId(),
                entity.getUsername(),
                entity.getCurrency(),
                entity.getFullname(),
                entity.getFirstname(),
                entity.getSurname(),
                entity.getPhoto(),
                entity.getPhotoSmall(),
                friendshipStatus,
                null
        );
    }

    public UserJson addTestData(TestData testData) {
        return new UserJson(
                id,
                username,
                currency,
                surname,
                fullname,
                firstname,
                photo,
                photoSmall,
                friendshipStatus,
                testData
        );
    }
}
