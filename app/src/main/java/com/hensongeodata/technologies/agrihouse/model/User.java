package com.hensongeodata.technologies.agrihouse.model;

import com.google.gson.annotations.SerializedName;

public class User {

      @SerializedName("email")
      private String email;
      @SerializedName("first_name")
      private String first_name;
      @SerializedName("last_name")
      private String last_name;
      @SerializedName("ea")
      private String ea;

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getFirst_name() {
            return first_name;
      }

      public void setFirst_name(String first_name) {
            this.first_name = first_name;
      }

      public String getLast_name() {
            return last_name;
      }

      public void setLast_name(String last_name) {
            this.last_name = last_name;
      }

      public String getEa() {
            return ea;
      }

      public void setEa(String ea) {
            this.ea = ea;
      }
}
