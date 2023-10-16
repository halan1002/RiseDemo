package testcases.user;

import common.BaseClass;
import keyword.WebUI;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static common.BaseClass.*;

//Test case: login.docx
public class login extends BaseClass {
    static double num = Math.random();
    static String url = "https://rise.fairsketch.com/signin";
    static String user1 = "client@demo.com";
    static String passUser1 = "riseDemo";
    static String firstName2 = "User2" + new Date();
    static String lastName2 = "Test";
    static String user2 = "testuser" + num + "@demo.com";
    static String invalidPassUser2 = "123";
    static String validPassUser2 = "123456";
    static String user3 = "testuser3@demo.com";
    static String user4 = "testuser4";
    static String clientName1 = "Client Demo";

    @Test(priority = 1)
    //Requirement 01: Signup
    public static void signUp() {
        driver.get(url);
        WebUI.sleep(2);
        //Steps 2

        findEleByXPath("//a[normalize-space()='Sign up']").click();
        //implicitWait(5);
        explicitWait("//p[normalize-space()='Create an account as a new client.']", 5);
        checkElementDisplay("//p[normalize-space()='Create an account as a new client.']", "Step 2: Sign up page is not displayed.");
        //Step 3
        findEleByXPath("//input[@id='first_name']").sendKeys(firstName2);
        findEleByXPath("//input[@id='last_name']").sendKeys(lastName2);
        findEleByXPath("//label[normalize-space()='Individual']").click();
        WebUI.sleep(2);
        Boolean eleCompany = findEleByXPath("//input[@id='company_name']").isDisplayed();
        Assert.assertFalse(eleCompany, "Step 3: The Company field should not be displayed.");
        captureScreenPrint("Login", "LoginStep3");
        //Step 4
        findEleByXPath("//label[normalize-space()='Organization']").click();
        findEleByXPath("//input[@id='company_name']").sendKeys(clientName1);
        WebUI.sleep(2);
        //Step 5
        findEleByXPath("//input[@id='email']").sendKeys(user1);
        findEleByXPath("//input[@id='password']").sendKeys(passUser1);
        findEleByXPath("//input[@id='retype_password']").sendKeys(invalidPassUser2);
        findEleByXPath("//button[normalize-space()='Sign up']").click();
        WebUI.sleep(2);
        checkContentMessage("//span[@id='retype_password-error']", "Please enter the same value again.", "Step 5: The error message displayed incorrectly.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep5");
        //Step 6
        clearTextField("//input[@id='email']");
        clearTextField("//input[@id='password']");
        clearTextField("//input[@id='retype_password']");
        findEleByXPath("//input[@id='email']").sendKeys(user1);
        findEleByXPath("//input[@id='password']").sendKeys(passUser1);
        findEleByXPath("//input[@id='retype_password']").sendKeys(passUser1);
        findEleByXPath("//button[normalize-space()='Sign up']").click();
        checkContentMessage("//div[@class='app-alert-message']", "Account already exists for your email address. Sign in", "Step 5: The error message displayed incorrectly.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep6");
        //Step 7
        clearTextField("//input[@id='email']");
        clearTextField("//input[@id='password']");
        clearTextField("//input[@id='retype_password']");
        findEleByXPath("//button[normalize-space()='Sign up']").click();
        checkContentMessage("//span[@id='email-error']", "This field is required.", "Step 7 Email: The error message displayed incorrectly.");
        checkContentMessage("//span[@id='password-error']", "This field is required.", "Step 7 Password: The error message displayed incorrectly.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep7");
        //Step 8
        findEleByXPath("//input[@id='email']").sendKeys(user2);
        findEleByXPath("//input[@id='password']").sendKeys(invalidPassUser2);
        findEleByXPath("//button[normalize-space()='Sign up']").click();
        checkContentMessage("//span[@id='password-error']", "Please enter at least 6 characters.", "Step 8 Password: The error message displayed incorrectly.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep8");
        //Step 9
        clearTextField("//input[@id='password']");
        findEleByXPath("//input[@id='password']").sendKeys(validPassUser2);
        findEleByXPath("//input[@id='retype_password']").sendKeys(invalidPassUser2);
        WebUI.sleep(2);
        checkContentMessage("//span[@id='retype_password-error']", "Please enter the same value again.", "Step 9: The error message displayed incorrectly.");

        captureScreenPrint("Login", "LoginStep9");
        //Step 10
        clearTextField("//input[@id='retype_password']");
        findEleByXPath("//input[@id='retype_password']").sendKeys(validPassUser2);
        findEleByXPath("//button[normalize-space()='Sign up']").click();
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep10Temp");
        checkContentMessage("//div[@class='app-alert-message']", "Your account has been created successfully! Sign in", "Step 10: The error message displayed incorrectly.");
        checkElementDisplay("//a[normalize-space()='Sign in']", "Step 10 Sign in: The Sign in link is not displayed.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep10");
    }

