package guru.qa.niffler.api.auth;

import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.RestClient;
import retrofit2.Response;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserdataApiClient extends RestClient {
    private UserdataApi userdataApi;

    public UserdataApiClient() {
        super(CFG.userdataUrl());
        userdataApi = create(UserdataApi.class);
    }

    public @Nullable UserJson currentUser(String username) {
        final Response<UserJson> response;
        try {
            response = userdataApi.currentUser(username).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    public List<UserJson> allUsers(String userName, @Nullable String searchQuery) {
        final Response<List<UserJson>> response;
        try {
            response = userdataApi.allUsers(userName, null).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }
}
