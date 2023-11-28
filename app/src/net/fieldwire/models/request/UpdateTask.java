package net.fieldwire.models.request;

import java.util.Date;

public record UpdateTask(int priority, Date dueDate, int lastEditorUserId) {}
