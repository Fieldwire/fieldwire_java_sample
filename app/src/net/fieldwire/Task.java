package net.fieldwire;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Task extends BaseDeviceModel {

    @SerializedName("project_id")
    public UUID project_id;

    @SerializedName("team_id")
    public UUID team_id;

    @SerializedName("floorplan_id")
    public UUID floorplan_id;

    @SerializedName("name")
    public String name;

    @SerializedName("priority")
    public int priority;

    @SerializedName("pos_x")
    public int pos_x;

    @SerializedName("pos_y")
    public int pos_y;

    @SerializedName("pos_z")
    public int pos_z;

    @SerializedName("fixed_at")
    public Date fixed_at;

    @SerializedName("verified_at")
    public Date verified_at;

    @SerializedName("is_local")
    public boolean is_local;

    @SerializedName("due_date")
    public String due_date;

    @SerializedName("sequence_number")
    public int sequence_number;

    @SerializedName("user_ids")
    public List<Integer> user_ids;

    @SerializedName("tags")
    public List<String> tags;

    @Override
    public String toString() {
        return "Task{" +
                "project_id=" + project_id +
                ", team_id=" + team_id +
                ", floorplan_id=" + floorplan_id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", pos_x=" + pos_x +
                ", pos_y=" + pos_y +
                ", pos_z=" + pos_z +
                ", fixed_at=" + fixed_at +
                ", verified_at=" + verified_at +
                ", is_local=" + is_local +
                ", due_date=" + due_date +
                ", sequence_number=" + sequence_number +
                ", user_ids=" + user_ids +
                ", tags=" + tags +
                "} " + super.toString();
    }
}

