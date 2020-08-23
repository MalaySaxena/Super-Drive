package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class NotesTab {

    @FindBy(id="nav-notes-tab")
    private WebElement navNote;

    @FindBy(id="add-note")
    private WebElement addButton;

    @FindBy(name = "noteTitle")
    private WebElement inputNoteTitle;

    @FindBy(name="noteDescription")
    private WebElement inputNoteDescription;

    @FindBy(id="noteSubmit")
    private WebElement submitButton;

    @FindBy(xpath = "//td[text()='Example Title']")
    private List<WebElement> displayTitle;

    @FindBy(xpath = "//td[text()='Example Description']")
    private List<WebElement> displayDescription;

    @FindBy(xpath = "//div[@id='nav-notes']//*[@class='btn btn-success']")
    private List<WebElement> editButton;

    @FindBy(xpath = "//div[@id='nav-notes']//*[@class='btn btn-danger']")
    private List<WebElement> deleteLink;

    public NotesTab(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void navigateNotes(){
        navNote.click();
    }

    public void submitNOteForm(String title, String description){
        addButton.click();
        inputNoteTitle.sendKeys(title);
        inputNoteDescription.sendKeys(description);
        submitButton.click();
    }

    public List<String> getDetails(){
        List<String> detail = new ArrayList<>(List.of(displayTitle.get(0).getText(),
                displayDescription.get(0).getText()));
        return detail;
    }

    public void editNote(String title, String description){
        editButton.get(0).click();
        inputNoteTitle.sendKeys(title);
        inputNoteDescription.sendKeys(description);
        submitButton.click();
    }

    public void deleteNote(){
        deleteLink.get(0).click();
    }

}
