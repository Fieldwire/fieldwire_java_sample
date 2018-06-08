package net.fieldwire;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class SheetUpload {
    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("id")
    public UUID id;

    @SerializedName("name")
    public String name;

    @SerializedName("file_url")
    public String file_url;

    @SerializedName("file_size")
    public String file_size;

    @SerializedName("pages")
    public Integer pages;

    @SerializedName("user_id")
    public int user_id;

    @SerializedName("project_id")
    public UUID project_id;

    @SerializedName("is_processed")
    public Boolean is_processed;

    @SerializedName("has_errors")
    public Boolean has_errors;

    @SerializedName("folder_id")
    public UUID folder_id;

    @SerializedName("version_description")
    public String version_description;

    @SerializedName("version_notes")
    public String version_notes;
}
