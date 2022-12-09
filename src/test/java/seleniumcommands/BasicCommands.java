package seleniumcommands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BasicCommands {
    public static void main(String[] args) {
        WebDriver driver;
        /** Creating webdriver instance**/
        driver = new ChromeDriver();
        driver.get("https://selenium.obsqurazone.com/index.php");
        driver.manage().window().maximize();
        driver.close();
        /**Launching Edge driver**/
        driver = new EdgeDriver();
        driver.get("https://selenium.obsqurazone.com/index.php");
        driver.manage().window().maximize();
        driver.close();
        /**Launching Firefox driver**/
        driver = new FirefoxDriver();
        driver.get("https://selenium.obsqurazone.com/index.php");
        driver.manage().window().maximize();
        driver.close();

    }
}
