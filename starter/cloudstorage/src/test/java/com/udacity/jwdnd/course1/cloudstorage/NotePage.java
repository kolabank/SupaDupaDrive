package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {

    @FindBy(id="nav-notes-tab")
    WebElement navNoteTab;

    @FindBy(id = "addNewNote")
    WebElement newNoteButton;


    @FindBy (id = "note-title")
    WebElement noteTitleText;


    @FindBy(id="note-description")
    WebElement noteDescriptionText;

    @FindBy(id="submitNote")
    WebElement submitNote;

    @FindBy(id="noteTitle")
    WebElement noteTitleView;

    @FindBy( id="noteDescription")
    WebElement noteDescriptionView;

    @FindBy(id="noteEdit")
    WebElement noteEdit;

    @FindBy(id= "noteDelete")
    WebElement noteDeleteButton;

    public NotePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }


    public  void clickNavNoteTab(){
        this.navNoteTab.click();
    }

    public void addNote(){
        this.newNoteButton.click();
    }

    public void newNoteTitle(String newTitle){
        this.noteTitleText.clear();
        this.noteTitleText.sendKeys(newTitle);
    }

    public void newNoteDescription (String newDescription){
        this.noteDescriptionText.clear();
        this.noteDescriptionText.sendKeys(newDescription);
    }

    public void noteSubmit(){
        this.submitNote.click();
    }

    public String viewTitle(){
        return this.noteTitleView.getText();

    }

    public String viewDescription(){
        return this.noteDescriptionView.getText();
    }

    public void editButtonClick(){
        this.noteEdit.click();
    }

    public void editNoteTitle(String newTitle){
        this.noteTitleText.clear();
        this.noteTitleText.sendKeys(newTitle);
    }

    public void deleteNote(){
        this.noteDeleteButton.click();
    }
}
