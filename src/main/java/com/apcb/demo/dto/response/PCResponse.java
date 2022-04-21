package com.apcb.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PCResponse {
    private CPUInitialResponse cpu;
    private MoboInitialResponse mobo;
    private ProductInitialResponse ram;
}
