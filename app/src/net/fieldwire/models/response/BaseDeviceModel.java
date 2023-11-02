package net.fieldwire.models.response;

import java.util.UUID;

public class BaseDeviceModel extends BaseModel<UUID> {
    public String deviceCreatedAt;
    public String deviceUpdatedAt;
    public String deletedAt;
}
