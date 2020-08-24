package com.udacity.jwdnd.course1.cloudstorage;

import org.h2.mvstore.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CredentialsTab {

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredential;

    @FindBy(id = "add-credential")
    private WebElement addButton;

    @FindBy(id = "edit-credential")
    private List<WebElement> editButton;

    @FindBy(id = "delete-credential")
    private List<WebElement> deleteButton;

    @FindBy(id = "credUrl")
    private List<WebElement> urlList;

    @FindBy(id = "credUsername")
    private List<WebElement> usernameList;

    @FindBy(id = "credPassword")
    private List<WebElement> passwordList;

    @FindBy(id = "credential-url")
    private WebElement inputUrl;

    @FindBy(id = "credential-username")
    private WebElement inputUsername;

    @FindBy(id = "credential-password")
    private WebElement inputPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement submitButton;

    @FindBy(id = "close-button")
    private WebElement closeButton;

    @FindBy(id = "credential-modal-submit")
    private WebElement submitModalButton;

    public CredentialsTab(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getPassword(){
        return inputPassword;
    }

    public List<String> getDetail(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
        navCredential.click();
        wait.until(ExpectedConditions.visibilityOf(addButton));
        List<String> detail = new ArrayList<>(List.of(urlList.get(0).getText(),
                usernameList.get(0).getText(),
                passwordList.get(0).getText()));

        return detail;
    }

    public void close(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(closeButton)).click();
    }

    public void addCredential(WebDriver driver, String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();

        wait.until(ExpectedConditions.visibilityOf(addButton)).click();

        wait.until(ExpectedConditions.visibilityOf(inputUrl)).sendKeys(url);
        wait.until(ExpectedConditions.visibilityOf(inputUsername)).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys(password);

        wait.until(ExpectedConditions.visibilityOf(submitModalButton)).click();

        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
    }

    public void editCredential(WebDriver driver, String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();

        wait.until(ExpectedConditions.visibilityOf(editButton.get(0))).click();

        wait.until(ExpectedConditions.visibilityOf(inputUrl));
        inputUrl.clear();;
        inputUrl.sendKeys(url);

        wait.until(ExpectedConditions.visibilityOf(inputUsername));
        inputUsername.clear();
        inputUsername.sendKeys(username);

        wait.until(ExpectedConditions.visibilityOf(inputPassword));
        inputPassword.clear();
        inputPassword.sendKeys(password);

        wait.until(ExpectedConditions.visibilityOf(submitModalButton)).click();
    }

    public void deleteCredential(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();

        wait.until(ExpectedConditions.visibilityOf(deleteButton.get(0))).click();
    }
}
