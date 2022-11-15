package com.bookdemo.testcases;

import com.aventstack.extentreports.Status;
import com.bookdemo.base.BaseClass;
import com.bookdemo.pageobjects.FlightsPage;
import com.bookdemo.pageobjects.HomeStaysPage;
import com.bookdemo.utilities.Log;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.bookdemo.utilities.ExtentManager.test;

public class  FlightsPageTest extends BaseClass{



    private HomeStaysPage staysPage;
    private FlightsPage flightsPage;


    @BeforeMethod(groups = {"Smoke", "Regression"})
    public void setup() {

        launchApp();
        staysPage = new HomeStaysPage(driver);
        flightsPage = staysPage.clickOnFlightsBtn();

    }

    @AfterMethod(groups = {"Smoke", "Regression"})
    public void tearDown() {
        driver.quit();
    }


    //=======TESTS===========================================================================================================

    @Test(groups = {"Smoke", "Regression"})
    void test001_VerifyTripOptionsRadioGroup() {
        Log.startTestCase("test001_VerifyTripOptionsRadioGroup");
        test.log(Status.INFO, "Validating round trip radio button is displayed");
        Assert.assertTrue(flightsPage.isRoundTripRadioDisplayed());
        test.log(Status.INFO, "Validating one way radio button is displayed");
        Assert.assertTrue(flightsPage.isOneWayRadioDisplayed());
        test.log(Status.INFO, "Validating multi stop radio button is displayed");
        Assert.assertTrue(flightsPage.isMultiStopRadioDisplayed());


    }

    @Test(groups = "Smoke")
    void test002_VerifyRoundTripIsSelected() {

        Log.startTestCase("test002_VerifyRoundTripIsSelected");
        test.log(Status.INFO, "Validating round trip radio button is selected");
        Assert.assertTrue(flightsPage.isRoundTripRadioSelected());
    }


}
