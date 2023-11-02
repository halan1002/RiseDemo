package testcases.client;

import common.BaseClass;
import keyword.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.tracing.opentelemetry.SeleniumSpanExporter;
import org.testng.Assert;

import java.util.List;


public class ClientBase extends BaseClass {
    public static void openClientPage() {
        WebUI.explicitWait(driver, "//span[normalize-space()='Clients']", 10);
        String getClientsMenu = findEleByXPath("//span[normalize-space()='Clients']").getText();
        Assert.assertTrue(getClientsMenu.contains("Clients"), "Clients menu is not available.");
        findEleByXPath("//span[normalize-space()='Clients']").click();
        findEleByXPath("//a[contains(text(),'Clients')]").click();
    }

    public static int displayClientLits(WebDriver webDriver, String xPath, int col, String filter) {
        //column=2 => no filter, display Client Name
        //Column =4 => filter by Client Groups (Gold, VIP, Siliver)
        //Column =5 => filter by Labels(Inactive, Referal, Unsastified, Sastified, Coporate, Potential, 90% Probability, 50% Probability, Call this week)
        WebUI.explicitWait(driver, xPath, 10);
        List<WebElement> clientElement = driver.findElements(By.xpath(xPath));
        int totalClient = clientElement.size();
        System.out.println("--------displayClientLits-------------");
        System.out.println("\t Element size: " + totalClient);
        String filterColumn="Name";
        boolean noRecordFound= existsElement("//td[@class='dataTables_empty']");
        //System.out.println("\t - No record found: " + noRecordFound);
        if (totalClient > 1 & noRecordFound==false) {
            for (int i = 1; i < totalClient; i++) {

                switch (col) {
                    case 2://no filter, displayed Client Name
                        System.out.println(" ++ Default case, no filter, displayed by: " + filterColumn);
                        break;
                    case 4: //Filter by CLient Groups, VIP
                        filterColumn="Client Groups";
                        System.out.println("\t ++ Displaying the Client Names are filtering by " + filterColumn + " as " + filter);
                        String clientGroupXPath = xPath + "[" + i + "]" + "/td[" + col + "]";
                        String clientGroups = findEleByXPath(clientGroupXPath).getText();
                        Assert.assertEquals(clientGroups, filter, "Case 4 - filter by Client Groups: The Client "+ i +" is filtered incorrectly.");
                        break;
                    case 5://Labels(Inactive, Referal, Unsastified, Sastified, Coporate, Potential, 90% Probability, 50% Probability, Call this week)
                        filterColumn="Labels";
                        System.out.println("\t Client Name is filtering by" + filterColumn + " as " + filter);
                        String labelsXPath = xPath + "[" + i + "]" + "/td[" + col + "]";
                        String actualFilter = findEleByXPath(labelsXPath).getText();
                        Assert.assertEquals(actualFilter, filter, "Case 5 - filter by Labels: The Client "+ i +" is filtered incorrectly.");
                        break;
                }
                String cLientNameXPath = xPath + "[" + i + "]" + "/td[2]";
                System.out.println("\t Element: " + cLientNameXPath);
                String clientName = findEleByXPath(cLientNameXPath).getText();
                System.out.println("\t Client Name " + i + ": " + clientName);
            }
            System.out.println("\t - Showed all records in the list on the first page, total is: " + (totalClient - 1));
        } else {
            System.out.println("\t - There is no record displayed.");
            totalClient-=1;//Subtraction of the no record found row
        }
        System.out.println("--------Ending displayClientLits method -------------");
        return totalClient-1;
    }

    public static void searchCreatedClient(String txtCompany) {
        //String clientName = txtCompany;
        findEleByXPath("//a[@data-bs-target='#clients_list']").click();
        WebUI.explicitWait(driver, "//div[@id='client-table_filter']//input", 10);
        findEleByXPath("//div[@id='client-table_filter']//input").sendKeys(txtCompany);
        //System.out.println(txtCompany);
        WebUI.sleep(2);
        String results = findEleByXPath("//a[normalize-space()='" + txtCompany + "']").getText();
        Assert.assertTrue(results.equals(txtCompany), "The Client was added successfully");
    }

    public static void searchClient(String txtCompany) {
        //String clientName = txtCompany;
        findEleByXPath("//a[@data-bs-target='#clients_list']").click();
        WebUI.explicitWait(driver, "//div[@id='client-table_filter']//input", 10);
        findEleByXPath("//div[@id='client-table_filter']//input").sendKeys(txtCompany);
        WebUI.sleep(2);
    }
}
