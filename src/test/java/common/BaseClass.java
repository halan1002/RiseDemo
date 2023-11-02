package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import keyword.WebUI;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseClass {
    public static WebDriver driver;
    public static JavascriptExecutor js = (JavascriptExecutor) driver;
    //public static Actions action = new Actions(driver);

    @BeforeClass
    public static void createDriver() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public static void closeDriver() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

    public static WebElement findEleByXPath(String xPath) {
        WebElement eleXPath = driver.findElement(By.xpath(xPath));
        return eleXPath;
    }

    public static void clickElement(String xPath) {
        driver.findElement(By.xpath(xPath)).click();
    }

    public static void signIn(String userName, String password) {
        driver.get("https://rise.fairsketch.com/signin");
        //String pageTitle = driver.getTitle();
        String loginTitle = findEleByXPath("//h2[normalize-space()='Sign in']").getText();
        System.out.println(loginTitle);
        Assert.assertTrue(loginTitle.contains("Sign in"), "The page is not loaded fully.");
        // String userName = findEleByXPath("//div[normalize-space()='admin@demo.com']").getText();
        //String password = findEleByXPath("//div[normalize-space()='riseDemo']").getText();
        findEleByXPath("//input[@id='email']").clear();
        findEleByXPath("//input[@id='email']").sendKeys(userName);
        findEleByXPath("//input[@id='password']").clear();
        findEleByXPath("//input[@id='password']").sendKeys(password);
        findEleByXPath("//button[normalize-space()='Sign in']").click();

    }

    public static void captureScreenPrint(String subFolder, String fileName) {
        //DriverManager.getDriver().get(url);
        //Assert.assertEquals(DriverManager.getDriver().getTitle(), "Anh Tester Automation Testing");
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File theDir = new File("./screenshots/" + subFolder + "/");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        try {
            String urlFileName = "./screenshots/" + subFolder + "/" + fileName + ".jpg";
            System.out.println(urlFileName);
            FileHandler.copy(source, new File(urlFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Captured Screenshot successfully!!");
    }

    public static void clearTextField(String clearXpath) {
        driver.findElement(By.xpath(clearXpath)).clear();

    }

    public static int trimTextConvertToInt(String str) {
        String newString = str.trim();
        int convertToInt = Integer.parseInt(newString);
        return convertToInt;
    }

    public static void checkElementDisplay(String eleXpath, String message) {
        Boolean actualText = findEleByXPath(eleXpath).isDisplayed();
        Assert.assertTrue(actualText, message);
    }

    public static void checkElementNotDisplay(String eleXpath, String message) {
        Boolean actualText = findEleByXPath(eleXpath).isDisplayed();
        Assert.assertFalse(actualText, message);
    }

    public static void checkContentMessage(String eleXpath, String expectedMessage, String errMessage) {
        String actualText = findEleByXPath(eleXpath).getText();
        Assert.assertEquals(actualText, expectedMessage, errMessage);

    }

    public static void logout() {
        // implicitWait(10);
        //Use Explicite wait
        keyword.WebUI.explicitWait(driver, "//span[@class='user-name ml10']", 10);
        findEleByXPath("//span[@class='user-name ml10']").click();
        WebUI.sleep(1);
        findEleByXPath("//a[normalize-space()='Sign Out']").click();
        WebUI.sleep(3);
        checkElementDisplay("//h2[normalize-space()='Sign in']", "Step 21: Login page is not displayed, user is not logged out successfully. ");
        WebUI.sleep(2);
    }
    public static boolean existsElement(String xPath) {
        try {
            driver.findElement(By.xpath(xPath));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
    public static int divideInt(int a, int b) {
        int result = 0;
        if (b >= 0) {
            result = a / b;
            return result;
        } else {
            System.out.println("The data is not valid.");
            return result;
        }
    }

    public static int roundUp(int a, int b) {
        int result = 0;
        if (b >= 0) {

            result = (int) Math.ceil((double) a / (double) b);
            return result;
        } else {
            System.out.println("The data is not valid.");
            return result;
        }
    }
}
