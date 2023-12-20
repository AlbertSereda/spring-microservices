package com.optimagrowth.license.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class LicenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService licenseService;

    @Test
    void getLicense_returnsValidResponse() throws Exception {
        //given
        String organizationId = "5";
        String licenseId = "4";
        Locale locale = Locale.US;

        License license = new License();
        license.setOrganizationId(organizationId);
        license.setLicenseId(licenseId);

        //when
        when(licenseService.getLicense(licenseId, organizationId, locale))
                .thenReturn(license);

        //then
        mockMvc.perform(get(String.format("/v1/organization/%s/license/%s", organizationId, licenseId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.organizationId", is(organizationId)))
                .andExpect(jsonPath("$.licenseId", is(licenseId)));
    }

    @Test
    void createLicense_returnsValidResponse() throws Exception {
        //given
        String organizationId = "5";
        String licenseId = "3";

        License license = new License();
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setDescription("Software product");
        license.setProductName("Ostock");
        license.setLicenseType("full");

        //when
        when(licenseService.createLicense(organizationId, license))
                .thenReturn(license);

        //then
        mockMvc.perform(post(String.format("/v1/organization/%s/license", organizationId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(license)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.organizationId", is(organizationId)))
                .andExpect(jsonPath("$.licenseId", is(licenseId)));
    }

    @Test
    void updateLicense_returnsValidResponse() throws Exception {
        //given
        String organizationId = "5";
        String licenseId = "3";

        License license = new License();
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setDescription("Software product");
        license.setProductName("Ostock");
        license.setLicenseType("full");

        //when
        when(licenseService.updateLicense(license))
                .thenReturn(license);

        //then
        mockMvc.perform(put(String.format("/v1/organization/%s/license", organizationId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(license)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.organizationId", is(organizationId)))
                .andExpect(jsonPath("$.licenseId", is(licenseId)));
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}