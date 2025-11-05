package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.entity.category.CategoryEntity;
import guru.qa.niffler.model.CategoryJson;

import java.util.List;
import java.util.Optional;

import static guru.qa.niffler.data.Databases.transaction;

public class CategoryDbClient {
    private static final Config CFG = Config.getInstance();

    public CategoryJson createCategory(CategoryJson categoryJson) {
        return transaction(connection -> {
                    CategoryEntity category = CategoryEntity.fromJson(categoryJson);
                    return CategoryJson.fromEntity(new CategoryDaoJdbc().create(category));
                }, CFG.spendJdbcUrl()
        );
    }

    public Optional<CategoryJson> findCategoryByUserNameAndCategoryName(String userName, String categoryName) {
        return transaction(connection -> {
                    Optional<CategoryEntity> se = new CategoryDaoJdbc().findCategoryByUserNameAndCategoryName(userName, categoryName);
                    return se.map(CategoryJson::fromEntity);
                }, CFG.spendJdbcUrl()
        );
    }

    public List<CategoryEntity> findAllByUserName(String userName) {
        return transaction(connection -> {
                    return new CategoryDaoJdbc().findAllByUserName(userName);
                }, CFG.spendJdbcUrl()
        );
    }

    public void deleteCategory(CategoryEntity category) {
        transaction(connection -> {
                    new CategoryDaoJdbc().deleteCategory(category);
                }, CFG.spendJdbcUrl()
        );
    }

}
