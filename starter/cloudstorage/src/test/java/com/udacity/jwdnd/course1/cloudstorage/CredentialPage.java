package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {

    //Nav tab for credentials on home page
    @FindBy(id="nav-credentials-tab")
    WebElement navCredentialsTab;

    @FindBy(id = "addNewCredential" )
    WebElement newCredentialButton;

    ///Elements for modal for adding new credentials
    @FindBy (id = "credential-URL")
    WebElement urlText;

    @FindBy(id="credential-username")
    WebElement usernameText;

    @FindBy(id="credential-password")
    WebElement passwordText;

    @FindBy(id ="submitCredential")
    WebElement submitCredential;

    @FindBy(id="closeButton")
    WebElement closeButton;

/////Elements on main credentials page
    @FindBy(id = "credentialURL")
    WebElement credentialUrl;

    @FindBy(id = "credentialUsername")
    WebElement credentialUsername;

    @FindBy(id = "credentialPassword")
    WebElement credentialPassword;

    @FindBy(id="credentialEdit" )
    WebElement credentialEditButton;

    @FindBy(id="credentialDelete")
    WebElement credentialDeleteButton;



    public CredentialPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void clickCredentialsTab(){
        this.navCredentialsTab.click();
    }

    public void addNewCredentials(){
        this.newCredentialButton.click();
    }
    public void fillURL(String URL){
        this.urlText.clear();
        this.urlText.sendKeys(URL);

    }

    public void fillUsername(String username){
        this.usernameText.clear();
        this.usernameText.sendKeys(username);
    }

    public void fillPassword (String password){
        this.passwordText.clear();
        this.passwordText.sendKeys(password);
    }

    public void saveChanges(){
        this.submitCredential.click();
    }

    public String viewURL(){
        return this.credentialUrl.getText();
    }


    public String viewUsername(){
        return this.credentialUsername.getText();
    }

    public String viewPassword(){
        return this.credentialPassword.getText();
    }

    public void clickCredentialDelete(){
        this.credentialDeleteButton.click();
    }

    public void clickCredentialEdit(){
        this.credentialEditButton.click();
    }

    public String viewModalURL(){
        return this.urlText.getAttribute("value");
    }

    public String viewModalUsername(){
        return this.usernameText.getAttribute("value");
    }

    public String viewModalPassword(){
        return this.passwordText.getAttribute("value");
    }


}
