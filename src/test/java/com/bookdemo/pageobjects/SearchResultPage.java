package com.bookdemo.pageobjects;

import com.bookdemo.actiondriver.MyActions;
import com.bookdemo.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchResultPage {

    private WebDriver driver;

    @FindAll(@FindBy(xpath = "//div[@data-testid='price-and-discounted-price']/span[last()]"))
    private List<WebElement> prices;
    @FindBy(xpath = "//ul[@role='menubar']//li[@data-id='price']")
    private WebElement priceLFBtn;
    @FindBy(xpath = "//button[@data-testid='sorters-dropdown-trigger']")
    private WebElement sortersDD;
    @FindBy(xpath = "//button[@data-id='price']")
    private WebElement priceLFBtnInDD;
    @FindBy(xpath = "//ul[@role='menubar']//li[@data-id='dropdown']")
    private WebElement optionsDD;
    @FindBy(xpath = "//ul[@role='menubar']//li[@data-id='dropdown']//li[@data-id='class_asc']")
    private WebElement classAcsOrderOption;
    @FindAll(@FindBy(xpath = "//div[@data-testid='property-card'][2]//div[@data-testid='price-for-x-nights']"))
    private List<WebElement> nightsAdultsLabels;
    @FindBy(xpath = "//div[@data-testid='recommended-units']//span[text()='Breakfast included']")
    private List<WebElement> breakfastLabels;
    @FindBy(xpath = "//div[@data-testid='review-score']//div[contains(@aria-label,'Scored')]")
    private List<WebElement> reviewScores;
    @FindBy(xpath = "//div[@data-testid='availability-cta']/a")
    private List<WebElement> availabilityBtns;
    @FindBy(xpath = "//div[@data-testid='title']")
    private List<WebElement> propertyTitles;
    @FindBy(xpath = "(//div[@data-filters-group='mealplan']//div[contains(text(),'Breakfast Included')]/ancestor::div[@data-filters-item]/input)[1]")
    private WebElement breakfastCheckbox;
    @FindBy(xpath = "((//div[@data-filters-group='pri'])[1]//input)[position() > 1]")
    private List<WebElement> budgetFilterCheckBoxes;
    @FindBy(xpath = "(//div[@data-filters-group='pri'])[1]//div[@data-testid='filters-group-label-container']/span")
    private List<WebElement> budgetFiltersCounts;
    @FindBy(xpath = "(//div[@data-filters-group='pri'])[1]//div[contains(text(),'$')]")
    private List<WebElement> budgetFiltersLabels;


    @FindBy(xpath = "//*[@data-testid='overlay-spinner']")
    private WebElement spinner;

    private String selectedProperty;
    private MyActions myAction = new MyActions();


    //  CONSTRUCTOR ========================================================================================================
    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        System.out.println("Now on page:" + driver.getTitle());

    }
    // ====================================================================================================================

    public String getSelectedProperty() {
        return selectedProperty;
    }

    public PropertyPage selectPropertyByIndex(int ind) {
        String propertyTitle = "Title";
        return new PropertyPage(driver);
    }

    //Order results by price
    public boolean setPriceLowerFirst() {
        boolean flag = true;
        try {
            priceLFBtn.click();
        } catch (Exception e) {
            System.out.println("Cannot find Menu Bar");
            try {
                sortersDD.click();
                priceLFBtnInDD.click();
            } catch (Exception ex) {
                flag = false;
                System.out.println("Cannot set price sorter");
                throw new RuntimeException(e);
            }
        }
        waitForSpinnerToDisappear();
        return flag;
    }


    //After setting filter or changing order spinner comes up indicating progress.
    public void waitForSpinnerToDisappear() {
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOf(spinner));
        } catch (Exception e) {
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(spinner));

    }

    //Select "Breakfast included" filter
    public void selectBreakfastIncluded(){
        myAction.scrollByVisibilityOfElement(driver, breakfastCheckbox);
        if (!breakfastCheckbox.isSelected()) {
            breakfastCheckbox.click();
        }
        waitForSpinnerToDisappear();

    }

    //Counting how many properties on the page have "Breakfast included"
    public int countOfPropertiesWithBreakfast() {
        return breakfastLabels.size();
    }

    //Counting number of properties on the current page
    public int countOfProperties() {
        return propertyTitles.size();
    }

    //Getting list of properties prices by the order they are displayed on the page
    public List<Integer> getPrices() {
        List<Integer> pricesNum = new ArrayList<>();
        for (WebElement price : prices) {
            pricesNum.add(Integer.parseInt(price.getText().
                    replace("$", "").
                    replace(",", "")));
        }
        return pricesNum;
    }

    public void moveToProperty(int index) {
        WebElement element = propertyTitles.get(index);
        myAction.scrollByVisibilityOfElement(driver, element);

    }

    //Selecting one of budget filters. The method returns max price per night allowed for that filter
    public int selectBudgetFilter(int index) {
        myAction.scrollByVisibilityOfElement(driver, budgetFilterCheckBoxes.get(index));
        int maxPrice = -1;
        if (index < budgetFilterCheckBoxes.size() - 1) {  // "-1" is for excluding the last checkbox $xxx+
            try {
                maxPrice = getBudgetFiltersMax().get(index);
            } catch (Exception e) {
                System.out.println("Cannot fetch budget max value");
                e.printStackTrace();
            }
        }
        if (!budgetFilterCheckBoxes.get(index).isSelected()) {
            budgetFilterCheckBoxes.get(index).click();
        }
        waitForSpinnerToDisappear();

        return maxPrice;

    }

    //Getting list with numbers of properties for each of budget filters.
    public List<Integer> getBudgetFiltersCounts() {
        List<Integer> counts = new ArrayList<>();
        for (WebElement count : budgetFiltersCounts) {
            counts.add(Integer.parseInt(count.getText()));
        }
        return counts;
    }

    //This method returns max price from the expression like $100-$200. Ignoring the case $xxx+
    public List<Integer> getBudgetFiltersMax() {
        myAction.scrollByVisibilityOfElement(driver, budgetFilterCheckBoxes.get(0));
        List<Integer> maxValues = new ArrayList<>();
        for (WebElement label : budgetFiltersLabels) {
            if (!label.getText().contains("+")) {  //Excluding the last label looking like $xxx+
                maxValues.add(Integer.parseInt(label.getText().replaceAll("\\$.*\\$", "")));
            }
        }
        return maxValues;
    }

    //Navigate to property by index
    public PropertyPage navigateToProperty(int index) {
        String currentHandle = driver.getWindowHandle();
        propertyTitles.get(index).click();
        Set<String> handles = driver.getWindowHandles();
        for (String h : handles) {
            if (!h.equals(currentHandle)) {
                driver.switchTo().window(h);
            }
        }
        return new PropertyPage(driver);

    }


}
