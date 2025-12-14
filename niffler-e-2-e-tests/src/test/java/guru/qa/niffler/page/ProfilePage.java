package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.Header;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@ParametersAreNonnullByDefault
public class ProfilePage extends BasePage<ProfilePage>{
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

    private final Header header = new Header();

    @Step("Уставливаем имя '{0}' в поле name ")
    @Nonnull
    public ProfilePage setUserName(String userName) {
        nameInput.setValue(userName);
        return this;
    }

    @Step("Сохраняем изменения")
    @Nonnull
    public ProfilePage saveChanges() {
        saveChangesButton.click();
        return this;
    }

    @Step("Добавляем новую категорию '{0}'")
    @Nonnull
    public ProfilePage addNewCategory(String categoryName) {
        category.setValue(categoryName).pressEnter();
        return this;
    }

    @Step("Проверяем, что активная категория '{0}' есть ")
    @Nonnull
    public ProfilePage checkThatActiveCategoryIsPresented(String name) {
        activeCategoryRows.find(text(name)).shouldBe(visible);
        return this;
    }

    @Step("Проверяем, что архивировная  категория '{0}' есть ")
    @Nonnull
    public ProfilePage checkThatArchivedCategoryIsPresented(String name) {
        archivedCategoryRows.find(text(name)).shouldBe(visible);
        return this;
    }

    @Step("Кликнуть на показать архивированые категории")
    @Nonnull
    public ProfilePage showArchivedCategories() {
        showArchivedToggle.click();
        return this;
    }

    @Step("Архивировать категорию")
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

    @Step("Разархивировать категорию")
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

    @Step("Перейти на главную страницу кликом на Niffler")
    @Nonnull
    public MainPage goToMain() {
        return header.toMainPage();
    }

    @Step("Проверить, что поле username содержит значение '{0}'")
    public void checkProfileName(String userName) {
        nameInput.shouldHave(value(userName));
    }
}
