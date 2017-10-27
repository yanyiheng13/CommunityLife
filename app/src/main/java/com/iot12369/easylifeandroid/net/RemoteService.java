package com.iot12369.easylifeandroid.net;


import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:15
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public interface RemoteService {


    //1.首页业绩信息详情
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/indexCommission/{version}/{appkey}/{token}")
    Flowable<ResponseBody> home(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token);
    //2.首页产品列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/products/{version}/{appkey}/{token}")
    Flowable<ResponseBody> addressList(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token);
    //3.个人业绩列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/personalPerformance/{version}/{appkey}/{token}/{month}/{pageSize}/{currentPageNo}")
    Flowable<ResponseBody> complain(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token,
                                    @Field("month") String month, @Field("pageSize") String pageSize, @Field("currentPageNo") String currentPageNo);
    //4.团队业绩列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/teamPerformance/{version}/{appkey}/{token}/{type}/{month}/{pageSize}/{currentPageNo}")
    Flowable<ResponseBody> announcement(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token,
                                        @Field("type") String type, @Field("month") String month,
                                        @Field("pageSize") String pageSize, @Field("currentPageNo") String currentPageNo);
    //个人详细信息
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/clerkDetail/{version}/{appkey}/{token}")
    Flowable<ResponseBody> personInfo(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token);

    //绑定手机号
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("member/v1/wx/phone")
    Flowable<ResponseBody> bindPhone(@Body RequestBody body);

    //手机号登录
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("member/v1/phone/signin")
    Flowable<ResponseBody> loginPhone(@Body RequestBody body);

    //微信注册
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("member/v1/wx/signup")
    Flowable<ResponseBody> wechatRegister(@Body RequestBody requestBody);

    //微信登录
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("member/v1/wx/signin")
    Flowable<ResponseBody> wechatLogin(@Body RequestBody requestBody);

    //开锁
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("lock/v1")
    Flowable<ResponseBody> lock(@Body RequestBody body);

    //移除店员
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/removeClerk/{version}/{appkey}/{token}/{leparID}/{userID}")
    Flowable<ResponseBody> maintain(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token,
                                    @Field("leparID") String leparID, @Field("userID") String userID);
    //修改店员所属门店
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/modifyClerkStore/{version}/{appkey}/{token}/{userID}/{aimLeparID}/{leparID}")
    Flowable<ResponseBody> mfodifyStaffStore(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token,
                                            @Field("userID") String userID, @Field("aimLeparID") String aimLeparID, @Field("leparID") String leparID);
    //10. 添加店员
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/addClerk/{version}/{appkey}/{token}/{name}/{telePhone}/{noID}/{leparID}")
    Flowable<ResponseBody> addStaff(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token,
                                    @Field("name") String name, @Field("telePhone") String telePhone, @Field("noID") String noID,
                                    @Field("leparID") String leparID);
    //验证码
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("notification/v1/sms/code")
    Flowable<ResponseBody> verificationCode(@Body RequestBody requestBody);

    //语音验证码
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("notification/v1/voice/code")
    Flowable<ResponseBody> verificationVoiceCode(@Body RequestBody body);
    //12.移动店员
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/moveCustomer/{version}/{appkey}/{token}/{storeID}/{assistantID}/{userID}")
    Flowable<ResponseBody> moveStaff(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token,
                                     @Field("storeID") String storeID, @Field("assistantID") String assistantID, @Field("userID") String userID);
    //13.客户那的店铺列表
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/queryOwnStore/{version}/{appkey}/{token}")
    Flowable<ResponseBody> customerStoreList(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token);
    //14.团队客户----》获取全部店员列表
    @FormUrlEncoded
    @POST("promotion/marketingO2o/clerks/{version}/{appkey}/{token}")
    Flowable<ResponseBody> statistic(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token);

    //15.客户详情
    @FormUrlEncoded
    @POST("promotion/marketingO2o/customer/{version}/")
    Flowable<ResponseBody> noSolve(@Field("version") String version);

    //16.店铺详情
    @FormUrlEncoded
    @POST("promotion/marketingO2o/managerStore/{version}/{appkey}/{token}/{storeID}")
    Flowable<ResponseBody> complainProgress(@Field("token") String token, @Field("storeID") String storeID);

    //17.短链
    @FormUrlEncoded
    @POST("promotion/marketingO2o/shorturl/add/v1/{appkey}")
    Flowable<ResponseBody> maintainProgress(@Field("appkey") String appkey, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("url")
    Call<Result> uploadFile(@Part("description") RequestBody file);
}
