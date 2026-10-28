package com.saurabh.framework.utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    /** Columns: TestCaseId, Username, Password, ExpectedResult, ExpectedMessage */
    @DataProvider(name = "loginData", parallel=true)
    public static Object[][] loginData() {
        return ExcelUtils.readSheet("testdata/LoginData.xlsx", "Login");
    }
}
