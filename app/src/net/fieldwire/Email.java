package net.fieldwire;

import com.google.gson.annotations.SerializedName;

public class Email {
    @SerializedName("is_primary")
    public boolean is_primary;

    @SerializedName("email")
    public String email;

    @Override
    public String toString() {
        return "Email{" +
                "is_primary=" + is_primary +
                ", email='" + email + '\'' +
                '}';
    }
}

