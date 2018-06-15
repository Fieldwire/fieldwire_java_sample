package net.fieldwire;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class AWSPostToken {
    @SerializedName("post_address")
    public String post_address;

    @SerializedName("post_parameters")
    public Map<String, String> post_parameters;

    @Override
    public String toString() {
        String result = "AWSPostToken{" +
                "post_address='" + post_address + "'" +
                "post_parameters={";
        for (Map.Entry<String, String> entry : post_parameters.entrySet()) {
            result += entry.getKey() + "='" + entry.getValue() + "',\n";
        }

        return result + "}";
    }
}
