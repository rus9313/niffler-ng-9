package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class RegisterPage {
    private final SelenideElement usernameInput = $("input[id='username']");
    private final SelenideElement passwordInput = $("input[id='password']");
    private final SelenideElement submitButton = $("input[id='passwordSubmit']");
    private final SelenideElement signUp = $("button[type='submit']");
    private final SelenideElement signIn = $x("//a[text()='Sign in']");
    private final SelenideElement error = $(".form__error");

    public RegisterPage setUserName(String userName) {
        usernameInput.setValue(userName);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String password) {
        submitButton.setValue(password);
        return this;
    }

    public RegisterPage signUp() {
        signUp.click();
        return this;
    }

    public LoginPage signIn() {
        signIn.click();
        return new LoginPage();
    }

    public void checkMessageError() {
        error.should(visible);
    }
}
