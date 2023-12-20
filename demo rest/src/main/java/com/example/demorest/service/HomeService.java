package com.example.demorest.service;

import com.example.demorest.model.HomeDTO;

public interface HomeService {
    HomeDTO handleHome(String firstName, String lastName);
}
