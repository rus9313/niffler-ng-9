package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ParametersAreNonnullByDefault
public class FriendsPage extends BasePage<FriendsPage>{
    private final SelenideElement textMessage = $x("//p[text()='There are no users yet']");
    private final ElementsCollection rowsFriends = $$x("//tbody[@id='friends']//tr");
    private final ElementsCollection rowsAllPeople = $$x("//tbody[@id='all']//tr");
    private final ElementsCollection rowsFriendRequest = $$x("//tbody[@id='requests']//tr");
    private final SelenideElement search = $("input[placeholder='Search']");

    @Step("Проверить, что пользователь имеет друга")
    public void userHaveFriend(String... users) {
        for (String user: users) {
            searchUser(user);
            rowsFriends.find(text(user)).shouldHave(text(user));
        }
    }

    @Step("Проверить, что пользователь имеет запрос на дружбу")
    public void userHaveFriendRequest(String... users) {
        for (String user: users) {
            searchUser(user);
            rowsFriendRequest.find(text(user)).shouldHave(text(user));
        }
    }

    @Step("Проверить, что таблица друзей содержит предложение от '{0}' и принять его")
    @Nonnull
    public FriendsPage acceptFriendRequestFromUser(String userName) {
        userHaveFriendRequest(userName);
        SelenideElement friendRow = rowsFriendRequest.find(text(userName));
        friendRow.$(byText("Accept")).click();
        rowsFriends.find(text(userName)).shouldHave(text(userName));
        return this;
    }

    @Step("Проверить, что таблица друзей содержит предложение от '{0}' и отклонить его")
    @Nonnull
    public FriendsPage declineFriendRequestFromUser(String user) {
        SelenideElement friendRow = rowsFriendRequest.find(text(user));
        friendRow.$(byText("Decline")).click();
        $("div[role='dialog']").$(byText("Decline")).click();
        checkFriendsListIsEmpty();
        return this;
    }

    @Step("Проверить, что таблица друзей пустая")
    public void checkFriendsListIsEmpty() {
        textMessage.should(visible);
    }


    @Step("Проверить, что пользователь отправил запрос на дружбу")
    public void userHaveOutcomeInvitation(String... users) {
        for (String user: users) {
            searchUser(user);
            rowsAllPeople.find(text(user)).shouldHave(text(user));
        }
    }

    private void searchUser(String name) {
        search.shouldBe(visible).clear();
        search.sendKeys(name);
        search.pressEnter();
    }
}
