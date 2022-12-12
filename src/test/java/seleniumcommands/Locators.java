package seleniumcommands;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Locators {
    public static void main(String[] args) {
        WebDriver driver;
        driver = new ChromeDriver();                    /** Creating webdriver instance**/
        driver.manage().window().maximize();            /** Maximize window**/
        driver.manage().deleteAllCookies();             /** Delete all cookies**/
        driver.get("https://selenium.obsqurazone.com/simple-form-demo.php");
        // Element declaration
        WebElement messageField = driver.findElement(By.id("single-input-field"));
        WebElement showButton = driver.findElement(By.id("button-one"));
        WebElement message = driver.findElement(By.id("message-one"));
        WebElement inputFieldA = driver.findElement(By.id("value-a"));
        WebElement inputFieldB = driver.findElement(By.id("value-b"));
        WebElement buttonGetTotal = driver.findElement(By.id("button-two"));
        WebElement messageFieldTotal = driver.findElement(By.id("message-two"));
        // Element action
        messageField.sendKeys("Test");
        showButton.click();
        String myMessage = message.getText();
        System.out.println(myMessage);
        inputFieldA.sendKeys("2");
        inputFieldB.sendKeys("4");
        buttonGetTotal.click();
        String messageTotal = messageFieldTotal.getText();
        System.out.println(messageTotal);
        driver.close();
    }
}
