package guru.qa.niffler.test;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.SpendDbClient;
import guru.qa.niffler.service.UserDataDbClient;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;


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
        UserJson user = usersDbClient.createUser(
                new UserJson(
                        null,
                        "valentin12",
                        CurrencyValues.RUB,
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );
        System.out.println(user);
    }
}
