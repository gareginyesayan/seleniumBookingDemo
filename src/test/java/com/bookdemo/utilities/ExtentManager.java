package com.bookdemo.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bookdemo.base.BaseClass;


public class ExtentManager {

    // public static ExtentHtmlReporter htmlReporter;
    public static ExtentSparkReporter htmlReporter;// = new ExtentSparkReporter("Spark.html");
    public static ExtentReports extent;
    public static ExtentTest test;

    public static void setExtent() {
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/ExtentReport/" + "report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("Booking Demo Test Automation Report");
        htmlReporter.config().setTheme(Theme.DARK);

        extent.setSystemInfo("HostName", "MyHost");
        extent.setSystemInfo("OS", "Win11");
        extent.setSystemInfo("Browser", "Chrome");

    }

    public static void endReport() {
        extent.flush();
    }
}
