package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ParametersAreNonnullByDefault
public class FriendsPage {
    private final SelenideElement textMessage = $x("//p[text()='There are no users yet']");
    private final ElementsCollection rowsFriends = $$x("//tbody[@id='friends']//tr");
    private final ElementsCollection rowsAllPeople = $$x("//tbody[@id='all']//tr");
    private final ElementsCollection rowsFriendRequest = $$x("//tbody[@id='requests']//tr");
    private final SelenideElement search = $("input[placeholder='Search']");


    public void checkTextMessage() {
        assertEquals("There are no users yet", textMessage.getText());
    }

    public void userHaveFriend(String... users) {
        for (String user: users) {
            searchUser(user);
            rowsFriends.find(text(user)).shouldHave(text(user));
        }
    }

    public void userHaveFriendRequest(String... users) {
        for (String user: users) {
            searchUser(user);
            rowsFriendRequest.find(text(user)).shouldHave(text(user));
        }
    }

    public void userHaveOutcomeInvitation(String... users) {
        for (String user: users) {
            searchUser(user);
            rowsAllPeople.find(text(user)).shouldHave(text(user));
        }
    }

    public void searchUser(String name) {
        search.shouldBe(visible).clear();
        search.sendKeys(name);
        search.pressEnter();
    }
}
