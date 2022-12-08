package seleniumcommands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BasicCommands {
    public static void main(String[] args) {
//        Creating webdriver instance
        WebDriver driver1 = new ChromeDriver();
        WebDriver driver2 = new EdgeDriver();
        WebDriver driver3 = new FirefoxDriver();

//        Maximizing window
        driver1.manage().window().maximize();
        driver2.manage().window().maximize();
        driver3.manage().window().maximize();

//        Closing the browser
        driver1.close();
        driver2.close();
        driver3.close();

    }
}
