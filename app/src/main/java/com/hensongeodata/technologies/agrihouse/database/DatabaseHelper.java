package com.hensongeodata.technologies.agrihouse.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hensongeodata.technologies.agrihouse.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

      private static final String TAG = DatabaseHelper.class.getSimpleName();
      Context mCxt;

      //      Database Name
      public static final String DATABASE_NAME = "database_agrihouse";
      // Database Version
      private static final int DATABASE_VERSION = 1;
      // Define database and table properties
      private static final String TABLE_AGRI = "agrihouse";

      private static String ID = "id";
      private static String SENT = "sent";

      //Field fields
      private static String FARMER_GROUP = "farmer_group";
      private static String NAME = "name";
      private static String PHONE = "phone";
      private static String USEREMAIL = "user_email";
      private static String COMPANY = "company";
      private static String JOB_ROLE = "job_role";
      private static String AGE = "age";
      private static String REGION = "region";
      private static String TOWN = "town";
      private static String EVENT1 = "event1";
      private static String EVENT2 = "event2";
      private static String EVENT3 = "event3";
      private static String EVENT4 = "event4";
      private static String EVENT5 = "event5";
      private static String EVENT6 = "event6";
      private static String INTEREST = "interest";
      private static String PACKAGES = "packages";
      private static String FARM_TYPE = "farm_type";
      private static String FARM_SIZE = "farm_size";
      private static String DATE = "date";
      private static String TIME = "time";

      @Override
      public void onCreate(SQLiteDatabase db) {
            createTable(db, TABLE_AGRI);
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            if (newVersion > oldVersion) {
//                  db.execSQL("ALTER TABLE " + TABLE_AGRI + " ADD COLUMN " + TOWN + " VARCHAR ");
//            }
      }

      public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      private boolean createTable(SQLiteDatabase db, String tableName) {
            if (tableName != null) {
                  if (tableName.trim().equalsIgnoreCase(TABLE_AGRI)) {
                        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                                + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "" + FARMER_GROUP + " VARCHAR," +
                                "" + NAME + " VARCHAR," +
                                "" + PHONE + " VARCHAR," +
                                "" + USEREMAIL + " VARCHAR," +
                                "" + COMPANY + " VARCHAR," +
                                "" + JOB_ROLE + " VARCHAR," +
                                "" + AGE + " VARCHAR," +
                                "" + REGION + " VARCHAR," +
                                "" + TOWN + " VARCHAR," +
                                "" + EVENT1 + " VARCHAR," +
                                "" + EVENT2 + " VARCHAR," +
                                "" + EVENT3 + " VARCHAR," +
                                "" + EVENT4 + " VARCHAR," +
                                "" + EVENT5 + " VARCHAR," +
                                "" + EVENT6 + " VARCHAR," +
                                "" + INTEREST + " VARCHAR," +
                                "" + PACKAGES + " VARCHAR," +
                                "" + FARM_TYPE + " VARCHAR," +
                                "" + FARM_SIZE + " VARCHAR," +
                                "" + DATE + " VARCHAR," +
                                "" + TIME + " VARCHAR," +
                                "" + SENT + " INTEGER);");

                        return true;
                  }
            }
            return false;
      }

      public void savedData(String farmergroup, String name, String phone, String email, String company, String jobrole,
                            String age, String region, String town, String event1, String event2, String event3, String event4, String event5, String event6,
                            String interest, String packages, String farmtype, String farmsize, String date, String time)
      {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(FARMER_GROUP, farmergroup);
            cv.put(NAME, name);
            cv.put(PHONE, phone);
            cv.put(USEREMAIL, email);
            cv.put(COMPANY, company);
            cv.put(JOB_ROLE, jobrole);
            cv.put(AGE, age);
            cv.put(REGION, region);
            cv.put(TOWN, town);
            cv.put(EVENT1, event1);
            cv.put(EVENT2, event2);
            cv.put(EVENT3, event3);
            cv.put(EVENT4, event4);
            cv.put(EVENT5, event5);
            cv.put(EVENT6, event6);
            cv.put(INTEREST, interest);
            cv.put(PACKAGES, packages);
            cv.put(FARM_TYPE, farmtype);
            cv.put(FARM_SIZE, farmsize);
            cv.put(DATE, date);
            cv.put(TIME, time);
            cv.put(SENT, 0);

            db.insert(TABLE_AGRI, null, cv);
            db.close();

      }

      public boolean checkData(Context mcontext){

            boolean result = false;
            DatabaseHelper formdb = null;
            formdb = new DatabaseHelper(mcontext);
            SQLiteDatabase db = formdb.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_AGRI + " WHERE " + SENT + " = '0';" , null);

            if (cursor.moveToFirst()){
                  Log.e(TAG, "checkData: exit" );
                  result = true;
            }

            cursor.close();
            db.close();
            return result;

      }

      public void updateSavedData(String sub_id){
            SQLiteDatabase db = getWritableDatabase();

            if (sub_id != null) {

                  Log.e(TAG, "update: ID is -> " + sub_id );
                  ContentValues contentValues = new ContentValues();
                  contentValues.put(SENT, 1);
                  db.update(TABLE_AGRI, contentValues, ID + " = ?", new String[]{sub_id});
                  Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_AGRI +  " WHERE " + ID + " = '" + Integer.parseInt(sub_id) + "';", null);
                  int sent = cursor.getColumnIndexOrThrow(SENT);
                  int id = cursor.getColumnIndexOrThrow(ID);
                  Log.e(TAG, "updateSavedData: Sent is " +sent +", " + id );

                  cursor.close();
            }
            db.close();

      }

      public boolean partExit(Context mcontext, String phone, String date){
            boolean result = false;
            DatabaseHelper formdb = null;
            formdb = new DatabaseHelper(mcontext);
            SQLiteDatabase db = formdb.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_AGRI + " WHERE " + PHONE + " = '" + phone + "' AND " + DATE + " = '" + date + "';", null);

            if (cursor.moveToFirst()){
                  result = true;
            }

            cursor.close();
            db.close();
            return result;
      }

      public boolean dataExit(Context mcontext, String phone){
            boolean result = false;
            DatabaseHelper formdb = null;
            formdb = new DatabaseHelper(mcontext);
            SQLiteDatabase db = formdb.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_AGRI + " WHERE " + PHONE + " = '" + phone + "';", null);

            if (cursor.moveToFirst()){
                  result = true;
            }

            cursor.close();
            db.close();
            return result;
      }

      public List<DataModel> getSingleData(Context mCtx, String phone){
            DatabaseHelper formdb = null;
            formdb = new DatabaseHelper(mCtx);

            List<DataModel> data = new ArrayList<>();
            SQLiteDatabase db = formdb.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_AGRI + " WHERE " + PHONE + " = '" + phone + "';", null);
            DataModel dataModel = null;

            while (cursor.moveToNext()){
                  dataModel = new DataModel();
                 dataModel.setFarmergroup(cursor.getString(cursor.getColumnIndexOrThrow(FARMER_GROUP)));
                 dataModel.setPerson_name(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                 dataModel.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(PHONE)));
                 dataModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(USEREMAIL)));
                 dataModel.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COMPANY)));
                 dataModel.setJobrole(cursor.getString(cursor.getColumnIndexOrThrow(JOB_ROLE)));
                 dataModel.setAge(cursor.getString(cursor.getColumnIndexOrThrow(AGE)));
                 dataModel.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(REGION)));
                 dataModel.setTown(cursor.getString(cursor.getColumnIndexOrThrow(TOWN)));
                 dataModel.setInterest(cursor.getString(cursor.getColumnIndexOrThrow(INTEREST)));
                 dataModel.setPackages(cursor.getString(cursor.getColumnIndexOrThrow(PACKAGES)));
                 dataModel.setFarmtype(cursor.getString(cursor.getColumnIndexOrThrow(FARM_TYPE)));
                 dataModel.setFarmsize(cursor.getString(cursor.getColumnIndexOrThrow(FARM_SIZE)));

                 data.add(dataModel);
            }

            cursor.close();
            db.close();
            return data;
      }

      public List<DataModel> getdata(Context mcontext) {

            DatabaseHelper formdb = null;
            formdb = new DatabaseHelper(mcontext);

            List<DataModel> data = new ArrayList<>();
            SQLiteDatabase db = formdb.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_AGRI + " WHERE " + SENT + " = '0';" , null);
            StringBuilder stringBuffer = new StringBuilder();
            DataModel dataModel = null;
            while (cursor.moveToNext()) {
                  dataModel = new DataModel();
                  String cid = cursor.getString(cursor.getColumnIndexOrThrow(ID));
                  String farmergroup = cursor.getString(cursor.getColumnIndexOrThrow(FARMER_GROUP));
                  String person_name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
                  String phone = cursor.getString(cursor.getColumnIndexOrThrow(PHONE));
                  String email = cursor.getString(cursor.getColumnIndexOrThrow("user_email"));
                  String company = cursor.getString(cursor.getColumnIndexOrThrow("company"));
                  String jobrole = cursor.getString(cursor.getColumnIndexOrThrow("job_role"));
                  String age = cursor.getString(cursor.getColumnIndexOrThrow("age"));
                  String region = cursor.getString(cursor.getColumnIndexOrThrow("region"));
//                  String town = cursor.getString(cursor.getColumnIndexOrThrow("town"));
                  String event1 = cursor.getString(cursor.getColumnIndexOrThrow(EVENT1));
                  String event2 = cursor.getString(cursor.getColumnIndexOrThrow("event2"));
                  String event3 = cursor.getString(cursor.getColumnIndexOrThrow("event3"));
                  String event4 = cursor.getString(cursor.getColumnIndexOrThrow("event4"));
                  String event5 = cursor.getString(cursor.getColumnIndexOrThrow("event5"));
                  String event6 = cursor.getString(cursor.getColumnIndexOrThrow("event6"));
                  String interest = cursor.getString(cursor.getColumnIndexOrThrow("interest"));
                  String packages = cursor.getString(cursor.getColumnIndexOrThrow("packages"));
                  String farmtype = cursor.getString(cursor.getColumnIndexOrThrow("farm_type"));
                  String farmsize = cursor.getString(cursor.getColumnIndexOrThrow("farm_size"));
                  String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE));
