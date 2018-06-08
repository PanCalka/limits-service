package com.panCalka.microservices.limitsservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.panCalka.microservices.limitsservice.bean.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitConfigurationController {

    @Autowired
    Configuration configuration;

    @GetMapping("/limits")
    public LimitConfiguration retriveLimitsFromConfiguration() {
        return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());

    }

    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod="fallbackRetriveConfiguration")
    public LimitConfiguration retriveConfiguration() {
        throw new RuntimeException("Not available service");
    }

    public LimitConfiguration fallbackRetriveConfiguration() {
        return new LimitConfiguration(1, 1);
    }
}
