package seleniumbasics;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DemoWebShop {
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
    @Parameters({"browser", "base_url"})
    public void setUp(String browserName, String url) {
        testInitialise(browserName);
        driver.get(url);
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
    public void TC_002_verifyLogin() {
        WebElement loinButton = driver.findElement(By.className("ico-login"));
        loinButton.click();
        String mail = "abelSam@gmail.com";
        WebElement email = driver.findElement(By.xpath("//input[@id='Email']"));
        email.sendKeys(mail);
        WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
        password.sendKeys("Abel@123");
        WebElement loginButton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
        loginButton.click();
        WebElement userAccount = driver.findElement(By.xpath("(//a[@class='account'])[1]"));
        String actMail = userAccount.getText();
        Assert.assertEquals(mail, actMail, "Login Failed");
    }


    @Test
    public void TC_003_verifyRegistration() {
        WebElement reg1 = driver.findElement(By.xpath("//a[@class='ico-register']"));
        reg1.click();
        List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
        selectGender("F", gender);
        WebElement firstName = driver.findElement(By.id("FirstName"));
        firstName.sendKeys("Arya123");
        WebElement lastName = driver.findElement(By.id("LastName"));
        lastName.sendKeys("V");
        WebElement emailField = driver.findElement(By.id("Email"));
        String email = "hdb8@gmail.com";
        emailField.sendKeys(email);
        WebElement passwordField = driver.findElement(By.id("Password"));
        passwordField.sendKeys("Arya@123");
        WebElement passwordConfirm = driver.findElement(By.id("ConfirmPassword"));
        passwordConfirm.sendKeys("Arya@123");
        WebElement register = driver.findElement(By.id("register-button"));
        register.click();
        WebElement userAccount = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
        String actualEmail = userAccount.getText();
        Assert.assertEquals(actualEmail, email, "Registration failed");
    }

    public void selectGender(String gen, List<WebElement> gender) {
        for (int i = 0; i < gender.size(); i++) {
            String genderValue = gender.get(i).getAttribute("value");
            if (genderValue.equals(gen)) {
                gender.get(i).click();

            }
        }
    }

    @Test
    public void TC_004_verifyTitleFromExcelSheet() throws IOException {
        String actualTitle = driver.getTitle();                 /** Get title**/
        String excelPath = "\\src\\test\\resources\\TestData.xlsx";
        String sheetName = "HomePage";
        String expectedTitle = ExcelUtility.readStringData(excelPath, sheetName, 0, 1);
        Assert.assertEquals(actualTitle, expectedTitle, "Invalid data found in table");
    }

    @Test
    public void TC_005_verifyRegisterPageFromExcelSheet() throws IOException {
        String actualTitle = driver.getTitle();                 /** Get title**/
        String excelPath = "\\src\\test\\resources\\TestData.xlsx";
        String sheetName = "Register";
        String expectedTitle = ExcelUtility.readStringData(excelPath, sheetName, 1, 6);
        Assert.assertEquals(actualTitle, expectedTitle, "Invalid data found in table");
        WebElement reg1 = driver.findElement(By.xpath("//a[@class='ico-register']"));
        reg1.click();
        List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
        selectGender(ExcelUtility.readStringData(excelPath, sheetName, 1, 0), gender);
        WebElement firstName = driver.findElement(By.id("FirstName"));
        firstName.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 1, 1));
        WebElement lastName = driver.findElement(By.id("LastName"));
        lastName.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 1, 2));
        WebElement emailField = driver.findElement(By.id("Email"));
        emailField.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 1, 3));
        WebElement passwordField = driver.findElement(By.id("Password"));
        passwordField.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 1, 4));
        WebElement passwordConfirm = driver.findElement(By.id("ConfirmPassword"));
        passwordConfirm.sendKeys(ExcelUtility.readStringData(excelPath, sheetName, 1, 5));
        WebElement register = driver.findElement(By.id("register-button"));
        register.click();
        WebElement userAccount = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
        String actualEmail = userAccount.getText();
        Assert.assertEquals(actualEmail, ExcelUtility.readStringData(excelPath, sheetName, 1, 3), "Registration failed");
    }

    @Test(dataProvider = "InvalidCredentials")
    public void TC_006_verifyLoginWithInvalidDatas(String userName, String pWord) {
        WebElement loinButton = driver.findElement(By.className("ico-login"));
        loinButton.click();
        WebElement email = driver.findElement(By.xpath("//input[@id='Email']"));
        email.sendKeys(userName);
        WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
        password.sendKeys(pWord);
        WebElement loginButton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
        loginButton.click();
        WebElement errorMessage = driver.findElement(By.xpath("//div[@class='validation-summary-errors']//span"));
        String actualMessage = errorMessage.getText();
        String expectedMessage = "Login was unsuccessful. Please correct the errors and try again.";
        Assert.assertEquals(expectedMessage, actualMessage, "Invalid error message");
    }

    @DataProvider(name = "InvalidCredentials")
    public Object[][] userCredentials() {
        Object[][] data = {{"aryaa@gmail.com", "Arya@123"}, {"arya@gmail.com", "Aryaa@123"}, {"aryaa@gmail.com", "Aryaa@123"}};
        return data;
    }

    @Test
    public void TC_007_verifyRegistrationUsingRandomGenerator() {
        WebElement reg1 = driver.findElement(By.xpath("//a[@class='ico-register']"));
        reg1.click();
        String fName = RandomDataUtility.getfName();
        String lName = RandomDataUtility.getlName();
        String emailID = RandomDataUtility.getRandomEmail();
        String pword = fName + "@123";
        List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
        selectGender("F", gender);
        WebElement firstName = driver.findElement(By.id("FirstName"));
        firstName.sendKeys(fName);
        WebElement lastName = driver.findElement(By.id("LastName"));
        lastName.sendKeys(lName);
        WebElement emailField = driver.findElement(By.id("Email"));
        emailField.sendKeys(emailID);
        WebElement passwordField = driver.findElement(By.id("Password"));
        passwordField.sendKeys(pword);
        WebElement passwordConfirm = driver.findElement(By.id("ConfirmPassword"));
        passwordConfirm.sendKeys(pword);
        WebElement register = driver.findElement(By.id("register-button"));
        register.click();
        WebElement userAccount = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
        String actualEmail = userAccount.getText();
        Assert.assertEquals(actualEmail, emailID, "Registration failed");
    }

    @Test
    public void TC_008_verifyDemoWebShopTitleHomeWork() throws IOException {
        String actualTitle = driver.getTitle();
        List<ArrayList<String>> data = ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx", "HomePage");
        String expectedTitle = data.get(0).get(1);
        Assert.assertEquals(actualTitle, expectedTitle, "Invalid Title found");
    }
    @Test(dataProvider = "LoginCredentials")
    public void TC_009_verifyLoginWithValidDatas(String userName, String pWord) {
        WebElement loinButton = driver.findElement(By.className("ico-login"));
        loinButton.click();
        WebElement email = driver.findElement(By.xpath("//input[@id='Email']"));
        email.sendKeys(userName);
        WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
        password.sendKeys(pWord);
        WebElement loginButton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
        loginButton.click();
        WebElement logoutButton = driver.findElement(By.className("ico-logout"));
        logoutButton.click();
    }

    @DataProvider(name = "LoginCredentials")
    public Object[][] userLoginCredentials() {
        Object[][] data = {{"mini@yopmail.com", "Test@123"}, {"mary@yopmail.com", "Test@123"}};
        return data;
    }
    @Test
    @Parameters({"uName", "pWord"})
    public void TC_010_verifyLoginWithValidDatasUsingParameters(String userName, String pWord) {
        WebElement loinButton = driver.findElement(By.className("ico-login"));
        loinButton.click();
        WebElement email = driver.findElement(By.xpath("//input[@id='Email']"));
        email.sendKeys(userName);
        WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));
        password.sendKeys(pWord);
        WebElement loginButton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
        loginButton.click();
        WebElement logoutButton = driver.findElement(By.className("ico-logout"));
        logoutButton.click();
    }
    @Test
    public void TC_011_verifyRegisterPageFromExcelSheetAndMailAsRandomGenrator() throws IOException {
        String actualTitle = driver.getTitle();
        List<ArrayList<String>> data = ExcelUtility.excelDataReader("\\src\\test\\resources\\TestData.xlsx", "Register");
        String expectedTitle = data.get(1).get(6);
        Assert.assertEquals(actualTitle, expectedTitle, "Invalid Title found");
        WebElement reg1 = driver.findElement(By.xpath("//a[@class='ico-register']"));
        reg1.click();
        List<WebElement> gender = driver.findElements(By.xpath("//input[@name='Gender']"));
        selectGender("F", gender);
        WebElement firstName = driver.findElement(By.id("FirstName"));
        String fName= data.get(1).get(1);
        firstName.sendKeys(fName);
        WebElement lastName = driver.findElement(By.id("LastName"));
        String lName = data.get(1).get(2);
        lastName.sendKeys(lName);
        WebElement emailField = driver.findElement(By.id("Email"));
        String emailID = RandomDataUtility.getRandomEmail();
        emailField.sendKeys(emailID);
        WebElement passwordField = driver.findElement(By.id("Password"));
        String pword= data.get(1).get(4);
        passwordField.sendKeys(pword);
        WebElement passwordConfirm = driver.findElement(By.id("ConfirmPassword"));
        String confirmPword= data.get(1).get(4);
        passwordConfirm.sendKeys(confirmPword);
        WebElement register = driver.findElement(By.id("register-button"));
        register.click();
        WebElement userAccount = driver.findElement(By.xpath("//div[@class='header-links']//a[@class='account']"));
        String actualEmail = userAccount.getText();
        Assert.assertEquals(actualEmail, emailID, "Registration failed");
    }
}
