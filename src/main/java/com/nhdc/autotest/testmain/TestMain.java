package com.nhdc.autotest.testmain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestMain {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hhmmss");
        System.out.println(System.getProperty("user.dir")+"\\src\\main\\resources");
        System.out.println(System.getProperty("user.dir")+"/test-output/screenshot/"+"AAA"+".png");
        System.out.println(sdf.format(Calendar.getInstance().getTime()));
    }
}
