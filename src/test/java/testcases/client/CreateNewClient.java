package testcases.client;
import common.BaseClass;

import keyword.WebUI;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Set;

import static java.lang.Thread.sleep;

public class CreateNewClient extends BaseClass {
    protected static String txtCompany = "Client ABC " + new Date();
    @BeforeClass
    public static void signInPage(){
        signIn("admin@demo.com", "riseDemo");
    }
    @Test(priority = 1)
    public static void openClientPage(){
        explicitWait("//span[normalize-space()='Clients']", 10);
        String getClientsMenu = findEleByXPath("//span[normalize-space()='Clients']").getText();
        Assert.assertTrue(getClientsMenu.contains("Clients"), "Clients menu is not available.");
        findEleByXPath("//span[normalize-space()='Clients']").click();
        findEleByXPath("//a[contains(text(),'Clients')]").click();
    }

    @Test(priority = 2)
    public static void createClientContact()  {
        String clientName = txtCompany;
       explicitWait("//a[normalize-space()='Add client']", 10);
        findEleByXPath("//a[normalize-space()='Add client']").click();
        WebUI.sleep(3);
        //Check Add Client form displayed
        String chekAddClientForm=findEleByXPath("(//h4[normalize-space()='Add client'])[1]").getText();
        System.out.println("On page: "+ chekAddClientForm);
        Assert.assertTrue(chekAddClientForm.equals("Add client"));
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
        WebUI.sleep(3);
        //driver.switchTo().alert().dismiss();

    }

   @Test(priority = 3)
    //Search the created client
    public static void searchClient() {
        //String clientName = txtCompany;
        findEleByXPath("//button[@class='btn-close']").click();
        findEleByXPath("//a[@data-bs-target='#clients_list']").click();
        explicitWait("//div[@id='client-table_filter']//input", 10);
        findEleByXPath("//div[@id='client-table_filter']//input").sendKeys(txtCompany);
        System.out.println(txtCompany);
        WebUI.sleep(2);
        String results = findEleByXPath("//a[normalize-space()='" + txtCompany + "']").getText();
        Assert.assertTrue(results.equals(txtCompany), "The Client was added successfully");

    }


}