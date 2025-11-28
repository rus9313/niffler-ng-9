package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.category.CategoryApiClient;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.impl.CategoryDbClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static guru.qa.niffler.jupiter.extension.TestMethodContextExtension.context;
import static guru.qa.niffler.utils.RandomDataUtils.randomCategoryName;

public class CategoryExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);

    private final CategoryApiClient categoryApiClient = new CategoryApiClient();
    private String categoryName = randomCategoryName();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(userAnno -> {
                    if(userAnno.categories().length != 0) {
                        final @Nullable UserJson createdUser = UserExtension.createdUser();
                        final List<CategoryJson> result = new ArrayList<>();
                        for (Category categoryAnno : userAnno.categories()) {
                            CategoryJson category = new CategoryJson(
                                    null,
                                    randomCategoryName(),
                                    userAnno.username(),
                                    categoryAnno.archived()
                            );
                            CategoryJson created = categoryApiClient.createCategory(category);
                            if (categoryAnno.archived()) {
                                CategoryJson archivedCategory = new CategoryJson(
                                        created.id(),
                                        created.name(),
                                        created.username(),
                                        true
                                );
                                created = categoryApiClient.updateCategory(archivedCategory);
                            }
                            result.add(created);
                        }
                        if (createdUser != null) {
                            createdUser.testData().categories().addAll(result);
                        } else {
                            context.getStore(NAMESPACE).put(
                                    context.getUniqueId(),
                                    result.stream().toArray(CategoryJson[]::new)
                            );
                        }
                    }
                });
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        CategoryJson[] categories = createdCategory();
        if (categories != null) {
            for (CategoryJson category : categories) {
                if (category != null && !category.archived()) {
                    category = new CategoryJson(
                            category.id(),
                            category.name(),
                            category.username(),
                            true
                    );
                    categoryApiClient.updateCategory(category);
                }
            }
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson[].class);
    }

    @Override
    public CategoryJson[] resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return createdCategory();
    }

    public static CategoryJson[] createdCategory() {
        final ExtensionContext methodContext = context();
        return methodContext.getStore(NAMESPACE)
                .get(methodContext.getUniqueId(), CategoryJson[].class);
    }
}
