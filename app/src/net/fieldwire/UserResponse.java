package net.fieldwire;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("success")
    public boolean success;

    @SerializedName("users")
    public List<User> users;
}
