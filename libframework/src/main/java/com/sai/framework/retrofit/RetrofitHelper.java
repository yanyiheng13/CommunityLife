package com.sai.framework.retrofit;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sai.framework.securty.TrustSSLSocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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


    public CommonParamListener mListener;
    public interface CommonParamListener {
        Map<String, String> commonParams();
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
            Request originRequest = chain.request();
            Request.Builder newRequest = originRequest.newBuilder();
            if(mBuilder.isDebug){
                Log.d(TAG, originRequest.url().toString());
            }

            Map<String, String> mHeaderMap = mBuilder.headers;
            if (mHeaderMap == null) {
                mHeaderMap = new HashMap<>();
            }
            newRequest.headers(Headers.of(mHeaderMap));
            OnDynamicParameterListener listener = mBuilder.mOnDynamicParameterListener;
            if (listener != null) {
                HashMap<String, String> headerMap = listener.headers();
                if (headerMap != null) {
                    for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                        newRequest.addHeader(entry.getKey(), entry.getValue());
                    }
                }
            }

            if ("POST".equals(originRequest.method())) {
                RequestBody body = originRequest.body();
                if (body != null && body instanceof FormBody) {
                    // POST Param x-www-form-urlencoded
                    FormBody formBody = (FormBody) body;
                    Map<String, String> formBodyParamMap = new HashMap<>();
                    int bodySize = formBody.size();
                    for (int i = 0; i < bodySize; i++) {
                        formBodyParamMap.put(formBody.name(i), formBody.value(i));
                    }

                    Map<String, String> newFormBodyParamMap = null;
                    if (listener != null) {
                        newFormBodyParamMap = listener.commonParam();
                    }
                    if (newFormBodyParamMap != null) {
                        formBodyParamMap.putAll(newFormBodyParamMap);
                        FormBody.Builder bodyBuilder = new FormBody.Builder();
                        for (Map.Entry<String, String> entry : formBodyParamMap.entrySet()) {
                            bodyBuilder.add(entry.getKey(), entry.getValue());
                        }
                        newRequest.method(originRequest.method(), bodyBuilder.build());
                    }
                } else if (body != null && body instanceof MultipartBody) {
                    // POST Param form-data
                    MultipartBody multipartBody = (MultipartBody) body;
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    Map<String, String> extraFormBodyParamMap = null;
                    if (listener != null) {
                        extraFormBodyParamMap = listener.commonParam();
                    }
                    if (extraFormBodyParamMap != null && extraFormBodyParamMap.size() > 0) {
                        for (Map.Entry<String, String> entry : extraFormBodyParamMap.entrySet()) {
                            builder.addFormDataPart(entry.getKey(), entry.getValue());
                        }
                    }
                    List<MultipartBody.Part> parts = multipartBody.parts();
                    for (MultipartBody.Part part : parts) {
                        builder.addPart(part);
                    }
                    newRequest.post(builder.build());
                }
            }
            Response response = chain.proceed(newRequest.build());
            return response;
        }
    }

    public static class Builder {
        public Builder() {
        }

        private HashMap<String, String> headers;
        private HashMap<String, String> commonBody;
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
        HashMap<String, String> headers();
        HashMap<String, String> commonParam();
    }
}
