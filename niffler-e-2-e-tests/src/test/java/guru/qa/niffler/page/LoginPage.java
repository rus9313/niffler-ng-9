package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@ParametersAreNonnullByDefault
public class LoginPage {
  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitButton = $("button[type='submit']");
  private final SelenideElement newAccount = $x("//*[@id=\"register-button\"]");
  private final SelenideElement error = $(".form__error");

  @Step("На странице логина ввести имя '{0}' и пароль '{1}'")
  @Nonnull
  public MainPage login(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
    return new MainPage();
  }

  @Step("На странице логина кликнуть на кнопку create new account")
  @Nonnull
  public RegisterPage createNewAccount() {
    newAccount.click();
    return new RegisterPage();
  }

  @Step("Проверяем сообщение об ошибке")
  public void checkMessageError() {
    error.should(visible);
  }
}
