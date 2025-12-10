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
    @User(
            incomeInvitations = 1
    )
    void incomeInvitationBePresentInFriendsTableTest(UserJson user) {
        final UserJson friendRq = user.testData().incomeInvitations().getFirst();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriendsPage()
                .userHaveFriendRequest(friendRq.username());
    }

    @Test
    @User(
            outcomeInvitations = 1
    )
    void outcomeInvitationBePresentInFriendsTableTest(UserJson user) {
        final UserJson outcome = user.testData().outcomeInvitations().getFirst();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openAllPeoplePage()
                .userHaveOutcomeInvitation(outcome.username());
    }

    @Test
    @User(
            incomeInvitations = 1
    )
    void acceptingFriendRequestTest(UserJson user) {
        final UserJson friendRq = user.testData().incomeInvitations().getFirst();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriendsPage()
                .acceptFriendRequestFromUser(friendRq.username());
    }

    @Test
    @User(
            incomeInvitations = 1
    )
    void declineFriendRequestTest(UserJson user) {
        final UserJson friendRq = user.testData().incomeInvitations().getFirst();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriendsPage()
                .declineFriendRequestFromUser(friendRq.username());
    }
}
