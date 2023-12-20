package com.optimagrowth.license;

import com.optimagrowth.license.controller.LicenseController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LicenseServiceApplicationTests {

    @Autowired
    private LicenseController licenseController;

    @Test
    void contextLoads() {
        assertThat(licenseController).isNotNull();
    }

}
