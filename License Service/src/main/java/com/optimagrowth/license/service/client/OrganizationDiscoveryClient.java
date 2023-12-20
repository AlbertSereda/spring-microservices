package com.optimagrowth.license.service.client;

import com.optimagrowth.license.exception.ServiceInstanceNotFound;
import com.optimagrowth.license.model.Organization;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {
    private final DiscoveryClient discoveryClient;

    public OrganizationDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");
        if (instances.size() == 0) throw new ServiceInstanceNotFound("organization-service instance not found");

        String serviceUri = String.format("%s/v1/organization/%s",
                instances.get(0).getUri().toString(),
                organizationId);

        ResponseEntity<Organization> response = restTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error get organization by ID : " + response.getStatusCode() + " : " + response.getBody());
        }
    }
}
