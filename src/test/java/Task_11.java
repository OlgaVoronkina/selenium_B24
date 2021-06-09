import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task_11 extends BaseTest{
    @Test
    public void task_11() throws InterruptedException {
        driver.get("http://localhost/litecart/");
        wait.until(titleIs("Online Store | My Store"));

//1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало с ранее созданными пользователями, в том числе при предыдущих запусках того же самого сценария),
        WebElement newCustomers = driver.findElement(By.xpath("//div[@class='content']//a[contains(@href,'create_account')]"));
        newCustomers.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='content']//input[@name='firstname']")));
        driver.findElement(By.xpath("//div[@class='content']//input[@name='firstname']")).sendKeys("Olga");
        driver.findElement(By.xpath("//div[@class='content']//input[@name='lastname']")).sendKeys("Voronkina");
        driver.findElement(By.xpath("//div[@class='content']//input[@name='address1']")).sendKeys("Crimea");
        String postcode = generatePostcode();
        driver.findElement(By.xpath("//div[@class='content']//input[@name='postcode']")).sendKeys(postcode);
        String city = generateRandomString(6);
        driver.findElement(By.xpath("//div[@class='content']//input[@name='city']")).sendKeys(city);

        driver.findElement(By.xpath("//span[contains(@id,'country_code')]")).click();
        this.putSelectValue("country_code", "United States");

        driver.findElement(By.xpath("//div[@class='content']//input[@name='email']")).sendKeys(city+postcode+ "@mail.com");
        String email = driver.findElement(By.xpath("//div[@class='content']//input[@name='email']")).getAttribute("value");

        driver.findElement(By.xpath("//div[@class='content']//input[@name='phone']")).sendKeys("+123456789");

        driver.findElement(By.xpath("//div[@class='content']//input[@name='password']")).sendKeys("Qwerty123");
        driver.findElement(By.xpath("//div[@class='content']//input[@name='confirmed_password']")).sendKeys("Qwerty123");

        driver.findElement(By.xpath("//div[@class='content']//button[@name='create_account']")).click();

        if(driver.findElement(By.xpath("//select[@name='zone_code']")).isDisplayed()){
            sleep(500);
            new Select(driver.findElement(By.xpath("//select[@name='zone_code']"))).selectByVisibleText("Alaska");
            driver.findElement(By.xpath("//div[@class='content']//input[@name='password']")).sendKeys("Qwerty123");
            driver.findElement(By.xpath("//div[@class='content']//input[@name='confirmed_password']")).sendKeys("Qwerty123");
            driver.findElement(By.xpath("//div[@class='content']//button[@name='create_account']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='content']//a[contains(@href,'logout')]")));

//2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
        sleep(500);
        driver.findElement(By.xpath("//div[@class='content']//a[contains(@href,'logout')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='content']//button[@name='login']")));

//3) повторный вход в только что созданную учётную запись,
        driver.findElement(By.xpath("//div[@class='content']//input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//div[@class='content']//input[@name='password']")).sendKeys("Qwerty123");
        driver.findElement(By.xpath("//div[@class='content']//button[@name='login']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='content']//a[contains(@href,'logout')]")));

//4) и ещё раз выход.
        driver.findElement(By.xpath("//div[@class='content']//a[contains(@href,'logout')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='content']//button[@name='login']")));

    }

    public static String generatePostcode() {
        Random random = new Random();
        int min = 11111;
        int max = 99999;
        int diff = max - min;
        int i = random.nextInt(diff + 1);
        i += min;
        String randomPostcode = String.valueOf(i);
        return  randomPostcode;
    }

    public static String generateRandomString(int length) {
        final String characters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
        return sb.toString();
        }

    public void putSelectValue(String field, String value) {
        List selects = driver.findElements(By.xpath(String.format("//li[contains(@id,'"+ field +"')]")));
        for (int i = 0; i < selects.size(); i++) {
            WebElement select = (WebElement) selects.get(i);
            int index = i+1;
            String selectName = select.getText();
            if(selectName.equals(value)){
                System.out.println("нашли совпадение");
                driver.findElement(By.xpath("//li[contains(@id,'"+ field +"')][" + index +"]")).click();
                break;
            }
        }
    }
}

