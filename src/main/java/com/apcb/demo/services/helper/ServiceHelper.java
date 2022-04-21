package com.apcb.demo.services.helper;

import com.apcb.demo.dto.response.CPUInitialResponse;
import com.apcb.demo.shared.constants.CPUBrands;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceHelper {

    public static int getCpuAllowedPrice(int price, Enum brand){
        if(isIntel(brand))
        return price * 30 / 100;
        else return price * 50/100;
    }
    public static int getRamAllowedPrice(int price){
        return price * 15/100;
    }

    public static int getMoboAllowedPrice(int price, int cpuPrice, Enum brand){
        if(isIntel(brand))
            return ( getCpuAllowedPrice(price, brand) - cpuPrice) + (price * 25 / 100);
        else return ( getCpuAllowedPrice(price, brand) - cpuPrice) + (price * 20 / 100) ;
    }

    public boolean isCPUWithoutGPU(String name, Enum brand ){

        if(brand == CPUBrands.INTEL)
            return name.matches("(.*)[0-9]*(F|KF)(.*)");
        else
            return name.matches("(.*)[0-9]*(Without|X)(.*)");

    }
//    placeholder
    public CPUInitialResponse chooseCPU(List<CPUInitialResponse> cpus){
        CPUInitialResponse output = new CPUInitialResponse();

        for(CPUInitialResponse cpu: cpus){
            if (!cpu.isWithoutIGPU()) {
                output = cpu;
                break;
            }
        }
        return output;
    }

    public boolean regTest(String s){
        return s.matches("(.*)[0-9]*F(.*)");
    }

   public static String extractInt(String str)
    {
        str = str.replaceAll("[^\\d]", "");
        str = str.trim();
        str = str.replaceAll(" +", "");

        if (str.equals(""))
            return "-1";

        return str;
    }

   static boolean isIntel(Enum brand){
        if(brand.equals("intel")) return true;
        else return false;
    }

   public static String extractComponentInfo(Document doc, String regex){
       String info = null;
       Elements element = null;
        try{
            element =  doc.getElementsMatchingOwnText(regex);

            info =  element.get(0).nextElementSibling().text();

        }catch(NullPointerException npe){
//            if there are more than one regex match
//            then can give null pointer excpetion
//            for index 0
            info =  element.get(1).nextElementSibling().text();
        }
        catch (Exception e){
            System.err.println(e);
        }
        return info;
    }
}
