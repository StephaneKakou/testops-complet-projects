package actions.homepagecontaineractions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import modules.UseFullMethods;
import modules.homepagecontainermethods.HomePageContainerMethods;
import utils.DelayClass;
import utils.ExtentSparkReportConfig;
import utils.IConstantValueOfTestCase;

public class HomePageActions extends HomePageContainerMethods {
    protected HomePageContainerMethods homePageContainerMethods;
    protected ExtentReports extent;

    public HomePageActions(String browserName){
        super(browserName);
        this.homePageContainerMethods = new HomePageContainerMethods(IConstantValueOfTestCase.CHROME_BROWSER);
        this.extent = ExtentSparkReportConfig.extentSparkConfiguration();
    }
    /**
     * Cette méthode permet de rechercher un produit via la barre de recherche des produits
     * la méthode searchAnyProduct().
     * En cas de succès, un test est enregistré avec le statut PASS,
     * sinon, en cas d'exception, un test est enregistré avec le statut FAIL.
     */
    public void searchAnyProduct() {
        try {
            Thread.sleep(DelayClass.TIMEOUT_LOADER_SLEEP); // Petite pause pour s'assurer que l'interface est
            this.searchRandomProduct();
            this.extent.createTest("JM-TUI01: Rechercher un produit")
                    .log(Status.PASS, "Recherche de produit effectué avec succès");
            this.extent.flush();
        } catch (Exception e) {
            // Capture de l'exception et enregistrement de l'échec dans le rapport
            this.extent.createTest("JM-TUI01: Rechercher un produit")
                    .log(Status.FAIL, "Erreur rencontrée lors de la Recherche de produit" + e.getMessage());
            UseFullMethods.logError("Erreur rencontrée lors de la Recherche de produit", e);
        } finally {
            // Vidage des rapports pour sauvegarder les résultats
            this.extent.flush();
        }
    }

}
