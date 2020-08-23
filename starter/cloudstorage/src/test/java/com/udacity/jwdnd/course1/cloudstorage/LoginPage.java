package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends LoadableComponent<LoginPage> {

    @FindBy(id = "inputUserName")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signup-msg")
    private WebElement signupSuccess;

    @FindBy(id = "submit-button")
    private WebElement button;

    private WebDriver webDriver;

    public LoginPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
        isLoaded();
    }

    @Override
    protected void load() {

    }

    @Override
    public void isLoaded() throws Error {
        // Initial loading, called when creating the page object to make sure that the page is loaded to a state where it is ready to interact with us, in our case it means that button is present in DOM and visible.
        waitForVisibility(button);
    }

    private void waitForVisibility(WebElement element) throws Error{
        new WebDriverWait(webDriver, 60)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void login(String username, String pass) {
        waitForVisibility(userName);
        this.userName.sendKeys(username);
        waitForVisibility(password);
        this.password.sendKeys(pass);
        waitForVisibility(button);
        this.button.click();
    }

}
