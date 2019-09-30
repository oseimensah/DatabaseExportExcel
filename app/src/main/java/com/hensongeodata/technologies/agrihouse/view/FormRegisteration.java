package com.hensongeodata.technologies.agrihouse.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.hensongeodata.technologies.agrihouse.MyApplication;
import com.hensongeodata.technologies.agrihouse.R;
import com.hensongeodata.technologies.agrihouse.controller.BroadcastCall;
import com.hensongeodata.technologies.agrihouse.controller.request.NetworkRequestUtil;
import com.hensongeodata.technologies.agrihouse.database.DatabaseHelper;
import com.hensongeodata.technologies.agrihouse.model.DataModel;
import com.hensongeodata.technologies.agrihouse.model.Keys;
import com.hensongeodata.technologies.agrihouse.utils.Utils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FormRegisteration extends AppCompatActivity implements View.OnClickListener{

      private static final String TAG = FormRegisteration.class.getSimpleName();
      TextInputEditText edFarmerGroup,edName, edPhone, edEmail, edCompany, edOther, edFarmType, edFarmSize, edTown, edInterest, jobRoleOther;
      RadioGroup radioGroup1, radioGroup2;
      RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9, radioButton10, radioButton11;
      CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
      Button btnSubmit;
      CardView formCardView;
      ScrollView formScroll;
      MyApplication myApplication;
      NetworkRequestUtil networkRequestUtil;
      DatabaseHelper databaseHelper;
      TextView regist_er, errorSpinner1, errorSpinner2, errorSpinner3, progressText, progressClose;
      NiceSpinner niceSpinner1, niceSpinner3;
      FloatingActionButton fabSync, fabPush, fabPull, fabView;
      RelativeLayout progressRelative;

      SpinKitView progressBar;

      private FormBroadcast receiver;
      private IntentFilter filter, filter1;

      String jobRole, region;
      int tr_selected,tr_selected1;
      Boolean isFABOpen = false;

      List<DataModel> dataModels;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_form_registeration);

            init();
      }

      private void init() {


            Utils.getStoragePermission(this);
            myApplication = new MyApplication();
            networkRequestUtil = new NetworkRequestUtil(this);
            databaseHelper = new DatabaseHelper(this);
            dataModels = new ArrayList<DataModel>();
            receiver = new FormBroadcast();
            filter = new IntentFilter(BroadcastCall.DATA_SENT);
            filter1 = new IntentFilter(BroadcastCall.DATA_SAVED);

            registerReceiver(receiver, filter);
            registerReceiver(receiver, filter1);

            edFarmerGroup = findViewById(R.id.farmer_group);
            edName = findViewById(R.id.name);
            edPhone = findViewById(R.id.phone_no);
            edEmail = findViewById(R.id.email_address);
            edCompany = findViewById(R.id.organisation);
            edOther = findViewById(R.id.other);
            edFarmType = findViewById(R.id.farmer);
            edFarmSize = findViewById(R.id.farmer_size);
            edTown = findViewById(R.id.town);
            edInterest = findViewById(R.id.interest_get);
            jobRoleOther = findViewById(R.id.job_role_other);

            radioGroup1 = findViewById(R.id.radiogroup1);
            radioGroup2 = findViewById(R.id.radiogroup2);
            btnSubmit = findViewById(R.id.submit);

            radioButton1 = findViewById(R.id.radio1);
            radioButton2 = findViewById(R.id.radio2);
            radioButton3 = findViewById(R.id.radio3);
            radioButton4 = findViewById(R.id.radio4);
            radioButton5 = findViewById(R.id.radio5);
            radioButton6 = findViewById(R.id.radio6);
            radioButton7 = findViewById(R.id.radio7);
            radioButton8 = findViewById(R.id.radio8);
            radioButton9 = findViewById(R.id.radiog1);
            radioButton10 = findViewById(R.id.radiog2);
            radioButton11 = findViewById(R.id.radiog3);

            checkBox1 = findViewById(R.id.check1);
            checkBox2 = findViewById(R.id.check2);
            checkBox3 = findViewById(R.id.check3);
            checkBox4 = findViewById(R.id.check4);
            checkBox5 = findViewById(R.id.check5);
            checkBox6 = findViewById(R.id.check6);

            regist_er = findViewById(R.id.register_form);
            errorSpinner1 = findViewById(R.id.error1);
            errorSpinner2 = findViewById(R.id.error2);
            errorSpinner3 = findViewById(R.id.error3);

            niceSpinner1 = findViewById(R.id.spinner1);
            niceSpinner3 = findViewById(R.id.spinner3);

            fabSync = findViewById(R.id.sync);
            fabPull = findViewById(R.id.pull_data);
            fabPush = findViewById(R.id.send_data);
            fabView = findViewById(R.id.view_data);

            formCardView = findViewById(R.id.formCard);
            formScroll = findViewById(R.id.form_scroll);

            progressRelative = findViewById(R.id.progress_relative);
            progressText = findViewById(R.id.progress_text);
            progressBar = findViewById(R.id.progress_spin);
            progressClose = findViewById(R.id.progress_disappear);

            fabPull.hide();
            fabPush.hide();
            fabView.hide();

            edOther.setVisibility(View.GONE);
            jobRoleOther.setVisibility(View.GONE);
            edFarmType.setEnabled(false);
            edFarmSize.setEnabled(false);

            Sprite doubleBounce = new DoubleBounce();
            progressBar.setIndeterminateDrawable(doubleBounce);

            progressRelative.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

            final List<String> jobList = new LinkedList<>(Arrays.asList("--- Choose One ---", "Farmer", "AEA","Ministry, Agric","Ministry, Other", "NGO", "Producer",  "Input Dealer", "IT",
                    "Machinery and Equipment", "Irrigation", "Packaging and Processing", "Finance", "Development Partner", "Embassy","Media", "Teacher" ,"Student","Other (Please specify)"));
            niceSpinner1.attachDataSource(jobList);

            List<String> regionList = new LinkedList<>(Arrays.asList("--- Choose One ---", "Ahafo Region ", "Ashanti Region", "Bono Region", "Bono East Region ", "Central Region",
                    "Eastern Region", "Greater Accra Region", "Northern Region", "North East Region", "Oti Region", "Savannah Region", "Upper East Region", "Upper West Region ",
                    "Volta Region", "Western North Region", "Western Region"));
            niceSpinner3.attachDataSource(regionList);

            checkBox6.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                              edOther.setEnabled(true);
                              edOther.requestFocus();
                              edOther.setVisibility(View.VISIBLE);
                        }
                        else {
                              edOther.setVisibility(View.GONE);
                              edOther.setEnabled(false);
                              edOther.setText("");
                        }
                  }
            });

            niceSpinner1.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                  @Override
                  public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                        jobRole = (String) parent.getItemAtPosition(position);
                        if (niceSpinner1.getSelectedIndex() != 0){
                              errorSpinner1.setVisibility(View.GONE);
                        }
                        if (jobRole.equalsIgnoreCase("Farmer")){
                              edFarmType.setEnabled(true);
                              edFarmSize.setEnabled(true);
                        }
                        else {
                              edFarmType.setEnabled(false);
                              edFarmSize.setEnabled(false);
                        }

                        if (jobRole.equalsIgnoreCase("Other (Please specify)")){
                              jobRoleOther.setVisibility(View.VISIBLE);
                        }
                  }
            });

            niceSpinner3.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                  @Override
                  public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                        region = (String) parent.getItemAtPosition(position);
                        if (niceSpinner3.getSelectedIndex() != 0){
                              errorSpinner3.setVisibility(View.GONE);
                        }
                  }
            });

            radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (radioGroup1.getCheckedRadioButtonId() != -1){
                              radioButton1.setError(null);
                        }
                  }
            });
            radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (radioGroup2.getCheckedRadioButtonId() != -1){
                              radioButton9.setError(null);
                        }
                  }
            });

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            final String formattedDate = df.format(c);

            edPhone.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  }

                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count) {

                  }

                  @Override
                  public void afterTextChanged(Editable s) {
                        int length = s.length();
                        if (length > 9) {
//                              myApplication.toastShortMessage(getApplicationContext(), s.toString());
                              boolean isExist = databaseHelper.dataExit(getApplicationContext(), s.toString());
                              if (isExist){
                                    boolean isYesterday = databaseHelper.partExit(getApplicationContext(), s.toString(), formattedDate);
                                    if (isYesterday){
                                          AlertDialog.Builder builder = new AlertDialog.Builder(FormRegisteration.this);
                                          builder.setMessage("Participant has already Registered")
                                                  .setCancelable(false)
                                                  .setTitle("Participant Exist")
                                                  .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                              dialog.cancel();
                                                              clearAll();
                                                        }
                                                  });
                                          AlertDialog alert = builder.create();
                                          alert.show();
                                    }
                                    else {
                                          dataModels = databaseHelper.getSingleData(getApplicationContext(), s.toString());

                                          View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet, null);
                                          TextInputEditText edFarmerGroupFrag, edNameFrag, edPhoneFrag, edEmailFrag, edCompanyFrag, edJobRoleFrag,
                                                  edAgeFrag, edRegionFrag, edPackageFrag, edFarmTypeFrag, edFarmSizeFrag, edTownFrag, edInterestFrag;
                                          Button btnSave;
                                          final BottomSheetDialog mydialog = new BottomSheetDialog(FormRegisteration.this);
                                          mydialog.setContentView(view);
                                          edFarmerGroupFrag = (TextInputEditText) mydialog.findViewById(R.id.farmer_group_frag);
                                          edNameFrag = (TextInputEditText) mydialog.findViewById(R.id.name_frag);
                                          edPhoneFrag = (TextInputEditText) mydialog.findViewById(R.id.phone_no_frag);
                                          edEmailFrag = (TextInputEditText) mydialog.findViewById(R.id.email_address_frag);
                                          edCompanyFrag = (TextInputEditText) mydialog.findViewById(R.id.organisation_frag);
                                          edJobRoleFrag = (TextInputEditText) mydialog.findViewById(R.id.job_role_frag);
                                          edAgeFrag = (TextInputEditText) mydialog.findViewById(R.id.age_frag);
                                          edRegionFrag = (TextInputEditText) mydialog.findViewById(R.id.region_frag);
                                          edTownFrag = (TextInputEditText) mydialog.findViewById(R.id.town_frag);
                                          edPackageFrag = (TextInputEditText) mydialog.findViewById(R.id.packages_frag);
                                          edInterestFrag = (TextInputEditText) mydialog.findViewById(R.id.interest_frag);
                                          edFarmTypeFrag = (TextInputEditText) mydialog.findViewById(R.id.farm_type_frag);
                                          edFarmSizeFrag = (TextInputEditText) mydialog.findViewById(R.id.farm_size_frag);
                                          btnSave = (Button) mydialog.findViewById(R.id.save_frag);

                                          for (int position =0; position < dataModels.size(); position ++) {
                                                DataModel dataModel = dataModels.get(position);
                                                edFarmerGroupFrag.setText(dataModel.getFarmergroup());
                                                edNameFrag.setText(dataModel.getPerson_name());
                                                edPhoneFrag.setText(dataModel.getPhone());
                                                edEmailFrag.setText(dataModel.getEmail());
                                                edCompanyFrag.setText(dataModel.getCompany());
                                                edJobRoleFrag.setText(dataModel.getJobrole());
                                                edAgeFrag.setText(dataModel.getAge());
                                                edRegionFrag.setText(dataModel.getRegion());
                                                edTownFrag.setText(dataModel.getTown());
                                                edPackageFrag.setText(dataModel.getPackages());
                                                edInterestFrag.setText(dataModel.getInterest());
                                                edFarmTypeFrag.setText(dataModel.getFarmtype());
                                                edFarmSizeFrag.setText(dataModel.getFarmsize());

                                          }
                                          mydialog.setCanceledOnTouchOutside(false);

                                          final String fgroup = edNameFrag.getText().toString();
                                          final String fname = edNameFrag.getText().toString();
                                          final String pno = edPhoneFrag.getText().toString();
                                          final String ema = edEmailFrag.getText().toString();
                                          final String comp = edCompanyFrag.getText().toString();
                                          final String jobr = edJobRoleFrag.getText().toString();
                                          final String agee = edAgeFrag.getText().toString();
                                          final String regi = edRegionFrag.getText().toString();
                                          final String tow = edTownFrag.getText().toString();
                                          final String pack = edPackageFrag.getText().toString();
                                          final String inte = edInterestFrag.getText().toString();
                                          final String fty = edFarmTypeFrag.getText().toString();
                                          final String fsi = edFarmSizeFrag.getText().toString();

                                          Date c = Calendar.getInstance().getTime();
                                          SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                          SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            final String formattedDate = df.format(c);
                                          final String formattedTime = sdf.format(c);
                                          Log.e(TAG, "saveData: " + formattedTime );

                                          btnSave.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                      String message = " Successfully Registered." ;

                                                      databaseHelper.savedData(fgroup, fname, pno,ema, comp, jobr,
                                                              agee, regi, tow, "", "", "", "", "", "", inte,
                                                              pack, fty, fsi, formattedDate, formattedTime);

                                                      myApplication.toastLongMessage(getApplicationContext(), message);
                                                      mydialog.dismiss();
                                                      clearAll();
                                                }
                                          });
                                          mydialog.show();

                                    }
                              }else{
                                    Log.v(TAG, "afterTextChanged: " );
                              }
                        }
                  }
            });

            isFABOpen = false;

            btnSubmit.setOnClickListener(this);
            fabSync.setOnClickListener(this);
            fabPush.setOnClickListener(this);
            fabPull.setOnClickListener(this);
            fabView.setOnClickListener(this);
            progressClose.setOnClickListener(this);

      }

      private boolean validDataNow(String name, String phone, String town, String interest_data){

            if (!myApplication.textFieldEmpty(name, edName))
                  return false;
            else if (!myApplication.textFieldEmpty(phone, edPhone))
                  return false;
            else if (!myApplication.phoneNumber(phone, edPhone))
                  return false;
            else if (!myApplication.textFieldEmpty(town, edTown))
                  return false;
            else if (!myApplication.textFieldEmpty(interest_data, edInterest))
                  return false;
            else if (!myApplication.spinnerSelection(niceSpinner1)) {
                  errorSpinner1.setVisibility(View.VISIBLE);
                  errorSpinner1.requestFocus();
                  return false;
            }
            else if (!myApplication.spinnerSelection(niceSpinner3)) {
                  errorSpinner3.setVisibility(View.VISIBLE);
                  errorSpinner3.requestFocus();
                  return false;
            }
            else if (!myApplication.radioSelection(radioGroup1)) {
                  radioButton1.requestFocus();
                  radioButton1.setError("Please this field is mandatory");
                  return false;
            }
            else if (!myApplication.radioSelection(radioGroup2)) {
                  radioButton9.requestFocus();
                  radioButton9.setError("Please this field is mandatory");
                  return false;
            }
//            else if (niceSpinner1.getSelectedItem().equals("Other (Please specify)")){
//                  if (!myApplication.textFieldEmpty(name, jobRoleOther)){
//                        return false;
//                  }
//            }
            else
                  return true;
      }

      private void showFABMenu(){
            isFABOpen=true;

            fabPush.show();
            fabPull.show();
            fabView.show();
            fabPush.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
            fabPull.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
            fabView.animate().translationX(-getResources().getDimension(R.dimen.dimen_medium_two));
      }

      private void closeFABMenu(){
            isFABOpen=false;
            fabPush.animate().translationY(0);
            fabPull.animate().translationY(0);
            fabView.animate().translationY(0);
            fabPush.hide();
            fabPull.hide();
            fabView.hide();
      }

      private void sendData(){

            if (myApplication.hasNetworkConnection(getApplicationContext())){
                  boolean isInfoThere = databaseHelper.checkData(getApplicationContext());
                  Log.v(TAG, "run: Boolean " + String.valueOf(isInfoThere));

                  if (isInfoThere){
                        dataModels = databaseHelper.getdata(getApplicationContext());
                        progressBar.setVisibility(View.VISIBLE);
                        for (int position =0; position < dataModels.size(); position ++){
                              DataModel dataModel=dataModels.get(position);
                              String id = dataModel.getId();
                              String farmer_group = dataModel.getFarmergroup();
                              String name = dataModel.getPerson_name();
                              String phone = dataModel.getPhone();
                              String email = dataModel.getEmail();
                              String company = dataModel.getCompany();
                              String job_role = dataModel.getJobrole();
                              String age = dataModel.getAge();
                              String region = dataModel.getRegion();
                              String town = dataModel.getTown();
                              String event1 = dataModel.getEvent1();
                              String event2 = dataModel.getEvent2();
                              String event3 = dataModel.getEvent3();
                              String event4 = dataModel.getEvent4();
                              String event5 = dataModel.getEvent5();
                              String event6 = dataModel.getEvent6();
                              String interest = dataModel.getInterest();
                              String packages = dataModel.getPackages();
                              String farm_type = dataModel.getFarmtype();
                              String farm_size = dataModel.getFarmsize();
                              String date = dataModel.getDate();
                              String time = dataModel.getTime();

                              networkRequestUtil.sendData(id, farmer_group, name, phone, email, company,
                                      job_role, age, region, town, event1, event2, event3, event4, event5, event6,
                                      interest, packages, farm_type, farm_size, date, time);

                              if (position == (dataModels.size()-1)){
                                    myApplication.toastLongMessage(getApplicationContext(), "Data successfully sent");
                                    progressBar.setVisibility(View.GONE);
                              }

                        }
                  }
                  else
                        Log.v(TAG, "run:  No Data" );
            }
            else
                  myApplication.toastLongMessage(getApplicationContext(), "No Internet Connection");

      }

      private void retrieveData(){
            progressBar.setVisibility(View.VISIBLE);
            networkRequestUtil.fetchAllData(FormRegisteration.this);
      }

      private void saveData(){
            tr_selected = radioGroup1.getCheckedRadioButtonId();
            tr_selected1 = radioGroup2.getCheckedRadioButtonId();

            String event1 = "";
            String event2 = "";
            String event3 = "";
            String event4 = "";
            String event5 = "";
            String event6 = "";

            String name = edName.getText().toString();
            String phone = edPhone.getText().toString();
            String farmergroup = edFarmerGroup.getText().toString();
            String company = edCompany.getText().toString();
            String town = edTown.getText().toString();
            String email = edEmail.getText().toString();
//            String event6 = edOther.getText().toString();
            String farmtype = edFarmType.getText().toString();
            String farmsize = edFarmSize.getText().toString();
            String interest_now = edInterest.getText().toString();
            Log.e(TAG, "saveData: "+ interest_now );

//            if(checkBox1.isChecked())
//                  event1=checkBox1.getText().toString();
//            else
//                  event1 = "";
//            if(checkBox2.isChecked())
//                  event2=checkBox2.getText().toString();
//            else
//                  event2 = "";
//            if(checkBox3.isChecked())
//                  event3=checkBox3.getText().toString();
//            else
//                  event3 = "";
//            if(checkBox4.isChecked())
//                  event4=checkBox4.getText().toString();
//            else
//                  event4 = "";
//            if(checkBox5.isChecked())
//                  event5=checkBox5.getText().toString();
//            else
//                  event5 = "";

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            String formattedDate = df.format(c);
//            String formattedDate = "25-09-2019";
            String formattedTime = sdf.format(c);

            if (validDataNow(name, phone, town, interest_now)){

                  RadioButton radio1 = (RadioButton) findViewById(tr_selected);
                  String age = radio1.getText().toString();
                  RadioButton radio2 = (RadioButton) findViewById(tr_selected1) ;
                  String packages = radio2.getText().toString();
                  if (niceSpinner1.getSelectedItem().equals("Other (Please specify)"))
                        jobRole = jobRoleOther.getText().toString();

                  String message = " Successfully Registered." ;

                  databaseHelper.savedData(farmergroup, name, phone,email, company, jobRole,
                          age, region, town, event1, event2, event3, event4, event5, event6, interest_now,
                          packages, farmtype, farmsize, formattedDate, formattedTime);

                  myApplication.toastLongMessage(getApplicationContext(), message);
                  clearAll();
            }

      }

      private void clearAll(){
            edFarmerGroup.setText("");
            edName.setText("");
            edPhone.setText("");
            edEmail.setText("");
            edCompany.setText("");
            edOther.setText("");
            edFarmType.setText("");
            edFarmSize.setText("");
            edTown.setText("");
            jobRoleOther.setText("");
            edInterest.setText("");

            radioGroup1.clearCheck();
            radioGroup2.clearCheck();

            checkBox1.setChecked(false);
            checkBox2.setChecked(false);
            checkBox3.setChecked(false);
            checkBox4.setChecked(false);
            checkBox5.setChecked(false);
            checkBox6.setChecked(false);

            niceSpinner1.setSelectedIndex(0);
            niceSpinner3.setSelectedIndex(0);

            formScroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                  @Override
                  public void onGlobalLayout() {
                        formScroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        formScroll.fullScroll(View.FOCUS_UP);
                  }
            });

      }

      @Override
      public void onClick(View v) {

            if (v == btnSubmit){
                  saveData();
            }

            if (v == fabSync){
                  if (!isFABOpen){
                        showFABMenu();
                  }
                  else {
                        closeFABMenu();
                  }
            }

            if (v == fabPush) {
                  sendData();
                  closeFABMenu();
            }

            if (v == fabPull) {
                  retrieveData();
                  closeFABMenu();
            }

            if (v == progressClose)
                  progressRelative.setVisibility(View.GONE);

            if (v == fabView){
                  finish();
                  startActivity(new Intent(getApplicationContext(), History.class));
            }

      }

      @Override
      public void onBackPressed() {
            super.onBackPressed();
      }

      @Override
      protected void onResume() {
            super.onResume();
            registerReceiver(receiver, filter);
            registerReceiver(receiver, filter1);
      }

      @Override
      protected void onDestroy() {
            super.onDestroy();
            unregisterReceiver(receiver);
      }

      class FormBroadcast extends BroadcastReceiver{

            @Override
            public void onReceive(Context context, Intent intent) {
                  if (intent.getAction().equalsIgnoreCase(BroadcastCall.DATA_SENT)){
                        String message = intent.getStringExtra("message");
                        int status = intent.getIntExtra("status", 0);

                        if (status == Keys.STATUS_SUCCESS){
                              databaseHelper.updateSavedData(message);
                        }
                        else if (status == Keys.STATUS_FAIL){

                        }
                  }
                  if (intent.getAction().equalsIgnoreCase(BroadcastCall.DATA_SAVED)){
                        String message = intent.getStringExtra("message");
                        int status = intent.getIntExtra("status", 0);

                        if (status == Keys.STATUS_SUCCESS){
                              myApplication.toastLongMessage(FormRegisteration.this, message);
                              progressBar.setVisibility(View.GONE);
                              progressRelative.setVisibility(View.VISIBLE);

                        }
                        else if (status == Keys.STATUS_FAIL){
                             progressBar.setVisibility(View.GONE);
                             View view = findViewById(R.id.formRelative);
                              Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                              snackbar.show();
                              // add snackbar
                        }
                  }

            }
      }
}
