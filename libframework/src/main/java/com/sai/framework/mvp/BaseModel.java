package com.sai.framework.mvp;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class BaseModel implements MvpModel {


    protected Flowable<String> convert(Flowable<ResponseBody> body) {
        return body.subscribeOn(Schedulers.io()).map(new Function<ResponseBody, String>() {
            @Override
            public String apply(ResponseBody responseBody) throws Exception {
                return responseBody.string();
            }
        });
    }
}
