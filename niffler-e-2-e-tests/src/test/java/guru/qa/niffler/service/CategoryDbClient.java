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

    public CategoryJson createCategory(CategoryJson categoryJson, int isolationLevel) {
        return transaction(connection -> {
                    CategoryEntity category = CategoryEntity.fromJson(categoryJson);
                    return CategoryJson.fromEntity(new CategoryDaoJdbc(connection).create(category));
                }, CFG.spendJdbcUrl(),
                isolationLevel
        );
    }

    public Optional<CategoryJson> findCategoryByUserNameAndCategoryName(String userName, String categoryName, int isolationLevel) {
        return transaction(connection -> {
                    Optional<CategoryEntity> se = new CategoryDaoJdbc(connection).findCategoryByUserNameAndCategoryName(userName, categoryName);
                    return se.map(CategoryJson::fromEntity);
                }, CFG.spendJdbcUrl(),
                isolationLevel
        );
    }

    public List<CategoryEntity> findAllByUserName(String userName, int isolationLevel) {
        return transaction(connection -> {
                    return new CategoryDaoJdbc(connection).findAllByUserName(userName);
                }, CFG.spendJdbcUrl(),
                isolationLevel
        );
    }

    public void deleteCategory(CategoryEntity category, int isolationLevel) {
        transaction(connection -> {
                    new CategoryDaoJdbc(connection).deleteCategory(category);
                }, CFG.spendJdbcUrl(),
                isolationLevel
        );
    }

}
