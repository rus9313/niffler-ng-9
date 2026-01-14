package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage<T extends BasePage<?>> {

    protected final SelenideElement alert = $(".MuiSnackbar-root");
    protected static final Config CFG = Config.getInstance();

    @SuppressWarnings("unchecked")
    public T checkAlert(String text) {
        alert.shouldHave(text(text));
        return (T) this;
    }
}