package guru.qa.niffler.data.dao.impl;

import guru.qa.niffler.data.dao.UserDataUserDao;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import guru.qa.niffler.model.CurrencyValues;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDataUserJdbc implements UserDataUserDao {
    private final Connection connection;

    public UserDataUserJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO \"user\" (username, currency, firstname, surname, photo, photo_small, full_name) " +
                        "VALUES ( ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setString(1, userEntity.getUsername());
            ps.setString(2, userEntity.getCurrency().name());
            ps.setString(3, userEntity.getFirstname());
            ps.setString(4, userEntity.getSurname());
            ps.setObject(5, userEntity.getPhoto());
            ps.setObject(6, userEntity.getPhotoSmall());
            ps.setString(7, userEntity.getFullname());

            ps.executeUpdate();

            final UUID generatedKey;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedKey = rs.getObject("id", UUID.class);
                } else {
                    throw new SQLException("Can`t find id in ResultSet");
                }
            }
            userEntity.setId(generatedKey);
            return userEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM \"user\" WHERE id = ?"
        )) {
            ps.setObject(1, id);
            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    UserEntity ue = new UserEntity();
                    ue.setId(rs.getObject("id", UUID.class));
                    ue.setUsername(rs.getString("username"));
                    ue.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
                    ue.setFirstname(rs.getString("firstname"));
                    ue.setSurname(rs.getString("surname"));
                    return Optional.of(ue);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM \"user\""
        )) {
            ps.execute();
            List<UserEntity> uelist = new ArrayList<>();
            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    UserEntity result = new UserEntity();
                    result.setId(rs.getObject("id", UUID.class));
                    result.setUsername(rs.getString("username"));
                    result.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
                    result.setFirstname(rs.getString("firstname"));
                    result.setSurname(rs.getString("surname"));
                    result.setFullname(rs.getString("full_name"));
                    result.setPhoto(rs.getBytes("photo"));
                    result.setPhotoSmall(rs.getBytes("photo_small"));
                    uelist.add(result);
                }
                return uelist;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
