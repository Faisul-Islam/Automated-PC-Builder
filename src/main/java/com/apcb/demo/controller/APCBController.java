package com.apcb.demo.controller;

import com.apcb.demo.dto.response.PCResponse;
import com.apcb.demo.services.APCBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apcb/")
public class APCBController {

    private final APCBService apcbService;

    public APCBController(APCBService apcbService) {
        this.apcbService = apcbService;
    }

    @CrossOrigin(origins = {"https://auto-pc-builder.netlify.app", "http://localhost/"})
    @GetMapping(path ="/get/b={brand}&p={price}")
    public ResponseEntity<PCResponse> getCPUInfo(@PathVariable String brand, @PathVariable int price){
        return new ResponseEntity(apcbService.getPCInfo(brand,price), HttpStatus.OK);
    }
}
