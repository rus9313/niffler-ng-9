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

    public AuthUserJson createUser(AuthUserEntity user) {
        return transaction(connection -> {
                    AuthUserDaoJdbc daoJdbc = new AuthUserDaoJdbc(connection);
                    return AuthUserJson.fromEntity(daoJdbc.createUser(user));
                },
                CFG.authJdbcUrl()
        );
    }

    public Optional<AuthUserEntity> findUserById(UUID id) {
        return transaction(connection -> {
                    return new AuthUserDaoJdbc(connection).findById(id);
                }, CFG.authJdbcUrl()
        );
    }
}
