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
import retrofit2.http.Query;

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
    @FormUrlEncoded
    @POST("promotion/marketingO2o/products/{version}/{appkey}/{token}")
    Flowable<ResponseBody> addressList(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token);
    //维修
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("promotion/marketingO2o/personalPerformance/{version}/{appkey}/{token}/{month}/{pageSize}/{currentPageNo}")
    @FormUrlEncoded
    Flowable<ResponseBody> complain(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token,
                                    @Field("month") String month, @Field("pageSize") String pageSize, @Field("currentPageNo") String currentPageNo);
    //系统公告
    @GET("notice/v1/sys")
    Flowable<ResponseBody> announcementSystem(@Query("start") String start, @Query("length") String length);

    //小区公告
    @GET("notice/v1/community")
    Flowable<ResponseBody> announcementCommunity(@Query("start") String start,
                                                 @Query("length") String length,
                                                 @Query("phone") String phone);

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

    //维修
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("authentication/v1")
    Flowable<ResponseBody> maintain(@Body RequestBody body);

    //添加物业地址 账号认证
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("authentication/v1")
    Flowable<ResponseBody> addAddress(@Body RequestBody body);

    //物业地址列表接口
    @GET("member/v1/estate")
    Flowable<ResponseBody> addressList(@Query("phone") String phone);

    //首页三条公告请求
    @GET("notice/v1?start=0&length=3")
    Flowable<ResponseBody> homeThreeNotice();

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("member/v1/estate")
    Flowable<ResponseBody> addStaff(@Body RequestBody body);

    //验证码
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("notification/v1/sms/code")
    Flowable<ResponseBody> verificationCode(@Body RequestBody requestBody);

    //语音验证码
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("notification/v1/voice/code")
    Flowable<ResponseBody> verificationVoiceCode(@Body RequestBody body);
    //小区列表
    @GET("authentication/v1/community")
    Flowable<ResponseBody> communityList();
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
