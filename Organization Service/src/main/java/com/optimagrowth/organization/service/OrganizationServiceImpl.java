package com.optimagrowth.organization.service;

import com.optimagrowth.organization.events.model.Action;
import com.optimagrowth.organization.events.source.PublisherOrganizationChange;
import com.optimagrowth.organization.exception.OrganizationNotFoundException;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final PublisherOrganizationChange publisher;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   PublisherOrganizationChange publisher) {
        this.organizationRepository = organizationRepository;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public Organization getOrganization(String organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId));
    }

    @Override
    @Transactional
    public Organization createOrganization(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = organizationRepository.save(organization);
        publisher.publishOrganizationChange(Action.CREATED, organization.getId());
        return organization;
    }
}
