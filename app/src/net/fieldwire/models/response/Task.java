package net.fieldwire.models.response;

import java.util.List;
import java.util.UUID;

public class Task extends BaseDeviceModel {
    public UUID projectId;
    public String name;
    public int priority;
    public String dueDate;
    public int sequenceNumber;
    public List<Integer> userIds;

    @Override
    public String toString() {
        return "Task{" +
                "project_id=" + projectId +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", due_date='" + dueDate + '\'' +
                ", sequence_number=" + sequenceNumber +
                ", user_ids=" + userIds +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deviceCreatedAt='" + deviceCreatedAt + '\'' +
                ", deviceUpdatedAt='" + deviceUpdatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", id=" + id +
                '}';
    }
}

