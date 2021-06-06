import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task_10 extends BaseTest{
    @Test
    public void task_10(){
        driver.get("http://localhost/litecart/");
        wait.until(titleIs("Online Store | My Store"));

//получение данных на главной странице
        WebElement productCatalog = driver.findElement(By.xpath("//div[contains(@id,'campaigns')]//li[1]"));
        //название продукта
        String catalogName = productCatalog.findElement(By.xpath(".//div[@class='name']")).getText();
        //обычная цена
        String catalogPrice = productCatalog.findElement(By.xpath(".//div[@class='price-wrapper']//s")).getText();
        //акционная цена
        String catalogSale = productCatalog.findElement(By.xpath(".//div[@class='price-wrapper']//strong")).getText();
        //пролучение свойства text-decoration
        String catalogPriceCrossedOut = productCatalog.findElement(By.xpath(".//div[@class='price-wrapper']//s")).getCssValue("text-decoration");
        //получение цвета обычной цены
        String catalogPriceColor = productCatalog.findElement(By.xpath(".//div[@class='price-wrapper']//s")).getCssValue("color");
        String[] arrayCatalogPriceColor = (catalogPriceColor.replaceAll("\\D+", " ").trim()).split(" ");
        int[] catalogPriceColors = new int[arrayCatalogPriceColor.length];
        for (int i = 0; i < catalogPriceColors.length; i++) {
            catalogPriceColors[i] = Integer.valueOf(arrayCatalogPriceColor[i]);
        }
        //получение размера шрифта обычной цены
        String catalogPriceSize = productCatalog.findElement(By.xpath(".//div[@class='price-wrapper']//s")).getCssValue("font-size").substring(0,2);
        int catalogPriceSizeValue = Integer.parseInt(catalogPriceSize);
        //получение цвета акционной цены
        String catalogSaleColor = productCatalog.findElement(By.xpath(".//div[@class='price-wrapper']//strong")).getCssValue("color");
        String[] arrayCatalogSaleColor = (catalogSaleColor.replaceAll("\\D+", " ").trim()).split(" ");
        int[] catalogSaleColors = new int[arrayCatalogSaleColor.length];
        for (int j = 0; j < arrayCatalogSaleColor.length; j++) {
            catalogSaleColors[j] = Integer.valueOf(arrayCatalogSaleColor[j]);
        }
        //получение тега для акционной цены
        String catalogSaleWeight = productCatalog.findElement(By.xpath(".//div[@class='price-wrapper']//strong")).getTagName();
        //получение размера шрифта акционной цены
        String catalogSaleSize = productCatalog.findElement(By.xpath(".//div[@class='price-wrapper']//strong")).getCssValue("font-size").substring(0,2);
        int catalogSaleSizeValue = Integer.parseInt(catalogSaleSize);

//переходим в карточку товара
        productCatalog.click();

//а) на главной странице и на странице товара совпадает текст названия товара
        String  cardName = driver.findElement(By.xpath("//h1[@class='title']")).getText();
        Assert.assertTrue(cardName.equalsIgnoreCase(catalogName));

//б) на главной странице и на странице товара совпадают цены (обычная и акционная)
        String cardPrice = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//s")).getText();
        Assert.assertTrue(cardPrice.equalsIgnoreCase(catalogPrice));

        String cardSale = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//strong")).getText();
        Assert.assertTrue(cardSale.equalsIgnoreCase(catalogSale));

//в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
        //проверка (в каталоге)
        Assert.assertTrue(catalogPriceCrossedOut.contains("line-through"));
        Assert.assertTrue(catalogPriceColors[0]==catalogPriceColors[1] && catalogPriceColors[1]==catalogPriceColors[2]);

        //проверка (в карточке продукта)
        String cardPriceCrossedOut = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//s")).getCssValue("text-decoration");
        Assert.assertTrue(cardPriceCrossedOut.contains("line-through"));

        String cardPriceColor = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//s")).getCssValue("color");
        String[] arrayCardPriceColor = (cardPriceColor.replaceAll("\\D+", " ").trim()).split(" ");
        int[] cardPriceColors = new int[arrayCardPriceColor.length];
        for (int k = 0; k < catalogPriceColors.length; k++) {
            cardPriceColors[k] = Integer.valueOf(arrayCardPriceColor[k]);
        }
        Assert.assertTrue(cardPriceColors[0]==cardPriceColors[1] && cardPriceColors[1]==cardPriceColors[2]);

//г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения (цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
        //проверка (в каталоге)
        Assert.assertTrue(catalogSaleWeight.equalsIgnoreCase("strong"));
        Assert.assertTrue(catalogSaleColors[1]==0 && catalogSaleColors[2]==0);
        //проверка (в карточке продукта)
        String cardSaleWeight = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//strong")).getTagName();
        Assert.assertTrue(cardSaleWeight.equalsIgnoreCase("strong"));

        String cardSaleColor = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//strong")).getCssValue("color");
        String[] arrayCardSaleColor = (cardSaleColor.replaceAll("\\D+", " ").trim()).split(" ");
        int[] cardSaleColors = new int[arrayCardSaleColor.length];
        for (int n = 0; n < arrayCardSaleColor.length; n++) {
            cardSaleColors[n] = Integer.valueOf(arrayCardSaleColor[n]);
        }
        Assert.assertTrue(cardSaleColors[1]==0 && cardSaleColors[2]==0);

//д) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
        //проверка (в каталоге)
        Assert.assertTrue(catalogSaleSizeValue>catalogPriceSizeValue);
        //проверка (в карточке продукта)
        String cardPriceSize = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//s")).getCssValue("font-size").substring(0,2);
        int cardPriceSizeValue = Integer.parseInt(cardPriceSize);
        String cardSaleSize = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//strong")).getCssValue("font-size").substring(0,2);
        int cardSaleSizeValue = Integer.parseInt(cardSaleSize);
        Assert.assertTrue(cardSaleSizeValue>cardPriceSizeValue);
    }

}
