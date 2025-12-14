package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.Header;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@ParametersAreNonnullByDefault
public class MainPage extends BasePage<MainPage>{
    private final ElementsCollection tableRows = $$("tbody tr");
    private final SelenideElement statistics = $x("//h2[text()='Statistics']");
    private final SelenideElement historyOfSpendings = $x("//h2[text()='History of Spendings']");
    private final SelenideElement profileBtn = $("button div.MuiAvatar-root");
    private final SelenideElement profileMenuItem = $("a[href='/profile']");
    private final SelenideElement friendsItem = $("a[href='/people/friends']");
    private final SelenideElement allPeopleItem = $("a[href='/people/all']");
    private final SelenideElement search = $("input[placeholder='Search']");

    private final Header header = new Header();

    @Step("Редактируем spending")
    @Nonnull
    public EditSpendingPage editSpending(String spendingDescription) {
        search(spendingDescription);
        tableRows.find(text(spendingDescription)).$$("td").get(5).click();
        return new EditSpendingPage();
    }

    @Step("Добавляем новый spending")
    @Nonnull
    public EditSpendingPage addNewSpending() {
        return header.addSpending();
    }

    @Step("Проверяем,что страница содержит spending '{0}'")
    public void checkThatTableContainsSpending(String... spendingDescriptions) {
        for (String description : spendingDescriptions) {
            search(description);
            tableRows.find(text(description)).should(visible);
        }
    }

    @Step("Проверяем поля на странице profile")
    public void checkFieldsAtPage() {
        statistics.should(visible);
        historyOfSpendings.should(visible);
    }

    @Step("Открываем страницу profile")
    @Nonnull
    public ProfilePage openProfilePage() {
        profileBtn.click();
        profileMenuItem.click();
        return new ProfilePage();
    }

    @Step("Открываем страницу friends")
    @Nonnull
    public FriendsPage openFriendsPage() {
        profileBtn.click();
        friendsItem.click();
        return new FriendsPage();
    }

    @Step("Открываем страницу All people")
    @Nonnull
    public FriendsPage openAllPeoplePage() {
        profileBtn.click();
        allPeopleItem.click();
        return new FriendsPage();
    }

    public void search(String name) {
        search.shouldBe(visible).clear();
        search.sendKeys(name);
        search.pressEnter();
    }
}
