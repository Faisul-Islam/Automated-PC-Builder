package com.apcb.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CPUInitialResponse extends ProductInitialResponse {
    boolean withoutIGPU;
}
