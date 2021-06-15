import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Task_13 extends BaseTest{
    @Test
    public void task_13(){

        //1)открыть главную страницу
        driver.get("http://localhost/litecart/");
        wait.until(titleIs("Online Store | My Store"));

        //2)открыть первый товар из списка. 2) добавить его в корзину. 3) подождать, пока счётчик товаров в корзине обновится. 4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
        for(int i=0; i<3; i++){
            List<WebElement> products = driver.findElements(By.xpath("//div[@id='box-most-popular']//li[@class='product column shadow hover-light']"));
            WebElement product = products.get(i);
            product.click();

            try {
                if (driver.findElement(By.xpath("//select[@name='options[Size]']")).isDisplayed()) {
                    WebElement productSize = driver.findElement(By.xpath("//select[@name='options[Size]']"));
                    productSize.click();
                    new Select(productSize).selectByVisibleText("Small");
                }
            }
            catch (Exception e){
            };

            int before = Integer.parseInt(driver.findElement(By.xpath("//div[@id='cart']//span[@class='quantity']")).getText());
            int after = before + 1;
            driver.findElement(By.xpath("//button[@name='add_cart_product']")).click();
            wait.until(textToBePresentInElement(driver.findElement(By.xpath("//div[@id='cart']//span[@class='quantity']")), String.valueOf(after)));

            driver.findElement(By.xpath("//header//a[@href='http://localhost/litecart/en/']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//header//a[contains(@href,'checkout')][@class='link']")));
        }

    // 5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
    driver.findElement(By.xpath("//header//a[contains(@href,'checkout')][@class='link']")).click();
    wait.until(titleIs("Checkout | My Store"));

    //6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица

        do{
            try{
                WebElement duck = driver.findElement(By.xpath("//ul[@class='shortcuts']/li[1]"));
                duck.click();
            } catch (Exception e){}

            String duckName = driver.findElement(By.xpath("//a[contains(@href,'rubber-ducks')]/strong")).getText();
            WebElement productSelect = driver.findElement(By.xpath("//td[@class='item'][contains(text(),'"+duckName+"')]"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='remove_cart_item']")));
            driver.findElement(By.xpath("//button[@name='remove_cart_item']")).click();
            wait.until(ExpectedConditions.stalenessOf(productSelect));
        } while(driver.findElements(By.xpath("//td[@class='item']")).size()!=0);

        Assert.assertTrue(driver.findElement(By.xpath("//em[contains(text(),'There are no items in your cart')]")).isDisplayed());
    }
}
