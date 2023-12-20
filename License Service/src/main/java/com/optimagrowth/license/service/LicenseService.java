package com.optimagrowth.license.service;

import com.optimagrowth.license.model.License;

import java.util.List;
import java.util.Locale;

public interface LicenseService {
    List<License> getAllLicense();

    License getLicense(String licenseId, String organizationId, Locale locale);
    License getLicense(String licenseId, String organizationId, Locale locale, String clientType);

    License createLicense(String organizationId, License license);

    License updateLicense(License license);

    String deleteLicense(String licenseId, String organizationId, Locale locale);
}
