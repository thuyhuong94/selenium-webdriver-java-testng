package webdriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Popup {

	WebDriver driver;
	Actions action;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	long timeinsecond = 2;
	long timeoutdefault = 15;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		//set flexible timeout
		expliciWait = new WebDriverWait(driver, 15);
		//ep kieu driver into action
		action = new Actions(driver);
		//Ep kieu tuong minh cho driver
		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(timeoutdefault, TimeUnit.SECONDS);	 
	}
		
	@Test
	public void TC_01_fixed_Popup () {
		driver.get("https://zingpoll.com/");
		
		By signinpopup = By.cssSelector(".modal-dialog.modal_dialog_custom");
		
		driver.findElement(By.xpath("//a[contains(text(),'SIGN IN')]")).click();
		sleepinSeconds(timeinsecond);
		//Verify popup is displayed
		Assert.assertTrue(driver.findElement(signinpopup).isDisplayed());
		driver.findElement(By.name("email")).sendKeys("adf@gmail.com");
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.cssSelector(".modal_dialog_custom .close")).click();
		sleepinSeconds(timeinsecond);
		//Verify popup is not displayed
		Assert.assertFalse(driver.findElement(signinpopup).isDisplayed());
	}
	
	@Test
	public void TC_2_shopee () {
		driver.get("https://shopee.vn/");
		
		By imagepopup = By.xpath("//img[@alt='home_popup_banner']");
		//Verify popup is displayed
		Assert.assertTrue(isElementDisplayed(imagepopup));
		//Verify popup is not displayed
		driver.findElement(By.xpath("//div[@class='shopee-popup__close-btn']")).click();
		sleepinSeconds(timeinsecond);
		//Verify popup is not displayed
		Assert.assertFalse(isElementDisplayed(imagepopup));
	}
	
	@Test
	public void TC_3_Random_popup_In_DOM () {
		driver.get("https://shopee.vn/");
		String searchKeyword = "Macbook pro";
		By imagepopup = By.xpath("//img[@alt='home_popup_banner']");
		
		if (isElementDisplayed(imagepopup)) {
			driver.findElement(By.xpath("//div[@class='shopee-popup__close-btn']")).click();
			sleepinSeconds(timeinsecond);
		}
		driver.findElement(By.xpath("//input[@class='shopee-searchbar-input__input']")).sendKeys(searchKeyword);
		sleepinSeconds(timeinsecond);
		driver.findElement(By.cssSelector(".btn-solid-primary")).click();
		sleepinSeconds(10);
		
		List<WebElement> products = driver.findElements(By.cssSelector(".shopee-search-item-result__item div[data-sqe='name'] .yQmmFK"));
		System.out.println(products.size());
		//Verify result
		for (WebElement product : products) {
			String productname = product.getText().toLowerCase();
			System.out.println(productname);
			Assert.assertTrue(productname.contains(searchKeyword.split(" ")[0].toLowerCase()) || productname.contains(searchKeyword.split(" ")[1].toLowerCase()));
		}
		
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public boolean isElementDislayed_customDisplayed (By by) {
		try {
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isElementDisplayed (By by) {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(by);
		driver.manage().timeouts().implicitlyWait(timeoutdefault, TimeUnit.SECONDS);
		if (elements.size() == 0) {
			return false;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public void sleepinSeconds(long timeoutinsecond) {
		try {
			Thread.sleep(timeoutinsecond*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}