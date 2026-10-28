# Data-Driven TestNG Framework (Selenium + TestNG + Excel + Extent + Log4j2)

## Import into Eclipse
1. Install TestNG plugin: Help > Eclipse Marketplace > search "TestNG" (optional, for running testng.xml from the IDE).
2. File > Import > Maven > **Existing Maven Projects** > select this folder > Finish.
3. Wait for Maven to download dependencies (needs internet, first time only).
4. Right-click `testng.xml` > Run As > **TestNG Suite**.
   Or from terminal: `mvn clean test`

Requires JDK 11+ and Chrome installed (Selenium Manager downloads the driver automatically).

## Structure
- `base/BaseTest`            - browser setup/teardown (ThreadLocal driver, chrome/firefox/edge, headless)
- `pages/`                   - Page Object classes (one per screen)
- `utils/`                   - ConfigReader, ExcelUtils, WaitUtils, ScreenshotUtil, DataProviders
- `listeners/`               - TestListener (Extent + screenshot on failure), RetryAnalyzer, RetryTransformer
- `tests/`                   - TestNG test classes
- `src/test/resources/config`   - config.properties, log4j2.xml
- `src/test/resources/testdata` - LoginData.xlsx

## How data-driven works
`LoginData.xlsx` (sheet "Login") -> `ExcelUtils.readSheet()` -> `@DataProvider` -> test method runs once per row.
Add a row in Excel = add a test case. No code change needed.

## Outputs
- `reports/ExtentReport_<timestamp>.html`  (screenshots embedded for failures)
- `reports/screenshots/`
- `logs/automation.log`

## Useful overrides
`mvn clean test -Dbrowser=firefox -Dheadless=true -DretryCount=0`
(note: the `browser` value in testng.xml takes priority over config.properties)
