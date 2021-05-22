import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LoginInLitecartAdmin_Chrome {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
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
