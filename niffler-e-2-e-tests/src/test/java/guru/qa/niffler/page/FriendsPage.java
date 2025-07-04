package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FriendsPage {
    private final SelenideElement textMessage = $x("//p[text()='There are no users yet']");
    private final ElementsCollection rowsFriends = $$x("//tbody[@id='friends']//tr");
    private final ElementsCollection rowsAllPeople = $$x("//tbody[@id='all']//tr");
    private final SelenideElement tableFriendRequest = $x("//tbody[@id='requests']");


    public void checkTextMessage() {
        assertEquals("There are no users yet", textMessage.getText());
    }

    public void userHaveFriend(String nameFriend) {
        rowsFriends.filter(text(nameFriend)).first().shouldHave(text(nameFriend));
    }

    public void userHaveFriendRequest(String nameFriend) {
        tableFriendRequest.shouldHave(text(nameFriend));
    }

    public void userHaveOutcomeInvitation(String nameFriend) {
        rowsAllPeople.filter(text(nameFriend)).first().shouldHave(text(nameFriend));
    }
}
