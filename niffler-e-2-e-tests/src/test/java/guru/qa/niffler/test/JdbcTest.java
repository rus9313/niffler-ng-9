package guru.qa.niffler.test;

import guru.qa.niffler.data.repository.AuthUserRepository;
import guru.qa.niffler.data.repository.impl.AuthUserRepositorySpringJdbc;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.impl.SpendDbClient;
import guru.qa.niffler.service.impl.UserDataDbClient;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;


public class JdbcTest {

    @Test
    void txTest() {
        SpendDbClient spendDbClient = new SpendDbClient();
        SpendJson spend = spendDbClient.createSpend(new SpendJson(
                null,
                new Date(),
                new CategoryJson(
                        null,
                        "cat-name-tx22",
                        "888",
                        false
                ),
                CurrencyValues.RUB,
                1000.0,
                "spend-name-tx111",
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
