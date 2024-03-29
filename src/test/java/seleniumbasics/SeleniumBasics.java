package seleniumbasics;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.List;

public class
SeleniumBasics {
    WebDriver driver;

    public void testInitialise(String browser) {
        if (browser.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            driver = new EdgeDriver();
        } else {
            try {
                throw new Exception("Invalid browser");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("This is before suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("This is before test");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("This is before class");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("This is after suite");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("This is after test");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("This is after class");
    }

    @BeforeMethod
    public void setUp() {
        testInitialise("chrome");
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("./Screenshots/" + result.getName() + ".png"));
        }
//        driver.close();
        driver.quit();
    }

    @Test
    public void TC_001_verifyObsquraTitle() {
        driver.get("https://selenium.obsqurazone.com/index.php");
        String actualTitle = driver.getTitle();                 /** Get title**/
        String expectedTitle = "Obsqura Testing";
        Assert.assertEquals(actualTitle, expectedTitle, "Invalid Title found");
    }

    @Test
    public void TC_002_verifySingleInputFieldMessage() {
        driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
        WebElement messageField = driver.findElement(By.id("single-input-field"));
        WebElement showButton = driver.findElement(By.id("button-one"));
        WebElement singleInputMessage = driver.findElement(By.id("message-one"));
        messageField.sendKeys("Test");
        showButton.click();
        String actualMessage = singleInputMessage.getText();
        String expectedMessage = "Your Message : Test";
        Assert.assertEquals(actualMessage, expectedMessage, "Invalid Message found");
    }

    @Test
    public void TC_003_verifyTwoInputFieldMessage() {
        driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
        WebElement inputFieldA = driver.findElement(By.id("value-a"));
        WebElement inputFieldB = driver.findElement(By.id("value-b"));
        WebElement buttonGetTotal = driver.findElement(By.id("button-two"));
        WebElement messageFieldTotal = driver.findElement(By.id("message-two"));
        inputFieldA.sendKeys("2");
        inputFieldB.sendKeys("4");
        buttonGetTotal.click();
        String actualMessage = messageFieldTotal.getText();
        String expectedMessage = "Total A + B : 6";
        Assert.assertEquals(actualMessage, expectedMessage, "Invalid Message found");
    }

    @Test
    public void TC_004_verifyEmptyFieldvalidation() {
        driver.get("https://selenium.obsqurazone.com/form-submit.php");
        WebElement submitButton = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
        WebElement firstnameFieldValidation = driver.findElement(By.xpath("//input[@id='validationCustom01']//following-sibling::div[1]"));
        String expectedFirstNameErrorMessage = "Please enter First name.";
        WebElement lastnameFieldValidation = driver.findElement(By.xpath("//input[@id='validationCustom02']//following-sibling::div[1]"));
        String expectedLastNameErrorMessage = "Please enter Last name.";
        WebElement userNameValidation = driver.findElement(By.xpath("//input[@id='validationCustomUsername']//following-sibling::div[1]"));
        String expectedUserNameErrorMessage = "Please choose a username.";
        WebElement cityFieldValidation = driver.findElement(By.xpath("//input[@id='validationCustom03']//following-sibling::div[1]"));
        String expectedCityFieldErrorMessage = "Please provide a valid city.";
        WebElement stateFieldValidation = driver.findElement(By.xpath("//input[@id='validationCustom04']//following-sibling::div[1]"));
        String expectedStateFieldErrorMessage = "Please provide a valid state.";
        WebElement zipFieldValidation = driver.findElement(By.xpath("//input[@id='validationCustom05']//following-sibling::div[1]"));
        String expectedZipFieldErrorMessage = "Please provide a valid zip.";
        WebElement termsConditionFieldValidation = driver.findElement(By.xpath("//input[@id='invalidCheck']//following-sibling::div[1]"));
        String expectedTermsConditionFieldErrorMessage = "You must agree before submitting.";
        submitButton.click();
        Assert.assertEquals(firstnameFieldValidation.getText(), expectedFirstNameErrorMessage, "Invalid Message found");
        Assert.assertEquals(lastnameFieldValidation.getText(), expectedLastNameErrorMessage, "Invalid Message found");
        Assert.assertEquals(userNameValidation.getText(), expectedUserNameErrorMessage, "Invalid Message found");
        Assert.assertEquals(cityFieldValidation.getText(), expectedCityFieldErrorMessage, "Invalid Message found");
        Assert.assertEquals(stateFieldValidation.getText(), expectedStateFieldErrorMessage, "Invalid Message found");
        Assert.assertEquals(zipFieldValidation.getText(), expectedZipFieldErrorMessage, "Invalid Message found");
        Assert.assertEquals(termsConditionFieldValidation.getText(), expectedTermsConditionFieldErrorMessage, "Invalid Message found");
    }

    @Test
    public void TC_005_verifyEmptyStateZipCode() {
        driver.get("https://selenium.obsqurazone.com/form-submit.php");
        WebElement firstname = driver.findElement(By.xpath("//input[@id='validationCustom01']"));
        WebElement lastName = driver.findElement(By.xpath("//input[@id='validationCustom02']"));
        WebElement userName = driver.findElement(By.xpath("//input[@id='validationCustomUsername']"));
        WebElement zipField = driver.findElement(By.xpath("//input[@id='validationCustom05']"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
        WebElement cityFieldValidation = driver.findElement(By.xpath("//input[@id='validationCustom03']//following-sibling::div[1]"));
        String expectedCityFieldErrorMessage = "Please provide a valid city.";
        WebElement stateFieldValidation = driver.findElement(By.xpath("//input[@id='validationCustom04']//following-sibling::div[1]"));
        String expectedStateFieldErrorMessage = "Please provide a valid state.";
        WebElement termsConditionFieldValidation = driver.findElement(By.xpath("//input[@id='invalidCheck']//following-sibling::div[1]"));
        String expectedTermsConditionFieldErrorMessage = "You must agree before submitting.";
        firstname.sendKeys("arya");
        lastName.sendKeys("v");
        userName.sendKeys("aryav123");
        zipField.sendKeys("123456");
        submitButton.click();
        Assert.assertEquals(cityFieldValidation.getText(), expectedCityFieldErrorMessage, "Invalid Message found");
        Assert.assertEquals(stateFieldValidation.getText(), expectedStateFieldErrorMessage, "Invalid Message found");
        Assert.assertEquals(termsConditionFieldValidation.getText(), expectedTermsConditionFieldErrorMessage, "Invalid Message found");
    }

    @Test
    public void TC_006_verifySucessfullFormSubmission() {
        driver.get("https://selenium.obsqurazone.com/form-submit.php");
        WebElement firstname = driver.findElement(By.xpath("//input[@id='validationCustom01']"));
        WebElement lastName = driver.findElement(By.xpath("//input[@id='validationCustom02']"));
        WebElement userName = driver.findElement(By.xpath("//input[@id='validationCustomUsername']"));
        WebElement cityField = driver.findElement(By.xpath("//input[@id='validationCustom03']"));
        WebElement stateField = driver.findElement(By.xpath("//input[@id='validationCustom04']"));
        WebElement zipField = driver.findElement(By.xpath("//input[@id='validationCustom05']"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
        WebElement successValidationMessage = driver.findElement((By.xpath("//button[@class='btn btn-primary']/following-sibling::div")));
        String expectedSuccessMessage = "Form has been submitted successfully!";
        WebElement termsCondition = driver.findElement(By.xpath("//input[@id='invalidCheck']"));
        firstname.sendKeys("arya");
        lastName.sendKeys("v");
        userName.sendKeys("aryav123");
        cityField.sendKeys("Kochi");
        stateField.sendKeys("Ernakulam");
        zipField.sendKeys("123456");
        termsCondition.click();
        submitButton.click();
        Assert.assertEquals(successValidationMessage.getText(), expectedSuccessMessage, "Invalid Message found");
    }

    @Test
    public void TC_007_verifyNewsletterSubscription() {
        driver.get("https://demowebshop.tricentis.com");
        WebElement emailField = driver.findElement(By.cssSelector("#newsletter-email"));
        WebElement subscribeButton = driver.findElement(By.cssSelector("#newsletter-subscribe-button"));
        emailField.sendKeys("test@gmail.com");
        subscribeButton.click();
    }

    @Test
    public void TC_008_verifyInstantDemoRequestForm() {
        driver.get("https://phptravels.com/demo/");
        WebElement inputFieldFirstName = driver.findElement(By.cssSelector("input.first_name"));
        WebElement inputFieldLastName = driver.findElement(By.cssSelector("input.last_name"));
        WebElement inputFieldBusinessName = driver.findElement(By.cssSelector("input.business_name"));
        WebElement inputFieldEmail = driver.findElement(By.cssSelector("input.email"));
        WebElement buttonSubmit = driver.findElement(By.cssSelector("button#demo"));
        WebElement number1 = driver.findElement(By.cssSelector("span#numb1"));
        WebElement number2 = driver.findElement(By.cssSelector("span#numb2"));
        WebElement resultField = driver.findElement(By.cssSelector("input#number"));
        WebElement completedBox = driver.findElement(By.cssSelector("div.completed"));
        inputFieldFirstName.sendKeys("arya");
        inputFieldLastName.sendKeys("v");
        inputFieldBusinessName.sendKeys("aryav123");
        inputFieldEmail.sendKeys("test@gmail.com");
        String numberFieldValue1 = number1.getText();
        String numberFieldValue2 = number2.getText();
        int num1 = Integer.parseInt(numberFieldValue1);
        int num2 = Integer.parseInt(numberFieldValue2);
        int sum = num1 + num2;
        String sumText = String.valueOf(sum);
        resultField.sendKeys(sumText);
        buttonSubmit.click();
        completedBox.isDisplayed();
    }

    @Test
    public void TC_008_verifyQuitAndClose() {
        driver.get("https://demo.guru99.com/popup.php");
        WebElement clickHereButton = driver.findElement(By.xpath("//a[text()='Click Here']"));
        clickHereButton.click();

    }

    @Test
    public void TC_009_verifyNavigateTo() {
//        driver.get("https://demowebshop.tricentis.com");
        driver.navigate().to("https://demowebshop.tricentis.com");

    }

    @Test
    public void TC_010_verifyRefresh() {
        driver.get("https://demowebshop.tricentis.com");
        WebElement newsLetterEmailField = driver.findElement(By.xpath("//input[@id='newsletter-email']"));
        newsLetterEmailField.sendKeys("test@gmail.com");
        driver.navigate().refresh();

    }

    @Test
    public void TC_011_verifyForwardAndBackward() throws InterruptedException {
        driver.get("https://demowebshop.tricentis.com");
        WebElement loginMenu = driver.findElement(By.xpath("//a[text()='Log in']"));
        loginMenu.click();
        driver.navigate().back();
        Thread.sleep(2000);
        driver.navigate().forward();
    }

    @Test
    public void TC_012_verifyWebElementsCommand() throws InterruptedException {
        driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
        WebElement subjectField = driver.findElement(By.xpath("//input[@id='subject']"));
        subjectField.sendKeys("Selenium");
        WebElement descriptionField = driver.findElement(By.xpath("//textarea[@id='description']"));
        descriptionField.sendKeys("Automation Testing");
        WebElement submitButton = driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
        subjectField.clear();
        String classAttributeValue = subjectField.getAttribute("class");
        System.out.println("test" + classAttributeValue);
        String TagNameValue = subjectField.getTagName();
        System.out.println("test" + TagNameValue);
        subjectField.sendKeys("Selenium Testing");
        submitButton.click();
        Thread.sleep(10000);
        WebElement successValidationMessage = driver.findElement(By.id("message-one"));
        String expectedValidationMessage = "Form has been submitted successfully!";
        Assert.assertEquals(successValidationMessage.getText(), expectedValidationMessage, "Invalid Message");
    }

    @Test
    public void TC_013_verifyIsDisplayed() throws InterruptedException {
        driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
        WebElement subjectField = driver.findElement(By.xpath("//input[@id='subject']"));
        subjectField.sendKeys("Selenium");
        boolean status = subjectField.isDisplayed();
        System.out.println(status);
        Assert.assertTrue(status, "Subject field is not displayed");
    }

    @Test
    public void TC_014_verifyIsSelected() throws InterruptedException {
        driver.get("https://selenium.obsqurazone.com/check-box-demo.php");
        WebElement singleDemoCheckBox = driver.findElement(By.xpath("//input[@id='gridCheck']"));
        boolean statusBeforeClick = singleDemoCheckBox.isSelected();
        System.out.println(statusBeforeClick);
        Assert.assertFalse(statusBeforeClick, "Checkbox is selected");
        singleDemoCheckBox.click();
        boolean status = singleDemoCheckBox.isSelected();
        System.out.println(status);
        Assert.assertTrue(status, "Checkbox is not selected");
    }

    @Test
    public void TC_015_verifyIsEnabled() throws InterruptedException {
        driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
        WebElement submitButton = driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
        boolean status = submitButton.isEnabled();
        System.out.println(status);
        Assert.assertTrue(status, "submit button not enabled");
        Point point = submitButton.getLocation();
        System.out.println(point.x + "," + point.y);
        Dimension dim = submitButton.getSize();
        System.out.println(dim.height + "," + dim.width);
        String backgroundColor = submitButton.getCssValue("background-color");
        System.out.println(backgroundColor);
        WebElement element = driver.findElement(By.tagName("input"));
        System.out.println(element);
        List<WebElement> elements = driver.findElements(By.tagName("input"));
        System.out.println(elements);
        submitButton.submit();
    }

    @Test
    public void TC_017_VerifyTheMessageDisplayedInNewTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        WebElement newTabButton = driver.findElement(By.id("tabButton"));
        boolean status = newTabButton.isEnabled();
        Assert.assertTrue(status, "submit button not enabled");
        newTabButton.click();
        driver.navigate().to("https://demoqa.com/sample");
        WebElement sampleHeading = driver.findElement(By.id("sampleHeading"));
        String actualMessage = sampleHeading.getText();
        String expectedText = "This is a sample page";
        Assert.assertEquals(actualMessage, expectedText, "Invalid message");
    }

    @Test
    public void TC_018_VerifyTheMessageDisplayedInNewTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        WebElement newTabButton = driver.findElement(By.id("tabButton"));
        boolean status = newTabButton.isEnabled();
        Assert.assertTrue(status, "submit button not enabled");
        newTabButton.click();
        driver.navigate().to("https://demoqa.com/sample");
        WebElement sampleHeading = driver.findElement(By.id("sampleHeading"));
        String actualMessage = sampleHeading.getText();
        String expectedText = "This is a sample page";
        Assert.assertEquals(actualMessage, expectedText, "Invalid message");
    }

    @Test
    public void TC_019_VerifyTheMessageDisplayedInNewWindow() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        String parentWindow = driver.getWindowHandle();
        System.out.println("parent window id =" + parentWindow);
        WebElement newWindowClickButton = driver.findElement(By.id("windowButton"));
        newWindowClickButton.click();
        Set<String> handles = driver.getWindowHandles();
        System.out.println(handles);
        Iterator<String> handleIds = handles.iterator();
        while (handleIds.hasNext()) {
            String childWindow = handleIds.next();
            if (!childWindow.equals(parentWindow)) {
                driver.switchTo().window(childWindow);
                WebElement simpleHeading = driver.findElement(By.id("sampleHeading"));
                String actualText = simpleHeading.getText();
                String expectedText = "This is a sample page";
                Assert.assertEquals(actualText, expectedText, "Message not found");
                driver.close();

            }
        }
        driver.switchTo().window(parentWindow);
    }

    @Test
    public void TC_20VerifyTheTextDisplayedInNewWindow() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        String parentWindow = driver.getWindowHandle();
        System.out.println("parent window id =" + parentWindow);
        WebElement newWindowMessageButton = driver.findElement(By.id("messageWindowButton"));
        newWindowMessageButton.click();
        Set<String> handles = driver.getWindowHandles();
        System.out.println(handles);
        Iterator<String> handleIds = handles.iterator();
        while (handleIds.hasNext()) {
            String childWindow = handleIds.next();
            if (!childWindow.equals(parentWindow)) {
                driver.switchTo().window(childWindow);
//                WebElement simpleHeading = driver.findElement(By.id("sampleHeading"));
                String actualText = driver.getPageSource();
                String expectedText = "Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization.";
                Assert.assertEquals(actualText, expectedText, "Message not found");
                driver.close();

            }
        }
        driver.switchTo().window(parentWindow);
    }

    @Test
    public void TC_021_VerifySimpleAlert() throws InterruptedException {
        driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
        WebElement clickMe = driver.findElement(By.xpath("//button[@class='btn btn-success']"));
        clickMe.click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println(alertText);
        alert.accept();
    }

    @Test
    public void TC_022_VerifyConfirmAlert() throws InterruptedException {
        driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
        WebElement clickMe = driver.findElement(By.xpath("//button[@class='btn btn-warning']"));
        clickMe.click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println(alertText);
        alert.dismiss();
    }

    @Test
    public void TC_023_VerifyPromptAlert() throws InterruptedException {
        driver.get("https://selenium.obsqurazone.com/javascript-alert.php");
        WebElement clickMe = driver.findElement(By.xpath("//button[@class='btn btn-danger']"));
        clickMe.click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println(alertText);
        alert.sendKeys("Text message");
        alert.accept();
    }

    @Test
    public void TC_024_VerifyTextInAFrame() throws InterruptedException {
        driver.get("https://demoqa.com/frames");
        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        int numberofFrames = frames.size();
        System.out.println(numberofFrames);
//        driver.switchTo().frame(3);
//        driver.switchTo().frame("frame1");
        WebElement frame = driver.findElement(By.id("frame1"));
        driver.switchTo().frame(frame);
        WebElement heading = driver.findElement(By.id("sampleHeading"));
        String headingText = heading.getText();
        System.out.println(headingText);
//        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();
    }

    @Test
    public void TC_025_DifferenceBtwFindElementAndFineElements() {
        driver.get("https://selenium.obsqurazone.com/radio-button-demo.php");
        List<WebElement> genders = driver.findElements(By.xpath("//input[@name='student-gender']"));
        System.out.println(genders);
        for (int i = 0; i < genders.size(); i++) {
            String gender = genders.get(i).getAttribute("value"); // Male will be saved
            if (gender.equals("Male")) {
                genders.get(i).click();
            }
        }
    }

    @Test
    public void TC_026_VerifyRightClick() {
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");
        WebElement rightClickButton = driver.findElement(By.xpath("//span[@class='context-menu-one btn btn-neutral']"));
        Actions action = new Actions(driver);
        action.contextClick(rightClickButton).build().perform();
//        action.contextClick().build().perform();
    }

    @Test
    public void TC_027_VerifyDoubleClick() {
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");
        WebElement rightClickButton = driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
        Actions action = new Actions(driver);
        action.doubleClick(rightClickButton).build().perform();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Test
    public void TC_028_VerifyMouseOver() {
        driver.get("https://demoqa.com/menu/");
        WebElement mainItem1 = driver.findElement(By.xpath("//a[text()='Main Item 1']"));
        Actions action = new Actions(driver);
        action.moveToElement(mainItem1).build().perform();
//        action.moveToElement(mainItem1,50,50).build().perform();
//        action.moveByOffset(40,80).build().perform();
    }

    @Test
    public void TC_028_VerifyDragAndDrop() {
        driver.get("https://demoqa.com/droppable");
        WebElement dragMe = driver.findElement(By.id("draggable"));
        WebElement dropHere = driver.findElement(By.id("droppable"));
        Actions action = new Actions(driver);
        action.dragAndDrop(dragMe, dropHere).build().perform();
//        action.moveToElement(mainItem1,50,50).build().perform();
//        action.moveByOffset(40,80).build().perform();
    }

    @Test
    public void TC_029_VerifyDragAndDropByOffset() {
        driver.get("https://demoqa.com/dragabble");
        WebElement dragBox = driver.findElement(By.xpath("//div[@id='dragBox']"));
        Actions action = new Actions(driver);
        action.dragAndDropBy(dragBox, 100, 100).build().perform();
    }

    @Test
    public void TC_030_VerifyClickAndHoldResize() {
        driver.get("https://demoqa.com/resizable");
        WebElement dragBox = driver.findElement(By.xpath("//div[@id='resizableBoxWithRestriction']/child::span"));
        Actions action = new Actions(driver);
        action.clickAndHold(dragBox).build().perform();
        action.dragAndDropBy(dragBox, 100, 100).build().perform();
    }

    @Test
    public void TC_031_VerifyDragAndDropMultipleBoxes() {
        driver.get("https://selenium.obsqurazone.com/drag-drop.php");
        WebElement dragBox1 = driver.findElement(By.xpath("//div[@id='todrag']/child::span[text()='Draggable n°1']"));
        WebElement dragBox2 = driver.findElement(By.xpath("//div[@id='todrag']/child::span[text()='Draggable n°2']"));
        WebElement dragBox3 = driver.findElement(By.xpath("//div[@id='todrag']/child::span[text()='Draggable n°3']"));
        WebElement dragBox4 = driver.findElement(By.xpath("//div[@id='todrag']/child::span[text()='Draggable n°4']"));
        WebElement dropHere = driver.findElement(By.xpath("//div[@id='mydropzone']"));
        Actions action = new Actions(driver);
        action.dragAndDrop(dragBox1, dropHere).build().perform();
        action.dragAndDrop(dragBox2, dropHere).build().perform();
        action.dragAndDrop(dragBox3, dropHere).build().perform();
        action.dragAndDrop(dragBox4, dropHere).build().perform();

    }

    @Test
    public void TC_032_VerifyValuesInDropDown() {
        driver.get("https://demo.guru99.com/test/newtours/register.php");
        WebElement countryDropdown = driver.findElement(By.xpath("//select[@name='country']"));
        List<String> expDropDownList = new ArrayList<>();
        expDropDownList.add("ALBANIA");
        expDropDownList.add("ALGERIA");
        expDropDownList.add("AMERICAN SAMOA");
        expDropDownList.add("ANDORRA");
        List<String> actDropDownList = new ArrayList<>();
        Select select = new Select(countryDropdown);
        List<WebElement> dropdownOptions = select.getOptions();
        for (int i = 0; i < 4; i++) {
            actDropDownList.add(dropdownOptions.get(i).getText());
        }
        System.out.println(actDropDownList);
        Assert.assertEquals(actDropDownList, expDropDownList, "Dropdown option not found");
//        select.selectByVisibleText("INDIA");
//        select.selectByIndex(23);
        select.selectByValue("CHILE");
    }

    @Test
    public void TC_033_VerifyMethodsInSelectClass() {
        driver.get("https://www.softwaretestingmaterial.com/sample-webpage-to-automate/");
        WebElement multiSelectDrpdown = driver.findElement(By.xpath("//select[@name='multipleselect[]']"));
        Select select = new Select(multiSelectDrpdown);
        boolean status = select.isMultiple();
        System.out.println(status);
        select.selectByVisibleText("Performance Testing");
        select.selectByVisibleText("Manual Testing");
        List<WebElement> selectedOptions = select.getAllSelectedOptions();
        for (int i = 0; i < selectedOptions.size(); i++) {
            System.out.println(selectedOptions.get(i).getText());
        }
        select.deselectAll();
    }

    @Test
    public void TC_034_VerifyFileUploadInSelenium() {
        driver.get("https://demo.guru99.com/test/upload/");
        WebElement chooseFileMenu = driver.findElement(By.xpath("//input[@id='uploadfile_0']"));
        chooseFileMenu.sendKeys("D:\\Selenium\\test.txt");
        WebElement acceptRadioButton = driver.findElement(By.xpath("//input[@id='terms']"));
        acceptRadioButton.click();
        WebElement submitFileButton = driver.findElement(By.xpath("//button[@id='submitbutton']"));
        submitFileButton.click();
    }

    @Test
    public void TC_035_VerifyClickAndSendKeysUsingJavaScriptExecutioner() {
        driver.get("https://demowebshop.tricentis.com/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('newsletter-email').value='test@test.com'");
        js.executeScript("document.getElementById('newsletter-subscribe-button').click()");

    }

    @Test
    public void TC_036_verifyWaitInSelenium() {
        driver.get("https://demowebshop.tricentis.com/");
        /* Page load wait */
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        /* Implicit wait */
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement emailField = driver.findElement(By.xpath("//input[@id='newsletter-email']"));
        emailField.sendKeys("test@gmail.com");
        WebElement subscribeButton = driver.findElement(By.xpath("//input[@id='newsletter-subscribe-button']"));
        /*Explicit wait*/
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(subscribeButton));
        /*Fluent wait*/
        FluentWait fwait = new FluentWait<>(driver);
        fwait.withTimeout(Duration.ofSeconds(10));
        fwait.pollingEvery(Duration.ofSeconds(1));
        fwait.until(ExpectedConditions.visibilityOf(subscribeButton));
        subscribeButton.click();

    }

    @Test
    public void TC_037_verifyScrollDownOfAWebPage() {
        driver.get("https://demo.guru99.com/test/guru99home/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");

    }

    @Test
    public void TC_037_verifyScrollIntoViewOfAWebElement() {
        driver.get("https://demo.guru99.com/test/guru99home/");
        WebElement linuxElement = driver.findElement(By.linkText("Linux"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", linuxElement);

    }

    @Test
    public void TC_038_verifyScrollToTheBottomOfAPage() {
        driver.get("https://demo.guru99.com/test/guru99home/");
        WebElement linuxElement = driver.findElement(By.linkText("Linux"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    @Test
    public void TC_039_verifyHorizontalScroll() {
        driver.get("http://demo.guru99.com/test/guru99home/scrolling.html");
        WebElement vbScriptElement = driver.findElement(By.linkText("VBScript"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", vbScriptElement);
    }

    @Test
    public void TC_040_verifyTable() throws IOException {
        driver.get("https://www.w3schools.com/html/html_tables.asp");
        List<WebElement> rowElements = driver.findElements(By.xpath("//table[@id='customers']//tbody//tr"));
        List<WebElement> columnElements = driver.findElements(By.xpath("//table[@id='customers']//tbody//tr//td"));
        List<ArrayList<String>> actGridData = TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowElements, columnElements);
        List<ArrayList<String>> expGridData = ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx", "Table");
        Assert.assertEquals(actGridData, expGridData, "Invalid data found in table");
    }

    @Test
    public void TC_041_verifyTableContantAndPrintTheValue() throws IOException {
        driver.get("https://www.w3schools.com/html/html_tables.asp");
        List<WebElement> rowElements = driver.findElements(By.xpath("//table[@id='customers']//tbody//tr"));
        List<WebElement> columnElements = driver.findElements(By.xpath("//table[@id='customers']//tbody//tr//td"));
        List<ArrayList<String>> actGridData = TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowElements, columnElements);
        List<ArrayList<String>> expGridData = ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx", "Table");
        Assert.assertEquals(actGridData, expGridData, "Invalid data found in table");
//        ArrayList<String>(Arrays.asList(columnList));
//        TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowElements,columnElements).contains("Island Trading");
//        System.out.println(TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowElements,columnElements));

        if (TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowElements, columnElements).contains("Island Trading")) ;
        {
            List<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
            int x = 0;                                               //Controlling the cell values
            for (int i = 0; i < 2; i++) {                        // Iterating each row
                for (int j = 0; j < 2; j++) {        // Iterating each array
                    rowList = TableUtility.get_Dynamic_TwoDimension_TablElemnts(rowElements, columnElements);
                    x++;
                }
            }
        }
    }

    @Test
    public void TC_042_verifyFileUploadUsingRobotClass() throws AWTException, InterruptedException {
        driver.get("https://www.foundit.in/seeker/registration");
        StringSelection s = new StringSelection("D:\\Selenium\\test.txt");               // Copy to clipboard //
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
        WebElement chooseFile = driver.findElement(By.xpath("//span[text()='Choose CV']"));
        chooseFile.click();
        Thread.sleep(2000);
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(2000);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
        Thread.sleep(2000);
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);

    }


}