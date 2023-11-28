package net.fieldwire.endpoints;

import net.fieldwire.models.request.CreateJwt;
import net.fieldwire.models.response.AccountUserResponse;
import net.fieldwire.models.response.JwtResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SuperEndpoint {
    // Account & user information lives in super. It also deals with
    // generating access tokens (JWTs) for refresh tokens
    String API_BASE_URL = "https://client-api.super.fieldwire.com/";

    @GET("account/users")
    Call<List<AccountUserResponse>> getAccountUsers(@Header("Authorization") String authorization);

    @POST("api_keys/jwt")
    Call<JwtResponse> createAccessToken(@Body CreateJwt data);
}

