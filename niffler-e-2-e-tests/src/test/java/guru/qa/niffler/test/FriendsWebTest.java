package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.*;

@WebTest
public class FriendsWebTest {
    private static final Config CFG = Config.getInstance();

    @Test
    @User(
            friends = 0
    )
    void friendsTableShouldBeEmptyForNewUserTest(UserJson user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriendsPage()
                .checkTextMessage();
    }

    @Test
    @User(
            friends = 1
    )
    void friendShouldBePresentInFriendsTableTest(UserJson user) {
        final UserJson friend = user.testData().friends().getFirst();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriendsPage()
                .userHaveFriend(friend.username());
    }

    @Test
    void incomeInvitationBePresentInFriendsTableTest(@UserType(empty = UserType.Type.WITH_INCOME_REQUEST) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.password())
                .openFriendsPage()
                .userHaveFriendRequest(user.income());
    }

    @Test
    void outcomeInvitationBePresentInFriendsTableTest(@UserType(empty = UserType.Type.WITH_OUTCOME_REQUEST) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.password())
                .openAllPeoplePage()
                .userHaveOutcomeInvitation(user.outcome());
    }
}
