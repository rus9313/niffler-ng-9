package guru.qa.niffler.service;

import guru.qa.niffler.data.dao.UserDataUserDao;
import guru.qa.niffler.data.dao.impl.UserDataUserJdbc;
import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.Optional;
import java.util.UUID;

public class UserDataDbClient {
    private UserDataUserDao userDao = new UserDataUserJdbc();

    public UserEntity createUser(UserEntity user) {
        return userDao.createUser(user);
    }

    public Optional<UserEntity> findById(String id) {
        return userDao.findById(UUID.fromString(id));
    }

    public Optional<UserEntity> findByUserName(String name) {
        return userDao.findByUserName(name);
    }

    public void delete(UserEntity entity) {
         userDao.delete(entity);
    }
}
