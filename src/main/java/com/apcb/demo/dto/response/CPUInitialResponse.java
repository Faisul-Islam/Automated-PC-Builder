package com.apcb.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CPUInitialResponse {
    private String name;
    int price;
    boolean withoutIGPU;
}
