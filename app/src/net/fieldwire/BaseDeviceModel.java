package net.fieldwire;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class BaseDeviceModel {
    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("device_created_at")
    public String device_created_at;

    @SerializedName("device_updated_at")
    public String device_updated_at;

    @SerializedName("deleted_at")
    public String deleted_at;

    @SerializedName("id")
    public UUID id;

    @Override
    public String toString() {
        return "BaseDeviceModel{" +
                "created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", device_created_at='" + device_created_at + '\'' +
                ", device_updated_at='" + device_updated_at + '\'' +
                ", deleted_at='" + deleted_at + '\'' +
                ", id=" + id +
                '}';
    }
}
