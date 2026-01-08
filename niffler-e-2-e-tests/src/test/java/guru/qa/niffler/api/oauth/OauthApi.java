package guru.qa.niffler.api.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.*;

public interface OauthApi {

    @GET("oauth2/authorize")
    Call<Void> authorize(@Query("response_type") String responseType,
                         @Query("client_id") String clientId,
                         @Query("scope") String scope,
                         @Query(value = "redirect_uri", encoded = true) String redirectUri,
                         @Query("code_challenge") String codeChallenge,
                         @Query("code_challenge_method") String code_challengeMethod);

    @FormUrlEncoded
    @POST("login")
    Call<Void> login(@Field("username") String username,
                     @Field("password") String password,
                     @Field("_csrf") String csrf);

    @FormUrlEncoded
    @POST("oauth2/token")
    Call<JsonNode> token(@Field("code") String code,
                         @Field(value = "redirect_uri", encoded = true) String redirectUri,
                         @Field("code_verifier") String codeVerifier,
                         @Field("grant_type") String grantType,
                         @Field("client_id") String clientId);
}
