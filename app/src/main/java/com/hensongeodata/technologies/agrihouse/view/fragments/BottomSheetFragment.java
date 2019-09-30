package com.hensongeodata.technologies.agrihouse.view.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.hensongeodata.technologies.agrihouse.MyApplication;
import com.hensongeodata.technologies.agrihouse.R;
import com.hensongeodata.technologies.agrihouse.database.DatabaseHelper;
import com.hensongeodata.technologies.agrihouse.model.DataModel;
import com.hensongeodata.technologies.agrihouse.view.FormRegisteration;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetFragment extends Fragment {

      private static String TAG = BottomSheetFragment.class.getSimpleName();
      View bottomSheetView;
      List<DataModel> dataModels;
      DatabaseHelper databaseHelper;
      MyApplication myApplication;


      public BottomSheetFragment() {
            // Required empty public constructor
      }

      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
      }

      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            bottomSheetView = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

            init();

            return bottomSheetView;
      }

      private void init(){

            databaseHelper = new DatabaseHelper(getActivity());
            myApplication = new MyApplication();
            dataModels = new ArrayList<DataModel>();

      }


}
