package com.saurabh.framework.pages;

import com.saurabh.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WaitUtils wait;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }

    public LoginPage enterUsername(String username) {
        wait.visible(usernameField).clear();
        wait.visible(usernameField).sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        wait.visible(passwordField).clear();
        wait.visible(passwordField).sendKeys(password);
        return this;
    }

    public void clickLogin() {
        wait.clickable(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return wait.visible(errorMessage).getText();
    }
}
