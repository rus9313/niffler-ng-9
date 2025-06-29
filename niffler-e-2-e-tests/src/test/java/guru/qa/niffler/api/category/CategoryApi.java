package guru.qa.niffler.api.category;

import guru.qa.niffler.model.CategoryJson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CategoryApi {
    @POST("/internal/categories/add")
    Call<CategoryJson> createCategory(@Body CategoryJson category);

    @PATCH("/internal/categories/update")
    Call<CategoryJson> updateCategory(@Body CategoryJson category);

    @GET("/internal/categories/all")
    Call<List<CategoryJson>> getCategories(
            @Query("username") String username,
            @Query("excludeArchived") boolean excludeArchived);
}