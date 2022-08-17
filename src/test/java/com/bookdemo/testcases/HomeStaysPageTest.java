package com.bookdemo.testcases;

import com.aventstack.extentreports.Status;
import com.bookdemo.base.BaseClass;
import com.bookdemo.dataprovider.DataProviders;
import com.bookdemo.pageobjects.HomeStaysPage;
import com.bookdemo.pageobjects.LoginPage;
import com.bookdemo.utilities.Log;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

import static com.bookdemo.utilities.ExtentManager.test;


public class HomeStaysPageTest extends BaseClass {

    private HomeStaysPage staysPage;
    SoftAssert softAssert;


    @BeforeMethod(groups = {"Smoke", "Regression"})
    public void setup() {
        launchApp();
        staysPage = new HomeStaysPage();
        softAssert = new SoftAssert();

    }

    @AfterMethod(groups = {"Smoke","Regression"})
    public void tearDown() {
        driver.quit();
    }

    //=======TESTS=======================================================================================================

    @Test(groups = "Smoke")
    void test001_ValidateIsStaysBtnSelected() {
        Log.startTestCase("test001_ValidateIsStaysBtnSelected");
        Assert.assertTrue(staysPage.isStaysSelected());
    }
//----------------------------------------------------------------------------------------------------------------------------
    @Test(groups = "Smoke")
    void test002_verifySignInButton() {
        Log.startTestCase("test002_verifySignInButton");
        LoginPage loginPage = staysPage.clickOnSignIn();
        test.log(Status.INFO, "Validating if Sign In button is displayed and clicking leads to the login page");
        Assert.assertTrue(loginPage.validateHeaderText(), "'Sign in or create an account' is not displayed");

    }
//-------------------------------------------------------------------------------------------------------------------------
    //This test
    // enters destination into input,
    // get suggestions and select the top choice
    // verifies that text in destination input initial contents user input
    @Test(groups = "Regression", dataProvider = "destinationdata", dataProviderClass = DataProviders.class)
    void test003_verifySelectedDestination(String destination) {
        Log.startTestCase("test003_verifySelectedDestination: " + destination);
        test.log(Status.INFO, "Entering " + destination +" as destination");
        test.log(Status.INFO, "Getting list of suggestion and selecting the first choice");
        String selectedDestination = staysPage.inputDestination(destination, 0);
        test.log(Status.INFO, "Verifying that selected choice contains user input");
        Assert.assertTrue(selectedDestination.contains(destination), "Selected choice does not contain " + destination);
    }

//---------------------------------------------------------------------------------------------------------------------------
    //Verifying that all destination suggestions contain user input
    @Test(groups = "Regression",  dataProvider = "destinationdata", dataProviderClass = DataProviders.class)
    void test004_VerifyDestinationSuggestion(String destination) {
        Log.startTestCase("test004_VerifyDestinationSuggestion for " + destination);
        test.log(Status.INFO, "Entering" + destination +" as destination");
        test.log(Status.INFO, "Getting list of suggestions...");
        List<String> suggestions = staysPage.getDestinationSuggestions(destination);
        System.out.println(suggestions);
        boolean containsDestination = true;
        test.log(Status.INFO, "Verifying that all suggestions contain user input...");
        for (String suggestion : suggestions) {
            if (!suggestion.contains(destination)) {
                containsDestination = false;
                Log.error(suggestion + " does not contain " + destination);
                test.log(Status.FAIL, suggestion + " does not contain " + destination);
                break;
            }
        }
        Assert.assertTrue(containsDestination);

    }

    //-----------------------------------------------------------------------------------------------------------------------
    //Verifying that user can successfully enter checkin and checkout dates.
    // For testing offset of 1 day from the current date is set for checkin and 7 for checkout.
    // Later can be done with data provider, but that may require some improvement in SetDates method
    @Test(groups = "Regression")
    void test005_VerifyDates() {
        Log.startTestCase("test005_VerifyDates");
        test.log(Status.INFO, "Verifying user can input checkin and checkout dates");
        LocalDate checkinDate = LocalDate.now().plusDays(1);
        LocalDate checkoutDate = LocalDate.now().plusDays(7);
        test.log(Status.INFO, "Setting dates: checkin: " + checkinDate + ", checkout: " + checkoutDate);
        staysPage.setDates(checkinDate, checkoutDate);
        int checkinDay = staysPage.getCalendarCheckInDay();
        int checkoutDay = staysPage.getCalendarCheckOutDay();
        test.log(Status.INFO, "Verifying checkin and checkout dates are set as per user choice...");
        softAssert.assertEquals(checkinDay, checkinDate.getDayOfMonth(), "checkin day is not correct");
        softAssert.assertEquals(checkoutDay, checkoutDate.getDayOfMonth(), "checkout day is not correct");
        softAssert.assertAll();
    }

    //-----------------------------------------------------------------------------------------------------------------------
    //Verifying yser can add children. Possible values 0 - 10
    @Test(groups = "Regression", dataProvider = "childrendata", dataProviderClass = DataProviders.class)
    void test006_VerifyChildrenAgeBox(Integer childrenCount) {
        Log.startTestCase("test006_VerifyChildrenAgeBox: " + childrenCount);
        test.log(Status.INFO, "Verifying user can add " + childrenCount + " childrens and set ages");
        String children = staysPage.setChildren(childrenCount);
        String expectedStr = Integer.toString(childrenCount) + " child";
        test.log(Status.INFO, "Verifying childrens added as per user input");
        Assert.assertTrue(children.contains(expectedStr), "Children are not set correctly");
    }

}
