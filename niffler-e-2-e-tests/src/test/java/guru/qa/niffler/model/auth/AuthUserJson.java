package guru.qa.niffler.model.auth;

import guru.qa.niffler.data.entity.auth.AuthUserEntity;

import java.util.UUID;

public record AuthUserJson(
        UUID id,
        String username,
        String password,
        Boolean enabled,
        Boolean accountNonExpired,
        Boolean accountNonLocked,
        Boolean credentialsNonExpired) {

    public static AuthUserJson fromEntity(AuthUserEntity entity) {
        return new AuthUserJson(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.isEnabled(),
                entity.isAccount_non_expired(),
                entity.isAccount_non_locked(),
                entity.isCredentials_non_expired()
        );
    }
}
