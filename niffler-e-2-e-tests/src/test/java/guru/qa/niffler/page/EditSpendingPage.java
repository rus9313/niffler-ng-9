package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.CurrencyValues;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class EditSpendingPage extends BasePage<EditSpendingPage>{
    private final SelenideElement amountInput = $("#amount");
    private final SelenideElement descriptionInput = $("#description");
    private final SelenideElement categoryInput = $("#category");
    private final SelenideElement submitButton = $("#save");
    private final SelenideElement inputAmount = $("#amount");
    private final SelenideElement currencyInput = $("#currency");

    @Step("Вводим значение для поля описание spending")
    @Nonnull
    public EditSpendingPage setNewSpendingDescription(String description) {
        descriptionInput.clear();
        descriptionInput.setValue(description);
        return this;
    }

    @Step("Вводим значение для поля описание категории")
    @Nonnull
    public EditSpendingPage setNewCategoryDescription(String description) {
        categoryInput.clear();
        categoryInput.setValue(description);
        return this;
    }

    @Step("Сохраняем созданный spending")
    @Nonnull
    public MainPage save() {
        submitButton.click();
        return new MainPage();
    }

    @Step("Устанавливаем значение для поля суммы")
    @Nonnull
    public EditSpendingPage setNewSpendingAmount(double amount) {
        inputAmount.setValue(String.valueOf(amount));
        return this;
    }

    @Step("Устанавливаем значение для поля currency")
    @Nonnull
    public EditSpendingPage setSpendingCurrency(CurrencyValues currency) {
        inputAmount.setValue(String.valueOf(currency));
        return this;
    }
}
