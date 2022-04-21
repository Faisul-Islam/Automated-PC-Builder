package com.apcb.demo.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RamInitialResponse {
    private String type, bus, slots;
}
