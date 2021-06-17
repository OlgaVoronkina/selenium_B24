import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;
import java.util.List;
import java.util.Set;

public class Task_14 extends BaseTest{
    @Test
    public void task_14(){

        // 1) зайти в админку
        LoginInLitecartAdmin_Chrome loginInLitecartAdmin_chrome = new LoginInLitecartAdmin_Chrome();
        loginInLitecartAdmin_chrome.loginLiteCartAdmin();

        // 2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        // 3) открыть на редактирование какую-нибудь страну или начать создание новой
        driver.findElement(By.xpath("//tr[@class='row'][1]//a[contains(@href,'edit_country') and (contains(@title,'Edit'))]")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[contains(text(),' Edit Country')]"))));

        // 4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы и открываются в новом окне, именно это и нужно проверить.

        List links = driver.findElements(By.xpath("//i[@class='fa fa-external-link']"));
        for (int i=0; i<links.size();i++){
            WebElement link = (WebElement) links.get(i);
            String originalWindows = driver.getWindowHandle();
            Set<String> existingWindowsBefore = driver.getWindowHandles();

            link.click();

            wait.until(driver -> !driver.getWindowHandles().equals(existingWindowsBefore));
            Set<String> existingWindowsAfter = driver.getWindowHandles();

            Iterator<String> iterator= existingWindowsAfter.iterator();
            while(iterator.hasNext())
            { String newWindow=iterator.next();
                if(!originalWindows.equals(newWindow))
                {
                    driver.switchTo().window(newWindow);
                    System.out.println(driver.switchTo().window(newWindow).getTitle());
                    driver.close();
                }
            }
            driver.switchTo().window(originalWindows);
        }

    }
}

