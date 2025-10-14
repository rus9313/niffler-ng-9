package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.category.CategoryApiClient;
import guru.qa.niffler.data.entity.category.CategoryEntity;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.service.CategoryDbClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import static guru.qa.niffler.jupiter.extension.TestMethodContextExtension.context;
import static guru.qa.niffler.utils.RandomDataUtils.randomCategoryName;
import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class CategoryExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);

    private final CategoryApiClient categoryApiClient = new CategoryApiClient();
    private final CategoryDbClient categoryDbClient = new CategoryDbClient();
    private String categoryName = randomCategoryName();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(user -> {
                    if(user.categories().length != 0) {
                        Category anno = user.categories()[0];
                        CategoryJson category = new CategoryJson(
                                null,
                                categoryName,
                                user.username(),
                                anno.archived()
                        );
                        CategoryJson created = categoryDbClient.createCategory(category, TRANSACTION_READ_COMMITTED);
                        context.getStore(NAMESPACE).put(context.getUniqueId(), created);
                    }
                });
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        CategoryJson created = context.getStore(CategoryExtension.NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if(created != null) {
            CategoryJson categoryArchivedJson = new CategoryJson(
                    created.id(),
                    created.name(),
                    created.username(),
                    true);
            categoryDbClient.deleteCategory(CategoryEntity.fromJson(categoryArchivedJson), TRANSACTION_READ_COMMITTED);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public CategoryJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return createdCategory();
    }

    public static CategoryJson createdCategory() {
        final ExtensionContext methodContext = context();
        return methodContext.getStore(NAMESPACE)
                .get(methodContext.getUniqueId(), CategoryJson.class);
    }
}
