package searchproductcontainer;

import actions.homepagecontaineractions.HomePageActions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.IConstantValueOfTestCase;

public class SearchProductContainerTest {
    private HomePageActions homePageActions;
    @BeforeClass
    public void start() {
        // Charger l'environnement TEST_SPACE
        this.homePageActions = new HomePageActions(IConstantValueOfTestCase.CHROME_BROWSER);
        this.homePageActions.load(IConstantValueOfTestCase.TEST_SPACE, IConstantValueOfTestCase.ENV);
    }

    @Test
    public void searchProductTest(){
        try {
            homePageActions.searchRandomProduct();
        } catch (Exception e) {
            Assert.fail("Erreur : lors de la recherche du produit");
        }
    }

    @AfterClass
    public void end() {
        this.homePageActions.waitBeforeQuit();
    }
}
