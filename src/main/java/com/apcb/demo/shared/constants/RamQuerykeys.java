package com.apcb.demo.shared.constants;

import java.util.HashMap;

public final class RamQuerykeys {
    public static final HashMap<String, String>  type = new HashMap<String, String>();
    public static final HashMap<String, String>  capacity = new HashMap<String, String>();
    public static final HashMap<String, String>  bus = new HashMap<String, String>();
    static  {
        // type
        type.put("DDR3", "11-112%23dropdown%7C");
        type.put("DDR4", "11-114%23dropdown%7C");
        type.put("DDR5", "11-4230%23dropdown%7C");

        // capacity in GB
        capacity.put("4", "169-1373%23dropdown%7C");
        capacity.put("8", "169-1375%23dropdown%7C");
        capacity.put("16", "169-1376%23dropdown%7C");
        capacity.put("32", "169-1378%23dropdown%7C");

        // bus speed
        bus.put("1600", "170-1382%23dropdown%7C");
        bus.put("2400", "170-1386%23dropdown%7C");
        bus.put("2666", "170-1387%23dropdown%7C");
        bus.put("2933", "170-1389%23dropdown%7C");
        bus.put("3000", "170-1390%23dropdown%7C");
        bus.put("3200", "170-1391%23dropdown%7C");
        bus.put("3466", "170-1393%23dropdown%7C");
        bus.put("4266", "170-1945%23dropdown%7C");

        bus.put("3600", "170-3064%23dropdown%7C");
        bus.put("4000", "170-3121%23dropdown%7C");
        bus.put("4600", "170-3441%23dropdown%7C");
        bus.put("4400", "170-3464%23dropdown%7C");
        bus.put("4800", "170-4231%23dropdown%7C");
        bus.put("5200", "170-4233%23dropdown%7C");
        bus.put("5600", "170-4234%23dropdown%7C");
        bus.put("6200", "170-4250%23dropdown%7C");
        bus.put("3733", "170-4406%23dropdown%7C");
        bus.put("5000", "170-1394%23dropdown%7C");
        bus.put("6200", "170-4337%23dropdown%7C");
    }
}
