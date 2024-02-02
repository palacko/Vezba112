import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class AutomationTest {
    WebDriver driver;

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("CHROME") String browser) {
        if(browser.equals("CHROME")){
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } else if(browser.equalsIgnoreCase("FIREFOX")){
            System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        } else if(browser.equalsIgnoreCase("Headless")){
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless","--window-size=1920,1200");
            driver = new ChromeDriver(options);


//            driver.manage().window().maximize();
        }


        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {driver.quit();}

    @Test
    @Parameters({"subjectHeading","email","orderNum","upload","message","elementSelector","expectedMessage"})
    public void automationTest(String subjectHeading, String email, String orderNum, @Optional("No") String upload, String message, String elementSelector, String expectedMessage) throws InterruptedException {
        driver.get("http://www.automationpractice.pl/index.php");
        driver.findElement(By.cssSelector("[title='Contact us']")).click();
        Select selectSubjectHeading = new Select(driver.findElement(By.cssSelector("#id_contact")));
        selectSubjectHeading.selectByVisibleText(subjectHeading);
        driver.findElement(By.cssSelector("#email")).sendKeys(email);
        driver.findElement(By.cssSelector("#id_order")).sendKeys(orderNum);
        if (upload.equalsIgnoreCase("Yes")) {
            driver.findElement(By.cssSelector("#fileUpload")).sendKeys("C:/Users/MiroslavPalackovic/Desktop/Test.txt");
        }
        driver.findElement(By.cssSelector("textarea")).sendKeys(message);
        Thread.sleep(10000);
        driver.findElement(By.cssSelector("#submitMessage")).click();
        Thread.sleep(10000);
        Assert.assertEquals(driver.findElement(By.cssSelector(elementSelector)).getText(),expectedMessage);


    }

    @Test
    public void testMethod(){
        System.out.println("Test");
    }
}