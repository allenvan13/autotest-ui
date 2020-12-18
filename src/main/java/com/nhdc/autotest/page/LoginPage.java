package com.nhdc.autotest.page;

import com.nhdc.autotest.base.DriverBase;
import org.openqa.selenium.WebElement;

public class LoginPage extends PageBase{

    public LoginPage(DriverBase driver){
        super(driver);
    }

    public WebElement getLoginButton(){
        return explicitlyWait(3,"L_loginButton");
    }

    public void clickLogin(){
        getLoginButton().click();
    }
}
