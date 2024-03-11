package com.example.p7_daa_alexandre.database;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NetworkInterceptor implements Interceptor {

    private static int requestCount = 0;
    private static final int MAX_REQUESTS = 3;

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        // Increment the request count
        requestCount++;

        // Check if the request count exceeds the maximum allowed requests
        if (requestCount > MAX_REQUESTS) {
            // Implement your custom logic here for handling exceeding requests
            // For example, you can return a custom response or throw an exception
            // In this example, we'll just return a mock response with code 429 (Too Many Requests)
            return new Response.Builder()
                    .code(429)
                    .message("Too Many Requests")
                    .build();
        }

        // Continue with the original request
        Request request = chain.request();
        Response response = chain.proceed(request);

        // Reset the request count if necessary
        if (requestCount == MAX_REQUESTS) {
            requestCount = 0; // Reset to zero after reaching the maximum requests
        }

        return response;
    }
}