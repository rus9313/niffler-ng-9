package guru.qa.niffler.test;

import guru.qa.niffler.data.repository.AuthUserRepository;
import guru.qa.niffler.data.repository.impl.AuthUserRepositorySpringJdbc;
import guru.qa.niffler.jupiter.extension.ClientResolver;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.SpendClient;
import guru.qa.niffler.service.UsersClient;
import guru.qa.niffler.service.impl.SpendDbClient;
import guru.qa.niffler.service.impl.UserDataDbClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.UUID;

@ExtendWith(ClientResolver.class)
public class JdbcTest {

    private SpendClient spendClient;
    private UsersClient usersClient;

    @Test
    void txTest() {
        SpendJson spend = spendClient.createSpend(new SpendJson(
                null,
                new Date(),
                new CategoryJson(
                        null,
                        "cat-name-ytyytytyytyt",
                        "888",
                        false
                ),
                CurrencyValues.RUB,
                1000.0,
                "spend-name-uyuyuyuyuy",
                "duck"));
        System.out.println(spend);
    }

    @Test
    void springJdbcTest() {
        UserDataDbClient usersDbClient = new UserDataDbClient();
        UserJson user = usersDbClient.createUser("kolobok", "12345");
        System.out.println(user);
    }
    @Test
    void repositoryTest() {
        AuthUserRepository repositoryJdbc = new AuthUserRepositorySpringJdbc();

        System.out.println(repositoryJdbc.findById(UUID.fromString("f7de1a26-bf14-11f0-a328-562efc19339b")).get().getUsername());
    }
}
