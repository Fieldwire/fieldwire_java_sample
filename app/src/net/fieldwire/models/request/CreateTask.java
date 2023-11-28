package net.fieldwire.models.request;

public record CreateTask(
        String name,
        int priority,
        int ownerUserId,
        int creatorUserId,
        int lastEditorUserId
) {}
