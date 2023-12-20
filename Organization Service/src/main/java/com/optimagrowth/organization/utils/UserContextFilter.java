package com.optimagrowth.organization.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class UserContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        UserContext.setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        UserContext.setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        UserContext.setTmxAuthToken(httpServletRequest.getHeader(UserContext.TMX_AUTH_TOKEN));
        UserContext.setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContext.setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
