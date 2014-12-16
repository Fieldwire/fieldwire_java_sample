package net.fieldwire;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Project extends BaseDeviceModel {
    @SerializedName("name")
    public String name;

    @SerializedName("address")
    public String address;

    @SerializedName("archived_at")
    public Date archived_at;

    @SerializedName("access_token")
    public String access_token;

    @SerializedName("is_email_notifications_enabled")
    public boolean is_email_notifications_enabled;

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", archived_at=" + archived_at +
                ", access_token='" + access_token + '\'' +
                ", is_email_notifications_enabled=" + is_email_notifications_enabled +
                "} " + super.toString();
    }
}