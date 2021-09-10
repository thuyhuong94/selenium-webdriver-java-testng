package webdriver;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Wait_VI_Fluent {

	WebDriver driver;
	FluentWait<WebElement> fluentElement;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet Chrome
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}
		
	@Test
	public void TC_01_FluentWait_WebElement () {
		driver.get("https://automationfc.github.io/fluent-wait/");
		WebElement countdownTimer = driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		fluentElement = new FluentWait<WebElement>(countdownTimer);
		
		fluentElement.withTimeout(Duration.ofSeconds(15))
		.pollingEvery(Duration.ofMillis(100))
		.ignoring(NoSuchElementException.class)
		.until(new Function<WebElement, Boolean>() {
			public Boolean apply(WebElement countdown) {
				Boolean status = countdown.getText().endsWith("00");
				System.out.println("Text = " + countdown.getText() + "----------: "+ status);
				return status;
			}
		});
	}
	
	@Test
	public void TC_02_FluentWait_WebDriver () {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		clicktoElement(By.xpath("//div[@id='start']/button"));
		//1
		Assert.assertTrue(isElementDisplayed(By.xpath("//div[@id='finish']/h4[text()='Hello World!']")));
		//2
		Assert.assertEquals(getElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public WebElement getElement(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(polling))
				.ignoring(NoSuchElementException.class);
		
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		return element;
	}
	
	public void clicktoElement(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(polling))
				.ignoring(NoSuchElementException.class);
		
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		element.click();
	}
	
	public Boolean isElementDisplayed(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(polling))
				.ignoring(NoSuchElementException.class);
		
		Boolean status = wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return driver.findElement(locator).isDisplayed();
			}
		});
		return status;
	}
	
	long timeout = 15;
	long polling = 1;
}