package com.hensongeodata.technologies.agrihouse.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.hensongeodata.technologies.agrihouse.R;
import com.hensongeodata.technologies.agrihouse.adapter.RecyclerAdapter;
import com.hensongeodata.technologies.agrihouse.database.DatabaseHelper;
import com.hensongeodata.technologies.agrihouse.model.DataModel;
import com.hensongeodata.technologies.agrihouse.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

      private static final String TAG = History.class.getSimpleName();
      DatabaseHelper databaseHelper;
      RecyclerAdapter recyclerAdapter;
      List<DataModel> dataModels;

      ImageView toolBack;
      RecyclerView historyRecycler;
      TextView count;
      Button export;

      String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
      SQLiteToExcel sqliteToExcel;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            init();
      }

      private void init() {

            databaseHelper = new DatabaseHelper(this);
            dataModels = new ArrayList<DataModel>();

            toolBack = findViewById(R.id.toolbar_back);
            historyRecycler = findViewById(R.id.history);
            count = findViewById(R.id.textCount);
            export = findViewById(R.id.btn_export);

            String total = databaseHelper.getCount();
            count.setText(total);

            toolBack.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), FormRegisteration.class));
                  }
            });
            export.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        exportData(v);
                  }
            });

            File file = new File(directory_path);
            if (!file.exists()) {
                  Log.v(TAG +" file ", String.valueOf(file.mkdirs()));
            }

            loadList();

      }

      @SuppressLint("WrongConstant")
      private void loadList() {
            dataModels = databaseHelper.fetchAllData();
            historyRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerAdapter = new RecyclerAdapter(History.this, dataModels);
            historyRecycler.setAdapter(recyclerAdapter);
      }

      private void exportData(final View view){
            sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DatabaseHelper.DATABASE_NAME, directory_path);
            sqliteToExcel.exportAllTables("AgriHouse.xls", new SQLiteToExcel.ExportListener() {
                  @Override
                  public void onStart() {

                  }
                  @Override
                  public void onCompleted(String filePath) {
                        Utils.showSnackBar(view, "Successfully Exported");
                  }
                  @Override
                  public void onError(Exception e) {

                  }
            });
      }

      @Override
      public void onBackPressed() {
            super.onBackPressed();
            finish();
            startActivity(new Intent(getApplicationContext(), FormRegisteration.class));
      }

}
