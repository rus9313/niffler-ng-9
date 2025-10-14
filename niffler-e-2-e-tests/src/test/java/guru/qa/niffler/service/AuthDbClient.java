package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.impl.AuthUserDaoJdbc;
import guru.qa.niffler.data.entity.auth.AuthUserEntity;
import guru.qa.niffler.model.auth.AuthUserJson;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.Databases.transaction;

public class AuthDbClient {
    private static final Config CFG = Config.getInstance();

    public AuthUserJson createUser(AuthUserEntity user, int isolationLevel) {
        return transaction(connection -> {
                    AuthUserDaoJdbc daoJdbc = new AuthUserDaoJdbc(connection);
                    return AuthUserJson.fromEntity(daoJdbc.createUser(user));
                },
                CFG.authJdbcUrl(),
                isolationLevel
        );
    }
    public Optional<AuthUserEntity> findUserById(UUID id, int isolationLevel) {
        return transaction(connection -> {
            return new AuthUserDaoJdbc(connection).findById(id);
                }, CFG.authJdbcUrl(),
                isolationLevel
        );
    }

    public Optional<AuthUserEntity> findAllByUserName(String userName,  int isolationLevel) {
        return transaction(connection -> {
            return new AuthUserDaoJdbc(connection).findByUserName(userName);
        }, CFG.authJdbcUrl(),
                isolationLevel
        );
    }

    public void deleteUser(AuthUserEntity user, int isolationLevel) {
        transaction(connection -> {
            new AuthUserDaoJdbc(connection).delete(user);
        }, CFG.authJdbcUrl(),isolationLevel);
    }
}
