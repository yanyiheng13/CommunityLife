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
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:15
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public interface RemoteService {


    //首页支付信息
    @GET("estate/cost/v1")
    Flowable<ResponseBody> home(@Query("phone") String phone, @Query("memberid") String memberid);
    //
    @FormUrlEncoded
    @POST("promotion/marketingO2o/products/{version}/{appkey}/{token}")
    Flowable<ResponseBody> addressList(@Field("version") String version, @Field("appkey") String appkey, @Field("token") String token);
    //投诉
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/workOrder/getSuggestWorkOrderList")
    Flowable<ResponseBody> complain(@Body RequestBody body);

    //系统公告
    @GET("notice/v1/sys")
    Flowable<ResponseBody> announcementSystem(@Query("start") String start, @Query("length") String length);

    //小区公告
    @GET("notice/v1/community")
    Flowable<ResponseBody> announcementCommunity(@Query("start") String start,
                                                 @Query("length") String length,
                                                 @Query("phone") String phone);

    //公告详情
    @GET("notice/v1/{noticeCommunityId}")
    Flowable<ResponseBody> announcementDetail(@Path("noticeCommunityId") String noticeCommunityId);

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
    @POST("member/v2/wx/signup")
    Flowable<ResponseBody> wechatRegister(@Body RequestBody requestBody);

    //微信登录
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("member/v1/wx/signin")
    Flowable<ResponseBody> wechatLogin(@Body RequestBody requestBody);

    //开锁
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("lock/v1")
    Flowable<ResponseBody> lock(@Body RequestBody body);

    //提交维修工单
    @Multipart
    @POST("http://39.106.61.132:8989/workOrder/submitServiceWorkOrder")
    Flowable<ResponseBody> upMaintainRequireOrder(@PartMap() Map<String, RequestBody> requestBodyMap);

    //提交投诉工单
    @Multipart
    @POST("http://39.106.61.132:8989/workOrder/submitSuggestWorkOrder")
    Flowable<ResponseBody> upComplainRequireOrder(@PartMap() Map<String, RequestBody> requestBodyMap);

    //添加人员
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("authorization/v1")
    Flowable<ResponseBody> addPeople(@Body RequestBody body);

    //维修
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/workOrder/getServiceWorkOrderList")
    Flowable<ResponseBody> maintain(@Body RequestBody body);

    //添加物业地址 账号认证
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("authentication/v2")
    Flowable<ResponseBody> addAddress(@Body RequestBody body);

    //物业地址列表接口
    @GET("member/v1/estate")
    Flowable<ResponseBody> addressList(@Query("phone") String phone);

    //家庭成员及租客列表
    @GET("authorization/v1/member")
    Flowable<ResponseBody> familyList(@Query("phone") String phone, @Query("memberid") String memberid);

    //设置当前默认物业
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("authentication/v1/default")
    Flowable<ResponseBody> setDefaultAdress(@Body RequestBody bode);

    //移除当前授权人
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("authorization/v1/member/cancel")
    Flowable<ResponseBody> removePeople(@Body RequestBody bode);

    //获取支付凭证
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/paymax/pay/applyOrder")
    Flowable<ResponseBody> pay (@Body RequestBody bode);

    //交易记录
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/paymax/pay/getPaymentRecordList")
    Flowable<ResponseBody> transaction (@Body RequestBody bode);

    //首页三条公告请求
    @GET("notice/v1?start=0&length=3")
    Flowable<ResponseBody> homeThreeNotice();

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("config/v1/app/android/version/injection")
    Flowable<ResponseBody> update();

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
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("authentication/v2/community")
    Flowable<ResponseBody> communityList(@Body RequestBody body);

    //修改维修工单的状态
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/workOrder/updateServiceWorkOrder")
    Flowable<ResponseBody> setMaintainState(@Body RequestBody body);

    //修改投诉工单的状态
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/workOrder/updateSuggestWorkOrder")
    Flowable<ResponseBody> setComplainState(@Body RequestBody body);

    //获取工单详情
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/workOrder/getWorkOrderDetail")
    Flowable<ResponseBody> repairOrderDetail(@Body RequestBody body);

    //维修工单用户反馈
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/workOrder/serviceWorkOrderFeedback")
    Flowable<ResponseBody> repairOrderMaintainBack(@Body RequestBody body);

    //投诉工单用户反馈
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("http://39.106.61.132:8989/workOrder/serviceWorkOrderFeedback")
    Flowable<ResponseBody> repairOrderComplainBack(@Body RequestBody body);

    @Multipart
    @POST("http://39.106.61.132:8989/upload/uploadImages")
    Call<Result> uploadFile(@Part("description") RequestBody file);
}
