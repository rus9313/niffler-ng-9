package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.category.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryDao {
    CategoryEntity create(CategoryEntity category);
    Optional<CategoryEntity> findCategoryById(UUID id);
    Optional<CategoryEntity> findCategoryByUserNameAndCategoryName(String userName, String categoryName);
    List<CategoryEntity> findAllByUserName(String userName);
    void deleteCategory(CategoryEntity category);
    List<CategoryEntity> findAll();
}