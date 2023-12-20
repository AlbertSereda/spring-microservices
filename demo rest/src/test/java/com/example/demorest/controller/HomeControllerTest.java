package com.example.demorest.controller;

import com.example.demorest.model.HomeDTO;
import com.example.demorest.service.HomeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HomeService homeService;

    @Test
    void homeGET_ReturnsValidResponse() throws Exception {
        String firstName = "Albert";
        String lastName = "Sereda";

        HomeDTO homeDTO = new HomeDTO(firstName, lastName);
        when(homeService.handleHome(firstName, lastName)).thenReturn(homeDTO);

        this.mockMvc.perform(get("/home/" + firstName + "?lastName=" + lastName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andExpect(jsonPath("$.lastName", is(lastName)));
    }

    @Test
    void homePOST() throws Exception {
        String firstName = "Albert";
        String lastName = "Sereda";

        HomeDTO homeDTO = new HomeDTO(firstName, lastName);
        when(homeService.handleHome(firstName, lastName)).thenReturn(homeDTO);

        this.mockMvc.perform(post("/home")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(homeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andExpect(jsonPath("$.lastName", is(lastName)));
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}