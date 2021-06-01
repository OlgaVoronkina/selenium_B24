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
        System.out.println("1.a - проверить, что страны расположены в алфавитном порядке");
        this.checkSortCountriesList();
        System.out.println("1.b - для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке");
        this.checkSortZonesList();

        System.out.println("2. - зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке");
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        this.checkGeoZones();
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
                System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
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
                        System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
                    } else {
                        System.out.println("ERROR: Список не в алфавитном порядке");
                    }
                    driver.navigate().back();
                }
            }
        }

        public  void  checkGeoZones(){


            List geoZonesList = driver.findElements(By.xpath("//tr//a[contains(@href,'geo_zone_id')and not (contains(@title,'Edit'))]"));

            for (int k = 0; k < geoZonesList.size(); k++){
                int geoIndex = k+1;
                WebElement geoZone = driver.findElement(By.xpath("//tr["+ geoIndex +"]//a[contains(@href,'geo_zone_id')and not (contains(@title,'Edit'))]"));
                geoZone.click();
                List geoZonesZonesList = driver.findElements(By.xpath("//tr//input[contains(@name,'country_code')]/.."));
                ArrayList<String> getGeoZonesList = new ArrayList();
                ArrayList<String> sortGeoZonesList = new ArrayList();

                for (int l = 0; l < geoZonesZonesList.size(); l++) {
                    WebElement geoZonesZones = (WebElement) geoZonesZonesList.get(l);
                    String geoZonesName = geoZonesZones.getText();
                    getGeoZonesList.add(geoZonesName);
                }

                for (String getGeoZone : getGeoZonesList) {
                    sortGeoZonesList.add(getGeoZone);
                }

                Collections.sort(sortGeoZonesList);

                if (sortGeoZonesList.equals(getGeoZonesList)) {
                    System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
                } else {
                    System.out.println("ERROR: Список не в алфавитном порядке");
                }
                driver.navigate().back();
            }

        }

}
