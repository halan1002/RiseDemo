package keyword;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

import java.time.Duration;

public class WebUI {
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void explicitWait(WebDriver driver, String eleXPath, long second) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(second));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(eleXPath)));
    }
    public static void waitForEleClickable(WebDriver driver, String eleXPath, long second) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(second));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(eleXPath)));
    }
    public static void waitForElePresent(WebDriver driver, String eleXPath, long second) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(second));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(eleXPath)));
    }
}