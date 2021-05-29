import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task_8 extends  BaseTest{

    @Test
    public void task_8(){
        driver.get("http://localhost/litecart/");
        wait.until(titleIs("My Store | Online Store"));

        List productsList = driver.findElements(By.xpath("//article[@class='product-column']"));
        for(int i=0; i< productsList.size(); i++){
            WebElement product = (WebElement) productsList.get(i);
            List stickers = product.findElements(By.xpath(".//div[contains(@class,'sticker')]"));
            if(stickers.size()!=1){
                System.out.println("ERROR: количество стикеров на товаре не равно 1");
            }
        }
    }
}
