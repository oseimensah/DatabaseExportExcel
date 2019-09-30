package com.hensongeodata.technologies.agrihouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hensongeodata.technologies.agrihouse.R;
import com.hensongeodata.technologies.agrihouse.database.DatabaseHelper;
import com.hensongeodata.technologies.agrihouse.model.DataModel;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

      private static final String TAG = RecyclerAdapter.class.getSimpleName();

      private List<DataModel> dataModels;
      private DatabaseHelper databaseHelper;
      private Context mContext;
      private Myholder myholder;;

      public RecyclerAdapter( Context context, List<DataModel> dataModels) {
            this.mContext = context;
            this.dataModels = dataModels;
      }

      @NonNull
      @Override
      public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.custom_list, parent, false);
            return new Myholder(view);
      }

      @Override
      public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DataModel dataModel=dataModels.get(position);
            myholder =(Myholder) holder;
            myholder.name.setText(dataModel.getPerson_name());
            myholder.phone.setText(dataModel.getPhone());
            myholder.role.setText(dataModel.getJobrole());

      }

      @Override
      public int getItemCount() {
            return dataModels.size();
      }
//
//
      class Myholder extends RecyclerView.ViewHolder{
      public TextView name;
      public TextView phone;
      public TextView role;

            public Myholder(@NonNull View itemView) {
                  super(itemView);

                  name = (TextView) itemView.findViewById(R.id.his_name);
                  phone = (TextView) itemView.findViewById(R.id.his_number);
                  role = (TextView) itemView.findViewById(R.id.his_role);

            }
      }

}
