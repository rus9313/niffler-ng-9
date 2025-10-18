package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.auth.AuthAuthorityEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuthAuthorityDao {
    AuthAuthorityEntity createUser(AuthAuthorityEntity userEntity);

    Optional<AuthAuthorityEntity> findById(UUID id);

    Optional<AuthAuthorityEntity> findByUserId(String userName);

    void delete(AuthAuthorityEntity entity);
}
