package com.hensongeodata.technologies.agrihouse.controller;

import android.content.Context;
import android.content.Intent;

public class BroadcastCall {

      public static String SIGN_IN = "com.hensongeodata.broadcast.signin";
      public static String CHANGE_PASSWORD = "com.hensongeodata.broadcast.change_password";
      public static String DATA_SAVED = "com.hensongeodata.broadcast.server..saved";
      public static String DATA_SENT= "com.hensongeodata.broadcast.server.sent";

      public static void publishSignin(Context context,int status,String message){
            Intent intent = new Intent(SIGN_IN);
            intent.setAction(SIGN_IN);
            intent.putExtra("status",status);
            intent.putExtra("message", message);
            context.sendBroadcast(intent);
      }

      public static void publishChangePWD(Context context,int status,String message){
            Intent intent = new Intent(CHANGE_PASSWORD);
            intent.setAction(CHANGE_PASSWORD);
            intent.putExtra("status",status);
            intent.putExtra("message", message);
            context.sendBroadcast(intent);
      }

      public static void publishSavedData(Context context, int status, String message){
            Intent intent = new Intent(DATA_SAVED);
            intent.setAction(DATA_SAVED);
            intent.putExtra("status", status);
            intent.putExtra("message", message);
            context.sendBroadcast(intent);
      }

      public static void publishSentData(Context context, int status, String message){
            Intent intent = new Intent(DATA_SENT);
            intent.setAction(DATA_SENT);
            intent.putExtra("status",status);
            intent.putExtra("message", message);
            context.sendBroadcast(intent);
      }

}