//                  String time = cursor.getString(cursor.getColumnIndexOrThrow(TIME));

                  dataModel.setId(cid);
                  dataModel.setFarmergroup(farmergroup);
                  dataModel.setPerson_name(person_name);
                  dataModel.setPhone(phone);
                  dataModel.setEmail(email);
                  dataModel.setCompany(company);
                  dataModel.setJobrole(jobrole);
                  dataModel.setAge(age);
                  dataModel.setRegion(region);
//                  dataModel.setTown(town);
                  dataModel.setEvent1(event1);
                  dataModel.setEvent2(event2);
                  dataModel.setEvent3(event3);
                  dataModel.setEvent4(event4);
                  dataModel.setEvent5(event5);
                  dataModel.setEvent6(event6);
                  dataModel.setInterest(interest);
                  dataModel.setPackages(packages);
                  dataModel.setFarmtype(farmtype);
                  dataModel.setFarmsize(farmsize);
                  dataModel.setDate(date);
//                  dataModel.setTime(time);
                  stringBuffer.append(dataModel);
                  data.add(dataModel);
            }

            cursor.close();
            db.close();

            return data;
      }

      public List<DataModel> fetchAllData(){
            SQLiteDatabase db = this.getWritableDatabase();
            List<DataModel> all_data = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_AGRI + ";", null);
            StringBuilder stringBuffer = new StringBuilder();
            DataModel dataModel = null;

            while (cursor.moveToNext()){

                  dataModel = new DataModel();

                  dataModel.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                  dataModel.setFarmergroup(cursor.getString(cursor.getColumnIndexOrThrow(FARMER_GROUP)));
                  dataModel.setPerson_name(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                  dataModel.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(PHONE)));
                  dataModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(USEREMAIL)));
                  dataModel.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COMPANY)));
                  dataModel.setJobrole(cursor.getString(cursor.getColumnIndexOrThrow(JOB_ROLE)));
                  dataModel.setAge(cursor.getString(cursor.getColumnIndexOrThrow(AGE)));
                  dataModel.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(REGION)));
                  dataModel.setTown(cursor.getString(cursor.getColumnIndexOrThrow(TOWN)));
                  dataModel.setEvent1(cursor.getString(cursor.getColumnIndexOrThrow(EVENT1)));
                  dataModel.setEvent2(cursor.getString(cursor.getColumnIndexOrThrow(EVENT2)));
                  dataModel.setEvent3(cursor.getString(cursor.getColumnIndexOrThrow(EVENT3)));
                  dataModel.setEvent4(cursor.getString(cursor.getColumnIndexOrThrow(EVENT4)));
                  dataModel.setEvent5(cursor.getString(cursor.getColumnIndexOrThrow(EVENT5)));
                  dataModel.setEvent6(cursor.getString(cursor.getColumnIndexOrThrow(EVENT6)));
                  dataModel.setInterest(cursor.getString(cursor.getColumnIndexOrThrow(INTEREST)));
                  dataModel.setPackages(cursor.getString(cursor.getColumnIndexOrThrow(PACKAGES)));
                  dataModel.setFarmtype(cursor.getString(cursor.getColumnIndexOrThrow(FARM_TYPE)));
                  dataModel.setFarmsize(cursor.getString(cursor.getColumnIndexOrThrow(FARM_SIZE)));
                  dataModel.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                  dataModel.setTime(cursor.getString(cursor.getColumnIndexOrThrow(TIME)));

                  Log.e(TAG, "fetchAllData: " + dataModel );

                  stringBuffer.append(dataModel);
                  all_data.add(dataModel);
            }

            cursor.close();
            db.close();

            return all_data;

      }

      public String getCount(){
            SQLiteDatabase db = this.getWritableDatabase();
            String count = null;
            Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_AGRI + ";", null);

            int mycount = cursor.getCount();

            count = String.valueOf(mycount);

            cursor.close();
            return count;
      }

      public void saveFromOnline( Context context, String farmergroup, String name, String phone, String email, String company, String jobrole,
                                 String age, String region, String town, String event1, String event2, String event3, String event4, String event5, String event6,
                                 String interest, String packages, String farmtype, String farmsize, String date, String time)
      {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(FARMER_GROUP, farmergroup);
            cv.put(NAME, name);
            cv.put(PHONE, phone);
            cv.put(USEREMAIL, email);
            cv.put(COMPANY, company);
            cv.put(JOB_ROLE, jobrole);
            cv.put(AGE, age);
            cv.put(REGION, region);
            cv.put(TOWN, town);
            cv.put(EVENT1, event1);
            cv.put(EVENT2, event2);
            cv.put(EVENT3, event3);
            cv.put(EVENT4, event4);
            cv.put(EVENT5, event5);
            cv.put(EVENT6, event6);
            cv.put(INTEREST, interest);
            cv.put(PACKAGES, packages);
            cv.put(FARM_TYPE, farmtype);
            cv.put(FARM_SIZE, farmsize);
            cv.put(DATE, date);
            cv.put(TIME, time);
            cv.put(SENT, 1);

            boolean isExist = partExit(context, phone, date);
            if (isExist){
                  Log.e(TAG, "saveFromOnline: Part exist ooooo" );
            }else {
                  Log.e(TAG, "saveFromOnline: " + phone + " and " + date + " exist");
                  db.insert(TABLE_AGRI, null, cv);
            }

            db.close();
      }

}
