package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import lombok.NonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    @Nonnull
    public static UserJson fromEntity(@Nonnull UserEntity entity, @Nullable FriendshipStatus friendshipStatus) {
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

    @NonNull
    public UserJson addTestData(@Nonnull TestData testData) {
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
