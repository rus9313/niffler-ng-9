package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.utils.RandomDataUtils;
import org.junit.jupiter.api.Test;

@WebTest
public class SpendingTest {

    private static final Config CFG = Config.getInstance();

    @User(
            spendings = @Spending(
            amount = 89990.00,
            description = "Advanced 9 поток!",
            category = "Обучение"))
    @Test
    void categoryDescriptionShouldBeChangedFromTable(UserJson user) {
        final String newDescription = "Обучение Niffler Next Generation";
        final SpendJson spend = user.testData().spendings().getFirst();

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .editSpending(spend.description())
                .setNewSpendingDescription(newDescription)
                .save();

        new MainPage().checkThatTableContainsSpending(newDescription);
    }

    @User()
    @Test
    void addNewSpendingTest(UserJson user) {
        String description = RandomDataUtils.randomSentence(3);

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .addNewSpending()
                .setNewCategoryDescription(RandomDataUtils.randomCategoryName())
                .setNewSpendingAmount(50.0)
                .setSpendingCurrency(CurrencyValues.USD)
                .setNewSpendingDescription(description)
                .save();

        new MainPage().checkThatTableContainsSpending(description);
    }
}
