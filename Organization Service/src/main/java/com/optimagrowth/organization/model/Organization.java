package com.optimagrowth.organization.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organizations")
@Data
@NoArgsConstructor
public class Organization {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "contract_name")
    private String contractName;

    @Column(name = "contract_email")
    private String contractEmail;

    @Column(name = "contract_phone")
    private String contractPhone;
}
