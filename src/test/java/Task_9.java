import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Task_9 extends  BaseTest {

        @Test
    public void task_9()  {
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

        int indexColumnName =  this.getNumberColumn("Name");
        List<WebElement> countries = driver.findElements(By.xpath("//table[@class='dataTable']//td["+indexColumnName+"]"));

        ArrayList<String> getCountiesList = new ArrayList();
        ArrayList<String> sortCountiesList = new ArrayList();

        for (int i = 0; i < countries.size(); i++) {
            WebElement country = countries.get(i);
            String countryName = country.getText();
            getCountiesList.add(countryName);
        }
        System.out.println("getCountiesList = " +getCountiesList);

        for (String getCountry : getCountiesList) {
            sortCountiesList.add(getCountry);
        }
        Collections.sort(sortCountiesList);
        System.out.println("sortCountiesList = " +sortCountiesList);

        if (sortCountiesList.equals(getCountiesList)) {
            System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
        } else {
            System.out.println("ERROR: Список не в алфавитном порядке");
        }
    }


    public void checkSortZonesList() {
        int indexColumnZones =  this.getNumberColumn("Zones");

        List quantityZonesList = driver.findElements(By.xpath("//table[@class='dataTable']//td["+indexColumnZones+"]"));

        for (int z = 0; z < quantityZonesList.size(); z++) {
            int index = z + 1;
            WebElement countriesZone = driver.findElement(By.xpath("//tr[" + index + "]//td["+indexColumnZones+"]"));
            String quantityZoneCountry = countriesZone.getText();
            int n = Integer.parseInt(quantityZoneCountry);

            if (n > 0) {
                WebElement countryOfZones = driver.findElement(By.xpath("//tr[" + index + "]//a[contains(@href,'edit_country') and not (contains(@title,'Edit'))]"));
                countryOfZones.click();

                List countryZonesList = driver.findElements(By.xpath("//input[contains(@name,'zones')][contains(@name,'name')]"));
                ArrayList<String> getZonesList = new ArrayList();
                ArrayList<String> sortZonesList = new ArrayList();

                for (int j = 0; j < countryZonesList.size(); j++) {
                    WebElement countryZone = (WebElement) countryZonesList.get(j);
                    String countryZoneName = countryZone.getAttribute("value");
                    getZonesList.add(countryZoneName);
                }
                System.out.println("getZonesList  = " +getZonesList);

                for (String getZone : getZonesList) {
                    sortZonesList.add(getZone);
                }

                Collections.sort(sortZonesList);
                System.out.println("sortZonesList  = " +sortZonesList);

                if (sortZonesList.equals(getZonesList)) {
                    System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
                } else {
                    System.out.println("ERROR: Список не в алфавитном порядке");
                }
                driver.navigate().back();
            }
        }
    }


    public void checkGeoZones(){
        List <WebElement> geoZonesList = driver.findElements(By.xpath("//tr[@class='row']//a[contains(@href,'geo_zone_id')and not (contains(@title,'Edit'))]"));

        for (int k = 0; k < geoZonesList.size(); k++) {
            int geoIndex = k + 1;
            WebElement geoZone = driver.findElement(By.xpath("//tr[@class='row'][" + geoIndex + "]//a[contains(@href,'geo_zone_id')and not (contains(@title,'Edit'))]"));
            geoZone.click();

            List<WebElement> geoZonesZonesList = driver.findElements(By.xpath("//table[@id='table-zones']//select[contains(@name,'zone_code')]//option[@selected='selected']"));
            ArrayList<String> getGeoZonesList = new ArrayList();
            ArrayList<String> sortGeoZonesList = new ArrayList();

            for(WebElement geoZonesZones: geoZonesZonesList){
                    String geoZonesName = geoZonesZones.getText();
                    getGeoZonesList.add(geoZonesName);
            }
            System.out.println("getGeoZonesList = " +getGeoZonesList);
            for (String getGeoZone : getGeoZonesList) {
                sortGeoZonesList.add(getGeoZone);
            }

            Collections.sort(sortGeoZonesList);
            System.out.println("sortGeoZonesList = " +sortGeoZonesList);

            if (sortGeoZonesList.equals(getGeoZonesList)) {
                System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
            } else {
                System.out.println("ERROR: Список не в алфавитном порядке");
            }
            driver.navigate().back();
        }

    }

    public int getNumberColumn(String name) {
        List<WebElement> tableHeader = driver.findElements(By.xpath("//table[@class='dataTable']//th"));
        int index = 1;
            for (WebElement header: tableHeader) {
                String columnName = header.getText();
                if(columnName.equalsIgnoreCase(name)){
                    return index;
                } else {
                    index++;
                }
            }
            return index;
        }
    }
