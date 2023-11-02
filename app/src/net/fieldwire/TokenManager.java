package net.fieldwire;

import com.google.gson.Gson;

import net.fieldwire.endpoints.SuperEndpoint;
import net.fieldwire.models.request.CreateJwt;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenManager {
    @NotNull
    private final String refreshToken;
    @NotNull
    private String accessToken;
    @NotNull
    private Gson gson;

    public TokenManager(@NotNull String refreshToken, @NotNull String accessToken, @NotNull Gson gson) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.gson = gson;
    }

    public <T> T executeWithToken(Function<String, Call<T>> call) throws IOException {
        Response<T> response = call.apply(buildAuthzHeaderValue()).execute();
        if (!response.isSuccessful() && response.code() == 401) {
            System.out.println("Received 401 from the server. Trying to refresh token");

            // If the first call failed with 401, refresh the access token (since it
            // might have expired) & try again
            refresh();

            System.out.println("Token successfully refreshed. Retrying call");
            response = call.apply(buildAuthzHeaderValue()).execute();
        }

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException(String.format(
                    "Response code: %s\nResponse body: %s",
                    response.code(),
                    response.errorBody().string()
            ));
        }
    }

    private void refresh() throws IOException {
        this.accessToken = new Retrofit.Builder()
                .baseUrl(SuperEndpoint.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SuperEndpoint.class)
                .createAccessToken(new CreateJwt(this.refreshToken))
                .execute()
                .body()
                .access_token();
    }

    @NotNull
    private String buildAuthzHeaderValue() {
        return "Bearer " + this.accessToken;
    }
}
