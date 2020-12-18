package com.nhdc.autotest.testcase;

import com.nhdc.autotest.base.DriverBase;
import com.nhdc.autotest.utils.GetProfile;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CaseBase {

    public DriverBase driver;

    public DriverBase InitDriverBase(String browser) {
        return new DriverBase(browser);
    }

    @BeforeTest
    public void before() {
        driver = InitDriverBase("Chrome");
        String url = GetProfile.getEnvirConfig("url_test");
        driver.get(url);
        driver.setWindowMax();

        driver.implicitlyWait(10);
    }

    @AfterTest
    public void after() {
        driver.sleep(5);
        driver.stopDriver();
    }

    /**
     * 截图，并按名字+日期存储
     * @param result
     */
    public void takeScreenShot(String result) {

        File screenShot = ((TakesScreenshot)driver.getDriver()).getScreenshotAs(OutputType.FILE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-hh_mm_ss");
        StringBuilder screenshotPath = new StringBuilder(System.getProperty("user.dir"));
        screenshotPath.append("\\test-output\\screenshot\\").append(result).append(sdf.format(Calendar.getInstance().getTime())).append(".png");
        File desFile = new File(screenshotPath.toString());
        try {
            FileUtils.copyFile(screenShot, desFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
