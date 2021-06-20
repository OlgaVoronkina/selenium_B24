import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class Task_17 extends BaseTest{
    @Test
    public void task_17() {

        // 1) зайти в админку
        LoginInLitecartAdmin_Chrome loginInLitecartAdmin_chrome = new LoginInLitecartAdmin_Chrome();
        loginInLitecartAdmin_chrome.loginLiteCartAdmin();

        // 2) открыть каталог, категорию, которая содержит товары (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");

        // 3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения (любого уровня)
        List <WebElement> products = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']//a[contains(@href,'product_id') and not (contains(@title,'Edit'))]"));
        ArrayList<String> productsList = new ArrayList();

        for(int i=0; i<products.size(); i++) {
            WebElement product = products.get(i);
            String productTitle = product.getText();
            productsList.add(productTitle);
        }

        for(String productName : productsList){
            WebElement productLink = driver.findElement(By.xpath("//a[contains(text(),'"+productName+"')]"));
            productLink.click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1"))));
                driver.manage().logs().get("browser").forEach(l-> {System.out.println(l);
                Assert.assertTrue(l.equals(""));});
            driver.navigate().back();
        }
    }
}

