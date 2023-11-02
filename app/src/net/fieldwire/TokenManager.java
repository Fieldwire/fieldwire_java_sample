package net.fieldwire;

import com.google.gson.Gson;

import net.fieldwire.endpoints.SuperEndpoint;
import net.fieldwire.models.request.CreateJwt;
import net.fieldwire.models.response.JwtResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Function;

import okhttp3.ResponseBody;
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

    // `call` will be called with the access token. If the call made fails,
    // because of bad access token (expired or invalid), this method will
    // automatically retry & try the call again
    //
    // NOTE: make sure that the passed in function can be called multiple times
    // without messing up any of your internal state (i.e., change your
    // internal state only on a successful response from the server)
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

        return handleResponse(response);
    }

    private void refresh() throws IOException {
        // NOTE: In a multi-thread scenario, please make sure multiple threads
        // aren't refreshing at the same time - let the first thread do it &
        // for the other threads to use the newly acquired access token
        // (otherwise, there will be rate limit issues). May we suggest looking
        // into `Lock` for this? (a multi-process setup would require a more
        // sophisticated system)

        Response<JwtResponse> response = new Retrofit.Builder()
                .baseUrl(SuperEndpoint.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SuperEndpoint.class)
                .createAccessToken(new CreateJwt(this.refreshToken))
                .execute();

        // NOTE: please make sure to persist this somewhere outside the script
        // so it can reused for the next invocation of the script (otherwise,
        // there will be rate limit issues)
        this.accessToken = handleResponse(response).access_token();
    }

    @NotNull
    private <T> T handleResponse(Response<T> response) throws IOException {
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

    @NotNull
    private String buildAuthzHeaderValue() {
        return "Bearer " + this.accessToken;
    }
}
