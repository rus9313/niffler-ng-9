package guru.qa.niffler.service.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.tpl.JdbcTransactionTemplate;
import guru.qa.niffler.model.CategoryJson;

import java.util.List;
import java.util.Optional;


public class CategoryDbClient {
    private static final Config CFG = Config.getInstance();
    private final CategoryDao categoryDao = new CategoryDaoJdbc();


    private final JdbcTransactionTemplate jdbcTxTemplate = new JdbcTransactionTemplate(
            CFG.spendJdbcUrl()
    );

    public CategoryJson createCategory(CategoryJson categoryJson) {
        return jdbcTxTemplate.execute(() -> {
                    CategoryEntity category = CategoryEntity.fromJson(categoryJson);
                    return CategoryJson.fromEntity(categoryDao.create(category));
                }
        );
    }

    public Optional<CategoryJson> findCategoryByUserNameAndCategoryName(String userName, String categoryName) {
        return jdbcTxTemplate.execute(() -> {
                    Optional<CategoryEntity> se = categoryDao.findCategoryByUserNameAndCategoryName(userName, categoryName);
                    return se.map(CategoryJson::fromEntity);
                }
        );
    }

    public List<CategoryEntity> findAllByUserName(String userName) {
        return jdbcTxTemplate.execute(() -> {
                    return categoryDao.findAllByUserName(userName);
                }
        );
    }

    public void deleteCategory(CategoryEntity category) {
        categoryDao.deleteCategory(category);
    }

}