    //Requirement 02: Login
    @Test(priority = 2)
    public static void login() {
        WebUI.sleep(2);
        //Step 11, 12
        findEleByXPath("//a[normalize-space()='Sign in']").click();
        clearTextField("//input[@id='email']");
        clearTextField("//input[@id='password']");
        //Step 13
        signIn(user2, validPassUser2);
        //implicitWait(10);
        explicitWait("//span[normalize-space()='Dashboard']",10);
        checkElementDisplay("//span[normalize-space()='Dashboard']", "Step 13 dashboard: Dashboard page is not displayed.");
        String actualfullname = findEleByXPath("//span[@class='user-name ml10']").getText();
        String expectedfullName = firstName2 + " " + lastName2;
        checkContentMessage("//span[@class='user-name ml10']", expectedfullName, "Step 13 Fullanme: The full name of the logged user is not correct.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep13");
        logout();
        //Step 14
        clearTextField("//input[@id='email']");
        clearTextField("//input[@id='password']");
        findEleByXPath("//input[@id='email']").sendKeys(user1);
        findEleByXPath("//input[@id='password']").sendKeys(invalidPassUser2);
        findEleByXPath("//button[normalize-space()='Sign in']").click();
        WebUI.sleep(1);
        checkContentMessage("//div[@role='alert']", "Authentication failed!", "Step 14: The error message is not correct.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep14");
        //Step 15
        //implicitWait(5);
        explicitWait("//input[@id='email']",5);
        clearTextField("//input[@id='email']");
        clearTextField("//input[@id='password']");
        findEleByXPath("//input[@id='email']").sendKeys(user3);
        findEleByXPath("//input[@id='password']").sendKeys(validPassUser2);
        findEleByXPath("//button[normalize-space()='Sign in']").click();
        WebUI.sleep(2);
        checkContentMessage("//div[@role='alert']", "Authentication failed!", "Step 15: The error message is not correct.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep15");
        //Step 16
        clearTextField("//input[@id='email']");
        clearTextField("//input[@id='password']");
        findEleByXPath("//input[@id='email']").sendKeys(user4);
        findEleByXPath("//input[@id='password']").sendKeys(validPassUser2);
        WebUI.sleep(1);
        checkContentMessage("//span[@id='email-error']", "Please enter a valid email address.", "Step 16: The error message is not correct.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep16");
        //Step 17
        clearTextField("//input[@id='email']");
        findEleByXPath("//input[@id='password']").click();
        checkContentMessage("//span[@id='email-error']", "This field is required.", "Step 17: The error message is not correct.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep17");
        //Step 18
        findEleByXPath("//input[@id='email']").sendKeys(user1);
        clearTextField("//input[@id='password']");
        findEleByXPath("//button[normalize-space()='Sign in']").click();
        WebUI.sleep(1);
        checkContentMessage("//span[@id='password-error']", "This field is required.", "Step 18: The error message is not correct.");
        captureScreenPrint("Login", "LoginStep18");
        //Step 19
        //Actions action = new Actions(driver);
        // action.keyDown(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
        //implicitWait(10);
        explicitWait("//div[normalize-space()='Client']",10);
        findEleByXPath("//div[normalize-space()='Client']").click();
        WebUI.sleep(5);
        //implicitWait(5);
        explicitWait("//input[@id='email']",5);
        String txtSetEmail = findEleByXPath("//input[@id='email']").getAttribute("value");
        String txtSetPass = findEleByXPath("//input[@id='password']").getAttribute("value");
        Assert.assertEquals(txtSetEmail, user1, "Step 19: Email is not set correctly.");
        Assert.assertEquals(txtSetPass, passUser1, "Step 19: Password is not set correctly.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep19");
        //Step 20
        findEleByXPath("//button[normalize-space()='Sign in']").click();
        //implicitWait(10);
        explicitWait("//span[normalize-space()='Dashboard']",10);
        checkElementDisplay("//span[normalize-space()='Dashboard']", "Step 20: Dashboard page is not displayed.");
        WebUI.sleep(2);
        captureScreenPrint("Login", "LoginStep20");
        //Step 21
        logout();
    }
}
