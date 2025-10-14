package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.impl.AuthAuthorityDaoJdbc;
import guru.qa.niffler.data.entity.auth.AuthAuthorityEntity;
import guru.qa.niffler.model.auth.AuthorityJson;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.Databases.transaction;

public class AuthAuthorityDbClient {
    private static final Config CFG = Config.getInstance();

    public AuthorityJson createUser(AuthAuthorityEntity user, int isolationLevel) {
        return transaction(connection -> {
                    AuthAuthorityDaoJdbc daoJdbc = new AuthAuthorityDaoJdbc(connection);
                    return AuthorityJson.fromEntity(daoJdbc.createUser(user));
                },
                CFG.authJdbcUrl(),
                isolationLevel

        );
    }

    public Optional<AuthAuthorityEntity> findById(UUID id, int isolationLevel) {
        return transaction(connection -> {
                    return new AuthAuthorityDaoJdbc(connection).findById(id);
                }, CFG.authJdbcUrl(),
                isolationLevel
        );
    }

    public Optional<AuthAuthorityEntity> findByUserId(String userId, int isolationLevel) {
        return transaction(connection -> {
            return new AuthAuthorityDaoJdbc(connection).findByUserId(userId);
        }, CFG.authJdbcUrl(), isolationLevel);
    }

    public void deleteUser(AuthAuthorityEntity user, int isolationLevel) {
        transaction(connection -> {
            new AuthAuthorityDaoJdbc(connection).delete(user);
        }, CFG.authJdbcUrl(), isolationLevel);
    }
}
