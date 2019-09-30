package com.hensongeodata.technologies.agrihouse.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

public class Utils {

      private static final String TAG = Utils.class.getSimpleName();

      public static void showSnackBar(View view, String message) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
      }

      public static void getStoragePermission(Activity context){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                  if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                          == PackageManager.PERMISSION_GRANTED)
                        Log.v(TAG, "getStoragePermission: storage permission granted");
                  else {
                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        Log.v(TAG, "getStoragePermission: storage permission granting");
                  }
            }
      }

}
