package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

@WebTest
public class ProfileTest {
    private static final Config CFG = Config.getInstance();
    private static final String password = "rus";
    private static final String userName = "rus";

    @User(
            username = userName,
            categories = @Category(archived = false)
    )
    @Test
    void archivedCategoryShouldPresentInCategoryList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(userName, password)
                .openProfilePage()
                .checkThatActiveCategoryIsPresented(category.name())
                .archiveCategory(category.name())
                .showArchivedCategories()
                .checkThatArchivedCategoryIsPresented(category.name());
    }

    @User(
            username = userName,
            categories = @Category(archived = true)
    )
    @Test
    public void activeCategoryShouldPresentInCategoriesList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(userName, password)
                .openProfilePage()
                .showArchivedCategories()
                .checkThatArchivedCategoryIsPresented(category.name())
                .unArchiveCategory(category.name())
                .checkThatActiveCategoryIsPresented(category.name());
    }
}
