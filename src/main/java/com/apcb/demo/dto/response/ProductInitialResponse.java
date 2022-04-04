package com.apcb.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInitialResponse {
    private String name, link, image;
    int price;
}
