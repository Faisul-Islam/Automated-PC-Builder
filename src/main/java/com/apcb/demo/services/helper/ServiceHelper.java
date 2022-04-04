package com.apcb.demo.services.helper;

import com.apcb.demo.dto.response.CPUInitialResponse;
import com.apcb.demo.shared.constants.CPUBrands;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceHelper {

    public static int getCpuAllowedPrice(int price, Enum brand){
        if(isIntel(brand))
        return price * 30 / 100;
        else return price * 50/100;
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
        // Replacing every non-digit number
        // with a space(" ")
        str = str.replaceAll("[^\\d]", "");

        // Remove extra spaces from the beginning
        // and the ending of the string
        str = str.trim();

        // Replace all the consecutive white
        // spaces with a single space
        str = str.replaceAll(" +", "");

        if (str.equals(""))
            return "-1";

        return str;
    }

   static boolean isIntel(Enum brand){
        if(brand.equals("intel")) return true;
        else return false;
    }
}
