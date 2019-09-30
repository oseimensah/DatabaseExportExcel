package com.hensongeodata.technologies.agrihouse.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hensongeodata.technologies.agrihouse.MyApplication;
import com.hensongeodata.technologies.agrihouse.R;
import com.hensongeodata.technologies.agrihouse.controller.BroadcastCall;
import com.hensongeodata.technologies.agrihouse.controller.request.NetworkRequestUtil;
import com.hensongeodata.technologies.agrihouse.model.Keys;
import com.hensongeodata.technologies.agrihouse.model.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

      private static final String TAG = LoginActivity.class.getSimpleName();
      EditText u_email,u_password;
      Button btnLogin;
      MyApplication myApplication;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            init();
      }

      private void init() {
            u_email =findViewById(R.id.input_email);
            u_password = findViewById(R.id.input_password);
            btnLogin = findViewById(R.id.btn_login);

            myApplication = new MyApplication();

            btnLogin.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        String email = u_email.getText().toString();
                        String password = u_password.getText().toString();
                        doLogin(email, password);
                  }
            });

      }

      private void doLogin(String email, String password){
                  if (!myApplication.textFieldEmpty(email, u_email))
                        Log.v(TAG, "doLogin: empty");
                  else if (!myApplication.isEmailValid(email, u_email))
                        Log.v(TAG, "doLogin: invalid");
                  else if (!myApplication.textFieldEmpty(password, u_password))
                        Log.v(TAG, "password empty");
                  else if (!email.equals("user@henson.com")){
                        Log.v(TAG, "doLogin: " );
                        u_email.setText("");
                        myApplication.toastLongMessage(getApplicationContext(), "Incorrect Username");
                  }
                  else if (!password.equals("password")){
                        myApplication.toastLongMessage(getApplicationContext(), "Incorrect Password ");
                        u_password.setText("");
                  }
                  else {
                        SharedPrefManager.getInstance(getApplicationContext()).userIn("username", "kwadwo", "mensah");
                        finish();
                        startActivity(new Intent(getApplicationContext(), FormRegisteration.class));
                  }
      }

      @Override
      protected void onStart() {
            super.onStart();
            if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                  finish();
                  startActivity(new Intent(getApplicationContext(), FormRegisteration.class));
            }
      }

      @Override
      public void onBackPressed() {
            super.onBackPressed();
      }

      @Override
      protected void onResume() {
            super.onResume();
      }

      @Override
      protected void onDestroy() {
            super.onDestroy();
      }

}
