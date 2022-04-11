package com.apcb.demo.shared.constants;

import java.util.HashMap;

public class RamQuerykeys {
    public static final HashMap<String, String>  type = new HashMap<String, String>();
    public static final HashMap<String, String>  capacity = new HashMap<String, String>();
    static  {
//        type
        type.put("DDR4", "%23dropdown%7C50-874");
        type.put("DDR5", "%23dropdown%7C50-1324");
        type.put("DDR3", "%23dropdown%7C50-1326");

        //        capacity
        capacity.put("4GB", "%23dropdown%7C50-874");
        capacity.put("8GB", "%23dropdown%7C50-874");
        capacity.put("16GB", "%23dropdown%7C50-1324");
        capacity.put("32GB", "%23dropdown%7C50-1324");
    }
}
