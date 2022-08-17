package com.bookdemo.pageobjects;

import com.bookdemo.actiondriver.MyActions;
import com.bookdemo.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeStaysPage extends BaseClass {


    //    LOCATORS =========================================================================================================
    @FindBy(xpath = "//span[contains(text(), 'Stays')]/ancestor::a")
    private WebElement staysBtn;
    @FindBy(xpath = "//span[contains(text(), 'Flights')]/ancestor::a")
    private WebElement flightsBtn;
    @FindBy(css = "input#ss")
    private WebElement destinationInput;
    @FindBy(css = "ul[aria-label='List of suggested destinations ']")
    private WebElement suggestedDestinations;
    @FindBy(css = "div.xp__dates-inner:not(.xp__dates__checkin):not(.xp__dates__checkout)")
    private WebElement calendarInput;
    @FindBy(xpath = "//div[@data-mode='checkout']//i[contains(@data-placeholder,'+')]")
    private WebElement checkoutDay;
    @FindBy(xpath = "//div[@data-mode='checkin']//i[contains(@data-placeholder,'+')]")
    private WebElement checkinDay;
    //
//    @FindBy(xpath = "//span[contains(@data-placeholder,'Check-in month')]")
//    private WebElement checkinMY;
//    @FindBy(xpath = "//span[contains(@data-placeholder,'Check-out month')]")
//    private WebElement checkoutMY;
    @FindBy(id = "xp__guests__inputs-container")
    private WebElement guestdInputContainer;
    @FindBy(id = "xp__guests__toggle")
    private WebElement guestdInput;
    @FindBy(xpath = "//div[contains(@class,'b-group-children')]//span[@data-bui-ref='input-stepper-value']")
    private WebElement currentChildren;  //Element name TBD!!!!!
    @FindBy(xpath = "//div[contains(@class,'sb-group-children')]//button[contains(@class, 'bui-stepper__add-button')]")
    private WebElement addChildrenBtn;
    @FindBy(xpath = "//div[contains(@class,'sb-group-children')]//button[contains(@class, 'bui-stepper__subtract-button')]")
    private WebElement subtractChildrenBtn;

    //    @FindBy(xpath = "//select[@name='age']")  //Usage may be wrong --> Verify later!!!!!!
//    private WebElement ageSelect;
    @FindAll(@FindBy(xpath = "//select[@name='age']"))
    private List<WebElement> childrenAgeSelect;
    @FindBy(xpath = "//span[@data-children-count]")
    private WebElement childrenBox;
    @FindBy(xpath = "//header//*[contains(text(),'Sign in')]")
    private WebElement SignInBtn;
    @FindBy(xpath = "//span[contains(text(),'Search')]/ancestor::button")
    private WebElement searchBtn;

    public WebElement getCalendarElementByDate(String date) {
        String xp = "//td[@data-date='" + date + "']";
        return driver.findElement(By.xpath(xp));
    }
    //=================================================================================================================

    private MyActions myActions = new MyActions();


    //  CONSTRUCTOR ========================================================================================================
    public HomeStaysPage() {
        PageFactory.initElements(driver, this);
        System.out.println(driver.getTitle());

    }

    //======================================================================================================================
    //Verifying that "Stays" is selected after launching the home page
    public boolean isStaysSelected() {
        return staysBtn.getAttribute("class").contains("selected");
    }

    //Navigating to Sign In page
    public LoginPage clickOnSignIn() {

        myActions.click(driver, SignInBtn);
        return new LoginPage();
    }

    //Navigating to Flights  page
    public FlightsPage clickOnFlightsBtn() {

        myActions.click(driver, flightsBtn);
        return new FlightsPage();
    }

    //Input destination by entering some key and selecting suggested choice by index
    public String inputDestination(String destination, int choice) {
        destinationInput.clear();
        destinationInput.sendKeys(destination);
        List<WebElement> listOfSuggestedDestinations = suggestedDestinations.findElements(By.tagName("li"));
        listOfSuggestedDestinations.get(choice).click();
        return destinationInput.getAttribute("value");
    }

    // Getting destination suggestions list after entering some key into destination input
    public List<String> getDestinationSuggestions(String destination) {
        myActions.type(destinationInput, destination);
        List<WebElement> listOfSuggestedDestinations = suggestedDestinations.findElements(By.tagName("li"));
        List<String> suggestions = new ArrayList<>();
        for (WebElement ele : listOfSuggestedDestinations) {
            suggestions.add(ele.getAttribute("data-label"));
        }
        return suggestions;
    }

    // Setting checkin and checkout dates
    public void setDates(LocalDate checkin, LocalDate checkout) {

        String checkinString = checkin.toString();
        String checkoutString = checkout.toString();
        try {
            getCalendarElementByDate(checkinString).click();
            getCalendarElementByDate(checkoutString).click();
        } catch (Exception e) {
            calendarInput.click();
            getCalendarElementByDate(checkinString).click();
            getCalendarElementByDate(checkoutString).click();
        }
    }


    //Getting value of checkin day from calendar input field
    public int getCalendarCheckInDay() {
        String day = checkinDay.getAttribute("innerHTML");
        int d = 0;
        try {
            d = Integer.parseInt(day);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return d;
    }

    //Getting value of checkout day from calendar input field
    public int getCalendarCheckOutDay() {
        String day = checkoutDay.getAttribute("innerHTML");
        int d = 0;
        try {
            d = Integer.parseInt(day);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return d;
    }

    //Setting number of children (max = 10) and entering their ages in the appearing age boxes
    public String setChildren(int childrenCount) {
        guestdInput.click();
        int currentCount = Integer.parseInt((currentChildren).getAttribute("innerHTML"));
        if (childrenCount >= currentCount) {
            for (int i = currentCount; i < childrenCount; i++)
                addChildrenBtn.click();
        } else {
            for (int i = childrenCount; i < currentCount; i++)
                subtractChildrenBtn.click();
        }
        System.out.println(childrenAgeSelect.size());

        if (childrenCount > 0) {
            //Selecting  children ages from the dropdown. Age value should be valid (0-17), but is meaningless here
            for (WebElement dd : childrenAgeSelect) {
                Select kid = new Select(dd);
                kid.selectByIndex(childrenCount + 1);
            }
        }
        return childrenBox.getText();
    }

    //Setting destination and dates and navigating to Search results. Guests number here is default (2 adults, 0 children)
    public SearchResultPage search(String destination, LocalDate checkin, LocalDate checkout) {
        inputDestination(destination, 0);
        setDates(checkin, checkout);
        searchBtn.click();
        return new SearchResultPage();
    }


}
