import Data.CustomDataProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Suite_CheckEmails extends TestBase {

    @Test(dataProvider = "LoginDataProvider", dataProviderClass = CustomDataProvider.class, priority = 1)
    void loginToEmail(String email, String password) throws IOException, InterruptedException {
        driver = initializeDriver();
        driver.get("https://mail.ru/");

        String title = driver.getTitle();
        Assert.assertEquals("Mail.ru: почта, поиск в интернете, новости, игры", title);

        driver.findElement(By.id("mailbox:login")).sendKeys(email);
        WebElement button = driver.findElement(By.xpath("//input[@class='o-control']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, 5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mailbox:password")));
        driver.findElement(By.id("mailbox:password")).sendKeys(password);
        button.click();
    }

    @Test(dependsOnMethods = {"loginToEmail"}, priority = 2)
    void homePage(){
        String title = driver.getTitle();
        System.out.println(title);
    }

    @AfterClass
    void tearDown() {
        driver.quit();
    }

}
