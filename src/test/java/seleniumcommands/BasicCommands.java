package seleniumcommands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BasicCommands {
    public static void main(String[] args) {
        WebDriver driver;
        driver = new ChromeDriver();                    /** Creating webdriver instance**/
        driver.manage().window().maximize();            /** Maximize window**/
        driver.manage().deleteAllCookies();             /** Delete all cookies**/
        driver.get("https://selenium.obsqurazone.com/index.php");
        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
        String title=driver.getTitle();                 /** Get title**/
        System.out.println(title);
        String sourceCode=driver.getPageSource();       /** Get sourcecode**/
        System.out.println(sourceCode);
        driver.close();

//        /**Launching Edge driver**/
//        driver = new EdgeDriver();
//        driver.manage().window().maximize();
//        driver.get("https://selenium.obsqurazone.com/index.php");
//        driver.close();
//
//        /**Launching Firefox driver**/
//        driver = new FirefoxDriver();
//        driver.manage().window().maximize();
//        driver.get("https://selenium.obsqurazone.com/index.php");
//        driver.close();

    }
}
