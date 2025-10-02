package guru.qa.niffler.data.dao.impl;

import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.SpendDao;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.CurrencyValues;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpendDaoJdbc implements SpendDao {

    private final Connection connection;

    public SpendDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public SpendEntity create(SpendEntity spend) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO spend (username, spend_date, currency, amount, description, category_id) " +
                        "VALUES ( ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setString(1, spend.getUsername());
            ps.setDate(2, spend.getSpendDate());
            ps.setString(3, spend.getCurrency().name());
            ps.setDouble(4, spend.getAmount());
            ps.setString(5, spend.getDescription());
            ps.setObject(6, spend.getCategory().getId());

            ps.executeUpdate();

            final UUID generatedKey;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedKey = rs.getObject("id", UUID.class);
                } else {
                    throw new SQLException("Can`t find id in ResultSet");
                }
            }
            spend.setId(generatedKey);
            return spend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SpendEntity> findSpendById(UUID id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM spend WHERE id = ?"
        )) {
            ps.setObject(1, id);
            ps.execute();
            CategoryDao categoryDao = new CategoryDaoJdbc(connection);
            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    SpendEntity spendEntity = new SpendEntity();
                    spendEntity.setId(rs.getObject("id", UUID.class));
                    spendEntity.setUsername(rs.getString("username"));
                    spendEntity.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
                    spendEntity.setSpendDate(rs.getDate("spend_date"));
                    spendEntity.setAmount(rs.getDouble("amount"));
                    spendEntity.setDescription(rs.getString("description"));
                    UUID uuid = rs.getObject("category_id", UUID.class);
                    spendEntity.setCategory(categoryDao.findCategoryById(uuid).get());
                    return Optional.of(spendEntity);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SpendEntity> findAllByUserName(String userName) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM spend WHERE userName = ?"
        )) {
            ps.setString(1, userName);
            ps.execute();
            List<SpendEntity> spendEntityList = new ArrayList<>();
            CategoryDao categoryDao = new CategoryDaoJdbc(connection);
            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    SpendEntity spendEntity = new SpendEntity();
                    spendEntity.setId(rs.getObject("id", UUID.class));
                    spendEntity.setUsername(rs.getString("username"));
                    spendEntity.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
                    spendEntity.setSpendDate(rs.getDate("spend_date"));
                    spendEntity.setAmount(rs.getDouble("amount"));
                    spendEntity.setDescription(rs.getString("description"));
                    UUID uuid = rs.getObject("category_id", UUID.class);
                    spendEntity.setCategory(categoryDao.findCategoryById(uuid).get());
                    spendEntityList.add(spendEntity);
                }
                return spendEntityList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSpend(SpendEntity spend) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM spend WHERE id = ?"
        )) {
            ps.setObject(1, spend.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}