package testcases.client;

import common.BaseClass;

import keyword.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Set;

import testcases.client.ClientBase;

import static java.lang.Thread.sleep;
import static testcases.client.ClientBase.openClientPage;

public class CreateNewClient extends ClientBase {
    protected static String txtCompany = "Client ABC " + new Date();

    @BeforeClass
    public static void navigateToClientPage() {
        signIn("admin@demo.com", "riseDemo");
        openClientPage();
    }

    @Test(priority = 1, invocationCount=3)//, invocationCount=3
    public static void createClientContact() {
        String clientName = txtCompany;
        WebUI.explicitWait(driver, "//a[normalize-space()='Add client']", 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement addClientButton = findEleByXPath("//a[normalize-space()='Add client']");
        js.executeScript("arguments[0].click();", addClientButton);
        WebUI.sleep(3);
        //Check Add Client form displayed
        WebUI.explicitWait(driver, "//h4[normalize-space()='Add client'][1]", 10);
//        String chekAddClientForm = findEleByXPath("(//h4[normalize-space()='Add client']").getText();
//        //WebUI.sleep(30);
//        System.out.println("On page: " + chekAddClientForm);
//        Assert.assertTrue(chekAddClientForm.equals("Add client"));

//        WebElement checkAddClientForm = findEleByXPath("(//h4[normalize-space()='Add client'])[1]");
//        Boolean chekAddClientForm = (Boolean) js.executeScript("arguments[0].isDisplayed();", checkAddClientForm);
//        Assert.assertTrue(chekAddClientForm,"Add client dialog is not displayed.");


        findEleByXPath("//input[@id='company_name']").sendKeys(clientName);
        findEleByXPath("//label[@for='created_by']/following-sibling::div").click();
        findEleByXPath("//div[@id='select2-drop']//input").sendKeys("Sara", Keys.ENTER);
        findEleByXPath("//textarea[@id='address']").sendKeys("123 ABC street");
        findEleByXPath("//input[@id='city']").sendKeys("Alpharetta");
        findEleByXPath("//input[@id='state']").sendKeys("Georgia");
        findEleByXPath("//input[@id='zip']").sendKeys("30200");
        findEleByXPath("//input[@id='country']").sendKeys("United State");
        findEleByXPath("//input[@id='phone']").sendKeys("4627899");
        findEleByXPath("//input[@id='website']").sendKeys("http://abc.com");
        findEleByXPath("//input[@id='vat_number']").sendKeys("999999");
        findEleByXPath("//input[@id='gst_number']").sendKeys("888888");
        WebUI.sleep(2);
        findEleByXPath("//label[normalize-space()='Client groups']/following-sibling::div//input").sendKeys("VIP", Keys.ENTER);
        findEleByXPath("//label[normalize-space()='Client groups']/following-sibling::div//input").sendKeys("Gold", Keys.ENTER);
        WebUI.sleep(2);
        //Currency
        findEleByXPath("//label[@for='currency']/following-sibling::div//a/span[@class='select2-chosen']").click();
        findEleByXPath("//div[@id='select2-drop']/child::div/input").sendKeys("CHW", Keys.ENTER);
        WebUI.sleep(2);
        //Currency Symbol
        findEleByXPath("//input[@id='currency_symbol']").sendKeys("$");
        WebUI.sleep(2);
        findEleByXPath("//label[@class='select2-offscreen' and normalize-space()='Labels']/following-sibling::input").sendKeys("Potential", Keys.ENTER);
        WebUI.sleep(2);
        findEleByXPath("//label[@class='select2-offscreen' and normalize-space()='Labels']/following-sibling::input").sendKeys("Referral", Keys.ENTER);
        findEleByXPath("//input[@id='disable_online_payment']").click();
        //Button
        findEleByXPath("//button[@id='save-and-continue-button']").click();
        //Add multiple contacts
        WebUI.explicitWait(driver, "//input[@id='first_name']", 10);
        findEleByXPath("//input[@id='first_name']").sendKeys("Winbigler");
        findEleByXPath("//input[@id='last_name']").sendKeys("Kalay");
        findEleByXPath("//input[@id='email']").sendKeys("abcd@gmail.com");
        findEleByXPath("//input[@id='phone']").sendKeys("999999");
        findEleByXPath("//input[@id='skype']").sendKeys("abc123");
        findEleByXPath("//input[@id='job_title']").sendKeys("Support Analyst");
        findEleByXPath("//input[@id='gender_female']").click();
        findEleByXPath("//input[@id='login_password']").sendKeys("123456");
        findEleByXPath("//input[@id='email_login_details']").click();
        findEleByXPath("//button[@id='save-and-add-button']").click();
        findEleByXPath("//form[@id='contact-form']//button[@type='button'][normalize-space()='Close']").click();
        //WebUI.sleep(30);

        WebElement closeButton = findEleByXPath("//button[@class='btn-close']");
        System.out.println("Close button is displayed: " + closeButton.isDisplayed());

        js.executeScript("arguments[0].click();", closeButton);
//        WebUI.sleep(5);
//        driver.switchTo().alert().dismiss();
        searchCreatedClient(txtCompany);

    }


}