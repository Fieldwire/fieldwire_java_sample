package net.fieldwire;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class User extends BaseModel {
        @SerializedName("first_name")
    public String first_name;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("company")
    public String company;

    @SerializedName("phone_number")
    public String phone_number;

    @SerializedName("photo_url")
    public String photo_url;

    @SerializedName("is_approved")
    public boolean is_approved;

    @SerializedName("is_confirmed")
    public boolean is_confirmed;

    @SerializedName("user_deleted_at")
    public Date user_deleted_at;

    @SerializedName("current_sign_in_at")
    public Date current_sign_in_at;

    @SerializedName("invited_by_id")
    public int invited_by_id;

    @SerializedName("role")
    public String role;

    @SerializedName("email_addresses")
    public List<Email> email_addresses;

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", company='" + company + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", is_approved=" + is_approved +
                ", is_confirmed=" + is_confirmed +
                ", user_deleted_at=" + user_deleted_at +
                ", current_sign_in_at=" + current_sign_in_at +
                ", invited_by_id=" + invited_by_id +
                ", role='" + role + '\'' +
                ", email_addresses=" + email_addresses +
                "} " + super.toString();
    }
}
