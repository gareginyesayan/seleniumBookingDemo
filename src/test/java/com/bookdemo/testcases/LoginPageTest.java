package com.bookdemo.testcases;


import com.aventstack.extentreports.Status;
import com.bookdemo.base.BaseClass;
import com.bookdemo.pageobjects.HomeStaysPage;
import com.bookdemo.pageobjects.LoginPage;
import com.bookdemo.utilities.Log;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.bookdemo.utilities.ExtentManager.test;

public class LoginPageTest extends BaseClass {

    private HomeStaysPage staysPage;
    private LoginPage loginPage;


    @BeforeMethod(groups = {"Smoke", "Regression"})
    public void setup() {

        launchApp();
        staysPage = new HomeStaysPage();
        loginPage = staysPage.clickOnSignIn();

    }

    @AfterMethod(groups = {"Smoke", "Regression"})
    public void tearDown() {
        driver.quit();
    }

    //=======TESTS===========================================================================================================

    @Test(groups = "Smoke")
    void test001_VerifyEmailInput() {
        Log.startTestCase("test001_VerifyEmailInput");
        test.log(Status.INFO, "Validating email input is displayed");
        Assert.assertTrue(loginPage.validateEmailInput());
        Log.endTestCase("Verifying email input is displayed");


    }

    @Test(groups = "Smoke")
    void test002_VerifySubmitEmailButton() {

        Log.startTestCase("test002_VerifySubmitEmailButton");
        test.log(Status.INFO, "Validating 'Continue with Email' button is displayed");
        Assert.assertTrue(loginPage.validateContinueWithEmailButton());

    }

}
