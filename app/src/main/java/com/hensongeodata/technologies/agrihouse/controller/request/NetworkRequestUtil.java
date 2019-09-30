package com.hensongeodata.technologies.agrihouse.controller.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.hensongeodata.technologies.agrihouse.MyApplication;
import com.hensongeodata.technologies.agrihouse.controller.BroadcastCall;
import com.hensongeodata.technologies.agrihouse.database.DatabaseHelper;
import com.hensongeodata.technologies.agrihouse.model.DataReceived;
import com.hensongeodata.technologies.agrihouse.model.Keys;
import com.hensongeodata.technologies.agrihouse.model.ReceiveUserData;
import com.hensongeodata.technologies.agrihouse.model.SharedPrefManager;
import com.hensongeodata.technologies.agrihouse.model.UserResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRequestUtil {

      private String TAG = NetworkRequestUtil.class.getSimpleName();
      private Context context;
      public ProgressDialog dialog;
      MyApplication myApplication;
      DatabaseHelper databaseHelper;

      public NetworkRequestUtil(Context context) {
            this.context = context;
      }

      @NonNull
      public static RequestBody createPartFromString(String descriptionString) {
            return RequestBody.create(
                    okhttp3.MultipartBody.FORM, descriptionString);
      }

      public void loginRequest(String email, String password){

            myApplication = new MyApplication();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Logging In...");
            dialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Keys.CENTRAL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Api api = retrofit.create(Api.class);
            Call<UserResponse> call = api.userLogin(email, password);

            call.enqueue(new Callback<UserResponse>() {
                  @Override
                  public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                              // Check login response for error
                              if (!response.body().getError()){

                                    Log.e(TAG, "onResponse: " + response.raw() );

                                    BroadcastCall.publishSignin(context, Keys.STATUS_SUCCESS, "email");
                                    SharedPrefManager.getInstance(context).userLogin(response.body().getUser());
                              }
                              else
                                    BroadcastCall.publishSignin(context, Keys.STATUS_FAIL, response.body().getMessage());

                        } else {
                              // Handle other response codes
                              Log.e(TAG, "onResponse: " + response.raw() );
                              BroadcastCall.publishSignin(context, Keys.STATUS_FAIL, "Error at server side");
                        }
                  }

                  @Override
                  public void onFailure(Call<UserResponse> call, Throwable t) {
                        dialog.dismiss();
                        Log.v(TAG, "onFailure: " + t.getMessage() );
                        BroadcastCall.publishSignin(context, Keys.STATUS_FAIL, t.getMessage());
                  }
            });

      }

      public void sendData(final String id, String farmer_group, String name, String phone, String email, String company,
                           String job_role, String age, String region_country,String town, String event1, String event2, String event3,
                           String event4, String event5, String event6, String interest, String packages, String farm_type,
                           String farm_size, String date, String time)
      {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Keys.BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Api api = retrofit.create(Api.class);
            Call<UserResponse> call = api.sendData(
                    farmer_group == null ? createPartFromString(""):createPartFromString(farmer_group),
                    name == null ? createPartFromString(""): createPartFromString(name),
                    phone == null ? createPartFromString(""):createPartFromString(phone),
                    email == null ? createPartFromString(""):createPartFromString(email),
                    company == null ? createPartFromString(""):createPartFromString(company),
                    job_role == null ? createPartFromString(""):createPartFromString(job_role),
                    age == null ? createPartFromString(""):createPartFromString(age),
                    region_country == null ? createPartFromString(""):createPartFromString(region_country),
                    town == null ? createPartFromString(""):createPartFromString(town),
                    event1 == null ? createPartFromString(""):createPartFromString(event1),
                    event2 == null ? createPartFromString(""):createPartFromString(event2),
                    event3 == null ? createPartFromString(""):createPartFromString(event3),
                    event4 == null ? createPartFromString(""):createPartFromString(event4),
                    event5 == null ? createPartFromString(""):createPartFromString(event5),
                    event6 == null ? createPartFromString(""):createPartFromString(event6),
                    interest == null ? createPartFromString(""):createPartFromString(interest),
                    packages == null ? createPartFromString(""):createPartFromString(packages),
                    farm_type == null ? createPartFromString(""):createPartFromString(farm_type),
                    farm_size == null ? createPartFromString(""):createPartFromString(farm_size),
                    date == null ? createPartFromString(""):createPartFromString(date),
                    time == null ? createPartFromString(""):createPartFromString(time));

            call.enqueue(new Callback<UserResponse>() {
                  @Override
                  public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()){
                              if (!response.body().getError()){
                                    BroadcastCall.publishSentData(context, Keys.STATUS_SUCCESS, id);
                              }else{
                                    String message = response.body().getMessage();
                                    BroadcastCall.publishSentData(context, Keys.STATUS_FAIL, message);
                              }
                        }else {
                              String message = response.message();
                              BroadcastCall.publishSentData(context, Keys.STATUS_FAIL, message);
                        }
                  }

                  @Override
                  public void onFailure(Call<UserResponse> call, Throwable t) {
                        BroadcastCall.publishSentData(context, Keys.STATUS_FAIL, t.getMessage());
                  }
            });
      }

      private void writeTv( Context mcontext, String response){

            if (response != null) {
                  try {
                        //getting the whole json object from the response
                        JSONObject obj = new JSONObject(response);
                        JSONArray dataArray = obj.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {

                              JSONObject dataobj = dataArray.getJSONObject(i);

                              String farmer_group = dataobj.getString("farmer_group");
                              String name = dataobj.getString("name");
                              String phone = dataobj.getString("phone");
                              String email = dataobj.getString("email");
                              String company = dataobj.getString("company");
                              String job_role = dataobj.getString("job_role");
                              String age = dataobj.getString("age");
                              String region_country = dataobj.getString("region_country");
                              String town = dataobj.getString("town_village");
                              String event1 = dataobj.getString("event1");
                              String event2 = dataobj.getString("event2");
                              String event3 = dataobj.getString("event3");
                              String event4 = dataobj.getString("event4");
                              String event5 = dataobj.getString("event5");
                              String event6 = dataobj.getString("event6");
                              String interest = dataobj.getString("interest");
                              String packages = dataobj.getString("package");
                              String farm_type = dataobj.getString("farm_type");
                              String farm_size = dataobj.getString("farm_size");
                              String date = dataobj.getString("date");
                              String time = dataobj.getString("time");

                              databaseHelper = new DatabaseHelper(mcontext);
                              databaseHelper.saveFromOnline(mcontext, farmer_group, name, phone, email, company, job_role, age, region_country,town, event1,
                                      event2, event3, event4, event5, event6, interest, packages, farm_type, farm_size, date, time);

                        }

                  } catch (JSONException e) {
                        e.printStackTrace();
                  }
            }
            else {
                  Log.v(TAG, "writeTv: " + response);
                  BroadcastCall.publishSavedData(mcontext, Keys.STATUS_FAIL, response);
            }

      }

      public void fetchAllData(final Context mContext){
            final String url = Keys.BASE + "pull/data";
            class DoFetchData extends AsyncTask<String, Void, String>{

                  @Override
                  protected String doInBackground(String... strings) {

                        HttpHandler handler = new HttpHandler();
                        String jsonString = handler.makeServiceCall(url);

                        Log.v(TAG, "Response from url: " + jsonString);

                        writeTv(mContext, jsonString);

                        BroadcastCall.publishSavedData(mContext, Keys.STATUS_SUCCESS, "success");

                        return null;
                  }

                  @Override
                  protected void onPreExecute() {
                        super.onPreExecute();
                  }

                  @Override
                  protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                  }

            }

            DoFetchData doFetchData = new DoFetchData();
            doFetchData.execute();
      }

}
