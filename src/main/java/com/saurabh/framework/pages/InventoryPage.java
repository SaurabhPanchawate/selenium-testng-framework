package com.saurabh.framework.pages;

import com.saurabh.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    private final WaitUtils wait;

    private final By pageTitle = By.cssSelector(".title");

    public InventoryPage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }

    public String getTitle() {
        return wait.visible(pageTitle).getText();
    }
}
