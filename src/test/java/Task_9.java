import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

public class Task_9 extends  BaseTest {

    @Test
    public void task_9() throws InterruptedException {

        LoginInLitecartAdmin_Chrome loginInLitecartAdmin_chrome = new LoginInLitecartAdmin_Chrome();
        loginInLitecartAdmin_chrome.loginLiteCartAdmin();

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        this.checkSortCountriesList();
        this.checkSortZonesList();
    }

        public void checkSortCountriesList() {
            List countries = driver.findElements(By.xpath("//form[@name='countries_form']//a[contains(@href,'edit_country') and not (contains(@title,'Edit'))]"));
            ArrayList<String> getCountiesList = new ArrayList();
            ArrayList<String> sortCountiesList = new ArrayList();

            for (int i = 0; i < countries.size(); i++) {
                WebElement country = (WebElement) countries.get(i);
                String countryName = country.getText();
                getCountiesList.add(countryName);
            }

            for (String getCountry : getCountiesList) {
                sortCountiesList.add(getCountry);
            }

            Collections.sort(sortCountiesList);

            if (sortCountiesList.equals(getCountiesList)) {
                System.out.println("Список стран отсортирован в алфавитном порядке");
            } else {
                System.out.println("ERROR: Список не в алфавитном порядке");
            }
        }

        public void checkSortZonesList() {
            List quantityZonesList = driver.findElements(By.xpath("//tr//td[@class='text-center']"));
            for (int z =0; z < quantityZonesList.size(); z++) {
                int index = z+1;
                WebElement countriesZone =  driver.findElement(By.xpath("//tr["+index+"]//td[@class='text-center']"));
                String quantityZoneCountry = countriesZone.getText();
                int n = Integer.parseInt(quantityZoneCountry);

                if(n>0){
                    WebElement countryOfZones = driver.findElement(By.xpath("//tr["+index+"]//a[contains(@href,'edit_country') and not (contains(@title,'Edit'))]"));
                    countryOfZones.click();

                    List countryZonesList = driver.findElements(By.xpath("//input[contains(@name,'zones')][contains(@name,'name')]"));
                    ArrayList<String> getZonesList = new ArrayList();
                    ArrayList<String> sortZonesList = new ArrayList();

                    for (int j = 0; j < countryZonesList.size(); j++) {
                        WebElement countryZone = (WebElement) countryZonesList.get(j);
                        String countryZoneName = countryZone.getAttribute("value");
                        getZonesList.add(countryZoneName);
                    }

                    for (String getZone : getZonesList) {
                        sortZonesList.add(getZone);
                    }

                    Collections.sort(sortZonesList);

                    if (sortZonesList.equals(getZonesList)) {
                        System.out.println("Список зон страны отсортирован в алфавитном порядке");
                    } else {
                        System.out.println("ERROR: Список не в алфавитном порядке");
                    }
                    driver.navigate().back();
                }
            }
        }
}
