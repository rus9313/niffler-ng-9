package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@ParametersAreNonnullByDefault
public class RegisterPage extends BasePage<RegisterPage>{
    private final SelenideElement usernameInput = $("input[id='username']");
    private final SelenideElement passwordInput = $("input[id='password']");
    private final SelenideElement submitButton = $("input[id='passwordSubmit']");
    private final SelenideElement signUp = $("button[type='submit']");
    private final SelenideElement signIn = $x("//a[text()='Sign in']");
    private final SelenideElement error = $(".form__error");

    @Step("Уставливаем имя пользователя")
    @Nonnull
    public RegisterPage setUserName(String userName) {
        usernameInput.setValue(userName);
        return this;
    }

    @Step("Уставливаем имя пароль")
    @Nonnull
    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Подтверждаем пароль")
    @Nonnull
    public RegisterPage setPasswordSubmit(String password) {
        submitButton.setValue(password);
        return this;
    }

    @Step("Кликаем signUp")
    @Nonnull
    public RegisterPage signUp() {
        signUp.click();
        return this;
    }

    @Step("Кликаем signIn")
    @Nonnull
    public LoginPage signIn() {
        signIn.click();
        return new LoginPage();
    }

    @Step("Проверяем, что сообщение об ошибке есть на странице")
    public void checkMessageError() {
        error.should(visible);
    }
}
