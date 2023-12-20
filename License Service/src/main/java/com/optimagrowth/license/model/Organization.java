package com.optimagrowth.license.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    private String id;
    private String organizationName;

    private String contractName;

    private String contractEmail;

    private String contractPhone;
}
