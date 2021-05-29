import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.lang.Thread.sleep;

public class Task_7 extends BaseTest {
    @Test
    public void task_7() throws InterruptedException {
        LoginInLitecartAdmin_Chrome loginInLitecartAdmin_chrome = new LoginInLitecartAdmin_Chrome();
        loginInLitecartAdmin_chrome.loginLiteCartAdmin();

        List mainMenu = driver.findElements(By.xpath("//li[contains(@class,'app')]"));
        for (int i = 1; i < mainMenu.size() + 1; i++) {
            WebElement mainpunkt = driver.findElement(By.xpath("//li[contains(@class,'app')][" + i + "]"));
            mainpunkt.click();
            sleep(500);
            List podPunkt = driver.findElements(By.xpath("//li[contains(@class,'doc')]"));
            if (podPunkt.size() > 0) {
                for (int k = 1; k < podPunkt.size() + 1; k++) {
                    WebElement podPunktName = driver.findElement(By.xpath("//li[contains(@class,'doc')][" + k + "]"));
                    podPunktName.click();
                    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='panel-heading']")).isDisplayed());
                }
            } else {
                Assert.assertTrue(driver.findElement(By.xpath("//div[@class='panel-heading']")).isDisplayed());
            }
        }
    }
}