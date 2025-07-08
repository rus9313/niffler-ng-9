package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.*;

@WebTest
public class FriendsWebTest {
    private static final Config CFG = Config.getInstance();

    @Test
    void friendsTableShouldBeEmptyForNewUserTest(@UserType(empty = UserType.Type.EMPTY) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.password())
                .openFriendsPage()
                .checkTextMessage();
    }

    @Test
    void friendShouldBePresentInFriendsTableTest(@UserType(empty = UserType.Type.WITH_FRIEND) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.password())
                .openFriendsPage()
                .userHaveFriend(user.friend());
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
