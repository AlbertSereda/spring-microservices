package com.optimagrowth.license.events.receiver;

import com.optimagrowth.license.events.model.OrganizationChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqMessageHandler {

    public void handleMqMessage(OrganizationChangeModel model) {
        log.info("Receive MQ message: {}", model);
    }
}
