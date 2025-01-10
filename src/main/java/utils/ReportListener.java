package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class ReportListener implements ITestListener {

    @Override
    public void onTestSuccess( ITestResult result) {
        System.out.println("Test réussi : " + result.getName());
    }

    @Override
    public void onTestFailure( ITestResult result) {
        System.err.println("Test échoué : " + result.getName());
        System.err.println("Test échoué : " + result.getThrowable());
        // Ici, vous pourriez ajouter du code pour capturer une capture d'écran
    }

    @Override
    public void onTestSkipped( ITestResult result) {
        System.out.println("Test ignoré : " + result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        System.err.println("Test échoué avec timout : " + result);
    }

    @Override
    public void onStart( ITestContext context) {

        System.out.println("*************************************************");
        System.out.println("*****************STARTING OF TEST ON BROKER SPACE ****************");
        System.out.println("*************************************************");
        System.out.println("Début des tests : " + context.getName());
    }

    @Override
    public void onFinish( ITestContext context) {
        System.out.println("*************************************************");
        System.out.println("********************END OF TEST ON BROKER SPACE******************");
        System.out.println("*************************************************");
        System.out.println("Fin des tests : " + context.getName());
    }

}

