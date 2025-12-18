package guru.qa.niffler.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Date;

@ParametersAreNonnullByDefault
public class Calendar extends BaseComponent<Calendar>{

    public Calendar(SelenideElement self) {
        super(self);
    }

    @Step("Выбираем дату в календаре '{0}'")
    @Nonnull
    public Calendar selectDateInCalendar(Date date) {
        long timestamp = date.getTime();
        self.$("button[data-timestamp=" + timestamp + "]").click();
        return this;
    }
}