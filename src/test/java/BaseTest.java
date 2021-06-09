
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

//    @Before
//    public void start() {
//        DesiredCapabilities caps = new DesiredCapabilities();
//        //caps.setCapability(FirefoxDriver.MARIONETTE, true);
//        driver = new FirefoxDriver(caps);
//        System.out.println(((HasCapabilities)driver).getCapabilities());
//        wait = new WebDriverWait(driver, 10);
//    }

    @After
    public void stop(){
        driver.quit();
        driver=null;
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
}
