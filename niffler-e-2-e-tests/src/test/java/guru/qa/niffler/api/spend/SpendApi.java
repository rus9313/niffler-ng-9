package guru.qa.niffler.api.spend;

import guru.qa.niffler.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.*;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface SpendApi {

    @POST("internal/spends/add")
    Call<SpendJson> addSpend(@Body SpendJson spending);

    @PATCH("internal/spends/edit")
    Call<SpendJson> editSpend(@Body SpendJson spend);

    @GET("/internal/spends/{id}")
    Call<SpendJson> getSpend(@Path("id") String id, @Query("username") String username);

    @GET("/internal/spends/all")
    Call<List<SpendJson>> getSpendsAll(@Query("username") String username,
                                       @Nullable @Query("filterCurrency") CurrencyValues filterCurrency,
                                       @Nullable @Query("from") String from,
                                       @Nullable @Query("to") String to);;

    @DELETE("internal/spends/remove")
    Call<Void> deleteSpends(@Query("username") String username,
                            @Query("ids") List<String> ids);
}
