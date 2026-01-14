package guru.qa.niffler.test;

import guru.qa.niffler.api.oauth.OauthApiClient;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.utils.OauthUtils.generateCodeChallenge;
import static guru.qa.niffler.utils.OauthUtils.generateCodeVerifier;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OauthFakeTest {
    @Test
    void oauthTest() {
        OauthApiClient client = new OauthApiClient();
        String codeVerifier = generateCodeVerifier();
        String codeChallange = generateCodeChallenge(codeVerifier);


        client.authorize(codeChallange);
        String code = client.login("duck", "12345");
        String token = client.token(code, codeVerifier);

        assertNotNull(token);
    }
}
