package com.bookdemo.pageobjects;

import com.bookdemo.base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseClass {

    @FindBy(name = "username")
    private WebElement usernameInput;
    @FindBy(xpath = "//button[@type='submit']//*[contains(text(),'Continue with email')]")
    private WebElement submitBtn;
    @FindBy(xpath = "//h1[text()='Sign in or create an account']")
    private WebElement headerText;
    //  CONSTRUCTOR ========================================================================================================
    public LoginPage() {
        PageFactory.initElements(driver, this);
        System.out.println("Now on page:" + driver.getTitle());
    }

    //======================================================================================================================
    public boolean validateEmailInput() {
        try {
            return usernameInput.isDisplayed();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean validateContinueWithEmailButton() {
        try {
            return submitBtn.isDisplayed();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean validateHeaderText() {
        boolean flag = false;
        try {
            flag = headerText.isDisplayed();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}
