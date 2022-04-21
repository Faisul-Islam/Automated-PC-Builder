package com.apcb.demo.services;

import com.apcb.demo.dto.response.*;
import com.apcb.demo.services.helper.ServiceHelper;
import com.apcb.demo.shared.constants.CPUBrands;
import com.apcb.demo.shared.constants.MoboChipKey;
import com.apcb.demo.shared.constants.RamQuerykeys;
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

    public PCResponse getPCInfo(String brand, int price) {

        PCResponse pcResponse = new PCResponse();

        List<CPUInitialResponse> output = new ArrayList<>();
        Enum brandEnum = brand.equals("intel") ? CPUBrands.INTEL : CPUBrands.AMD;

        int cpuAllowedPrice = helper.getCpuAllowedPrice(price, brandEnum);
        int ramAllowedPrice = helper.getRamAllowedPrice(price);


        CPUInitialResponse choosenCPU = new CPUInitialResponse();


        try {
            Document doc = Jsoup.connect("https://www.ryanscomputers.com/category/processor-" + brand + "?page=1&limit=108&query=1-p%23" + cpuAllowedPrice + "%7C&sort=HL").get();


            Elements cpuNames = doc.select(".product-box .product-title-grid");
            Elements cpuPrices = doc.select(".product-box .special-price.special-price-grid");
            Elements cpuLinks = doc.select(".product-box .product-title-grid");
            Elements cpuImages = doc.select(".product-box .product-thumb a img");
            for (short i = 0; i < cpuNames.size(); i++) {

                CPUInitialResponse tmp = new CPUInitialResponse();

                tmp.setName(cpuNames.get(i).attr("title"));
                tmp.setWithoutIGPU(helper.isCPUWithoutGPU(cpuNames.get(i).attr("title"), brandEnum));
                tmp.setPrice(Integer.parseInt(helper.extractInt(cpuPrices.get(i).text())));
                tmp.setLink(cpuLinks.get(i).attr("href"));
                tmp.setImage(cpuImages.get(i).attr("src"));

                output.add(tmp);
            }
            choosenCPU = helper.chooseCPU(output);
            pcResponse.setCpu(choosenCPU);

            doc = Jsoup.connect(choosenCPU.getLink()).get();

//            Ram
            List<ProductInitialResponse> rams = new ArrayList<>();

            RamInitialResponse initialRamInfo = getInitialRamInfo(doc);

           rams = getProductResponseOf("https://www.ryanscomputers.com/category/desktop-component-desktop-ram?page=1&limit=108&query="+ RamQuerykeys.type.get(initialRamInfo.getType()) + RamQuerykeys.bus.get(initialRamInfo.getBus()) +"1-p%23" + ramAllowedPrice + "%7C&sort=HL");
            System.out.println("toooooooooooooooo");
//            for (ProductInitialResponse ram: rams) {
//                System.err.println(ram.getName() + "Price :" + ram.getPrice());
//            }
            ProductInitialResponse choosenRam = rams.get(0);
            pcResponse.setRam(choosenRam);

            /*
            Getting the compatible chipset nodes from the mobo page link
            So that can get the unique
             */


            Elements motherBoardInfo;
            String motherBoardArch[];
            try {
                motherBoardInfo = doc.getElementsMatchingOwnText("(Compatible Products)");
                System.out.println(motherBoardInfo);
                motherBoardArch = motherBoardInfo.get(0).nextElementSibling().text().replace("Chipset:", "").split(",");
            } catch (Exception excp) {
// if unable to find mobo info based on compatible products then search using sockets supported
// & use that general mobo
                motherBoardInfo = doc.getElementsMatchingOwnText("(Sockets Supported)");
                motherBoardArch = motherBoardInfo.get(0).nextElementSibling().text().split(",");

            }

//            System.out.println(motherBoardArch[0].trim());
            String moboUniqueGetKeySubstring = MoboChipKey.amdMoboChipKey.get(motherBoardArch[0].trim());
            System.out.println("test " + motherBoardArch[0]);
            int moboAllowedPrice = helper.getMoboAllowedPrice(price, choosenCPU.getPrice(), brandEnum);
              /*
             Getting all the
             */
            System.out.println(moboAllowedPrice);
            System.out.println(moboUniqueGetKeySubstring);
            if (brand.equals("intel"))
                doc = Jsoup.connect("https://www.ryanscomputers.com/category/desktop-component-motherboard?page=1&limit=18&query=1640-3865" + moboUniqueGetKeySubstring + "%23dropdown%7C1-p%23" + moboAllowedPrice + "%23dropdown%7C&sort=HL").get();
            else
                doc = Jsoup.connect("https://www.ryanscomputers.com/category/desktop-component-motherboard?page=1&limit=18&query=1640-3866" + moboUniqueGetKeySubstring + "%23dropdown%7C1-p%23" + moboAllowedPrice + "%23dropdown%7C&sort=HL").get();


            Elements moboNames = doc.select(".product-box .product-title-grid");
            Elements moboPrices = doc.select(".product-box .special-price.special-price-grid");
            Elements moboLinks = doc.select(".product-box .product-title-grid");
            Elements moboImages = doc.select(".product-box .product-thumb a img");

            Elements te = doc.getElementsMatchingOwnText("(Sockets Supported)");
            String tet = te.get(0).nextElementSibling().text();
            System.out.println(tet);
            List<MoboInitialResponse> mobos = new ArrayList<>();


            for (short i = 0; i < moboNames.size(); i++) {

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

            pcResponse.setMobo(mobos.get(0));


        } catch (Exception ex) {

        }
        return pcResponse;
        //   return output;
    }


    //helper
    private RamInitialResponse getInitialRamInfo(Document cpu) {
        System.out.println("test----");
        String type = helper.extractComponentInfo(cpu, "(Memory Type)"),
                bus = helper.extractComponentInfo(cpu, "(Bus Speed)"),
                slots = helper.extractComponentInfo(cpu, "(Memory Slot|Memory Channel)");

        if (bus == null) {
            System.out.println("yoyoyo");
            if (type.contains("MHz")) {
                bus = helper.extractInt(type.split(" ")[1]);
                type = type.split(" ")[0];
            }
        } else {
            //stripping of Mhz, Ghz of bus speed
            bus = helper.extractInt(bus);
        }
        System.out.println("type: " + type);
        System.out.println("bus: " + bus);
        System.out.println("slots: " + slots);
        return new RamInitialResponse(type,bus,slots);
    }

    private List<ProductInitialResponse> getProductResponseOf(String query){
        List<ProductInitialResponse> output = new ArrayList<>();
        System.out.println(query);
        try{
            Document doc = Jsoup.connect(query).get();

            Elements names = doc.select(".product-box .product-title-grid");
            Elements prices = doc.select(".product-box .special-price.special-price-grid");
            Elements links = doc.select(".product-box .product-title-grid");
            Elements images = doc.select(".product-box .product-thumb a img");

            for (short i = 0; i < names.size(); i++) {
 ProductInitialResponse model = new ProductInitialResponse();
                model.setName(names.get(i).attr("title"));
                model.setPrice(Integer.parseInt(helper.extractInt(prices.get(i).text())));
                model.setLink(links.get(i).attr("href"));
                model.setImage(images.get(i).attr("src"));

                output.add(model);
            }

        }catch (Exception ex){

        }
        return output;
    }

}
