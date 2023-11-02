package net.fieldwire.models.request;

public record CreateSheetUpload(String name, String fileUrl, long fileSize, int userId) {}
