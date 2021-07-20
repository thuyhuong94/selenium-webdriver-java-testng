package webdriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
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

public class Topic_12_Tab_Window {

	WebDriver driver;
	Actions action;
	Select select;
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
		driver.manage().window().maximize();
	}
		
	@Test
	public void TC_01_Github () {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		//Get ra Tab/Window ID cuar trang dang active
		String parentTabID = driver.getWindowHandle();
		//click google
		driver.findElement(By.xpath("//a[text()= 'GOOGLE']")).click();
		//switch to new Tab
		Set<String> allTabID = driver.getWindowHandles();
		System.out.println("Total Tabs =" + allTabID.size());
		
		SwitchtoWindowByID (parentTabID);
		sleepinSeconds(timeinsecond);
		String GoogleID = driver.getWindowHandle();
		Assert.assertTrue(driver.getTitle().equals("Google"));
		
		//switch to parent Tab
		SwitchtoWindowByID (GoogleID);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.getCurrentUrl().equals("https://automationfc.github.io/basic-form/index.html"));
		
		//Switch to Facebook
		driver.findElement(By.xpath("//a[text()= 'FACEBOOK']")).click();
		SwitchWindowbyTitle("Facebook - Đăng nhập hoặc đăng ký");
		Assert.assertTrue(driver.getCurrentUrl().equals("https://www.facebook.com/"));
		//switch to parent Tab
		SwitchWindowbyTitle("SELENIUM WEBDRIVER FORM DEMO");
		Assert.assertTrue(driver.getCurrentUrl().equals("https://automationfc.github.io/basic-form/index.html"));
		//Switch to Tiki
		driver.findElement(By.xpath("//a[text()= 'TIKI']")).click();
		SwitchWindowbyTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		Assert.assertTrue(driver.getCurrentUrl().equals("https://tiki.vn/"));
		
		//Close all Window without parent
		CloseAllhWindowWithoutParent(parentTabID);
		sleepinSeconds(timeinsecond);
		System.out.println(driver.getCurrentUrl());
		
	}
	
	@Test
	public void TC_2_kyna () {
		driver.get("https://kyna.vn/");
		String parentTabID = driver.getWindowHandle();
		//Switch to Facebook
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//img[@alt='facebook']")));
		SwitchWindowbyTitle("Kyna.vn - Trang chủ | Facebook");
		Assert.assertTrue(driver.getCurrentUrl().equals("https://www.facebook.com/kyna.vn"));
		
		//switch to parent Tab
		SwitchWindowbyTitle("Kyna.vn - Học online cùng chuyên gia");
		Assert.assertTrue(driver.getCurrentUrl().equals("https://kyna.vn/"));
		
		//Switch to Youtube
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//img[@alt='youtube']")));
		SwitchWindowbyTitle("Kyna.vn - YouTube");
		Assert.assertTrue(driver.getCurrentUrl().equals("https://www.youtube.com/user/kynavn"));
	
		//Close all Window without parent
		CloseAllhWindowWithoutParent(parentTabID);
		sleepinSeconds(timeinsecond);
		System.out.println(driver.getCurrentUrl());
	}
	
	@Test
	public void TC_3_guru99 () {
		driver.get("http://live.demoguru99.com/index.php/");
		String parentTabID = driver.getWindowHandle();
		//Add to compare
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/following-sibling::div//a[text()='Add to Compare']")).click();
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Sony Xperia has been added to comparison list.']")).isDisplayed());
		
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div//a[text()='Add to Compare']")).click();
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Samsung Galaxy has been added to comparison list.']")).isDisplayed());
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		sleepinSeconds(timeinsecond);
		//Switch to Window compare
		SwitchtoWindowByID(parentTabID);
		Assert.assertTrue(driver.getTitle().equals("Products Comparison List - Magento Commerce"));
		//Close all Window without parent
		CloseAllhWindowWithoutParent(parentTabID);
		sleepinSeconds(timeinsecond);
		System.out.println(driver.getCurrentUrl());
		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		// accept alert
		driver.switchTo().alert().accept();

		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The comparison list was cleared.']")).isDisplayed());
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void SwitchtoWindowByID (String WindowID) {
		// Get all ID tab/Window at switch time
		Set<String> allWindowIDs = driver.getWindowHandles();
		//Duyet qua all gia tri trong allTabID
		for (String id : allWindowIDs) {
			if (!id.equals(WindowID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}
	
	public void SwitchWindowbyTitle (String tabTitle) {
		//Get all ID at get time
		Set<String> allWindowIDs = driver.getWindowHandles();
		//Duyet qua all gia tri cua allWindowTitles
		for (String id : allWindowIDs) {
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			if (actualTitle.equals(tabTitle)) {
				break;
			}
		}
	}
	
	public void CloseAllhWindowWithoutParent (String WindowID) {
		//Get all ID at get time
		Set<String> allWindowIDs = driver.getWindowHandles();
		//Duyet qua all gia tri cua allWindowTitles
		for (String id : allWindowIDs) {
			if (!id.equals(WindowID)) {
				driver.switchTo().window(id);
				driver.close();
			}
			driver.switchTo().window(WindowID);
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