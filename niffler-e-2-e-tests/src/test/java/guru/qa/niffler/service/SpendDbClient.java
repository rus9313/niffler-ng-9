package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.entity.category.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.SpendJson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.Databases.transaction;

public class SpendDbClient {

    private static final Config CFG = Config.getInstance();

    public SpendJson createSpend(SpendJson spend, int isolationLevel) {
        return transaction(connection -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    if (spendEntity.getCategory().getId() == null) {
                        CategoryEntity categoryEntity = new CategoryDaoJdbc(connection)
                                .create(spendEntity.getCategory());
                        spendEntity.setCategory(categoryEntity);
                    }
                    return SpendJson.fromEntity(
                            new SpendDaoJdbc(connection).create(spendEntity)
                    );
                },
                CFG.spendJdbcUrl(),
                isolationLevel
        );
    }

    public Optional<SpendJson> findSpendById(UUID id, int isolationLevel) {
        return transaction(connection -> {
                    Optional<SpendEntity> se = new SpendDaoJdbc(connection).findSpendById(id);
                    return se.map(SpendJson::fromEntity);

                }, CFG.spendJdbcUrl(),
                isolationLevel
        );
    }

    public List<SpendEntity> findAllByUserName(String userName, int isolationLevel) {
        return transaction(connection -> {
                    return new SpendDaoJdbc(connection).findAllByUserName(userName);
                }, CFG.spendJdbcUrl(),
                isolationLevel
        );
    }

    public void deleteSpend(SpendJson spend, int isolationLevel) {
        transaction(connection -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    new SpendDaoJdbc(connection).deleteSpend(spendEntity);
                }, CFG.spendJdbcUrl(),
                isolationLevel);
    }
}