import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public WebDriver driver;
    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();

    public WebDriver initializeDriver() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("src\\datadriver.properties");
        props.load(file);

        if (props.getProperty("browser").equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src\\chromedriver.exe");
            driver = new ChromeDriver();
        }
        else if (props.getProperty("browser").equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "src\\geckodriver.exe");
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        tdriver.set(driver);

        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tdriver.get();
    }
}
