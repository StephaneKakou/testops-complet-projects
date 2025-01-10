package pages.homepage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.BrowserFactory;

public class HomePageSelectors  extends BrowserFactory {

    public HomePageSelectors(String browserName) {
        super(browserName);
        PageFactory.initElements(driver, this);
    }
    protected By h1Element(String value) {
        return By.xpath("//h1[normalize-space()='" + value + "']");
    }

    protected By titleOfModalBroker = By.xpath("//h1[normalize-space()='Bravo ! Vous avez rejoint BIG BROKER !']");
    protected By codeManagementLinkBroker = By.xpath("//a[normalize-space()='Gestion de mes codes']");

    protected By h2Element(String value) {
        return By.xpath("//h2[normalize-space()='" + value + "']");
    }
    protected By labelElement(String value) {
        return By.xpath("//label[normalize-space()='" + value + "']");
    }
    protected By spanElement(String value) {
        return By.xpath("//span[normalize-space()='" + value + "']");
    }

    protected By aElement(String value) {
        return By.xpath("//a[normalize-space()='" + value + "']");
    }

    protected By aHrefElement(String value) {
        return By.xpath("//a[@href='" + value + "']");
    }

    protected By buttonElement(String value) {
        return By.xpath("//button[normalize-space()='" + value + "']");
    }

    protected By divElement(String value){
        return By.xpath("//div[normalize-space()='"+value+"']");
    }

    protected By pElement(String value){
        return By.xpath("//p[normalize-space()='"+value+"']");
    }
    protected By inputElement(String value){
        return By.xpath("//input[@aria-label='"+value+"']");
    }
}
