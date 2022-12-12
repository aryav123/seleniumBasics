package seleniumcommands;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoWebShop {
    public static void main(String[] args) {
        WebDriver driver;
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://demowebshop.tricentis.com/");
        WebElement loginLink=driver.findElement(By.className("ico-login"));
        loginLink.click();
        WebElement emailInput=driver.findElement(By.name("Email"));
        emailInput.sendKeys("arya74331@gmail.com");
        WebElement passWordInput= driver.findElement(By.name("Password"));
        passWordInput.sendKeys("Test@123");
        WebElement loginButton=driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[1]/div[2]/div[2]/form/div[5]/input"));
        loginButton.click();
        driver.close();
    }
}
