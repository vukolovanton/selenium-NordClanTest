import Data.CustomDataProvider;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.TestRunner.PriorityWeight.dependsOnMethods;

@Listeners({AllureListener.class})
public class Suite_CheckEmails extends TestBase {
    private Integer targetEmails = 0;
    public WebDriver driver;

    @BeforeClass
    public void setup() throws IOException {
        TestBase tb = new TestBase();
        driver = tb.initializeDriver();
        driver.get("https://mail.ru/");
    }

    @Test(dataProvider = "LoginDataProvider", dataProviderClass = CustomDataProvider.class, priority = 1)
    @Description("Login to mail box")
    void loginToEmail(String email, String password) throws IOException, InterruptedException {

        String title = driver.getTitle();
        Assert.assertEquals("Mail.ru: почта, поиск в интернете, новости, игры", title);

        driver.findElement(By.id("mailbox:login")).sendKeys(email);
        WebElement button = driver.findElement(By.xpath("//input[@class='o-control']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mailbox:password")));
        driver.findElement(By.id("mailbox:password")).sendKeys(password);
        button.click();

    }

    @Test(dependsOnMethods = {"loginToEmail"}, priority = 2)
    @Description("Check for target emails")
    void checkForEmails(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='compose-button__txt']")));
        int emailsCount = driver.findElements(By.xpath("//span[contains(@title, 'vukolovanton92@gmail.com')]")).size();
        targetEmails = emailsCount;
    }

    @Test(dependsOnMethods = {"checkForEmails"}, priority = 3)
    @Description("Send email with target data")
    void createNewEmail() {
        driver.findElement(By.xpath("//span[@class='compose-button__txt']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement inputEmail = driver.findElement(By.xpath("//div[contains(@class, 'contactsContainer')]//input[contains(@class, 'container')]"));
        WebElement inputSubject = driver.findElement(By.xpath("//div[contains(@class, 'subject__container')]//input[contains(@class, 'container')]"));
        WebElement inputText = driver.findElement(By.xpath("//div[@role='textbox']"));

        wait.until(ExpectedConditions.elementToBeClickable(inputEmail));
        inputEmail.sendKeys("ilya.filinin@nordclan.com");
        inputSubject.sendKeys("Количество писем");
        inputText.sendKeys("Я собираю количество писем от отправителя vukolovanton92@gmail.com. Всего их " + targetEmails);

        driver.findElement(By.xpath("//span[text()='Отправить']")).click();
    }

    @AfterClass
    void tearDown() {
        driver.quit();
    }

}
