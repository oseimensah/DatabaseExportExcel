package com.hensongeodata.technologies.agrihouse.model;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
      @SerializedName("error")
      private Boolean error;
      @SerializedName("message")
      private String message;
      @SerializedName("profile")
      private User user;

      public Boolean getError() {
            return error;
      }

      public String getMessage() {
            return message;
      }

      public User getUser() {
            return user;
      }

      public void setMessage(String message) {
            this.message = message;
      }

      public void setUser(User user) {
            this.user = user;
      }
}
