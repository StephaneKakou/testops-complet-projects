package utils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyZer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            return true;
        }
        throw new RuntimeException("Test arrêté après 3 tentatives");
        // return false;
    }

    public void reset() {
        retryCount = 0;
    }
}
