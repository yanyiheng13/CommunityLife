package com.sai.framework.retrofit;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sai.framework.securty.TrustSSLSocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private final String TAG  = "framework";
    private Retrofit mRetrofit;
    private Builder mBuilder;

    private RetrofitHelper(Builder builder) {
        mBuilder = builder;
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        SSLSocketFactory sslSocketFactory = TrustSSLSocketFactory.initSSL(builder.mSslKeyStream);
        if (sslSocketFactory != null) {
            httpBuilder.sslSocketFactory(sslSocketFactory);
        }
        if (builder.isDebug) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

                @Override
                public void log(String message) {
                    Log.d(TAG, message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(httpLoggingInterceptor);
        }
        httpBuilder.connectTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.readTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.writeTimeout(NetConfig.TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = httpBuilder.addInterceptor(new RetrofitInterceptor())
                .build();
        mRetrofit = new Retrofit.Builder().client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(builder.baseUrl).build();
    }

    public <T> T create(Class<T> cls) {
        if (mRetrofit == null) {
            return null;
        }
        return mRetrofit.create(cls);
    }

    private class RetrofitInterceptor implements Interceptor {

        public RetrofitInterceptor() {
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
            if(mBuilder.isDebug){
                Log.d(TAG, originalRequest.url().toString());
            }

            Map<String, String> mHeaderMap = mBuilder.headers;
            if (mHeaderMap == null) {
                mHeaderMap = new HashMap<>();
            }
            builder.headers(Headers.of(mHeaderMap));
            OnDynamicParameterListener listener = mBuilder.mOnDynamicParameterListener;
            if (listener != null) {
                HashMap<String, String> headerMap = listener.headers();
                if (headerMap != null) {
                    for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                        builder.addHeader(entry.getKey(), entry.getValue());
                    }
                }
            }

//            Response response1 = new Response.Builder().code(400).message("无网络").build();
            Response response = chain.proceed(builder.build());
            return response;
        }
    }

    public static class Builder {
        public Builder() {
        }

        private HashMap<String, String> headers;
        private String baseUrl;
        private boolean isDebug;
        private InputStream[] mSslKeyStream;
        private OnDynamicParameterListener mOnDynamicParameterListener;


        public Builder baseUrl(String url) {
            this.baseUrl = url;
            return this;
        }

        public Builder headers(HashMap<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder debug(boolean debug){
            isDebug = debug;
            return this;
        }

        public Builder sslKeyStream(InputStream... is) {
            mSslKeyStream = is;
            return this;
        }

        public Builder dynamicParameter(OnDynamicParameterListener listener) {
            mOnDynamicParameterListener = listener;
            return this;
        }

        public RetrofitHelper build() {
            return new RetrofitHelper(this);
        }
    }

    public interface OnDynamicParameterListener {
        public HashMap<String, String> headers();
    }
}
