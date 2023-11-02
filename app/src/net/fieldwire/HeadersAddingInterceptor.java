package net.fieldwire;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeadersAddingInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull final Chain chain) throws IOException {
        Request request = chain
                .request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Fieldwire-Version", "2023-08-08")
                .build();

        return chain.proceed(request);
    }
}
