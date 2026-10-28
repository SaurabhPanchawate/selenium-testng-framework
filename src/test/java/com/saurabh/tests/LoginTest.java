package com.saurabh.tests;

import com.saurabh.framework.base.BaseTest;
import com.saurabh.framework.pages.InventoryPage;
import com.saurabh.framework.pages.LoginPage;
import com.saurabh.framework.utils.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class,
          description = "Login with valid and invalid credentials from Excel")
    public void loginTest(String testCaseId, String username, String password,
                          String expectedResult, String expectedMessage) {

        log.info("Running {} -> user='{}', expected={}", testCaseId, username, expectedResult);
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("SUCCESS")) {
            InventoryPage inventory = new InventoryPage(getDriver());
            Assert.assertEquals(inventory.getTitle(), expectedMessage, "Inventory page title mismatch");
        } else {
            String actual = loginPage.getErrorMessage();
            Assert.assertTrue(actual.contains(expectedMessage),
                    "Expected error to contain '" + expectedMessage + "' but was '" + actual + "'");
        }
    }
}
// test the pull
