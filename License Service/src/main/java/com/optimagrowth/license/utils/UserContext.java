package com.optimagrowth.license.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String TMX_AUTH_TOKEN = "tmx-auth-token";
    public static final String AUTH_TOKEN = "Authorization";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";

    private String correlationId = "";
    private String tmxAuthToken = "";
    private String authToken = "";
    private String userId = "";
    private String organizationId = "";
}
