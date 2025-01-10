package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;

public class ScreenshotPage {

    public static final String nameFileScreenshot = System.getProperty("user.dir") + "/resources/screenshots/";
    // private static final Logger logger =
    // LogManager.getLogger(ScreenshotPage.class);

    public static void takeScreenshotForSuccefull(WebDriver driver, ITestResult testResult) {
        Date date = new Date();

        // Format the date as "yyyy-MM-dd_HH-mm-ss"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = formatter.format(date);
        if (ITestResult.SUCCESS == testResult.getStatus()) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(nameFileScreenshot + testResult.getName() + "-" + formattedDate + ".png");

            try {
                FileHandler.copy(source, destination);
            } catch (IOException ioException) {
                // logger.debug(ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

    public static void takeScreenshotForFailures(WebDriver driver, ITestResult testResult) {
        Date date = new Date();

        // Format the date as "yyyy-MM-dd"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = formatter.format(date);

        if (ITestResult.FAILURE == testResult.getStatus()) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(nameFileScreenshot + testResult.getName() + "-" + formattedDate + ".png");

            try {
                FileHandler.copy(source, destination);
            } catch (IOException ioException) {
                // logger.debug(ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }
}