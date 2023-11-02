package testcases.client;

import keyword.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CheckClientPagination extends ClientBase {
    @BeforeClass
    public static void navigateToClientPage() {
        signIn("admin@demo.com", "riseDemo");
        WebUI.sleep(2);
        openClientPage();
    }

  //  @Test(priority = 1)
    public static void checkNumberOfRecordsOnPage() {

        //Select 25 records on a page
        validateRecordsOnPage(25, 2, "None");
        //Select 50 records on a page
        //validateRecordsOnPage(50,2,"None");
        //Select 100 records on a page
        // validateRecordsOnPage(100,2,"None");
        //Select 10 records on a page
        validateRecordsOnPage(10, 2, "None");

    }
    @Test(priority = 1)
    public static void checkPagination(){
        clickOnEachPage("//ul[@class='pagination']/li", 10, 2, "None");

    }

//    @Test(priority = 2)
    public static void checkSearchByKeyword() {

        //Check cases for search
        String searchKeyword = "an";
        System.out.println("Searching keywords: " + searchKeyword);
        //Select 10 records on a page
        searchClient(searchKeyword);
        validateRecordsOnPage(10, 2, "None");
        //validateRecordsOnPage(50);
        //Select 50 records on a page
        //validateRecordsOnPage(50);
        //Select 100 records on a page
        //validateRecordsOnPage(100);
        //Select 10 records on a page
        //validateRecordsOnPage(10);
    }

   // @Test(priority = 3)
    public static void checkFilterByColumn() {
        //Filter function
        //Click on Show Filter Button
        Actions actions = new Actions(driver);
        WebElement showFilterbutton = findEleByXPath("//button[@class='btn btn-default show-filter-form-button']");
        actions.moveToElement(showFilterbutton);
        actions.release().perform();
        clickElement("//button[@class='btn btn-default show-filter-form-button']");
        WebUI.sleep(2);

        //Filter by Client Group
        // Column =4 => filter by Client Groups (Gold, VIP, Siliver)
        String xPathClientGroup = "//div[@class='filter-form']/div[3]";
        String xPathClientGroupSearch = "//div[@id='select2-drop']//input";
        WebUI.sleep(2);
        filterValue(xPathClientGroup, xPathClientGroupSearch, 10, 4, "VIP");
        WebUI.sleep(2);
        filterValue(xPathClientGroup, xPathClientGroupSearch, 10, 4, "Gold");
        WebUI.sleep(2);
        filterValue(xPathClientGroup, xPathClientGroupSearch, 50, 4, "Silver");
        //Invalid keyword
        WebUI.sleep(2);
        filterValue(xPathClientGroup, xPathClientGroupSearch, 10, 4, "TEST");

        //Filter by Labels
        //Column =5 => filter by Labels(Inactive, Referal, Unsastified, Sastified, Coporate, Potential, 90% Probability, 50% Probability, Call this week)
        String xPathLabels = "//span[@id='select2-chosen-10']";
        String xPathLabelsSearch = "//div[@id='select2-drop']//input";
        WebUI.sleep(2);
        filterValue(xPathLabels, xPathLabelsSearch, 10, 5, "Inactive");
        //Invalid keyword
        WebUI.sleep(2);
        filterValue(xPathLabels, xPathLabelsSearch, 10, 5, "ABC");

        WebUI.sleep(2);
        filterValue(xPathLabels, xPathLabelsSearch, 25, 5, "90% Probability");

    }

    public static void validateRecordsOnPage(int numberOption, int col, String filter) {
        System.out.println("***** Start running ValidateRecordsOnPage method ******");
        WebUI.explicitWait(driver, "//div[@id='client-table_length']//span[@id='select2-chosen-2']", 10);
        //Select number of records displayed on a page
        checkElementDisplay("//div[@id='client-table_length']//span[@id='select2-chosen-2']", "The element of Number of records is not displayed.");
        WebElement selectOptionEle = findEleByXPath("//div[@id='client-table_length']//span[@id='select2-chosen-2']");
        int defaultNumberOfPage = Integer.parseInt(selectOptionEle.getText());
        // Create action class
        Actions action = new Actions(driver);
        action.moveToElement(selectOptionEle).build().perform();
        WebUI.sleep(2);
        if (defaultNumberOfPage != numberOption) {
            selectOptionEle.click();
            //Click and select option
            String strEleSelectOption = "//div[@role='option' and normalize-space()='" + numberOption + "']";
            clickElement(strEleSelectOption);
            System.out.println("\t - Check selecting a number record displayed on a page: Successfully selected option.");
        }
        //Work on: 1-10 / 51
        checkElementDisplay("//div[@id='client-table_info']", "Total record is not displayed.");
        WebUI.sleep(2);
        String recordInfo = findEleByXPath("//div[@id='client-table_info']").getText();
        String[] parts = recordInfo.split("/");
        String[] part1 = parts[0].split("-");
        int currentViewFrom = trimTextConvertToInt(part1[0]);
        int currentViewTo = trimTextConvertToInt(part1[1]);
        int totalRecord = trimTextConvertToInt(parts[1]);
        System.out.println("\t - The displaying record From: " + currentViewFrom + " To " + currentViewTo);
        System.out.println("\t - The Total records on the right: " + totalRecord);
        //Check if records displayed correctly
        WebUI.sleep(2);
        int actualDisplay = displayClientLits(driver, "//table[@id='client-table']//tr", col, filter);
        //System.out.println("\t - Number of actualDisplay: " + actualDisplay);
        if (actualDisplay > 0) {
            if (actualDisplay <= numberOption & actualDisplay == currentViewTo) {
                String currentPage = findEleByXPath("(//div[@id='client-table_paginate']//a)[2]").getText();
                Assert.assertEquals(currentPage, "1", "Page number should be '1', please double check.");
                System.out.println("\t - The number of records in the list displayed correctly.");
            } else if (actualDisplay > numberOption || actualDisplay != currentViewTo) {
                System.out.println("\t - The number of records in the list or View To number displayed INCORRECTLY.");
            }

            //Check the first page is selected by default
            WebUI.sleep(2);
            checkElementDisplay("//li[@class='paginate_button page-item active']", "The first page is not displayed and selected.");
            //The number of pages in total
            int totalPage = roundUp(totalRecord, numberOption);
            if (totalPage > 0) {
                System.out.println("\t - Total pages of Client is: " + totalPage);
            } else {
                System.out.println("\t - The is no data.");
            }
        } else System.out.println("\t - There is no record found.");
        System.out.println("***** End running ValidateRecordsOnPage method ******");
    }

    public static void filterValue(String xPath, String xPathtxtSearch, int numberRecordPerPage, int col, String keyword) {
        Actions actions = new Actions(driver);
        WebUI.explicitWait(driver, xPath, 10);
        clickElement(xPath);
        WebUI.explicitWait(driver, xPathtxtSearch, 10);
        checkElementDisplay(xPathtxtSearch, "Input element does not existed.");
        findEleByXPath(xPathtxtSearch).clear();
        findEleByXPath(xPathtxtSearch).sendKeys(keyword, Keys.ENTER);
        WebUI.sleep(3);
        String columnFilter = "Name";
        //Check if no record matches found
        String resultEleText = "";

        switch (col) {
            case 2:
                columnFilter = "Name";
                break;
            case 4:
                columnFilter = "Client Groups";
                WebUI.sleep(2);
                resultEleText = findEleByXPath("//ul[@id='select2-results-8']").getText();
                break;
            case 5:
                columnFilter = "Labels";
                resultEleText = findEleByXPath("//ul[@id='select2-results-10']").getText();
                break;
        }
        System.out.println("*** Running Filter Value by " + columnFilter + ", keyword: " + keyword);
        System.out.println("\t - No matches found element is displayed:  " + resultEleText);
        if (resultEleText == "") {
            WebUI.sleep(2);
            validateRecordsOnPage(numberRecordPerPage, col, keyword);

        } else {
            System.out.println("\t - The filter keyword does not existed, please enter another keyword.");
            actions.sendKeys(Keys.ESCAPE).build().perform();
        }


    }

    public static void clickOnEachPage(String xPath, int numberOption, int col, String filter) {
        WebUI.explicitWait(driver,xPath,10);
        Boolean CheckElementExisted = existsElement(xPath);
        if (CheckElementExisted) {
            List<WebElement> pageElement = driver.findElements(By.xpath(xPath));
            int pageElementNumber = pageElement.size();
            int j=1;
            for (int i = 2; i < pageElementNumber; i++) {
                if (i!=2)
                    clickElement("(//ul[@class='pagination']/li)[" + i + "]");
                System.out.println(">>> Displaying Page: " + j);
                j=j+1;
                validateRecordsOnPage(numberOption, col, filter);

            }
        }else System.out.println("There is no page element displayed.");
    }
}
