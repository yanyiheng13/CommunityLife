package com.community.life.net;


import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @FormUrlEncoded
    @POST("/api/user/login/{string}")
    Flowable<ResponseBody> login(@FieldMap Map<String, Object> map, String string);

    //1.首页业绩信息详情
    @GET("/promotion/marketingO2o/indexCommission/{version}/{appkey}/{token}")
    Flowable<ResponseBody> home(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token);
    //2.首页产品列表
    @GET("/promotion/marketingO2o/products/{version}/{appkey}/{token}")
    Flowable<ResponseBody> homeList(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token);
    //3.个人业绩列表
    @GET("/promotion/marketingO2o/personalPerformance/{version}/{appkey}/{token}/{month}/{pageSize}/{currentPageNo}")
    Flowable<ResponseBody> complain(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token,
                                    @Path("month") String month, @Path("pageSize") String pageSize, @Path("currentPageNo") String currentPageNo);
    //4.团队业绩列表
    @GET("/promotion/marketingO2o/teamPerformance/{version}/{appkey}/{token}/{type}/{month}/{pageSize}/{currentPageNo}")
    Flowable<ResponseBody> announcement(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token,
                                        @Path("type") String type, @Path("month") String month,
                                        @Path("pageSize") String pageSize, @Path("currentPageNo") String currentPageNo);
    //5.个人详细信息
    @GET("/promotion/marketingO2o/clerkDetail/{version}/{appkey}/{token}")
    Flowable<ResponseBody> personInfo(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token);
    //6.我的店铺列表
    @GET("/promotion/marketingO2o/stores/{version}/{appkey}/{token}")
    Flowable<ResponseBody> login(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token);
    //7.店铺人员列表
    @GET("/promotion/marketingO2o/clerks/{version}/{appkey}/{token}/{leparID}/{currentPageNo}/{pageSize}")
    Flowable<ResponseBody> transaction(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token,
                                       @Path("leparID") String leparID, @Path("currentPageNo") String currentPageNo,
                                       @Path("pageSize") String pageSize);
    //8.移除店员
    @GET("/promotion/marketingO2o/removeClerk/{version}/{appkey}/{token}/{leparID}/{userID}")
    Flowable<ResponseBody> maintain(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token,
                                    @Path("leparID") String leparID, @Path("userID") String userID);
    //9.修改店员所属门店
    @GET("/promotion/marketingO2o/modifyClerkStore/{version}/{appkey}/{token}/{userID}/{aimLeparID}/{leparID}")
    Flowable<ResponseBody> modifyStaffStore(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token,
                                            @Path("userID") String userID, @Path("aimLeparID") String aimLeparID, @Path("leparID") String leparID);
    //10. 添加店员
    @GET("/promotion/marketingO2o/addClerk/{version}/{appkey}/{token}/{name}/{telePhone}/{noID}/{leparID}")
    Flowable<ResponseBody> addStaff(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token,
                                    @Path("name") String name, @Path("telePhone") String telePhone, @Path("noID") String noID,
                                    @Path("leparID") String leparID);
    //11.获取所有门店
    @GET("/promotion/marketingO2o/queryMyStore/{version}/{appkey}/{token}")
    Flowable<ResponseBody> getAllStore(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token);

    //11.客户列表
    @GET("/promotion/marketingO2o/customerDetails/{version}/{appkey}/{token}/{type}/{pageSize}/{pageNo}/{assistantID}/{storeID}")
    Flowable<ResponseBody> customerList(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token,
                                        @Path("type") String type, @Path("assistantID") String assistantID, @Path("storeID") String storeID,
                                        @Path("pageNo") String pageNo, @Path("pageSize") String pageSize);
    //12.移动店员
    @GET("/promotion/marketingO2o/moveCustomer/{version}/{appkey}/{token}/{storeID}/{assistantID}/{userID}")
    Flowable<ResponseBody> moveStaff(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token,
                                     @Path("storeID") String storeID, @Path("assistantID") String assistantID, @Path("userID") String userID);
    //13.客户那的店铺列表
    @GET("/promotion/marketingO2o/queryOwnStore/{version}/{appkey}/{token}")
    Flowable<ResponseBody> customerStoreList(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token);
    //14.团队客户----》获取全部店员列表
    @GET("/promotion/marketingO2o/clerks/{version}/{appkey}/{token}")
    Flowable<ResponseBody> statistic(@Path("version") String version, @Path("appkey") String appkey, @Path("token") String token);

    //15.客户详情
    @GET("/promotion/marketingO2o/customer/{version}/")
    Flowable<ResponseBody> noSolve(@Path("version") String version);

    //16.店铺详情
    @GET("/promotion/marketingO2o/managerStore/{version}/{appkey}/{token}/{storeID}")
    Flowable<ResponseBody> complainProgress(@Path("token") String token, @Path("storeID") String storeID);

    //17.短链
    @FormUrlEncoded
    @POST("/promotion/marketingO2o/shorturl/add/v1/{appkey}")
    Flowable<ResponseBody> maintainProgress(@Path("appkey") String appkey, @FieldMap Map<String, String> map);

    @Multipart
    @POST("url")
    Call<Result> uploadFile(@Part("description") RequestBody file);
}
