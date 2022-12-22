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
        WebElement newsLetterEmailField= driver.findElement(By.xpath("//input[@id='newsletter-email']"));
        newsLetterEmailField.sendKeys("test@gmail.com");
        driver.navigate().refresh();

    }
    @Test
    public void TC_011_verifyForwardAndBackward() throws InterruptedException {
        driver.get("https://demowebshop.tricentis.com");
        WebElement loginMenu= driver.findElement(By.xpath("//a[text()='Log in']"));
        loginMenu.click();
        driver.navigate().back();
        Thread.sleep(2000);
        driver.navigate().forward();
    }
    @Test
    public void TC_012_verifyWebElementsCommand() throws InterruptedException {
        driver.get("https://selenium.obsqurazone.com/ajax-form-submit.php");
        WebElement subjectField= driver.findElement(By.xpath("//input[@id='subject']"));
        subjectField.sendKeys("Selenium");
        WebElement descriptionField= driver.findElement(By.xpath("//textarea[@id='description']"));
        descriptionField.sendKeys("Automation Testing");
        WebElement submitButton= driver.findElement(By.xpath("//input[@class='btn btn-primary']"));
        submitButton.click();
        Thread.sleep(10000);
        WebElement successValidationMessage= driver.findElement(By.id("message-one"));
        String expectedValidationMessage="Form has been submitted successfully!";
        Assert.assertEquals(successValidationMessage.getText(),expectedValidationMessage,"Invalid Message");
    }
}

