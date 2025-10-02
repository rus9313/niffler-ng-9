package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.UserDataUserDao;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.dao.impl.UserDataUserJdbc;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.Databases.transaction;

public class UserDataDbClient {
    private static final Config CFG = Config.getInstance();

    public UserEntity createUser(UserEntity user) {
        return transaction(connection -> {
                    return new UserDataUserJdbc(connection).createUser(user);
                }, CFG.userdataJdbcUrl()
        );
    }

    public Optional<UserEntity> findById(String id) {
        return transaction(connection -> {
                    return new UserDataUserJdbc(connection).findById(UUID.fromString(id));
                }, CFG.userdataJdbcUrl()
        );
    }

    public Optional<UserEntity> findByUserName(String name) {
        return transaction(connection -> {
                    return new UserDataUserJdbc(connection).findByUserName(name);
                }, CFG.userdataJdbcUrl()
        );
    }

    public void delete(UserEntity entity) {
        transaction(connection -> {
            new UserDataUserJdbc(connection).delete(entity);
        }, CFG.spendJdbcUrl());
    }
}
