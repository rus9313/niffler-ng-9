package guru.qa.niffler.service.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.SpendDao;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.tpl.JdbcTransactionTemplate;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.service.SpendClient;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@ParametersAreNonnullByDefault
public class SpendDbClient implements SpendClient {

    private static final Config CFG = Config.getInstance();

    private final CategoryDao categoryDao = new CategoryDaoJdbc();
    private final SpendDao spendDao = new SpendDaoJdbc();

    private final JdbcTransactionTemplate jdbcTxTemplate = new JdbcTransactionTemplate(
            CFG.spendJdbcUrl()
    );

    @Step("Создаем spending в таблице spend")
    @Override
    @Nonnull
    public SpendJson createSpend(SpendJson spend) {
        return requireNonNull(jdbcTxTemplate.execute(() -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    if (spendEntity.getCategory().getId() == null) {
                        CategoryEntity categoryEntity = categoryDao
                                .create(spendEntity.getCategory());
                        spendEntity.setCategory(categoryEntity);
                    }
                    return SpendJson.fromEntity(spendDao.create(spendEntity));
                }
        ));
    }

    @Step("Обновляем spending в таблице spend")
    @Override
    @Nonnull
    public SpendJson update(SpendJson spend) {
        return requireNonNull(jdbcTxTemplate.execute(() -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    if (spendEntity.getCategory().getId() == null) {
                        CategoryEntity categoryEntity = categoryDao
                                .create(spendEntity.getCategory());
                        spendEntity.setCategory(categoryEntity);
                    }
                    return SpendJson.fromEntity(spendDao.update(spendEntity));
                }
        ));
    }

    @Step("Ищем spending по id в таблице spend")
    @Override
    @Nonnull
    public Optional<SpendJson> findSpendById(UUID id) {
        return requireNonNull(jdbcTxTemplate.execute(() -> {
                    Optional<SpendEntity> se = new SpendDaoJdbc().findSpendById(id);
                    return se.map(SpendJson::fromEntity);
                }
        ));
    }

    @Step("Ищем все spending по username в таблице spend")
    @Override
    @Nonnull
    public List<SpendJson> findAllByUserName(String userName) {
        return requireNonNull(jdbcTxTemplate.execute(() -> {
                    List<SpendEntity> entityList = spendDao.findAllByUserName(userName);
                    return entityList.stream()
                            .map(SpendJson::fromEntity)
                            .toList();
                }
        ));
    }

    @Step("Удаляем spending в таблице spend")
    @Override
    public void removeSpend(SpendJson spend) {
        SpendEntity spendEntity = SpendEntity.fromJson(spend);
        spendDao.deleteSpend(spendEntity);
    }

    @Step("Создаем category в таблице category")
    @Override
    @Nonnull
    public CategoryJson createCategory(CategoryJson category) {
        return requireNonNull(jdbcTxTemplate.execute(() -> {
                    CategoryEntity ce = CategoryEntity.fromJson(category);
                    return CategoryJson.fromEntity(categoryDao.create(ce));
                }
        ));
    }

    @Step("Удаляем category из таблицы category")
    @Override
    public void removeCategory(CategoryJson category) {
        categoryDao.deleteCategory(CategoryEntity.fromJson(category));
    }
}