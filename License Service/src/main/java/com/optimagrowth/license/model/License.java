package com.optimagrowth.license.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "licenses")
@Data
@NoArgsConstructor
public class License {
    @Id
    @Column(name = "license_id")
    private String licenseId;

    @Column(name = "description")
    private String description;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "license_type")
    private String licenseType;

    @Column(name = "comment")
    private String comment;

    @Transient
    private String organizationName;

    @Transient
    private String contractName;

    @Transient
    private String contractEmail;

    @Transient
    private String contractPhone;
}
