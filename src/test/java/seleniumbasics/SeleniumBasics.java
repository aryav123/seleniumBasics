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

public class SeleniumBasics {
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
}

