package guru.qa.niffler.service;

import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.SpendDao;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.entity.category.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.SpendJson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpendDbClient {

    private final SpendDao spendDao = new SpendDaoJdbc();
    private final CategoryDao categoryDao = new CategoryDaoJdbc();

    public SpendJson createSpend(SpendJson spend) {
        SpendEntity spendEntity = SpendEntity.fromJson(spend);
        CategoryEntity categoryEntity = spendEntity.getCategory();
        if (categoryEntity.getId() == null) {
            categoryDao.findCategoryByUserNameAndCategoryName(
                    categoryEntity.getUsername(), categoryEntity.getName())
                    .ifPresentOrElse(
                            ce -> spendEntity.setCategory(ce),
                            () -> spendEntity.setCategory(categoryDao.create(categoryEntity))
                    );
        }
        return SpendJson.fromEntity(spendDao.create(spendEntity));
    }

    public Optional<SpendJson> findSpendById(UUID id) {
        Optional<SpendEntity> se = spendDao.findSpendById(id);
        return se.map(SpendJson::fromEntity);
    }

    public List<SpendEntity> findAllByUserName(String userName) {
        return spendDao.findAllByUserName(userName);
    }

    public void deleteSpend(SpendJson spend) {
        SpendEntity spendEntity = SpendEntity.fromJson(spend);
        spendDao.deleteSpend(spendEntity);
    }
}