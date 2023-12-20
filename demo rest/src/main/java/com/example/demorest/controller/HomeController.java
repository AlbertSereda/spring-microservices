package com.example.demorest.controller;

import com.example.demorest.model.HomeDTO;
import com.example.demorest.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/home/{firstName}")
    public HomeDTO homeGET(@PathVariable String firstName,
                           @RequestParam String lastName) {
        return homeService.handleHome(firstName, lastName);
    }

    @PostMapping("/home")
    public HomeDTO homePOST(@RequestBody HomeDTO homeDTO) {
        return homeService.handleHome(homeDTO.getFirstName(), homeDTO.getLastName());
    }
}
