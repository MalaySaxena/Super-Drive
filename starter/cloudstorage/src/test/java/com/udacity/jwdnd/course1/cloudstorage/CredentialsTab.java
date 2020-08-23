package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CredentialsTab {

    @FindBy(id="nav-credentials-tab")
    private WebElement navCredential;

    @FindBy(id="add-credential")
    private WebElement addButton;

    @FindBy(id="credential-url")
    private WebElement inputUrl;

    @FindBy(id="credential-username")
    private WebElement inputUsername;

    @FindBy(id="credential-password")
    private WebElement inputPassword;

    @FindBy(id="credentialSubmit")
    private WebElement submitButton;

    @FindBy(xpath = "//td[text()='Example Username']")
    private List<WebElement> displayUsername;

    @FindBy(xpath = "//td[text()='Example Password']")
    private List<WebElement> displayPassword;

    @FindBy(xpath = "//th[text()='Example URL']")
    private List<WebElement> displayUrl;

    @FindBy(xpath = "//div[@id='nav-credentials']//*[@class='btn btn-success']")
    private WebElement editButton;

    @FindBy(xpath = "//div[@id='nav-credentials']//*[@class='btn btn-danger']")
    private WebElement deleteLink;

    public CredentialsTab(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void navigateCredential(){
        navCredential.click();
    }

    public void submitCredentialForm(String url, String userName, String password){
        addButton.click();
        inputUrl.sendKeys(url);
        inputUsername.sendKeys(userName);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    

}
