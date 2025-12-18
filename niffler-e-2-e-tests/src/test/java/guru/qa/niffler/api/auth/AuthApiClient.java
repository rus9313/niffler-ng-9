package guru.qa.niffler.api.auth;

import guru.qa.niffler.api.core.ThreadSafeCookieStore;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.RestClient;
import org.apache.commons.lang3.time.StopWatch;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@ParametersAreNonnullByDefault
public class AuthApiClient extends RestClient {

    private AuthApi authApi;
    private UserdataApiClient userdataApi;

    public AuthApiClient() {
        super(CFG.authUrl(), true);
        authApi = create(AuthApi.class);
        userdataApi = new UserdataApiClient();
    }

    public UserJson registerUser(String username,String password, String passwordSubmit) {
        try {
            authApi.requestRegisterForm().execute();
            authApi.requestRegister(
                    username,
                    password,
                    passwordSubmit,
                    ThreadSafeCookieStore.INSTANCE.cookieValue("XSRF-TOKEN")
            ).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StopWatch sw = StopWatch.createStarted();
        while (sw.getTime(TimeUnit.SECONDS) < 30) {
            UserJson userJson;
            userJson = userdataApi.currentUser(username);
            if (userJson != null && userJson.id() != null) {
                return userJson;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new AssertionError("Timed out");
    }
}
