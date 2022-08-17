package com.bookdemo.testcases;

import com.aventstack.extentreports.Status;
import com.bookdemo.base.BaseClass;
import com.bookdemo.dataprovider.DataProviders;
import com.bookdemo.pageobjects.HomeStaysPage;
import com.bookdemo.pageobjects.PropertyPage;
import com.bookdemo.pageobjects.SearchResultPage;
import com.bookdemo.utilities.Log;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;

import static com.bookdemo.utilities.ExtentManager.test;

public class PropertyPageTest extends BaseClass {



    private HomeStaysPage staysPage;
    private SearchResultPage resultsPage;
    private PropertyPage propertyPage;
    private LocalDate checkin;
    private LocalDate checkout;
    private int nights =3;
    private String destination = "Boston";

    private int propertyIndex = 0;

    //!!TBD!! All hardcoded values  should be moved to external file

    @BeforeMethod(groups = {"Smoke", "Regression"})
    public void setup() {
        launchApp();
        staysPage = new HomeStaysPage();
        checkin = LocalDate.now().plusDays(2);
        checkout = LocalDate.now().plusDays(2+nights);
        resultsPage = staysPage.search(destination, checkin, checkout);
        propertyPage = resultsPage.navigateToProperty(propertyIndex);


    }

    @AfterMethod(groups = {"Smoke", "Regression"})
    public void tearDown() {
        driver.quit();
    }

    //=======TESTS===========================================================================================================

    @Test(groups = "Regression")
    public void test001_VerifyingPageContent(){
        Log.startTestCase("test001_VerifyingPageContent");
        test.log(Status.INFO, "Now on page" + propertyPage.getPageTitle());
        SoftAssert softAssert = new SoftAssert();
        test.log(Status.INFO, "Verifying property title");
        softAssert.assertTrue(propertyPage.isPropertyTitleDisplayed(), "Property Title s not displayed");
        test.log(Status.INFO, "Verifying reserve button");
        softAssert.assertTrue(propertyPage.isReserveBtnDisplayed(), "Reserve button is not displayed");
        test.log(Status.INFO, "Verifying Hotel content");
        softAssert.assertTrue(propertyPage.isHotelContentDisplayed(), "Hotel content is not displayed");
        test.log(Status.INFO, "Verifying property highlights");
        softAssert.assertTrue(propertyPage.isPropertyHighlightHeaderDisplayed(), "Property highlights are not displayed");
        test.log(Status.INFO, "Verifying summary");
        softAssert.assertTrue(propertyPage.isHotelSummaryDisplayed(), "Summary is not displayed");
        test.log(Status.INFO, "Verifying available rooms table");
        softAssert.assertTrue(propertyPage.isAvailableRoomsTableDisplayed(), "Available Rooms table is not visible");
        softAssert.assertAll();
    }
}
