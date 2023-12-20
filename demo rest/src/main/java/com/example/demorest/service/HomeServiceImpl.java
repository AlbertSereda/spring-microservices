package com.example.demorest.service;

import com.example.demorest.model.HomeDTO;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
    @Override
    public HomeDTO handleHome(String firstName, String lastName) {
        return new HomeDTO(firstName, lastName);
    }
}
