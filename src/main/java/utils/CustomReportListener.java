package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import modules.UseFullMethods;

public class CustomReportListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        UseFullMethods.log.info("Test réussi : " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        UseFullMethods.log.error("Test échoué : " + result.getName());
        UseFullMethods.log.error("Test échoué : " + result.getThrowable());
        // Ici, vous pourriez ajouter du code pour capturer une capture d'écran
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        UseFullMethods.log.warn("Test ignoré : " + result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        UseFullMethods.log.error("Test échoué avec timout : " + result);
    }

    @Override
    public void onStart(ITestContext context) {
        UseFullMethods.log.info("*************************************************");
        UseFullMethods.log.info("*****************STARTING OF TEST****************");
        UseFullMethods.log.info("*************************************************");
        UseFullMethods.log.info("Début des tests : " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        UseFullMethods.log.info("*************************************************");
        UseFullMethods.log.info("********************END OF TEST******************");
        UseFullMethods.log.info("*************************************************");
        UseFullMethods.log.info("Fin des tests : " + context.getName());
    }
    // Implémentez d'autres méthodes selon vos besoins
}
