package com.apcb.demo.services;

import com.apcb.demo.dto.response.CPUInitialResponse;
import com.apcb.demo.dto.response.MoboInitialResponse;
import com.apcb.demo.dto.response.PCResponse;
import com.apcb.demo.services.helper.ServiceHelper;
import com.apcb.demo.shared.constants.CPUBrands;
import com.apcb.demo.shared.constants.MoboChipKey;
import org.apache.logging.log4j.util.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class APCBService {

    private final ServiceHelper helper;

    public APCBService(ServiceHelper helper) {
        this.helper = helper;
    }

    public PCResponse getPCInfo(String brand, int price){

        PCResponse pcResponse = new PCResponse();

        List<CPUInitialResponse> output = new ArrayList<>();
        Enum brandEnum =  brand.equals("intel")? CPUBrands.INTEL : CPUBrands.AMD;

        int cpuAllowedPrice = helper.getCpuAllowedPrice(price,brandEnum);


        CPUInitialResponse choosenCPU = new CPUInitialResponse();


        try {
            Document doc = Jsoup.connect("https://www.ryanscomputers.com/category/processor-" + brand + "?page=1&limit=108&query=1-p%23"+ cpuAllowedPrice + "%7C&sort=HL").get();

            Elements cpuNames = doc.select(".product-box .product-title-grid");
            Elements cpuPrices = doc.select(".product-box .special-price.special-price-grid");
            Elements cpuLinks = doc.select(".product-box .product-title-grid");
            Elements cpuImages = doc.select(".product-box .product-thumb a img");
            for(short i = 0;i < cpuNames.size(); i++){

                CPUInitialResponse tmp = new CPUInitialResponse();

                tmp.setName(cpuNames.get(i).attr("title"));
                tmp.setWithoutIGPU(helper.isCPUWithoutGPU(cpuNames.get(i).attr("title"), brandEnum));
                tmp.setPrice(Integer.parseInt(helper.extractInt(cpuPrices.get(i).text())));
                tmp.setLink(cpuLinks.get(i).attr("href"));
                tmp.setImage(cpuImages.get(i).attr("src"));

                output.add(tmp);
            }
            choosenCPU = helper.chooseCPU(output);


            doc = Jsoup.connect(choosenCPU.getLink()).get();

            /*
            Getting the compatiable chipset nodes from the mobo page link
            So that can get the unique
             */
            Elements motherBoardInfo = doc.getElementsMatchingOwnText("(Compatible Products)");
            String motherBoardArch[] = motherBoardInfo.get(0).nextElementSibling().text().replace("Chipset:", "").split(",");
//            System.out.println(MoboChipKey.amdMoboChipKey.get(motherBoardArch[0].trim()));
//            System.out.println(motherBoardArch[0].trim());
            String moboUniqueGetKeySubstring = MoboChipKey.amdMoboChipKey.get(motherBoardArch[0].trim());
            int moboAllowedPrice = helper.getMoboAllowedPrice(price,choosenCPU.getPrice(),brandEnum);
              /*
             Getting all the
             */
            doc = Jsoup.connect("https://www.ryanscomputers.com/category/desktop-component-motherboard?page=1&limit=18&query=1640-3866"+ moboUniqueGetKeySubstring +"%23dropdown%7C1-p%23"+ moboAllowedPrice+"%23dropdown%7C&sort=HL").get();

            Elements moboNames = doc.select(".product-box .product-title-grid");
            Elements moboPrices = doc.select(".product-box .special-price.special-price-grid");
            Elements moboLinks = doc.select(".product-box .product-title-grid");
            Elements moboImages = doc.select(".product-box .product-thumb a img");
            List<MoboInitialResponse> mobos = new ArrayList<>();


            for(short i = 0;i < moboNames.size(); i++){

                MoboInitialResponse tmp = new MoboInitialResponse();

                tmp.setName(moboNames.get(i).attr("title"));
                tmp.setPrice(Integer.parseInt(helper.extractInt(moboPrices.get(i).text())));
                tmp.setLink(moboLinks.get(i).attr("href"));
                tmp.setImage(moboImages.get(i).attr("src"));
                mobos.add(tmp);
            }
            System.out.println(mobos.get(0));


            /*
             Finally, combing all individual response in the PcResponse
             */
            pcResponse.setCpu(choosenCPU);
            pcResponse.setMobo(mobos.get(0));


        }catch(Exception ex){

        }
        return pcResponse;
     //   return output;
    }
}
