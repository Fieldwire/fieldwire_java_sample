package net.fieldwire.models.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Task extends BaseDeviceModel {
    public UUID projectId;
    public String name;
    public int priority;
    public Date dueDate;
    public int sequenceNumber;
    public List<Integer> userIds;

    @Override
    public String toString() {
        return "Task{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", sequenceNumber=" + sequenceNumber +
                ", userIds=" + userIds +
                ", deviceCreatedAt=" + deviceCreatedAt +
                ", deviceUpdatedAt=" + deviceUpdatedAt +
                ", deletedAt=" + deletedAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                '}';
    }
}

