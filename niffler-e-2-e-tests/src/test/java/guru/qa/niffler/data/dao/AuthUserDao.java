package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.auth.AuthUserEntity;
import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserDao {
    AuthUserEntity createUser(AuthUserEntity userEntity);

    Optional<AuthUserEntity> findById(UUID id);
}
