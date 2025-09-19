package guru.qa.niffler.service;

import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.entity.category.CategoryEntity;
import guru.qa.niffler.model.CategoryJson;

import java.util.List;
import java.util.Optional;

public class CategoryDbClient {
    private final CategoryDao categoryDao = new CategoryDaoJdbc();

    public CategoryJson createCategory(CategoryJson categoryJson) {
        CategoryEntity category = CategoryEntity.fromJson(categoryJson);
        return CategoryJson.fromEntity(categoryDao.create(category));
    }

    public Optional<CategoryJson> findCategoryByUserNameAndCategoryName(String userName, String categoryName) {
        Optional<CategoryEntity> se = categoryDao.findCategoryByUserNameAndCategoryName(userName, categoryName);
        return se.map(CategoryJson::fromEntity);
    }

    public List<CategoryEntity> findAllByUserName(String userName) {
        return categoryDao.findAllByUserName(userName);
    }

    public void deleteCategory(CategoryEntity category) {
        categoryDao.deleteCategory(category);
    }

}
