package guru.qa.niffler.page.component;

import guru.qa.niffler.page.EditSpendingPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class SpendingTable extends BaseComponent<SpendingTable>{
    private final By deleteButton = By.id("delete");
    private final By periodButton = By.id("period");
    private final By deleteButtonAlert = By.id("delete");
    private final By searchInput = By.cssSelector("input[aria-label=search]");

    public SpendingTable() {
        super($("#spendings"));
    }

    @Step("Кликаем кнопку периода и выбираем '{0}'")
    @Nonnull
    public SpendingTable selectPeriod(DataFiltersValue period) {
        self.$(periodButton).shouldBe(visible).click();
        self.$("li[data-value="+period+"]").shouldBe(visible).click();
        return this;
    }

    @Step("В таблице расходов найти расход в описанием '{0}' и нажать на иконку карандашика")
    @Nonnull
    public EditSpendingPage editSpending(String description) {
        self.$$("tbody tr").find(text(description))
                .$$("td")
                .get(5)
                .click();
        return new EditSpendingPage();
    }

    @Step("Удаляем spending c описанием '{0}' из списка")
    @Nonnull
    public SpendingTable deleteSpending(String description) {
        self.$$("tbody tr").find(text(description))
                .$$("td")
                .get(0)
                .click();
        $(deleteButton).click();
        $(deleteButtonAlert).shouldBe(visible).click();
        return this;
    }

    @Step("В таблице расходов найти через поиск расход '{0}'")
    @Nonnull
    public SpendingTable searchSpendingByDescription(String description) {
        self.$(searchInput).should(visible).setValue(description).pressEnter();
        self.$$("tbody tr")
                .findBy(text(description))
                .should(visible);
        return this;
    }

    @Step("Проверить, что таблица расходов содержит '{0}' расходов")
    @Nonnull
    public SpendingTable checkTableContains(String... expectedSpends) {
        for (String spends : expectedSpends) {
            searchSpendingByDescription(spends);
        }
        return this;
    }

    @Step("Проверить, что таблица расходов содержит '{0}' расходов")
    @Nonnull
    public SpendingTable checkTableSize(int expectedSize) {
        self.$("tbody").$$("tr").shouldHave(size(expectedSize));
        return this;
    }


}
