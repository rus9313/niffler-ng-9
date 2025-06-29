package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class ProfileTest {
    private static final Config CFG = Config.getInstance();
    private static  final String password = "rus";
    private static  final String userName = "rus";

    @Category(username = userName, archived = false)
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

    @Category(username = userName, archived = true)
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
