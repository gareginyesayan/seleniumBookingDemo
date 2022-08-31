package com.bookdemo.pageobjects;

import com.bookdemo.base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PropertyPage {

    private WebDriver driver;

//    @FindBy(xpath = "//h2[@class='pp-header__title']")
    @FindBy(css = "#wrap-hotelpage-top h2")
    private WebElement propertyTitle;
    @FindBy(css = "#hp_book_now_button")
    private WebElement reserveBtn;
    @FindBy(css = "#hotel_main_content")
    private WebElement hotelContent;
    @FindBy(css = "#summary")
    private WebElement hotelSummary;
    @FindBy(xpath = "//h3[contains(text(),'Property Highlights')]")
    private WebElement propertyHighlightHeader;
    @FindBy(css = "#hprt-table")
    private WebElement availableRoomsTable;

    //  CONSTRUCTOR ========================================================================================================
    public PropertyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        System.out.println("Now on page:" + driver.getTitle());
    }

    //=====================================================================================================================

    public boolean isAvailableRoomsTableDisplayed(){
        boolean flag = false;
        try {
            flag = availableRoomsTable.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isPropertyHighlightHeaderDisplayed(){
        boolean flag = false;
        try {
            flag = propertyHighlightHeader.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isHotelSummaryDisplayed(){
        boolean flag = false;
        try {
            flag = hotelSummary.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isHotelContentDisplayed(){
        boolean flag = false;
        try {
            flag = hotelContent.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isReserveBtnDisplayed(){
        boolean flag = false;
        try {
            flag = reserveBtn.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isPropertyTitleDisplayed(){
        boolean flag = false;
        try {
            flag = propertyTitle.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getPageTitle(){
        return driver.getTitle();
    }



}
