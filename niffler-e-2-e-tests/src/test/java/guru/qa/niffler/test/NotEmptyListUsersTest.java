package guru.qa.niffler.test;

import guru.qa.niffler.api.auth.UserdataApiClient;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Order(Integer.MAX_VALUE)
public class NotEmptyListUsersTest {
    @User
    @Test
    void checkEmptyUsersListIsNotEmptyTest(UserJson user) {
        UserdataApiClient client = new UserdataApiClient();
        List<UserJson> userJsonList = client.allUsers(user.username(), null);
        assertFalse(userJsonList.isEmpty());
    }
}
