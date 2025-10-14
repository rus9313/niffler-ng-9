package guru.qa.niffler.model.auth;

import guru.qa.niffler.data.entity.auth.AuthAuthorityEntity;

import java.util.UUID;

public record AuthorityJson(
        UUID id,
        UUID userId,
        Authority authority) {

    public static AuthorityJson fromEntity(AuthAuthorityEntity entity) {
        return new AuthorityJson(
                entity.getId(),
                entity.getUserId(),
                entity.getAuthority()
        );
    }
}
