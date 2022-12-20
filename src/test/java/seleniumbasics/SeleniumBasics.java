package seleniumbasics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    @BeforeMethod
    public void setUp() {
        testInitialise("chrome");
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
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
        WebElement successValidationMessage=driver.findElement((By.xpath("//button[@class='btn btn-primary']/following-sibling::div")));
        String expectedSuccessMessage = "Form has been submitted successfully!";
        WebElement termsCondition= driver.findElement(By.xpath("//input[@id='invalidCheck']"));
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
}

