package net.fieldwire;

import com.google.gson.annotations.SerializedName;

public class BaseModel {
    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("id")
    public int id;
}
