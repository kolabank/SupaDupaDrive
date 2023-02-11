package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id="inputUsername")
    public WebElement username;

    @FindBy(id="inputPassword")
    public WebElement password;

    @FindBy(id="login-button")
    public WebElement loginButton;

    public LoginPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }
    public void loginTest(String username, String password){
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.loginButton.click();
    }

}
