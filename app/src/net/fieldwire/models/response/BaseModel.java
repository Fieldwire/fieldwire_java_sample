package net.fieldwire.models.response;

import java.util.Date;

public class BaseModel<ID> {
    public Date createdAt;
    public Date updatedAt;
    public ID id;
}
