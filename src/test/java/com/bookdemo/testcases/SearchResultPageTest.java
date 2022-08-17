package com.bookdemo.testcases;

import com.aventstack.extentreports.Status;
import com.bookdemo.base.BaseClass;
import com.bookdemo.dataprovider.DataProviders;
import com.bookdemo.pageobjects.HomeStaysPage;
import com.bookdemo.pageobjects.SearchResultPage;
import com.bookdemo.utilities.Log;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static com.bookdemo.utilities.ExtentManager.test;

public class SearchResultPageTest extends BaseClass {


    private HomeStaysPage staysPage;
    private SearchResultPage resultsPage;
    private LocalDate checkin;
    private LocalDate checkout;
    private int nights;
    private String destination;
    private int checkinAddToCurrentDate;


    @BeforeMethod(groups = {"Smoke", "Regression"})
    public void setup() {
        launchApp();
        checkinAddToCurrentDate = 1;
        nights =3;
        staysPage = new HomeStaysPage();
        checkin = LocalDate.now().plusDays(checkinAddToCurrentDate);
        checkout = LocalDate.now().plusDays(checkinAddToCurrentDate+nights);
        destination = "Philadelphia";
        resultsPage = staysPage.search(destination, checkin, checkout);

        //!!TBD!! Hardcoded values for checkin/checkout dates and destination should be moved to external file

    }

    @AfterMethod(groups = {"Smoke", "Regression"})
    public void tearDown() {
        driver.quit();
    }


//=======TESTS===========================================================================================================


    // Verifying Breakfast included filter functionality.
    // Selecting filter and verifyimg that all properties on page have "Breakfast included" label
    @Test(groups = {"Regression", "Smoke"})
    public void test001_VerifyBreakfastFilter(){

        Log.startTestCase("test001_VerifyBreakfastFilter");
        test.log(Status.INFO, "Selecting breakfast included filter");
        resultsPage.selectBreakfastIncluded();
        test.log(Status.INFO, "Verifying that all properties have  \"Breakfast included\"");
        int withBreakfast = resultsPage.countOfPropertiesWithBreakfast();
        int allProperties = resultsPage.countOfProperties();
        Assert.assertEquals(withBreakfast, allProperties);

    }

    // Verifying sorting by price (from low to high).
    // Clicking on the sorting button.
    // Creating list with all price and verifying that the prices have ascending order
    @Test(groups = "Regression")
    public void test002_VerifySortingByPrices(){
        Log.startTestCase("test002_VerifySortingByPrices");
        test.log(Status.INFO, "Clicking od set price order button and getting the list of prices");
        boolean sorted = resultsPage.setPriceLowerFirst();
        List<Integer> prices= resultsPage.getPrices();

        test.log(Status.INFO, "Verifying prices have ascending order");
        for(int i = 0; i < prices.size() -1; i++){
            if (prices.get(i) > prices.get(i+1)){
                test.log(Status.FAIL, "Found not sorted values "+ prices.get(i) + ">" + prices.get(i+1));
                test.log(Status.INFO, "All prices: " +prices);
                sorted = false;
                resultsPage.moveToProperty(i);
                break;
            }
        }
        Assert.assertTrue(sorted);

    }

    // Verifying budget filter.
    // Selecting the filter.
    // Getting prices for all properties on the page.
    // Verifying that all peices are in the selected budget limits.
    // This test can fail because of not valid index. Requirements to number of budget filter are needed. To make indexes valid change destination
    @Test (groups = "Regression", dataProvider = "Indexes", dataProviderClass = DataProviders.class)
    public void test003_VerifyBudgetFilter(int index)  {
        Log.startTestCase("test003_VerifyBudgetFilter with the index: " + index);
        Log.warn("This test can fail because of not valid index. Requirements to number of budget filter are needed ");
        test.log(Status.INFO, "Verifying Budget Filter with the index: " + index);

        boolean selectedCorrectly = true;
        int numberOfFilters = resultsPage.getBudgetFiltersMax().size();
        int maxBudget = -1;

        test.log(Status.INFO, "Verifying if index is valid");
        Assert.assertTrue(index<numberOfFilters, "Not valid index: " + index +". Expected to be less than " + numberOfFilters);


        test.log(Status.INFO, "Selecting  filter with the index: " + index);
        maxBudget = nights * resultsPage.selectBudgetFilter(index);
        test.log(Status.INFO, "maximum allowed budget is set to " + maxBudget);

        List<Integer> prices = resultsPage.getPrices();

        test.log(Status.INFO, "Verifying all properties prices on the page are in the budget limits");
        for(int i = 0; i < prices.size(); i++){
            if (prices.get(i) > maxBudget){
                test.log(Status.FAIL, "Price " + prices.get(i) + " is greater than maximum allowed budget " + maxBudget);
                test.log(Status.INFO, "All prices: " + prices);
                selectedCorrectly = false;
                resultsPage.moveToProperty(i);
                break;
            }
        }
        Assert.assertTrue(selectedCorrectly);
    }

}
