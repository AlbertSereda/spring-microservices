package com.example.demorest.service;

import com.example.demorest.model.HomeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeServiceTest {
    @Autowired
    private HomeService homeService;

    @Test
    void handleHome() {
        HomeDTO excepted = new HomeDTO("Albert", "Sereda");
        HomeDTO actual = homeService.handleHome("Albert", "Sereda");

        assertEquals(excepted, actual);
    }
}