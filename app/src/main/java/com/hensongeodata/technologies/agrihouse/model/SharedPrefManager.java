package com.hensongeodata.technologies.agrihouse.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {

      private static SharedPrefManager mInstance;
      private static Context mCtx;

      private static final String SHARED_PREF_NAME = "gssenumerator";

      private static final String KEY_USER_FIRSTNAME = "keyenumfname";
      private static final String KEY_USER_LASTNAME = "keyenumlname";
      private static final String KEY_USER_EMAIL = "keyenumea";
      private static final String KEY_USER_EA = "keyenumsuper";
      private static final String KEY_DB_COUNT = "keydbcount";

      private static final String KEY_LATITUDE = "keylatitude";
      private static final String KEY_LONGITUDE = "keylongitude";

      public SharedPrefManager(Context context) {
            mCtx = context;
      }

      public static synchronized SharedPrefManager getInstance(Context context){
            if (mInstance == null){
                  mInstance = new SharedPrefManager(context);
            }
            return mInstance;
      }

      public boolean userIn(String email, String firstname, String lastname){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USER_EMAIL, email);
            editor.putString(KEY_USER_FIRSTNAME, firstname);
            editor.putString(KEY_USER_LASTNAME, lastname);

            editor.apply();

            return true;
      }

      public boolean isLoggedIn() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
                  return true;
            return false;
      }

      public boolean dbCount(String count){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_DB_COUNT, count);
            editor.apply();

            return true;
      }

      public boolean userLogin(User user) {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USER_EMAIL, user.getEmail());
            editor.putString(KEY_USER_FIRSTNAME, user.getFirst_name());
            editor.putString(KEY_USER_LASTNAME, user.getLast_name());
            editor.putString(KEY_USER_EA, user.getEa());
            editor.apply();

            return true;
      }

      public List<String> getLatLon(){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            List<String> latlon = new ArrayList<>();
            latlon.add(sharedPreferences.getString(KEY_LATITUDE, null));
            latlon.add(sharedPreferences.getString(KEY_LONGITUDE, null));

            return latlon;
      }

      public List<String> getProfile(){

            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            List<String> user = new ArrayList<>();
            user.add(sharedPreferences.getString(KEY_USER_EMAIL, null));
            user.add(sharedPreferences.getString(KEY_USER_FIRSTNAME, null));
            user.add(sharedPreferences.getString(KEY_USER_LASTNAME, null));
            user.add(sharedPreferences.getString(KEY_USER_EA, null));

            return user;
      }

      public String dbCountString(){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_DB_COUNT, null);
      }

      public boolean logout() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            return true;
      }
}
