package modules.homepagecontainermethods;

import modules.UseFullMethods;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.homepage.HomePageSelectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageContainerMethods extends HomePageSelectors {
    protected UseFullMethods useFullMethods;
    private final List<String> nameOfProducts = new ArrayList<>(Arrays.asList("Scanner De Codes-Barres Laser 1D",
            "Music Pioneer Kit Combiné 2-en-1 T900Ultra2&AirPro2S Smartwatch+Casque Blue",
            "normalize-space()='Chaussures Décontractées Pour Hommes",
            "EAGEAT LED 2.4G Rechargeable Sans Fil Souris Bluetooth 2 Modes",
            "Mode Digital Lave Montres Fer Métal Rouge LED Hommes - Noir",
            "ACE T-shirt Décontracté Pour Hommes, Col Rond, Manches Courtes, En Coton"));

    public HomePageContainerMethods(String browserName) {
        super(browserName);
        this.browserName = browserName;
        this.useFullMethods = new UseFullMethods(this.driver);
    }

    public void checkTitlePage() {
       try {
           this.useFullMethods.click(closePopup);
           Assert.assertEquals(this.useFullMethods.getText(aElement("Vendez sur Jumia")),
                   "Vendez sur Jumia",
                   "Title is not found");
           Assert.assertEquals(this.useFullMethods.getText(buttonElement("Rechercher")),
                   "Rechercher", "Title of table is not found");
       } catch (Exception e) {
           System.out.println("Title is not found");
       }
    }

    public void searchRandomProduct() {
        //Vérifier la présence des éléments sur la page d'accueil
//        checkTitlePage();
        //Elément de recherche
        String nameProduct = nameOfProducts
                .get(UseFullMethods.faker.random().nextInt(0, (nameOfProducts.size() - 1)));
        this.useFullMethods.clearAndType(inputElement("Rechercher"),
                nameProduct);
        this.useFullMethods.click(buttonElement("Rechercher"));
    }
}
