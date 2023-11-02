package net.fieldwire.models.response;

import java.util.Map;

public record AwsPostToken(String postAddress, Map<String, String> postParameters) {}
