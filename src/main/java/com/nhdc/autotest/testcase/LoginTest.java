package com.nhdc.autotest.testcase;

import com.nhdc.autotest.base.DriverBase;
import com.nhdc.autotest.listener.RetryListener;
import com.nhdc.autotest.listener.TestngListener;
//import com.nhdc.autotest.page.PageBase;
import com.nhdc.autotest.utils.GetProfile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestngListener.class, RetryListener.class})
public class LoginTest extends CaseBase{

    public DriverBase driver;

    @BeforeMethod
    public void beforeMethod() {
        driver = super.driver;
    }

    @Test(description = "登陆测试：输入XXXXX")
    public void testLogin(){
        System.out.println("=============================================");
        driver.getElement(GetProfile.getBy("ID_loginButton")).click();
        driver.sleep(2);
        driver.getElement(GetProfile.getBy("L_userName")).sendKeys("13333333333");
        driver.sleep(1);
        driver.getElement(GetProfile.getBy("L_password")).sendKeys("33333333");
        driver.sleep(1);
        driver.getElement(GetProfile.getBy("L_code")).sendKeys("3333");
        driver.sleep(1);
        driver.getElement(GetProfile.getBy("L_loginButton")).click();
        driver.sleep(2);

    }
}
