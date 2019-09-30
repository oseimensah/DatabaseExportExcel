package com.hensongeodata.technologies.agrihouse.controller.request;

import com.hensongeodata.technologies.agrihouse.model.ReceiveUserData;
import com.hensongeodata.technologies.agrihouse.model.UserResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

      @GET("csurvey/auth-user")
      Call<UserResponse> userLogin(
              @Query("u_mail") String u_email,
              @Query("pass") String u_password
      );


      @GET("save/data")
      Call<UserResponse> sendData1(
              @Query("farmer_group") String farmer_group,
              @Query("name") String name,
              @Query("phone") String phone,
              @Query("email") String email,
              @Query("company") String company,
              @Query("job_role") String job_role,
              @Query("age") String age,
              @Query("region_country") String region_country,
              @Query("event1") String event1,
              @Query("event2") String event2,
              @Query("event3") String event3,
              @Query("event4") String event4,
              @Query("event5") String event5,
              @Query("event6") String event6,
              @Query("interest") String interest,
              @Query("package") String packages,
              @Query("farm_type") String farm_type,
              @Query("farm_size") String farm_size,
              @Query("date") String date
      );

      @Multipart
      @POST("save/data")
      Call<UserResponse>sendData(
              @Part("farmer_group") RequestBody farmer_group,
              @Part("name") RequestBody name,
              @Part("phone") RequestBody phone,
              @Part("email") RequestBody email,
              @Part("company") RequestBody company,
              @Part("job_role") RequestBody job_role,
              @Part("age") RequestBody age,
              @Part("region_country") RequestBody region_country,
              @Part("town_village") RequestBody town,
              @Part("event1") RequestBody event1,
              @Part("event2") RequestBody event2,
              @Part("event3") RequestBody event3,
              @Part("event4") RequestBody event4,
              @Part("event5") RequestBody event5,
              @Part("event6") RequestBody event6,
              @Part("interest") RequestBody interest,
              @Part("package") RequestBody packages,
              @Part("farm_type") RequestBody farm_type,
              @Part("farm_size") RequestBody farm_size,
              @Part("date") RequestBody date,
              @Part("time") RequestBody time
      );

      @GET("pull/data")
      Call<ReceiveUserData> retrieveData();


}
