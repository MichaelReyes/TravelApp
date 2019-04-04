package com.sample.penbrothersexam.networking;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://lemi.travel/api/v5/";

    private static Retrofit retrofit;
    private static ApiService apiService;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true);
            /*.addInterceptor(new BasicAuthInterceptor("MichaelReyes", "mike12345"));*/

    public static ApiService getInstance() {
        if (apiService != null)
            return apiService;

        if (retrofit == null)
            initializeRetrofit();

        apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    private static void initializeRetrofit() {

        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    private ApiClient() {

    }

    static class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }

}
