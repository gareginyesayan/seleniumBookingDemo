package com.bookdemo.base;

import com.bookdemo.utilities.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


public class BaseClass {

    private static Properties prop;
    public static WebDriver driver;



    @BeforeSuite(groups = { "Smoke", "Regression" })
    public void loadConfig() {
        ExtentManager.setExtent();

        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/Configuration/config.properties");
            prop.load(ip);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @AfterSuite(groups = { "Smoke", "Regression"})
    public void afterSuite() {
        ExtentManager.endReport();
    }

    public void launchApp() {

        String browserName = prop.getProperty("browser", "Chrome");

        if (browserName.contains("Chrome")) {
            driver = WebDriverManager.chromedriver().create();
        } else if (browserName.contains("FireFox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get(prop.getProperty("url", "https://www.booking.com/"));

    }

}
