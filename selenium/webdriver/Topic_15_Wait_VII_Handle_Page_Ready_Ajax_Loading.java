package webdriver;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Wait_VII_Handle_Page_Ready_Ajax_Loading {

	WebDriver driver;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet Chrome
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}
		
	@Test
	public void TC_01_Icon_loading_Success() {
		driver.get("https://opensource-demo.orangehrmlive.com");
		driver.findElement(By.xpath("//input[@id='txtUsername']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("admin123");
		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		// Wait Page Ready(jQuery/ Ajax Loading)
		Assert.assertTrue(isJQueryLoadSuccess(driver));
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='3 month(s)']")).isDisplayed());
		
		driver.findElement(By.xpath("//b[text()='PIM']")).click();
		// Wait Page Ready(jQuery/ Ajax Loading)
		Assert.assertTrue(isJQueryLoadSuccess(driver));
		
		driver.findElement(By.xpath("//input[@id='empsearch_employee_name_empName']")).sendKeys("Lisa");
		driver.findElement(By.xpath("//input[@id='searchBtn']")).click();
		// Wait Page Ready(jQuery/ Ajax Loading)
		Assert.assertTrue(isJQueryLoadSuccess(driver));
		Assert.assertTrue(driver.findElement(By.xpath("//table[@id='resultTable']//td[3]/a[text()='Lisa']")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public Boolean isJQueryLoadSuccess (WebDriver driver) {
		expliciWait = new WebDriverWait(driver, timeout);
		jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active ===0);");
			}
		};
		return expliciWait.until(jQueryLoad);
	}
	long timeout = 15;
}