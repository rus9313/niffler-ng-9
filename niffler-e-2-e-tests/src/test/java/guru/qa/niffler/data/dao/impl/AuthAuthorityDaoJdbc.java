package guru.qa.niffler.data.dao.impl;

import guru.qa.niffler.data.dao.AuthAuthorityDao;
import guru.qa.niffler.data.entity.auth.AuthAuthorityEntity;
import guru.qa.niffler.model.auth.Authority;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class AuthAuthorityDaoJdbc implements AuthAuthorityDao {

    private final Connection connection;

    public AuthAuthorityDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AuthAuthorityEntity createUser(AuthAuthorityEntity userEntity) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO \"user\" (user_id, authority) " +
                        "VALUES ( ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setObject(1, userEntity.getUserId());
            ps.setString(2, userEntity.getAuthority().name());
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
    public Optional<AuthAuthorityEntity> findById(UUID id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM authority WHERE id = ?"
        )) {
            ps.setObject(1, id);
            ps.execute();
            AuthAuthorityEntity userEntity = new AuthAuthorityEntity();
            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    userEntity.setId(rs.getObject("id", UUID.class));
                    userEntity.setUserId(rs.getObject("user_id", UUID.class));
                    userEntity.setAuthority(Authority.valueOf(rs.getString("authority")));
                    return Optional.of(userEntity);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AuthAuthorityEntity> findByUserId(String userId) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM authority WHERE user_id = ?"
        )) {
            ps.setObject(1, userId);
            ps.execute();
            AuthAuthorityEntity userEntity = new AuthAuthorityEntity();
            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    userEntity.setId(rs.getObject("id", UUID.class));
                    userEntity.setUserId(rs.getObject("user_id", UUID.class));
                    userEntity.setAuthority(Authority.valueOf(rs.getString("authority")));
                    return Optional.of(userEntity);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(AuthAuthorityEntity entity) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM authority WHERE id = ?"
        )) {
            ps.setObject(1, entity.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
