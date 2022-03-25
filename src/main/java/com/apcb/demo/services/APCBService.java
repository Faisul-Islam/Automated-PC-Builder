package com.apcb.demo.services;

import com.apcb.demo.dto.response.CPUInitialResponse;
import com.apcb.demo.services.helper.ServiceHelper;
import com.apcb.demo.shared.constants.CPUBrands;
import org.apache.logging.log4j.util.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class APCBService {

    private final ServiceHelper helper;

    public APCBService(ServiceHelper helper) {
        this.helper = helper;
    }

    public List<CPUInitialResponse> getCpuInfo(String brand,int price){
        int cpuAllowedPrice = helper.getCpuAllowedPrice(price);
        System.out.println(cpuAllowedPrice);
        List<CPUInitialResponse> output = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.ryanscomputers.com/category/processor-" + brand + "?page=1&limit=108&query=1-p%23"+ cpuAllowedPrice + "%7C&sort=HL").get();
            Elements cpuNames = doc.select(".product-box .product-title-grid");
            Elements cpuPrices = doc.select(".product-box .special-price.special-price-grid");


            for(short i = 0;i < cpuNames.size(); i++){

                CPUInitialResponse tmp = new CPUInitialResponse();

                tmp.setName(cpuNames.get(i).attr("title"));
                tmp.setWithoutIGPU(helper.isCPUWithoutGPU(cpuNames.get(i).attr("title"), CPUBrands.INTEL));
                tmp.setPrice(Integer.parseInt(helper.extractInt(cpuPrices.get(i).text())));

                output.add(tmp);
            }

        }catch(Exception ex){

        }
//        for(short i = 0; i < output.length; i++){
//            System.out.println(output[i]);
//        }
        return output;
    }
}
