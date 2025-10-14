package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.service.SpendDbClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;

import static guru.qa.niffler.jupiter.extension.TestMethodContextExtension.context;
import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class SpendingExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(SpendingExtension.class);

    private final SpendDbClient spendDbClient = new SpendDbClient();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(user -> {
                            if (user.spendings().length != 0) {
                                Spending anno = user.spendings()[0];
                                SpendJson spendJson = new SpendJson(
                                        null,
                                        new Date(),
                                        new CategoryJson(
                                                null,
                                                anno.category(),
                                                user.username(),
                                                false),
                                        anno.currency(),
                                        anno.amount(),
                                        anno.description(),
                                        user.username()
                                );
                                context.getStore(NAMESPACE).put(
                                        context.getUniqueId(),
                                        spendDbClient.createSpend(spendJson, TRANSACTION_READ_COMMITTED)
                                );
                            }
                        }
                );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(SpendJson.class);
    }

    @Override
    public SpendJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return createdSpending();
    }

    public static SpendJson createdSpending() {
        final ExtensionContext methodContext = context();
        return methodContext.getStore(NAMESPACE)
                .get(methodContext.getUniqueId(), SpendJson.class);
    }
}
