package com.optimagrowth.license.service;

import com.optimagrowth.license.exception.LicenseNotFoundException;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.LicenseRepository;
import com.optimagrowth.license.service.client.OrganizationDiscoveryClient;
import com.optimagrowth.license.service.client.OrganizationFeignClient;
import com.optimagrowth.license.service.client.OrganizationRestTemplateClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LicenseServiceImpl implements LicenseService {

    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
    private final String message;
    private final OrganizationDiscoveryClient discoveryClient;
    private final OrganizationRestTemplateClient restTemplateClient;
    private final OrganizationFeignClient feignClient;


    public LicenseServiceImpl(MessageSource messageSource,
                              LicenseRepository licenseRepository,
                              @Value("${example.message}") String message,
                              OrganizationDiscoveryClient discoveryClient,
                              OrganizationRestTemplateClient restTemplateClient,
                              OrganizationFeignClient feignClient) {
        this.messageSource = messageSource;
        this.licenseRepository = licenseRepository;
        this.message = message;
        this.discoveryClient = discoveryClient;
        this.restTemplateClient = restTemplateClient;
        this.feignClient = feignClient;
    }

    @Override
    @Transactional
    public List<License> getAllLicense() {
        List<License> all = licenseRepository.findAll();
        log.info("all license : {}", all);
        return all;
    }

    @Override
    @Transactional
    public License getLicense(String licenseId, String organizationId, Locale locale) {
        License license = licenseRepository.findByLicenseIdAndOrganizationId(licenseId, organizationId)
                .orElseThrow(() -> new LicenseNotFoundException(String.format(messageSource.getMessage(
                        "license.search.error.message", null, locale), licenseId, organizationId)));
        license.setComment(message);

        return license;
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "licenseService",
            fallbackMethod = "buildFallbackLicenseList")
    @Bulkhead(name = "bulkheadLicenseService",
            fallbackMethod = "buildFallbackLicenseList",
            type = Bulkhead.Type.SEMAPHORE)
    @Retry(name = "retryLicenseService",
            fallbackMethod = "buildFallbackLicenseList")
    public License getLicense(String licenseId, String organizationId, Locale locale, String clientType) {
        //randomlyRunLong();
        License license = getLicense(licenseId, organizationId, locale);

        Organization organization = retrieveOrganizationInfo(organizationId, clientType)
                .orElseThrow(() -> new RuntimeException("organization not found"));
        license.setOrganizationName(organization.getOrganizationName());
        license.setContractName(organization.getContractName());
        license.setContractEmail(organization.getContractEmail());
        license.setContractPhone(organization.getContractPhone());

        return license;
    }

    private License buildFallbackLicenseList(
            String licenseId, String organizationId, Locale locale, String clientType, Throwable t) {
        log.error(t.getMessage(), t);
        License license = new License();
        license.setOrganizationId(organizationId);
        license.setComment("Deafult license");
        return license;
    }

    private void randomlyRunLong() {
//        Random random = new Random();
//        int i = random.nextInt(5) + 1;
//        if (i < 4)
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
            throw new IllegalStateException();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Organization> retrieveOrganizationInfo(String organizationId, String clientType) {
        Organization organization = null;
        switch (clientType) {
            case "discovery":
                organization = discoveryClient.getOrganization(organizationId);
                break;
            case "restTemplate":
                organization = restTemplateClient.getOrganization(organizationId);
                break;
            case "feign":
                organization = feignClient.getOrganization(organizationId);
        }
        return Optional.ofNullable(organization);
    }

    @Override
    @Transactional
    public License createLicense(String organizationId, License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        license.setComment(message);
        return licenseRepository.save(license);
    }

    @Override
    @Transactional
    public License updateLicense(License license) {
        license.setComment(message);
        return licenseRepository.save(license);
    }

    @Override
    @Transactional
    public String deleteLicense(String licenseId, String organizationId, Locale locale) {
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        return String.format(messageSource.getMessage("license.delete.message", null, locale), licenseId, organizationId);
    }
}
