package com.bookdemo.pageobjects;

import com.bookdemo.actiondriver.MyActions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeStaysPage {


    //    LOCATORS =========================================================================================================
    @FindBy(xpath = "//span[contains(text(), 'Stays')]/ancestor::a")
    private WebElement staysBtn;
    @FindBy(xpath = "//span[contains(text(), 'Flights')]/ancestor::a")
    private WebElement flightsBtn;
    @FindBy(css = "input[name='ss']")
    private WebElement destinationInput;

    @FindBy(xpath = "//ul[@data-testid='autocomplete-results']")
    private WebElement suggestedDestinations_other;
    @FindBy(xpath = "//input[@name='ss']/../following-sibling::ul[1]")
    private WebElement suggestedDestinations;
    @FindBy(css = ".xp__dates__checkin")
    private WebElement calendarInput;
    @FindBy(xpath = "//button[@data-testid = 'date-display-field-start']")
    private WebElement calendarInputNew;
    @FindBy(xpath = "//div[@data-mode='checkout']//i[contains(@data-placeholder,'+')]")
    private WebElement checkoutDay;
    @FindBy(xpath = "//div[@data-mode='checkin']//i[contains(@data-placeholder,'+')]")
    private WebElement checkinDay;
    @FindBy(id = "xp__guests__inputs-container")
    private WebElement guestdInputContainer;
    @FindBy(css = ".xp__guests__count")
    private WebElement guestdInput;
    @FindBy(xpath = "//button[@data-testid = 'occupancy-config']")
    private WebElement guestdInputNew;
    @FindBy(css = "button[data-testid='occupancy-config']")
    private WebElement guestdInput_other;

    @FindBy(xpath = "//div[contains(@class,'b-group-children')]//span[@data-bui-ref='input-stepper-value']")
    private WebElement currentChildren;  //Element name TBD!!!!!
    @FindBy(xpath = "//input[@Id='group_children']/../div[2]")
    private WebElement currentChildren_new;  //Element name TBD!!!!!

    @FindBy(xpath = "//div[contains(@class,'sb-group-children')]//button[contains(@class, 'bui-stepper__add-button')]")
    private WebElement addChildrenBtn;
    @FindBy(xpath = "//div[contains(@class,'sb-group-children')]//button[contains(@class, 'bui-stepper__subtract-button')]")
    private WebElement subtractChildrenBtn;

    @FindAll(@FindBy(xpath = "//select[@name='age']"))
    private List<WebElement> childrenAgeSelect;
    @FindBy(xpath = "//span[@data-children-count]")
    private WebElement childrenBox;
    @FindBy(xpath = "//header//*[contains(text(),'Sign in')]")
    private WebElement SignInBtn;
    @FindBy(xpath = "//span[contains(text(),'Search')]/ancestor::button")
    private WebElement searchBtn;

    public WebElement getCalendarElementByDate(String date) {
        String xp = "//*[@data-date='" + date + "']";
        return driver.findElement(By.xpath(xp));
    }
    //This wierd method is needed to fix some issues with locators where different home pages are loading intermittently
    //Fixing this in such non-usual way because no  possibility to discuss this with dev team
    private void changeToNewLocators() {
        try {
            // trying to see if old page has been launched
            guestdInput.isDisplayed();
        } catch (NoSuchElementException e) {
           // if not then changing to locators matching new page
            System.out.println("changing locator");
            guestdInput = guestdInput_other;
            currentChildren = currentChildren_new;
            suggestedDestinations = suggestedDestinations_other;
            calendarInput = calendarInputNew;
            guestdInput = guestdInputNew;
        }
    }
    //=================================================================================================================

    private MyActions myActions = new MyActions();
    private WebDriver driver;


    //  CONSTRUCTOR ========================================================================================================
    public HomeStaysPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        System.out.println(driver.getTitle());
        changeToNewLocators();

    }

    //======================================================================================================================
    //Verifying that "Stays" is selected after launching the home page
    public boolean isStaysSelected() {
        return staysBtn.getAttribute("class").contains("selected");
    }


    //Navigating to Sign In page
    public LoginPage clickOnSignIn() {

        myActions.click(driver, SignInBtn);
        return new LoginPage(driver);
    }

    //Navigating to Flights  page
    public FlightsPage clickOnFlightsBtn() {

        myActions.click(driver, flightsBtn);
        return new FlightsPage(driver);
    }

    //Input destination by entering some key and selecting suggested choice by index
    public String inputDestination(String destination, int choice) {
        destinationInput.clear();
        destinationInput.sendKeys(destination);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> listOfSuggestedDestinations = suggestedDestinations.findElements(By.tagName("li"));
        listOfSuggestedDestinations.get(choice).click();
        return destinationInput.getAttribute("value");
    }

    // Getting destination suggestions list after entering some key into destination input
    public List<String> getDestinationSuggestions(String destination) {
        myActions.type(destinationInput, destination);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> listOfSuggestedDestinations = suggestedDestinations.findElements(By.tagName("li"));

        List<String> suggestions = new ArrayList<>();
        for (WebElement ele : listOfSuggestedDestinations) {
            String suggestion = ele.getAttribute("data-label");
            if (suggestion == null) {
                suggestion = ele.getText();
            }
            suggestions.add(suggestion);

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
        int currentCount = Integer.parseInt((currentChildren).getText());

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
        return new SearchResultPage(driver);
    }


}
