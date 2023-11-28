package net.fieldwire.models.response;

import java.util.Date;

public class Project extends BaseDeviceModel {
    public String name;
    public String address;
    public Date archived_at;
    public boolean is_email_notifications_enabled;

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", archived_at=" + archived_at +
                ", is_email_notifications_enabled=" + is_email_notifications_enabled +
                ", deviceCreatedAt=" + deviceCreatedAt +
                ", deviceUpdatedAt=" + deviceUpdatedAt +
                ", deletedAt=" + deletedAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                '}';
    }
}
