package utils;

import java.time.Duration;


import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {
	public WebDriver driver;
	public ReadConfigProperties props;
	protected final int TIMER_TO_WAIT = 1000;
	// protected final String ENV = System.getenv("ENV");

	public BrowserFactory(String browserName) {
		this.props = new ReadConfigProperties();

		if (this.props.getEnv().equals("local")) {
			switch (browserName) {
				case "Chrome":
					WebDriverManager.chromedriver().setup();
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
					chromeOptions.addArguments("--start-maximized");
					// chromeOptions.addArguments("--remote-allow-origins=*");
					this.driver = new ChromeDriver(chromeOptions);
					break;
				case "Firefox":
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
					firefoxOptions.addArguments("--start-maximized");
					this.driver = new FirefoxDriver(firefoxOptions);
					break;
				case "Edge":
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
					edgeOptions.addArguments("--start-maximized");
					this.driver = new EdgeDriver(edgeOptions);
					break;
				default:
					System.out.println("We do not support this browser ");
					break;
			}
		} else {
			switch (browserName) {
				case "Chrome":
					WebDriverManager.chromedriver().setup();
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.addArguments("--start-maximized");
					chromeOptions.addArguments("--headless=new");
					// chromeOptions.addArguments("--remote-allow-origins=*");
					this.driver = new ChromeDriver(chromeOptions);
					break;
				case "Firefox":
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.addArguments("--start-maximized");
					firefoxOptions.addArguments("--headless=new");
					this.driver = new FirefoxDriver(firefoxOptions);
					break;
				case "Edge":
					WebDriverManager.edgedriver().setup();
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.addArguments("--start-maximized");
					edgeOptions.addArguments("--headless=new");
					this.driver = new EdgeDriver(edgeOptions);
					break;
				default:
					System.out.println("We do not support this browser ");
					break;
			}
		}
	}

	// retrieve login page title
	public void load(String nameSpace, String env) {
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DelayClass.PAGE_LOAD_TIMEOUT));
		this.driver.manage().deleteAllCookies();
		switch (nameSpace) {
			case "Jumia Space":
				this.driver.get(props.getUrlSpace(env));
				break;
			default:
				System.out.println(nameSpace + "is not found");
				break;
		}
	}

	public void waitBeforeQuit() {
		try {
			Thread.sleep(TIMER_TO_WAIT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.driver.quit();
	}
}
