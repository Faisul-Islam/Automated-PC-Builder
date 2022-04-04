package com.apcb.demo.controller;

import com.apcb.demo.dto.response.CPUInitialResponse;
import com.apcb.demo.dto.response.PCResponse;
import com.apcb.demo.services.APCBService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apcb/")
public class APCBController {

    private final APCBService apcbService;

    public APCBController(APCBService apcbService) {
        this.apcbService = apcbService;
    }

    @GetMapping(path ="/get/b={brand}&p={price}")
    public PCResponse getCPUInfo(@PathVariable String brand, @PathVariable int price){
        return apcbService.getPCInfo(brand,price);
    }
}
