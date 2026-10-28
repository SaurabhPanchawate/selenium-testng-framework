package com.saurabh.framework.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.saurabh.framework.base.BaseTest;
import com.saurabh.framework.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            name += " [" + params[0] + "]";   // first data column = test case id
        }
        ExtentTest test = ExtentManager.getInstance().createTest(name);
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        ExtentManager.setTest(test);
        log.info("START: {}", name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test passed");
        log.info("PASS: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("FAIL: {}", result.getName(), result.getThrowable());
        ExtentTest test = ExtentManager.getTest();
        WebDriver driver = BaseTest.getDriver();
        if (driver != null) {
            // onTestFailure runs before @AfterMethod, so the browser is still open here
            ScreenshotUtil.saveToFile(driver, result.getName());
            String base64 = ScreenshotUtil.asBase64(driver);
            test.fail(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Failure screenshot").build());
        } else {
            test.fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.skip(result.getThrowable() != null ? result.getThrowable().toString() : "Test skipped");
        }
        log.warn("SKIP: {}", result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
        ExtentManager.removeTest();
    }
}
