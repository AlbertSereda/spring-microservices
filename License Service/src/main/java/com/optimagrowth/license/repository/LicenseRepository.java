package com.optimagrowth.license.repository;

import com.optimagrowth.license.model.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LicenseRepository extends JpaRepository<License, String> {
    List<License> findAllByOrganizationId(String organizationId);

    Optional<License> findByLicenseIdAndOrganizationId(String licenseId, String organizationId);
}