package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter; 
import com.aventstack.extentreports.reporter.configuration.ViewName;
 
public class ExtentSparkReportConfig {
 
    public static ExtentReports extentSparkConfiguration() {
        try {
            File jsonFileCinfig = new File(System.getProperty("user.dir") + "/src/main/java/files/configs/spark-config.json");
            String uriOutReporter = System.getProperty("user.dir") + "/resources/reporting/TestReport.html";
            ExtentReports extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(uriOutReporter).viewConfigurer()
                    .viewOrder()
                    .as(new ViewName[] {
                            ViewName.DASHBOARD,
                            ViewName.TEST,
                            ViewName.AUTHOR,
                            ViewName.DEVICE,
                            ViewName.EXCEPTION,
                            ViewName.LOG
                    })
                    .apply();
 
            spark.loadJSONConfig(jsonFileCinfig);
            extent.attachReporter(spark);
            return extent;
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            System.out.println("Not able to load config file " + f.getMessage());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
 