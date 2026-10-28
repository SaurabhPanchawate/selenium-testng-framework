package com.saurabh.framework.listeners;

import com.saurabh.framework.utils.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempt = 0;
    private final int maxRetry = ConfigReader.getInt("retryCount");

    @Override
    public boolean retry(ITestResult result) {
        if (attempt < maxRetry) {
            attempt++;
            return true;
        }
        return false;
    }
}
