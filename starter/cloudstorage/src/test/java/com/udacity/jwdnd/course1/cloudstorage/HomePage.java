package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout")
    private WebElement logoutButton;

    public HomePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void testLogout(){
        this.logoutButton.click();

    }
}
