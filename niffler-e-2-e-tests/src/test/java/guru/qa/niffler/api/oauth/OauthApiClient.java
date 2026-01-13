package guru.qa.niffler.api.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.niffler.api.core.ThreadSafeCookieStore;
import guru.qa.niffler.service.RestClient;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OauthApiClient extends RestClient {
    private OauthApi client;

    public OauthApiClient() {
        super(CFG.authUrl(), true);
        client = create(OauthApi.class);
    }

    public void authorize(String codeChallenge) {
        final Response<Void> response;
        try {
            response = client.authorize(
                            "code",
                            "client",
                            "openid",
                            CFG.frontUrl() + "authorized",
                            codeChallenge,
                            "S256"
                    )
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(200, response.code());
    }

    public String login(String userName, String password) {
        final Response<Void> response;
        try {
            response = client.login(
                            userName,
                            password,
                            ThreadSafeCookieStore.INSTANCE.cookieValue("XSRF-TOKEN"))
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(200, response.code());
        return response.raw().request().url().toString().split("\\?code=")[1];
    }

    public String token(String code, String codeVerifier) {
        final Response<JsonNode> response;
        try {
            response = client.token(
                            code,
                            CFG.frontUrl() + "authorized",
                            codeVerifier,
                            "authorization_code",
                            "client")
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(200, response.code());
        return response.body().get("id_token").asText();
    }
}
