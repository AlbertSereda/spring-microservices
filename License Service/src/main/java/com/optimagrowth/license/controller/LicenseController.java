package com.optimagrowth.license.controller;

import com.optimagrowth.license.exception.LicenseNotFoundException;
import com.optimagrowth.license.model.ErrorResponse;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
public class LicenseController {
    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping()
    public ResponseEntity<List<License>> getAllLicense(@PathVariable String organizationId) {
        return ResponseEntity.ok(licenseService.getAllLicense());
    }

    @GetMapping("/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable String organizationId,
                                              @PathVariable String licenseId,
                                              @RequestHeader(value = "Accept-Language", required = false)
                                              Locale locale) {
        License license = licenseService.getLicense(licenseId, organizationId, locale);

        return ResponseEntity.ok(license);
    }

    @GetMapping("/{licenseId}/{clientType}")
    public ResponseEntity<License> getLicenseWithClient(@PathVariable String organizationId,
                                                        @PathVariable String licenseId,
                                                        @PathVariable String clientType,
                                                        @RequestHeader(value = "Accept-Language", required = false)
                                                        Locale locale) {
        License license = licenseService.getLicense(licenseId, organizationId, locale, clientType);
        return ResponseEntity.ok(license);
    }

    @PostMapping()
    public ResponseEntity<License> createLicense(@PathVariable String organizationId,
                                                 @RequestBody License license) {
        return ResponseEntity.ok(licenseService.createLicense(organizationId, license));
    }

    @PutMapping()
    public ResponseEntity<License> updateLicense(@PathVariable String organizationId,
                                                 @RequestBody License license) {
        return ResponseEntity.ok(licenseService.updateLicense(license));
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable String organizationId,
                                                @PathVariable String licenseId,
                                                @RequestHeader(value = "Accept-Language", required = false)
                                                Locale locale) {
        String response = licenseService.deleteLicense(licenseId, organizationId, locale);

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(LicenseNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private ResponseEntity<ErrorResponse> handleException(LicenseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorResponse(
                HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    private ErrorResponse getErrorResponse(HttpStatus status, String message) {
        return new ErrorResponse(status, message);
    }
}
