
import org.junit.Test;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LoginInLitecartAdmin_Chrome extends BaseTest{

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

}
