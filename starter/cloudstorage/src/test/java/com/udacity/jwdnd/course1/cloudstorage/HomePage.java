package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends LoadableComponent<HomePage> {

    @FindBy(xpath = "//button[text()='Logout']")
    private WebElement logoutButton;

    @FindBy(xpath = "//div[@id='logoutDiv']//h1")
    private WebElement name;

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
        isLoaded();
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        waitForVisibility(name);
    }
    private void waitForVisibility(WebElement element) throws Error{
        new WebDriverWait(webDriver, 60)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void logout(){
        logoutButton.click();
    }
    public String getName(){
        return name.getText();
    }
}
