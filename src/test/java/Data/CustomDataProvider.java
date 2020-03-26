package Data;

import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CustomDataProvider {
    @DataProvider(name = "LoginDataProvider")
    public Object[][] getData() throws IOException {

        Properties props = new Properties();
        FileInputStream file = new FileInputStream("src\\datadriver.properties");
        props.load(file);

        Object[][] data = {{props.getProperty("username"), props.getProperty("password")}};
        return data;
    }
}
