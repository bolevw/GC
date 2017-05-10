package com.boger.game.gc.restApi.client;

import com.boger.game.gc.base.BaseApplication;
import com.boger.game.gc.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by liubo on 2016/5/17.
 */
public class OkClient {
    private static OkHttpClient client;

    public static OkHttpClient getInstance() {
        File cacheFile = new File(BaseApplication.getContext().getCacheDir(), "/gcCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

        client = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(new NormalIn())
                .addInterceptor(new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return client;
    }

    public static OkHttpClient getUpdateUserClient(String session) {
        client = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new UserIn(session))
                .addInterceptor(new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();

        return client;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            if (!NetWorkUtils.hasNetWork(BaseApplication.getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if (!NetWorkUtils.hasNetWork(BaseApplication.getContext())) {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    private static class NormalIn implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .addHeader("X-LC-Id", "3KIaGcxNRBLjQHTIdW53qxPG-gzGzoHsz")
                    .addHeader("X-LC-Key", "dRW0Ct5da9dUmIQGEUDDb669")
                    .addHeader("Content-Type", "application/json")
                    .build();
            return chain.proceed(newRequest);
        }
    }

    static class UserIn implements Interceptor {
        private String session;

        public UserIn(String session) {
            this.session = session;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .addHeader("X-LC-Id", "3KIaGcxNRBLjQHTIdW53qxPG-gzGzoHsz")
                    .addHeader("X-LC-Key", "dRW0Ct5da9dUmIQGEUDDb669")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-LC-Session", session)
                    .build();
            return chain.proceed(newRequest);
        }
    }
}
