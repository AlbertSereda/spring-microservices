package com.optimagrowth.organization.events.source;

import com.optimagrowth.organization.events.model.Action;
import com.optimagrowth.organization.events.model.OrganizationChangeModel;
import com.optimagrowth.organization.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PublisherOrganizationChange {
    private final StreamBridge streamBridge;

    public PublisherOrganizationChange(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrganizationChange(Action action, String organizationId) {
        log.info("Sending Kafka message {} for organization Id: {}", action, organizationId);
        OrganizationChangeModel model = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                organizationId,
                UserContext.getCorrelationId());
        streamBridge.send("organization-change-model", model);
    }
}

