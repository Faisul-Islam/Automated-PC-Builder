package com.apcb.demo.services.helper;

import com.apcb.demo.shared.constants.CPUBrands;
import org.springframework.stereotype.Component;

@Component
public class ServiceHelper {

    public static int getCpuAllowedPrice(int price){
        return price * 30 / 100;
    }

    public boolean isCPUWithoutGPU(String name, Enum brand ){
        if(brand.equals(CPUBrands.INTEL))
      return name.matches("(.*)[0-9]*(F|KF)(.*)");
        else
            return name.matches("(.*)[0-9]*( |X)(.*)");
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
}
