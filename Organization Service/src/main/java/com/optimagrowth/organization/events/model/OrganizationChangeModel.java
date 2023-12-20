package com.optimagrowth.organization.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationChangeModel {
    private String type;
    private Action action;
    private String organizationId;
    private String correlationId;
}
