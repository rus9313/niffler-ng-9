package guru.qa.niffler.service.impl;

import guru.qa.niffler.api.category.CategoryApi;
import guru.qa.niffler.api.spend.SpendApi;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.service.SpendClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ParametersAreNonnullByDefault
public class SpendApiClient implements SpendClient {

    private static final Config CFG = Config.getInstance();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CFG.spendUrl())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final SpendApi spendApi = retrofit.create(SpendApi.class);
    private final CategoryApi categoryApi = retrofit.create(CategoryApi.class);

    @Override
    @Nonnull
    public SpendJson createSpend(SpendJson spend) {
        final Response<SpendJson> response;
        try {
            response = spendApi
                    .addSpend(spend)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(201, response.code());
        return response.body();
    }

    @Override
    @Nonnull
    public SpendJson update(SpendJson spend) {
        final Response<SpendJson> response;
        try {
            response = spendApi
                    .editSpend(spend)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return requireNonNull(response.body());
    }

    @Override
    @Nonnull
    public Optional<SpendJson> findSpendById(UUID id) {
        throw new UnsupportedOperationException("Operation not supported in API");
    }

    @Override
    @Nonnull
    public List<SpendJson> findAllByUserName(String userName) {
        throw new UnsupportedOperationException("Operation not supported in API");
    }

    @Override
    public void removeSpend(SpendJson spend) {
        try {
            spendApi.deleteSpends(spend.username(), List.of(spend.id().toString()))
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    @Nonnull
    public CategoryJson createCategory(CategoryJson category) {
        final Response<CategoryJson> response;
        try {
            response = categoryApi
                    .createCategory(category)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return requireNonNull(response.body());
    }

    @Override
    public void removeCategory(CategoryJson category) {
        throw new UnsupportedOperationException("Operation not supported in API");
    }

    public SpendJson getSpend(String id, String userName) {
        final Response<SpendJson> response;
        try {
            response = spendApi
                    .getSpend(id, userName)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    public List<SpendJson> getSpendAll(String userName,
                                       CurrencyValues filterCurrency,
                                       String from,
                                       String to) {
        final Response<List<SpendJson>> response;
        try {
            response = spendApi
                    .getSpendsAll(userName, filterCurrency, from, to)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    public void deleteSpends(String userName, List<String> ids) {
        try {
            spendApi.deleteSpends(userName, ids)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
