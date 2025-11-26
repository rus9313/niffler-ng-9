package guru.qa.niffler.service.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.SpendDao;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.tpl.JdbcTransactionTemplate;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.service.SpendClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpendDbClient implements SpendClient {

    private static final Config CFG = Config.getInstance();

    private final CategoryDao categoryDao = new CategoryDaoJdbc();
    private final SpendDao spendDao = new SpendDaoJdbc();

    private final JdbcTransactionTemplate jdbcTxTemplate = new JdbcTransactionTemplate(
            CFG.spendJdbcUrl()
    );

    @Override
    public SpendJson createSpend(SpendJson spend) {
        return jdbcTxTemplate.execute(() -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    if (spendEntity.getCategory().getId() == null) {
                        CategoryEntity categoryEntity = categoryDao
                                .create(spendEntity.getCategory());
                        spendEntity.setCategory(categoryEntity);
                    }
                    return SpendJson.fromEntity(spendDao.create(spendEntity));
                }
        );
    }

    @Override
    public Optional<SpendJson> findSpendById(UUID id) {
        return jdbcTxTemplate.execute(() -> {
                    Optional<SpendEntity> se = new SpendDaoJdbc().findSpendById(id);
                    return se.map(SpendJson::fromEntity);
                }
        );
    }

    @Override
    public List<SpendEntity> findAllByUserName(String userName) {
        return jdbcTxTemplate.execute(() -> {
                    return new SpendDaoJdbc().findAllByUserName(userName);
                }
        );
    }

    @Override
    public void removeSpend(SpendJson spend) {
        SpendEntity spendEntity = SpendEntity.fromJson(spend);
        new SpendDaoJdbc().deleteSpend(spendEntity);
    }
}