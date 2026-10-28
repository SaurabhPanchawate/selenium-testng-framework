package com.saurabh.framework.base;

import com.saurabh.framework.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    // ThreadLocal keeps each parallel test on its own browser
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("") String browserParam) {
        String browser = browserParam.isEmpty() ? ConfigReader.get("browser") : browserParam;
        boolean headless = ConfigReader.getBoolean("headless");
        log.info("Launching browser: {} (headless={})", browser, headless);

        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions ff = new FirefoxOptions();
                if (headless) {
                    ff.addArguments("-headless");
                }
                driver = new FirefoxDriver(ff);
                break;
            case "edge":
                EdgeOptions edge = new EdgeOptions();
                if (headless) {
                    edge.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edge);
                break;
            case "chrome":
            default:
                ChromeOptions chrome = new ChromeOptions();
                if (headless) {
                    chrome.addArguments("--headless=new", "--window-size=1920,1080");
                }
                chrome.addArguments("--disable-notifications");
                driver = new ChromeDriver(chrome);
                break;
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("pageLoadTimeout")));
        driver.manage().window().maximize();
        DRIVER.set(driver);
        driver.get(ConfigReader.get("baseUrl"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
            log.info("Browser closed");
        }
    }
}
// clone the repo and do the changes
