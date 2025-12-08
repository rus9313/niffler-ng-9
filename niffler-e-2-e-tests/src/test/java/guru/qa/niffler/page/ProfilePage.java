package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@ParametersAreNonnullByDefault
public class ProfilePage {
    private final SelenideElement nameInput = $("input[id='name']");
    private final SelenideElement usernameInput = $("input[id='username']");
    private final SelenideElement saveChangesButton = $("button[type=submit]");
    private final SelenideElement category = $("#category");

    private final SelenideElement profileBtn = $("button div.MuiAvatar-root");
    private final SelenideElement profileHeader = $(By.xpath("//h2[text()='Profile']"));
    private final SelenideElement profileImg = $("div.MuiContainer-root img]");
    private final SelenideElement uploadImgBtn = $("span[role='button']");

    private final SelenideElement categoriesHeader = $(By.xpath("//h2[text()='Categories']"));
    private final SelenideElement showArchivedToggle = $("input[type='checkbox']");
    private final ElementsCollection activeCategoryRows =
            $$(By.xpath("//div[contains(@class, 'MuiGrid-item')][.//button[@aria-label = 'Archive category']]"));
    private final ElementsCollection archivedCategoryRows =
            $$(By.xpath("//div[contains(@class, 'MuiGrid-item')][.//button[@aria-label = 'Unarchive category']]"));
    private final By archiveBtn = By.cssSelector("button[aria-label='Archive category']");
    private final By unArchiveBtn = By.cssSelector("button[aria-label='Unarchive category']");

    private final SelenideElement popUpArchiveBtn = $(By.xpath("//button[text() = 'Archive']"));
    private final SelenideElement popUpUnArchiveBtn = $(By.xpath("//button[text() = 'Unarchive']"));

    @Nonnull
    public ProfilePage setUserName(String userName) {
        nameInput.setValue(userName);
        return this;
    }

    @Nonnull
    public ProfilePage saveChanges() {
        saveChangesButton.click();
        return this;
    }

    @Nonnull
    public ProfilePage addNewCategory(String categoryName) {
        category.setValue(categoryName).pressEnter();
        return this;
    }

    @Nonnull
    public ProfilePage checkThatActiveCategoryIsPresented(String name) {
        activeCategoryRows.find(text(name)).shouldBe(visible);
        return this;
    }

    @Nonnull
    public ProfilePage checkThatArchivedCategoryIsPresented(String name) {
        archivedCategoryRows.find(text(name)).shouldBe(visible);
        return this;
    }

    @Nonnull
    public ProfilePage showArchivedCategories() {
        showArchivedToggle.click();
        return this;
    }

    @Nonnull
    public ProfilePage archiveCategory(String name) {
        activeCategoryRows
                .find(text(name))
                .shouldBe(visible)
                .findElement(archiveBtn)
                .click();
        popUpArchiveBtn.shouldBe(visible).click();
        return this;
    }

    @Nonnull
    public ProfilePage unArchiveCategory(String name) {
        archivedCategoryRows
                .find(text(name))
                .shouldBe(visible)
                .findElement(unArchiveBtn)
                .click();
        popUpUnArchiveBtn.shouldBe(visible).click();
        return this;
    }
}
