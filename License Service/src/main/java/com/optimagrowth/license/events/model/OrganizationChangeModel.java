package com.optimagrowth.license.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationChangeModel {
    private String type;
    private Action action;
    private String organizationId;
    private String correlationId;
}
