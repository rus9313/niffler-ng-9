package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.impl.AuthAuthorityDaoJdbc;
import guru.qa.niffler.data.entity.auth.AuthAuthorityEntity;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.Databases.transaction;

public class AuthAuthorityDbClient {
    private static final Config CFG = Config.getInstance();

    public void createUser(AuthAuthorityEntity user) {
        transaction(connection -> {
                    AuthAuthorityDaoJdbc daoJdbc = new AuthAuthorityDaoJdbc(connection);
                    daoJdbc.create(user);
                },
                CFG.authJdbcUrl()

        );
    }
}
