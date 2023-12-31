package com.optimagrowth.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class ResponseFilter {
    private final FilterUtils filterUtils;

    public ResponseFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return ((exchange, chain) -> chain
                .filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                    String correlationId = filterUtils.getCorrelationId(requestHeaders);
                    log.info("Adding the correlation id to the outbound headers. {}",
                    correlationId);
                    exchange.getResponse().getHeaders()
                            .add(FilterUtils.CORRELATION_ID, correlationId);
                    log.info("Completing outgoing request for {}.",
                    exchange.getRequest().getURI());
                })));
    }
}
