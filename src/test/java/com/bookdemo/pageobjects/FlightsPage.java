package com.bookdemo.pageobjects;

import com.bookdemo.base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class FlightsPage {

    private WebDriver driver;

    @FindBy(xpath = "//ul//*[@data-testid='searchbox_controller_trip_type_ROUNDTRIP']")
    private WebElement RoundTripOption;
    @FindBy(xpath = "//ul//*[@data-testid='searchbox_controller_trip_type_ONEWAY']")
    private WebElement OneWayOption;
    @FindBy(xpath = "//ul//*[@data-testid='searchbox_controller_trip_type_MULTISTOP']")
    private WebElement MultiStopOption;
    @FindBy(xpath = "//ul//*[@data-testid='searchbox_controller_trip_type_ROUNDTRIP']/input[@type='radio']")
    private WebElement RoundTripRadio;
    @FindBy(xpath = "//ul//*[@data-testid='searchbox_controller_trip_type_ONEWAY']/input[@type='radio']")
    private WebElement OneWayRadio;

//    private MyActions myActions = new MyActions();
//  CONSTRUCTOR ========================================================================================================
    public FlightsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        System.out.println("Now on page:" + driver.getTitle());
    }
    //==================================================================================================================
    public boolean isRoundTripRadioDisplayed(){
        boolean flag = false;
        try {
            flag = RoundTripOption.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isOneWayRadioDisplayed() {

        boolean flag = false;
        try {
            flag = OneWayOption.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isMultiStopRadioDisplayed() {
        boolean flag = false;
        try {
            flag = MultiStopOption.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public boolean isRoundTripRadioSelected() {
        boolean flag = false;
        try {
            flag = RoundTripRadio.isSelected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
