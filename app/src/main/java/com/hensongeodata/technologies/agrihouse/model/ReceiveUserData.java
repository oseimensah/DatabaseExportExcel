package com.hensongeodata.technologies.agrihouse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiveUserData {

      @SerializedName("total")
      @Expose
      private int total;
      @SerializedName("data")
      @Expose
      private DataReceived dataReceived;

      public int getTotal() {
            return total;
      }

      public void setTotal(int total) {
            this.total = total;
      }

      public DataReceived getDataReceived() {
            return dataReceived;
      }

      public void setDataReceived(DataReceived dataReceived) {
            this.dataReceived = dataReceived;
      }
}
