package com.optimagrowth.organization.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String TMX_AUTH_TOKEN = "tmx-auth-token";
    public static final String AUTH_TOKEN = "Authorization";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";

    public static final ThreadLocal<String> correlationIdThreadLocal = new ThreadLocal<>();
    public static final ThreadLocal<String> tmxAuthTokenThreadLocal = new ThreadLocal<>();
    public static final ThreadLocal<String> authTokenThreadLocal = new ThreadLocal<>();
    public static final ThreadLocal<String> userIdThreadLocal = new ThreadLocal<>();
    public static final ThreadLocal<String> organizationIdThreadLocal = new ThreadLocal<>();

    public static String getCorrelationId() {
        return correlationIdThreadLocal.get();
    }
    public static String getTmxAuthToken() {
        return tmxAuthTokenThreadLocal.get();
    }
    public static String getAuthToken() {
        return authTokenThreadLocal.get();
    }
    public static String getUserId() {
        return userIdThreadLocal.get();
    }
    public static String getOrganizationId() {
        return organizationIdThreadLocal.get();
    }

    public static void setCorrelationId(String correlationId) {
        correlationIdThreadLocal.set(correlationId);
    }
    public static void setTmxAuthToken(String tmxAuthToken) {
        tmxAuthTokenThreadLocal.set(tmxAuthToken);
    }
    public static void setAuthToken(String authToken) {
        authTokenThreadLocal.set(authToken);
    }
    public static void setUserId(String userId) {
        userIdThreadLocal.set(userId);
    }
    public static void setOrganizationId(String organizationId) {
        organizationIdThreadLocal.set(organizationId);
    }
}
