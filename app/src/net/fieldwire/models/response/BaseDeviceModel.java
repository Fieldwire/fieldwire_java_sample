package net.fieldwire.models.response;

import java.util.Date;
import java.util.UUID;

public class BaseDeviceModel extends BaseModel<UUID> {
    public Date deviceCreatedAt;
    public Date deviceUpdatedAt;
    public Date deletedAt;
}
