import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LoginInLitecartAdmin_FF {
    private WebDriver driver;
    private WebDriverWait wait;

    //Первый способ
//    @Before
//    public void start() {
//        driver = new FirefoxDriver();
//        wait = new WebDriverWait(driver, 10);
//    }

//Второй способ
    @Before
    public void start() {
        DesiredCapabilities caps = new DesiredCapabilities();
        //caps.setCapability(FirefoxDriver.MARIONETTE, true);
        driver = new FirefoxDriver(caps);
        System.out.println(((HasCapabilities)driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void loginLiteCartAdmin(){
        String username = "admin";
        String password = "admin";
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@name='login']")).click();
        wait.until(titleIs("Dashboard | My Store"));
    }

    @After
    public void stop(){
        driver.quit();
        driver=null;
    }
}
