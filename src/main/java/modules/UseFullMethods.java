package modules;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.io.IOException;
import java.lang.StringBuilder;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;

import utils.DelayClass;
import utils.IInsuranceRegime;
import utils.WriteReadJsonFile;

public class UseFullMethods {

    private final WebDriver driver;
    @SuppressWarnings({ "", "deprecation" })
    public static Faker faker = new Faker(new Locale("fr"));
    public static Logger log = LogManager.getLogger(UseFullMethods.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Map<String, String> SPECIAL_CHAR_MAP = new HashMap<>();
    private static JavascriptExecutor js;

    static {
        SPECIAL_CHAR_MAP.put("é", "e");
        SPECIAL_CHAR_MAP.put("è", "e");
        SPECIAL_CHAR_MAP.put("-", "");
        SPECIAL_CHAR_MAP.put("/", " ");
        SPECIAL_CHAR_MAP.put("?", "");
        SPECIAL_CHAR_MAP.put("_", "");
        SPECIAL_CHAR_MAP.put("ë", "e");
        SPECIAL_CHAR_MAP.put("ï", "i");
        SPECIAL_CHAR_MAP.put("î", "i");
        SPECIAL_CHAR_MAP.put("ù", "u");
        SPECIAL_CHAR_MAP.put("à", "a");
        SPECIAL_CHAR_MAP.put("ê", "e");
        SPECIAL_CHAR_MAP.put("â", "a");
        SPECIAL_CHAR_MAP.put("ü", "u");
        SPECIAL_CHAR_MAP.put("ç", "c");
        SPECIAL_CHAR_MAP.put("@yahoo.fr", "@gmail.com");
        // Ajoutez ici d'autres caractères spéciaux si nécessaire
    }
    // public static Date dateNow = new Date();
    // Get the current date
    public static LocalDate dateNow = LocalDate.now();
    public static List<String> pathFile = new ArrayList<>(Arrays.asList(
            System.getProperty("user.dir") + "/src/main/resources/Devoir_de_conseil.pdf",
            System.getProperty("user.dir") + "/src/main/resources/Fiche_client.pdf"));
    public List<String> productsAssociate = new ArrayList<String>();
    private static final int DEFAULT_TIMEOUT = 40000; // Default timeout value in seconds
    private static final int TIMEOUT_IN_SECONDS = 5000;
    private static final int TIMEOUT_IN_MILLISECONDS = 5000;
    private static final int TIMEOUT_IN_SECONDS_WAIT = 5000;
    private static final int WAIT_TIMEOUT = 500; // Temps d'attente maximal en secondes

    /**
     * @param driver
     */
    public UseFullMethods(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Fills a field with the specified value.
     *
     * @param selector CSS selector of the field
     * @param value    Value to input into the field
     */
    public void fillField(String selector, String value) {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(DelayClass.PAGE_LOAD_TIMEOUT));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Clicks an element identified by the specified selector.
     *
     * @param selectorType  Type of selector (e.g., "xpath" or "css")
     * @param selectorValue Value of the selector
     */
    public void clickElementWithSelector(String selectorType, String selectorValue) {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(DelayClass.PAGE_LOAD_TIMEOUT));
        WebElement element;

        if (selectorType.equalsIgnoreCase("xpath")) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selectorValue)));
        } else if (selectorType.equalsIgnoreCase("css")) {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selectorValue)));
        } else {
            throw new IllegalArgumentException("Invalid selector type. Use 'xpath' or 'css'.");
        }

        element.click();
    }

    /**
     * Waits for the specified WebElement to be visible on the page within the given
     * timeout.
     *
     * @param element          The WebElement to wait for.
     * @param timeoutInSeconds The maximum time to wait in seconds.
     */
    public void waitForElement(By element, int timeoutInSeconds) {
        // Create a new WebDriverWait instance with the specified timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        // Wait until the element is visible on the page
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    /**
     * Waits for the specified WebElement to be visible on the page using a default
     * timeout.
     *
     * @param element The WebElement to wait for.
     */
    public void waitForElement(By element) {
        // Call the overloaded method with the default timeout value
        waitForElement(element, DEFAULT_TIMEOUT);
    }

    /**
     * Waits for the specified WebElement to be visible on the page within the given
     * timeout.
     *
     * @param element          The WebElement to wait for.
     * @param timeoutInSeconds The maximum time to wait in seconds.
     */
    public void waitForElementWithFactory(WebElement element, int timeoutInSeconds) {
        // Create a new WebDriverWait instance with the specified timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        // Wait until the element is visible on the page
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for the specified WebElement to be visible on the page using a default
     * timeout.
     *
     * @param element The WebElement to wait for.
     */
    public void waitForElementWithFactory(WebElement element) {
        // Call the overloaded method with the default timeout value
        waitForElementWithFactory(element, DEFAULT_TIMEOUT);
    }

    /**
     * Finds an element using the specified locator.
     *
     * @param locator By locator of the element
     * @return WebElement found
     */
    public WebElement find(By locator) {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void chainMultipleExpectedConditions(By locator, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(locator)));
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    public void clickWithJavaScript(By element){
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", this.find(element));
    }
    /**
     * Finds a list of elements using the specified locator.
     *
     * @param locator By locator of the elements
     * @return List of WebElements found
     */
    public List<WebElement> findList(By locator) {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Selects multiple elements from a list, excluding "Conjoint" and "Enfants".
     *
     * @param locator        By locator of the elements
     * @param numberSelected Number of elements to select
     */
    public void multipleSelectionElements(By locator, int numberSelected) {
        this.waitPresentElement(locator);
        List<WebElement> elements = this.findList(locator);
        for (int i = 0; i < numberSelected; i++) {
            if (elements.get(i).getText() == "Conjoint" || elements.get(i).getText() == "Enfants") {
                continue;
            } else {
                // this.productsAssociate.add(elements.get(i).);
                this.clickWithFactory(elements.get(i));
            }
        }
    }

    public void selectElementInInputSelectField(By element, By selectedElement, String name) {
        int indexElement = 0;
        int numberDepartmentSelected = 0;
        if (name.equals("")) {
            this.click(element);
            indexElement = this.findList(selectedElement).size();
            numberDepartmentSelected = UseFullMethods.faker.number().numberBetween(1, indexElement);
            this.multipleSelectionElements(selectedElement, numberDepartmentSelected);

        } else {
            this.click(element);
            indexElement = this.findList(selectedElement).size();
            this.multipleSelectionElements(selectedElement, indexElement);
        }
    }

    /**
     * Inputs random values into a list of elements located by the specified
     * locator.
     *
     * @param locator By locator of the elements
     */
    public void inputElements(By locator, int interval) {
        waitPresentElement(locator);
        List<WebElement> elements = findList(locator);
        for (WebElement elt : elements) {
            int price = UseFullMethods.faker.number().numberBetween(1, interval);
            clearAndTypeWithFactory(elt, String.valueOf(price));
        }
    }

    /**
     * Inputs file paths into a list of elements located by the specified locator.
     *
     * @param locator By locator of the elements
     */
    public void inputElementsFile(By locator) {
        this.waitPresentElement(locator);
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(5));
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        for (WebElement elt : elements) {
            changeStyleByJs(driver, elt);
            if (elt.isDisplayed()) {
                elt.clear();
                elt.sendKeys(pathFile.get(faker.number().numberBetween(0, pathFile.size())));
                waitForElementWithFactory(elt);
                hiddenStyleByJs(driver, elt);
            } else {
                log.warn(elt + " n'est pas visible");
            }
        }
    }

    /**
     * Clicks an element located by the specified locator if it is enabled and
     * displayed.
     *
     * @param locator By locator of the element
     */
    public void click(By locator) {
        if (isEnabledElement(locator) && isDisplayedElement(locator)) {
            find(locator).click();
        } else {
            log.warn("This button isn't enabled or displayed: " + locator);
        }
    }

    /**
     * Clicks an element located by the specified locator using FluentWait if it is
     * enabled and displayed.
     *
     * @param locator By locator of the element
     */
    public void clickWithLoader(By locator) {
        if (isEnabledFluentWaitBy(locator) && isDisplayFluentWaitBy(locator)) {
            find(locator).click();
        } else {
            log.warn("This button isn't enabled or displayed: " + locator);
        }
    }

    public void clickNextOfDocument(WebElement element) {
        waitForElementWithFactory(element);
        // scrollToElement(this.driver, element);
        waitForElementWithFactory(element);
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Clicks a given WebElement if it is displayed and enabled.
     *
     * @param field WebElement to click
     */
    public void clickWithFactory(WebElement field) {
        if (isDisplayedEltWithFactory(field) && isEnabledEltWithFactory(field)) {
            field.click();
        } else {
            log.warn("This button isn't enabled or displayed: " + field);
        }
    }

    /**
     * Selects a specific date from a list of elements located by the specified
     * locator.
     *
     * @param locator By locator of the elements
     * @param day     Day to select
     */
    public void selectDate(By locator, int day) {
        waitPresentElement(locator);
        List<WebElement> elements = findList(locator);
        for (WebElement elt : elements) {
            if (Integer.parseInt(elt.getText()) == day) {
                clickWithFactory(elt);
                break;
            }
        }
    }

    public void fillingInputOfSelectedElements(By selectedElement, By level) {
        // Récupère une liste d'éléments Web correspondant au sélecteur
        // 'selectedElement'
        List<WebElement> elements = this.findList(selectedElement);

        // Parcourt chaque élément Web dans la liste récupérée
        for (WebElement webElement : elements) {
            // Effectue un clic sur l'élément Web en utilisant la méthode 'clickWithFactory'
            this.clickWithFactory(webElement);

            // Définit un délai d'attente implicite pour le driver (gestionnaire de
            // navigateur)
            this.driver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(DelayClass.TIMEOUT_SLEEP));

            // Remplit les éléments Web correspondant au sélecteur 'level' avec la valeur
            // 300
            this.inputElements(level, 300);
        }
    }

    /**
     * Waits until the element is present in the DOM.
     *
     * @param locator By locator of the element
     */
    public void waitPresentElement(By locator) {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Gets the text of an element located by the specified locator.
     *
     * @param locator By locator of the element
     * @return Text of the element
     */
    public String getText(By locator) {
        return find(locator).getText();
    }

    /**
     * Gets the text of a given WebElement.
     *
     * @param field WebElement to get the text from
     * @return Text of the WebElement
     */
    public String getTextWithFactory(WebElement field) {
        if (isDisplayedEltWithFactory(field)) {
            return field.getText();
        } else {
            log.warn("This element isn't displayed: " + field);
            return "";
        }
    }

    /**
     * Clears the input field located by the specified locator and types the given
     * text.
     *
     * @param locator By locator of the input field
     * @param text    Text to input
     */
    public void clearAndType(By locator, String text) {
        if (isDisplayedElement(locator)) {
            WebElement element = find(locator);
            element.clear();
            element.sendKeys(text);
            element.sendKeys(Keys.ENTER);
        } else {
            log.warn("This element isn't displayed: " + locator);
        }
    }

    /**
     * Clears the input field located by the specified locator and types the given
     * text.
     *
     * @param locator By locator of the input field
     * @param text    Text to input
     */
    public void clearAndTypeWithouthEnter(By locator, String text) {
        if (isDisplayedElement(locator)) {
            WebElement element = find(locator);
            element.clear();
            element.sendKeys(text);
        } else {
            log.warn("This element isn't displayed: " + locator);
        }
    }

    /**
     * Clears the input field located by the specified locator and types the given
     * text.
     *
     * @param locator By locator of the input field
     * @param text    Text to input
     */
    public void clearAndTypeOfDate(By locator, String text) {
        if (isDisplayedElement(locator)) {
            WebElement element = find(locator);
            element.clear();
            element.click();
            element.sendKeys(text);
            element.sendKeys(Keys.ENTER);
        } else {
            log.warn("This element isn't displayed: " + locator);
        }
    }

    /**
     * Clears the given input field and types the given text.
     *
     * @param field WebElement of the input field
     * @param text  Text to input
     */
    public void clearAndTypeWithFactory(WebElement field, String text) {
        if (isDisplayedEltWithFactory(field)) {
            field.clear();
            field.sendKeys(text);
            field.sendKeys(Keys.ENTER);
        } else {
            log.warn("This element isn't displayed: " + field);
        }
    }

    /**
     * Clears the given input field and types the given text.
     *
     * @param field WebElement of the input field
     * @param text  Text to input
     */
    public void clearAndTypeWithFactoryWithoutEnter(WebElement field, String text) {
        if (isDisplayedEltWithFactory(field)) {
            field.clear();
            field.sendKeys(text);
        } else {
            log.warn("This element isn't displayed: " + field);
        }
    }


    /**
     * Checks if an element located by the specified locator is enabled.
     *
     * @param locator By locator of the element
     * @return true if the element is enabled, false otherwise
     */
    public boolean isEnabledElement(By locator) {
        Wait<WebDriver> wait = new WebDriverWait(this.driver, Duration.ofMillis(TIMEOUT_IN_MILLISECONDS));
        return wait.until(driver -> find(locator).isEnabled());
    }

    /**
     * Checks if an element located by the specified locator is displayed.
     *
     * @param locator By locator of the element
     * @return true if the element is displayed, false otherwise
     */
    public boolean isDisplayedElement(By locator) {
        Wait<WebDriver> wait = new WebDriverWait(this.driver, Duration.ofMillis(TIMEOUT_IN_MILLISECONDS));
        return wait.until(driver -> find(locator).isDisplayed());
    }

    /**
     * Checks if an element located by the specified locator is selected.
     *
     * @param locator By locator of the element
     * @return true if the element is selected, false otherwise
     */
    public boolean isSelectedElement(By locator) {
        Wait<WebDriver> wait = new WebDriverWait(this.driver, Duration.ofMillis(TIMEOUT_IN_MILLISECONDS));
        return wait.until(driver -> find(locator).isSelected());
    }

    /**
     * Checks if a given WebElement is enabled.
     *
     * @param elt WebElement to check
     * @return true if the element is enabled, false otherwise
     */
    public boolean isEnabledEltWithFactory(WebElement elt) {
        Wait<WebDriver> wait = new WebDriverWait(this.driver, Duration.ofMillis(TIMEOUT_IN_MILLISECONDS));
        return wait.until(driver -> elt.isEnabled());
    }

    /**
     * Checks if a given WebElement is displayed.
     *
     * @param elt WebElement to check
     * @return true if the element is displayed, false otherwise
     */
    public boolean isDisplayedEltWithFactory(WebElement elt) {
        Wait<WebDriver> wait = new WebDriverWait(this.driver, Duration.ofMillis(TIMEOUT_IN_MILLISECONDS));
        return wait.until(driver -> elt.isDisplayed());
    }

    /**
     * Checks if a given WebElement is selected.
     *
     * @param elt WebElement to check
     * @return true if the element is selected, false otherwise
     */
    public boolean isSelectedEltWithFactory(WebElement elt) {
        Wait<WebDriver> wait = new WebDriverWait(this.driver, Duration.ofMillis(TIMEOUT_IN_MILLISECONDS));
        return wait.until(driver -> elt.isSelected());
    }

    public boolean isDisplayFluentWait(WebElement elt) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS_WAIT))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(ElementNotInteractableException.class);
        return wait.until(d -> elt.isDisplayed()).booleanValue();
    }

    public boolean isDisplayFluentWaitBy(By elt) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS_WAIT))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(ElementNotInteractableException.class);
        return wait.until(d -> this.find(elt).isDisplayed()).booleanValue();
    }

    public boolean isEnabledFluentWaitBy(By elt) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS_WAIT))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(ElementNotInteractableException.class);
            return wait.until(d -> this.find(elt).isDisplayed() && this.find(elt).isEnabled()).booleanValue();
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            // Logs the error in case of an exception and returns false.
            return false;
        }
    }

    public boolean waitElementButtonVisibled(By element, Integer timeSeconde) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeSeconde))
                .pollingEvery(Duration.ofMillis(1000))
                .ignoring(ElementNotInteractableException.class);
        return wait.until(b -> {
            if (this.find(element).isEnabled()) {
                this.find(element).click();
                return true;
            } else {
                return false;
            }
        });
    }

    public WebElement waitElementPresent(By element, Integer timeSeconde) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeSeconde))
                .pollingEvery(Duration.ofMillis(1000))
                .ignoring(ElementNotInteractableException.class);
        return wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public WebElement waitElementVisibled(By element, Integer timeSeconde) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeSeconde))
                .pollingEvery(Duration.ofMillis(1000))
                .ignoring(ElementNotInteractableException.class);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    /**
     * Method for activating product
     *
     * @param ligne
     * @param activateButtonLocator
     * @param product
     * @param status
     * @param risk
     */
    public void actionOfProductService(By ligne, By activateButtonLocator, String product, String status,
                                       String risk, String actionName) {
        List<WebElement> rows = findList(ligne);
        Optional<WebElement> matchingRow = rows.stream()
                .filter(
                        row -> row.getText().contains(product) &&
                                row.getText().contains(status) &&
                                row.getText().contains(risk))
                .findFirst();

        if (matchingRow.isPresent()) {
            WebElement rowElement = matchingRow.get();
            int rowIndex = rows.indexOf(rowElement);
            log.info("Row found product '" + product + "': " + rowElement.getText());

            List<WebElement> validButtons = rowElement.findElements(activateButtonLocator).stream()
                    .filter(btn -> actionName.equals(btn.getText()) && btn.isEnabled() && btn.isDisplayed())
                    .collect(Collectors.toList());

            if (validButtons.isEmpty()) {
                log.warn("No button found matching the action name: " + actionName);
            } else {
                WebElement actionButton = validButtons.get(rowIndex); // Always get the first valid button
                log.info("Performing action: " + actionButton.getText());
                actionButton.click();
            }
        } else {
            log.warn("No row found matching product: " + product);
        }
    }

    /**
     * Method for activating product
     *
     * @param tableRowLocator
     * @param uniqueElement
     * @param actionName
     */
    public void actionInTableOfCollaborator(By tableRowLocator, String uniqueElement, By buttonLocator,
                                            String actionName, By paginationButton, By pageLocator) {
        try {
            List<WebElement> pages = findList(pageLocator);
            String paginationText = pages.get(2).getText();
            int totalPages = Integer.parseInt(paginationText.split("/")[1].trim());

            boolean isCollaboratorFound = false;

            for (int i = 1; i <= totalPages; i++) {
                List<WebElement> rows = findList(tableRowLocator);

                // Recherche de la ligne contenant l'élément unique
                Optional<WebElement> matchingRow = rows.stream()
                        .filter(row -> row.getText().contains(uniqueElement))
                        .findAny();
                if (matchingRow.isPresent()) {
                    // log.info("Ligne correspondante trouvée " + matchingRow.get().getText());
                    // log.info("Ligne correspondante trouvée sur la page " + i);
                    clickActionBtnInTable(rows, matchingRow.get(), buttonLocator, actionName);
                    isCollaboratorFound = true;
                    break; // Sortir de la boucle si l'élément est trouvé
                } else {
                    // log.info("Le collaborateur introuvable sur la page " + i + ". Passer à la
                    // page suivante.");
                    clickNextPage(paginationButton, i, totalPages);
                }
            }

            if (!isCollaboratorFound) {
                log.warn(String.format("Le collaborateur avec l'adresse e-mail %s introuvable sur aucune page.",
                        uniqueElement));
            }

        } catch (NoSuchElementException noEl) {
            log.error(String.format("Element intouvable :%s", noEl.getMessage()));
        } catch (ElementClickInterceptedException el) {
            log.error(String.format("Impossible de cliquer sur l'élément %s: %s", actionName, el.getMessage()));
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la tentative de recherche du collaborateur: " + e.getMessage(),
                    e);
        }
    }

    private void clickActionBtnInTable(List<WebElement> rows, WebElement rowElement, By buttonLocator,
                                       String actionName) {
        try {
            int rowIndex = rows.indexOf(rowElement);
            List<WebElement> validButtons = rowElement.findElements(buttonLocator).stream()
                    .filter(btn -> actionName.equals(btn.getText()) && btn.isEnabled() && btn.isDisplayed())
                    .collect(Collectors.toList());
            if (validButtons.isEmpty()) {
                log.warn("Aucun bouton trouvé avec le nom de l'action: " + actionName);
            } else {
                WebElement actionButton = validButtons.get(rowIndex); // Toujours obtenir le premier bouton valide
                // log.info("Execution d'une action: " + actionButton.getText());
                actionButton.click();
            }
        } catch (Exception e) {
            log.error("Échec du clic sur le bouton d'action: " + e.getMessage(), e);
        }
    }

    private void clickNextPage(By paginationButton, int currentPage, int totalPages) {
        try {
            WebElement nextPageButton = find(paginationButton);

            if (nextPageButton.isEnabled() && currentPage < totalPages) {
                nextPageButton.click();
                Thread.sleep(1000); // Attendre que la page suivante charge
                // log.info("Navigation vers la page " + (currentPage + 1));
            } else {
                // log.info("Plus de pages disponibles ou déjà sur la dernière page.");
            }
        } catch (InterruptedException e) {
            log.error("Le fil de discussion a été interrompu en attendant le chargement de la page suivante.", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Échec de la navigation vers la page suivante: " + e.getMessage(), e);
        }
    }

    public String getValueOfDisabledElement(By locator) {
        WebElement element = this.find(locator);
        String value = element.getAttribute("value");
        return value;
    }

    /**
     * Method for checking if the product is activated
     *
     * @param rowLocator
     * @param activateButtonLocator
     * @param product
     * @param type
     * @param risk
     * @param expectedActiontText
     * @param confirmedBtn
     */
    public void actionOfProductInProduction(By rowLocator, By activateButtonLocator, String product, String risk,
                                            By paginationButton, By pageLocator, String type, String actionName, By expectedActiontText,
                                            By confirmedBtn) {
        try {
            List<WebElement> pages = findList(pageLocator);
            String textPage = pages.get(2).getText();
            int currentPage = Integer.parseInt(textPage.split("/")[0].trim());
            int totalPages = Integer.parseInt(textPage.split("/")[1].trim());

            boolean isProductFound = false;
            for (int i = currentPage; i <= totalPages; i++) {
                List<WebElement> rows = findList(rowLocator);
                Optional<WebElement> matchingRow = findMatchingRow(rows, product, risk);

                // log.info("Ligne trouvée : " + matchingRow);
                if (matchingRow.isPresent()) {
                    // log.info("Produit trouvé: '" + product + "' dans la ligne: " +
                    // matchingRow.get().getText());
                    isProductFound = true;

                    WebElement rowElement = matchingRow.get();
                    int rowIndex = rows.indexOf(rowElement);
                    List<WebElement> validButtons = findValidButtons(rowElement, activateButtonLocator, actionName);

                    if (validButtons.isEmpty()) {
                        log.warn("Aucun bouton valide trouvé pour l'action: " + actionName);
                        break;
                    }

                    WebElement actionButton = validButtons.get(rowIndex);

                    if (type.equals("view")) {
                        if (!actionButton.isEnabled()) {
                            // log.info("Ce produit est déjà actif.");
                            break;
                        } else {
                            // log.info("Ce produit est inactif.");
                            break;
                        }
                    } else if (type.equals("action")) {
                        log.info("Exécution de l'action: " + actionButton.getText());
                        if (actionButton.isEnabled()) {
                            if (actionName.equals("Activer produit") || actionName.equals("Desactiver produit")) {
                                // Détermine le texte en fonction de l'action (activation ou désactivation)
                                // String actionText = actionName.equals("Activer produit") ? "Activation du
                                // produit..."
                                // : "Désactivation du produit...";
                                // log.info(actionText);
                                clickWithFactory(actionButton);
                                actionConfirmedInModal(expectedActiontText, confirmedBtn);
                                break;
                            } else {
                                // log.info("Exécution de l'action personnalisée: " + actionName);
                                clickWithFactory(actionButton);
                                break;
                            }
                        } else {
                            log.warn(String.format("Cette action sur ce produit est inactive."));
                            break;
                        }
                    }
                } else if (i < totalPages) {
                    // Pagination logic
                    clickNextPage(paginationButton);
                }
            }

            logResult(isProductFound);
        } catch (NoSuchElementException noEl) {
            log.error(String.format("Element intouvable :%s", noEl.getMessage()));
        } catch (ElementClickInterceptedException el) {
            log.error(String.format("Impossible de cliquer sur l'élément %s: %s", actionName, el.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Une erreur s'est produite lors de l'appel à l'action %s : %s", actionName,
                    e.getMessage()));
        }
    }
    public void scrollElementAndGoToNextPage(By paginationButton) {
        scrollToElement(find(paginationButton));
        clickNextPage(paginationButton);
    }

    public void findActionInTable(By rowLocator, By actionButtonLocator, String productName, String typeName,
                                  By paginationButton, By pageLocator, String actionName) {
        try {
            List<WebElement> pages = findList(pageLocator);
            String textPage = pages.get(2).getText();
            int currentPage = Integer.parseInt(textPage.split("/")[0].trim());
            int totalPages = Integer.parseInt(textPage.split("/")[1].trim());

            UseFullMethods.log.info("Nombre de page : " + totalPages);
            boolean isProductFound = false;
            for (int i = currentPage; i <= totalPages; i++) {
                List<WebElement> rows = findList(rowLocator);
                Optional<WebElement> matchingRow = findMatchingRow(rows, productName, typeName);

                // log.info("Ligne trouvée : " + matchingRow);
                if (matchingRow.isPresent()) {
                    // log.info("Produit trouvé: '" + product + "' dans la ligne: " +
                    // matchingRow.get().getText());
                    isProductFound = true;

                    WebElement rowElement = matchingRow.get();
                    List<WebElement> validButtons = findValidButtons(rowElement, actionButtonLocator, actionName);

                    if (validButtons.isEmpty()) {
                        log.warn("Aucun bouton valide trouvé pour l'action: " + actionName);
                        break;
                    }

                    int rowIndex = rows.indexOf(rowElement);
                    WebElement actionButton = validButtons.get(rowIndex);

                    log.info("Exécution de l'action: " + actionButton.getText());
                    if (actionButton.isEnabled()) {
                        clickWithFactory(actionButton);
                        log.warn(String.format("Ligne : %s", rowElement.getText()));
                        break;
                    } else {
                        log.warn(String.format("Cette action sur ce produit est inactive."));
                        break;
                    }
                } else if (i < totalPages) {
                    scrollToElement(find(paginationButton)); // Défilement de la page
                    clickNextPage(paginationButton);
                }
            }

            logResult(isProductFound);
        } catch (NoSuchElementException noEl) {
            log.error(String.format("Element intouvable :%s", noEl.getMessage()));
        } catch (ElementClickInterceptedException el) {
            log.error(String.format("Impossible de cliquer sur l'élément %s: %s", actionName, el.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Une erreur s'est produite lors de l'appel à l'action %s : %s", actionName,
                    e.getMessage()));
        }
    }

    public void checkDataInTable(By rowLocator, String section) {
        try {
            List<WebElement> rows = findList(rowLocator);
            // log.info("Ligne trouvée : " + matchingRow);
            if (rows != null && rows.size() > 0) {
                log.info(section + ": Les données sont présentes dans le tableau.");
            } else {
                log.warn(section + ": Aucune données trouvées dans le tableau");
            }
        } catch (NoSuchElementException e) {
            e.getStackTrace();
            logError("Element intouvable ", e);
        } catch (Exception e) {
            e.getStackTrace();
            logError("Une erreur s'est produite ", e);
        }
    }

    public void scrollToElement(WebElement element) {
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    private Optional<WebElement> findMatchingRow(List<WebElement> rows, String productName, String name) {
        return rows.stream()
                .filter(row -> row.getText().contains(productName) && row.getText().contains(name))
                .findFirst();
    }

    private List<WebElement> findMatchingRows(List<WebElement> rows, String argOne, String argTwo, String argTree) {
        return rows.stream()
                .filter(row -> row.getText().contains(argOne) && row.getText().contains(argTwo)
                        && row.getText().contains(argTree))
                .collect(Collectors.toList());
    }

    private List<WebElement> findValidButtons(WebElement rowElement, By buttonLocator, String actionName) {
        return rowElement.findElements(buttonLocator).stream()
                .filter(btn -> btn.getText().equals(actionName) && btn.isDisplayed())
                .collect(Collectors.toList());
    }

    private void clickNextPage(By paginationButton) {
        WebElement nextPageButton = find(paginationButton);
        if (nextPageButton.isEnabled()) {
            clickWithFactory(nextPageButton);
        } else {
            log.warn("Le bouton de pagination n'est pas activé.");
        }
    }

    private void logResult(boolean isProductFound) {
        if (!isProductFound) {
            log.warn("Ce produit n'existe pas dans cet environnement.");
            return;
        }
    }

    /**
     * Method of validation some product
     *
     * @param ligne
     * @param activateButtonLocator
     * @param productName
     * @param domain
     * @param subDomain
     * @param createdDate
     * @param textBtn
     */
    public boolean clickCheckButtonToSpaceGest(By ligne, By activateButtonLocator, String productName, String domain,
                                               String subDomain, String createdDate, String textBtn, Integer timeSleep) {
        try {
            Thread.sleep(timeSleep);
            // Leverage Explicit Waits for Reliable Element Interaction
            WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10)); // Adjust timeout as needed

            // Locate the tbody element using your preferred strategy (replace with your
            // logic)
            WebElement tbody = wait.until(ExpectedConditions.presenceOfElementLocated(ligne));

            // Find all rows within the tbody
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            // Filter rows efficiently using streams and anyMatch (optional optimization)
            Optional<WebElement> matchingRow = rows.stream()
                    .filter(row -> row.getText().contains(productName) &&
                            row.getText().contains(domain) &&
                            row.getText().contains(subDomain) &&
                            row.getText().contains(createdDate))
                    .findFirst();

            if (matchingRow.isPresent()) {
                WebElement rowElement = matchingRow.get();
                int rowIndex = rows.indexOf(rowElement);
                // log.info("Row found product '" + productName + "': " + rowElement.getText());

                List<WebElement> validButtons = rowElement.findElements(activateButtonLocator).stream()
                        .filter(btn -> btn.isEnabled() && btn.isDisplayed())
                        .collect(Collectors.toList());

                if (validButtons.isEmpty()) {
                    log.warn("No button found matching the action name: " + find(activateButtonLocator).getText());
                    return false;
                } else {
                    WebElement actionButton = validButtons.get(rowIndex); // Always get the first valid button
                    // log.info("Performing action: " + find(activateButtonLocator).getText());
                    actionButton.click();
                    // log.info("Successfully fill data of commission.");
                    return true;
                }
            } else {
                log.warn("No row found matching product: " + productName);
                return false;
            }
        } catch (InterruptedException e) {
            log.error("Interrupted Exception occurred:", e);
            return false;
        } catch (StaleElementReferenceException s) {
            log.error("Stale Element Reference Exception occurred:", s);
            // Consider implementing a retry mechanism or more robust element handling here
            return false; // Or potentially retry the operation
        }
    }

    public boolean isNotPresentToSpaceGest(By ligne, By activateButtonLocator, String productName, String domain,
                                           String subDomain, String createdDate, String textBtn) {
        List<WebElement> tBody = this.findList(ligne);
        List<WebElement> rows = tBody.get(0).findElements(By.tagName("tr"));
        List<WebElement> filteredRows = rows.stream()
                .filter(row -> row.getText().contains(productName) &&
                        row.getText().contains(domain) &&
                        row.getText().contains(subDomain) &&
                        row.getText().contains(createdDate))
                .distinct()
                .collect(Collectors.toList());

        if (filteredRows.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method of setting social security number "dd/MM/yyyy"
     *
     * @param civilite
     * @param birthDay
     * @param postalCode
     * @return String
     */
    public static String setSocialSecurityNumber(Integer civilite, String birthDay, String postalCode) {
        if (civilite < 3 && civilite > 0) {
            String numeroSS = civilite.toString(); // Sexe
            numeroSS = numeroSS + birthDay.substring(8); // Année de naissance
            numeroSS = numeroSS + birthDay.substring(3, 5); // Mois de naissance
            numeroSS = numeroSS + (!postalCode.isEmpty() ? postalCode.toString().substring(0, 2) : ""); // Département
            // de naissance
            numeroSS = numeroSS + (!postalCode.isEmpty() ? postalCode.toString().substring(2) : ""); // Numéro Insee de
            // votre commune
            numeroSS = numeroSS + faker.number().numberBetween(100, 999); // Ordre d’enregistrement de votre naissance
            // sur le registre d’état civil de votre
            // commune
            long keySecuritySocial = 97 - (Long.parseLong(numeroSS) % 97); // Calcul de la clé de sécurité
            numeroSS = numeroSS + keySecuritySocial;
            return numeroSS;
        } else {
            System.out.println(
                    "Civilité invalide, veuillez saisir soit 1 si vous êtes un homme soit 2 si vous êtes une femme.");
            return "";
        }
    }

    /**
     * Génère un code d'organisme d'assurance basé sur le régime d'assurance fourni.
     *
     * Ce code est généré en concaténant un préfixe défini par le type de régime
     * avec un numéro aléatoire composé de deux segments :
     * - Un segment de 3 chiffres aléatoires (100 à 999)
     * - Un segment de 4 chiffres aléatoires (1000 à 9999)
     *
     * Le régime est identifié par une valeur textuelle (ex: "Régime général",
     * "TNS").
     *
     * @param regime Le type de régime d'assurance (ex : "Régime général", "AM",
     *               etc.).
     * @return Un code d'organisme unique pour le régime d'assurance.
     */
    public static String setCompanyCode(String regime) {
        String codeOrganisme = "";

        // Le switch permet de gérer les différents régimes d'assurance en fonction de
        // l'entrée "regime"
        switch (regime) {
            case "AM":
            case "Régime local Alsace-Moselle":
                // Génère un code pour le régime Alsace-Moselle
                codeOrganisme = IInsuranceRegime.AM + generateRandomCode();
                break;
            case "RG":
            case "Régime général":
                // Génère un code pour le régime général
                codeOrganisme = IInsuranceRegime.RG + generateRandomCode();
                break;
            case "TNS":
            case "Sécurité sociale des indépendants":
                // Génère un code pour le régime des indépendants (TNS)
                codeOrganisme = IInsuranceRegime.TNS + generateRandomCode();
                break;
            case "EA":
            case "Régime agricole":
                // Génère un code pour le régime agricole
                codeOrganisme = IInsuranceRegime.EA + generateRandomCode();
                break;
            case "R1E":
            case "Régime 1er euro":
                // Génère un code pour le régime 1er euro
                codeOrganisme = IInsuranceRegime.R1E + generateRandomCode();
                break;
            case "RCC":
            case "Régime complément CFE":
                // Génère un code pour le régime complément CFE
                codeOrganisme = IInsuranceRegime.RCC + generateRandomCode();
                break;
            default:
                throw new IllegalArgumentException("Régime inconnu: " + regime);
        }

        return codeOrganisme;
    }

    /**
     * Génère une partie aléatoire du code d'organisme d'assurance, composée de deux
     * segments :
     * - Un premier segment de 3 chiffres (100 à 999)
     * - Un second segment de 4 chiffres (1000 à 9999)
     *
     * @return Un segment aléatoire en tant que chaîne de caractères.
     */
    private static String generateRandomCode() {
        return faker.number().numberBetween(100, 999)
                + String.valueOf(faker.number().numberBetween(1000, 9999));
    }

    public static String formaterDateToString(LocalDate date) {
        return date.format(formatter);
    }

    /**
     *
     * @param scaleDay
     * @return
     */
    public static String generateDateByIncremente(int scaleDay) {
        // Increment the date by the specified number of days
        LocalDate newDate = dateNow.plusDays(scaleDay);

        // Format the date to the desired string format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return newDate.format(formatter);
    }

    public static String generateDatePlusOneYear(Integer scaleDay, Integer scaleMonth) {
        // Calculate the new date by adding one year, scale days, and scale months
        LocalDate newDate = dateNow.plusYears(1).withMonth(scaleMonth).withDayOfMonth(1); // Start with the first day of
        // the target month
        int maxDayOfMonth = newDate.getMonth().length(newDate.isLeapYear());
        newDate = newDate.withDayOfMonth(Math.min(maxDayOfMonth, dateNow.getDayOfMonth() + scaleDay));

        // Format the date to the desired string format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return newDate.format(formatter);
    }

    /**
     *
     * @param scaleDay
     * @return
     */
    public static Integer generateDay(int scaleDay) {
        // Get the current day of the month
        int day = dateNow.getDayOfMonth();

        // Return 0 if the current day is greater than or equal to 27, otherwise return
        // the scaleDay
        return day >= 27 ? 0 : scaleDay;
    }

    public static String generateReference() {
        int chainLength = 2; // Longueur de la chaîne
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        for (int i = 0; i < chainLength; i++) {
            int index = random.nextInt(chars.length());
            char randomChar = chars.charAt(index);
            builder.append(randomChar);
        }

        String randomString = builder.toString();
        return randomString + 0 + faker.number().numberBetween(10, 100);
    }

    public static String generateProductInsuranceName(String riskType) {
        String pathFile = "src/main/java/files/testdata/productInsuranceData.json";
        JsonNode productInsuranceNames = WriteReadJsonFile.readJsonNodeFromFile(pathFile);
        int length = productInsuranceNames.get(riskType).size();
        String name = productInsuranceNames.get(riskType).get(faker.number().numberBetween(0, length)).toString();
        return name.replace("\"", "");
    }

    public static JsonNode getDataOfProductFamily(String riskType, String segment) {
        String risq = riskType.replace("é", "e").toUpperCase();
        String pathFile = "src/main/java/files/testdata/productFamily_insuranceSpace.json";
        JsonNode productFamilies = WriteReadJsonFile.readJsonNodeFromFile(pathFile).get("data");
        int length = productFamilies.size();
        JsonNode domain = null;
        for (int i = 0; i < length; i++) {
            String code = productFamilies.get(i)
                    .get("domaine")
                    .get("code")
                    .toString()
                    .replace("\"", "");
            if (code.equals(risq)) {
                switch (segment.toUpperCase()) {
                    case "INDIVIDUEL":
                        domain = productFamilies.get(i).get("domaine").get("sousDomaines").get(0).get("typesProduit");
                        break;
                    case "COLLECTIF":
                        domain = productFamilies.get(i).get("domaine").get("sousDomaines").get(1).get("typesProduit");
                        break;
                    default:
                        log.info("Segment introuvable");
                        break;
                }
                break;
            }
        }
        // System.out.print("\ndomain: " + domain);
        return domain;
    }

    // bigbrockerfonctionaltest/\src\main\java\files\testdata\warrantiesOfPrevention_insuranceSpace.json
    public static JsonNode getDataOfJson(String element) {
        String pathFile = "src/main/java/files/testdata/warrantiesOfPrevention_insuranceSpace.json";
        JsonNode elements = WriteReadJsonFile.readJsonNodeFromFile(pathFile).get(element);
        return elements;
    }

    public static String generateIBAN() {
        return "FR20 " + UseFullMethods.faker.number().numberBetween(1000, 9999) + " 8000 7045 "
                + UseFullMethods.faker.number().numberBetween(1000, 9999) + " 1543 R05";
    }

    public static String generateBIC() {
        return "FR" + UseFullMethods.faker.number().numberBetween(10, 99) + " 14"
                + UseFullMethods.faker.number().numberBetween(10, 99);
    }

    /**
     * Method for changing the style of element
     *
     * @param driver
     * @param elt
     */
    public static void changeStyleByJs(WebDriver driver, WebElement elt) {
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.display = 'block';", elt);
        // ; arguments[0].style.visibility = 'visible'
    }

    /**
     * Method for hiddenning the style of element
     *
     * @param driver
     * @param elt
     */
    public static void hiddenStyleByJs(WebDriver driver, WebElement elt) {
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.display = 'none';", elt);
    }

    public static void writeDataTestCases_adminSpace(String riskName, String risk,
                                                     String segmentData, String createdDate) {
        String fileName = System.getProperty("user.dir")
                + "/src/main/java/files/testdata/currentDataTestCases_adminSpace.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("productName", riskName);
            jsonNode.put("domain", risk);
            jsonNode.put("subDomain", segmentData);
            jsonNode.put("createdDate", createdDate);
            objectMapper.writeValue(new File(fileName),
                    jsonNode);
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("\nError in writing to json file : " + io.getMessage());
        }
    }

    public static void writeDataTestCases_insuranceSpace(String productName, String status, String risq) {
        String fileName = System.getProperty("user.dir")
                + "/src/main/java/files/testdata/currentDataTestCases_insuranceSpace.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("productName", productName);
            jsonNode.put("status", status);
            jsonNode.put("risq", risq);
            objectMapper.writeValue(new File(fileName),
                    jsonNode);
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("\nError in writing to json file : " + io.getMessage());
        }
    }

    public static String getValueOfJsonFile(String pathFile, String value) {
        JsonNode productInsuranceNames = WriteReadJsonFile.readJsonNodeFromFile(pathFile);
        return productInsuranceNames.get(value).asText();
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public void clickHover(WebElement parentElement) {

        // Obtenir la taille de l'élément parent
        int parentWidth = parentElement.getSize().getWidth();
        int parentHeight = parentElement.getSize().getHeight();

        // Définir le décalage à l'intérieur du conteneur où vous voulez cliquer (ex. 10
        // pixels à droite et 10 pixels en bas)
        int offsetX = parentWidth - 10;
        int offsetY = parentHeight - 10;

        // Créer une instance d'Actions
        Actions actions = new Actions(this.driver);

        // Déplacer vers l'élément parent et cliquer dans le vide en utilisant le
        // décalage
        actions.moveToElement(parentElement, offsetX, offsetY).click().perform();
    }

    public static String removeSpecialChar(String input) {
        for (Map.Entry<String, String> entry : SPECIAL_CHAR_MAP.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue());
        }
        return input;
    }

    public void compareField(String fieldName, String expectedValue, By fieldLocator) {
        String actualValue = this.getValueOfDisabledElement(fieldLocator);
        log.info(String.format("%s : %s", fieldName, actualValue));
        Assert.assertEquals(actualValue, expectedValue, String.format("%s ne correspond pas", fieldName));
    }

    public void waitForElementAndClick(By locator) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public void waitForElementWithText(By locator, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
        wait.until(ExpectedConditions.textToBe(locator, expectedText));
        log.info("Titre du modal validé : " + expectedText);
    }

    public static void logError(String message, Exception e) {
        //log.error(message, e);
        Assert.fail(message + ": " + e.getMessage());
    }

    public void verifyText(By locatorElementMessage, String expectedMessage, String message) {
        waitForElement(locatorElementMessage);
        String actualMessage = getText(locatorElementMessage);
        Assert.assertEquals(actualMessage, expectedMessage, message);
    }

    public void waitElementFactoryAfterClick(WebElement webElement) {
        waitForElementWithFactory(webElement);
        clickWithFactory(webElement);
    }

    /**
     * This method checks if a given web element is displayed on the page.
     * It uses WebDriverWait to ensure that the element has enough time to become
     * visible before returning the result.
     * If any of the common exceptions related to the element's visibility occur
     * (NoSuchElementException,
     * StaleElementReferenceException, or TimeoutException), it logs the error and
     * returns false.
     *
     * @param element The WebElement to be checked.
     * @return true if the element is visible, false otherwise.
     */
    public boolean isElementDisplayed(WebElement element) {
        try {
            // Waits for the element to be visible, with a timeout of 1 second.
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.visibilityOf(element));

            // Once visible, it checks if the element is displayed.
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            // Logs the error in case of an exception and returns false.
            logError("Element not found or not visible on the page.", e);
            return false;
        }
    }

    public void waitForElementToBeGone(WebElement element, int timeout) {
        if (isElementDisplayed(element)) {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
        }
    }

    public void actionConfirmedInModal(By expectedText, By confirmedBtn) {

        // Vérifie si le texte de titre est correct
        verifyText(expectedText, getText(expectedText), "Title is not found");

        // Clique sur le bouton de confirmation
        click(confirmedBtn);
    }

    /**
     * Cette méthode vérifie si le deuxième élément d'une liste WebElement (élément
     * d'index 1) n'est pas vide.
     * Elle loggue également un message d'information si le champ est rempli et un
     * avertissement s'il est vide.
     *
     * @param element Le localisateur (By) qui identifie la liste des éléments à
     *                vérifier.
     * @return true si le champ n'est pas vide, false si le champ est vide ou si la
     *         liste est vide.
     */
    public boolean checkFieldNotEmptyOfList(By element) {
        // Récupération de la liste d'éléments Web correspondants au sélecteur fourni
        List<WebElement> elements = findList(element);

        // Vérification que la liste d'éléments est non vide et que le deuxième élément
        // (index 1) existe
        if (elements.size() > 1) {
            String fieldValue = elements.get(1).getText(); // Récupérer et nettoyer le texte du champ
            if (!fieldValue.isEmpty()) {
                // log.info(String.format("Le champ contient: %s", fieldValue)); // Loguer le
                // contenu du champ
                return true; // Retourner vrai si le champ est rempli
            } else {
                log.warn("Attention : le champ est vide."); // Avertir que le champ est vide
                return false; // Retourner faux si le champ est vide
            }
        } else {
            log.warn("La liste d'éléments est vide ou ne contient pas suffisamment d'éléments.");
            return false; // Retourner faux si la liste est vide ou ne contient pas suffisamment
            // d'éléments
        }
    }
}
