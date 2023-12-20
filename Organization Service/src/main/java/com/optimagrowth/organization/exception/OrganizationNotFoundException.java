package com.optimagrowth.organization.exception;

public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(String organizationId) {
        super("Not found organization with id : " + organizationId);
    }
}
