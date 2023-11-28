package net.fieldwire.models.response;

import java.util.UUID;

public class SheetUpload extends BaseModel<UUID> {
    public String name;
    public String fileUrl;
    public String fileSize;
    public int userId;
    public UUID project_id;

    @Override
    public String toString() {
        return "SheetUpload{" +
                "name='" + name + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", userId=" + userId +
                ", project_id=" + project_id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                '}';
    }
}
