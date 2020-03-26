import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureListener implements ITestListener {
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment
    public byte[] saveFailureScreenshot(WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("I am onTestStart method " + getTestMethodName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("I am onTestSuccess method " + getTestMethodName(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("I am onTestFailure method " + getTestMethodName(result));
        Object testClass = result.getInstance();
        WebDriver driver = TestBase.getDriver();

        if (driver instanceof WebDriver) {
            System.out.println("Screenshot captured for test case: " + getTestMethodName(result));
            saveFailureScreenshot(driver);
        }
        saveTextLog(getTestMethodName(result) + "failed and screenshot taken");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("I am onStart method " + context.getName());
        context.setAttribute("WebDriver", TestBase.getDriver());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("I am onFinish method " + context.getName());
    }
}
