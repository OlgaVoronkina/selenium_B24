import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

import static java.lang.Thread.sleep;

public class Task_12 extends BaseTest{

    @Test
    public void task_12() throws InterruptedException {
        LoginInLitecartAdmin_Chrome loginInLitecartAdmin_chrome = new LoginInLitecartAdmin_Chrome();
        loginInLitecartAdmin_chrome.loginLiteCartAdmin();

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");

//заполнение вкладки General
        driver.findElement(By.xpath("//a[contains(@href,'edit_product')]")).click();
        sleep(500);
        driver.findElement(By.xpath("//label[.=' Enabled']")).click();
        String productName = "Product name"+ generatePostcode();
        driver.findElement(By.xpath("//input[contains(@name,'name')]")).sendKeys(productName);
        driver.findElement(By.xpath("//input[@name='code']")).sendKeys("Product code");
        this.clearCheckBoxes();
        driver.findElement(By.xpath("//input[@data-name='Rubber Ducks']")).click();
        driver.findElements(By.xpath("//input[contains(@name,'product_groups')]")).clear();
        driver.findElement(By.xpath("//td[.='Unisex']/..//input")).click();
        driver.findElement(By.xpath("//input[@name='quantity']")).clear();
        driver.findElement(By.xpath("//input[@name='quantity']")).sendKeys("1");

        String file = new File ("src/test/resources/testfile.png").getAbsolutePath();
        driver.findElement(By.xpath("//input[@name='new_images[]']")).sendKeys(file);

        driver.findElement(By.xpath("//input[@name='date_valid_from']")).clear();
        driver.findElement(By.xpath("//input[@name='date_valid_from']")).sendKeys("2021-06-10");
        driver.findElement(By.xpath("//input[@name='date_valid_to']")).clear();
        driver.findElement(By.xpath("//input[@name='date_valid_to']")).sendKeys("2021-12-31");

//заполнение вкладки Information

        driver.findElement(By.xpath("//a[@href='#tab-information']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='manufacturer_id']")));

        driver.findElement(By.xpath("//select[@name='manufacturer_id']")).click();
        new Select(driver.findElement(By.xpath("//select[@name='manufacturer_id']"))).selectByVisibleText("ACME Corp.");
        driver.findElement(By.xpath("//input[@name='keywords']")).sendKeys("test");
        String shortDescription = generateRandomString(25);
        driver.findElement(By.xpath("//input[@name='short_description[en]']")).sendKeys(shortDescription);
        String description = generateRandomString(100);
        driver.findElement(By.xpath("//div[@class='trumbowyg-editor']")).sendKeys(description);
        driver.findElement(By.xpath("//input[@name='head_title[en]']")).sendKeys("test");
        driver.findElement(By.xpath("//input[@name='meta_description[en]']")).sendKeys("test");

//заполнение вкладки Prices
        driver.findElement(By.xpath("//a[@href='#tab-prices']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='purchase_price']")));

        driver.findElement(By.xpath("//input[@name='purchase_price']")).clear();
        driver.findElement(By.xpath("//input[@name='purchase_price']")).sendKeys("100");
        driver.findElement(By.xpath("//select[@name='purchase_price_currency_code']")).click();
        new Select(driver.findElement(By.xpath("//select[@name='purchase_price_currency_code']"))).selectByVisibleText("US Dollars");
        driver.findElement(By.xpath("//input[@name='gross_prices[USD]']")).clear();
        driver.findElement(By.xpath("//input[@name='gross_prices[USD]']")).sendKeys("100");
        driver.findElement(By.xpath("//input[@name='gross_prices[EUR]']")).clear();
        driver.findElement(By.xpath("//input[@name='gross_prices[EUR]']")).sendKeys("90");

        driver.findElement(By.xpath("//button[@type='submit'][@value='Save']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()=' Catalog']")));

        List products = driver.findElements(By.xpath("//table//tr//a[contains(@href,'product_id') and not (contains(@title,'Edit'))]"));
        for(int j=0; j<products.size(); j++){
            WebElement product = (WebElement) products.get(j);
            String productTitle = product.getText();
            if(productTitle.equals(productName)){
                System.out.println("SUCCESS: продукт добавлен в каталог");
            }
        }
    }

    public void clearCheckBoxes() {
        List checkBoxes = driver.findElements(By.xpath("//input[contains(@name,'categories')]"));
            for(int i = 0; i<checkBoxes.size(); i++){
                int index = i+1;
                WebElement checkBox = driver.findElement(By.xpath("//table//tr["+index+"]//input[contains(@name,'categories')]"));
                if(checkBox.getAttribute("checked") != null){
                    checkBox.click();
                }
            }
    }

}
