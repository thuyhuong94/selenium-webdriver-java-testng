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

public class Topic_11_Iframe_frame {

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
	public void TC_01_Iframe () {
		driver.get("https://kyna.vn/");
		//switch to iframe facebook
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'facebook')]")));
		//Element of facebook
		String likeOfNumber = driver.findElement(By.xpath("//div[@class='_1drq']")).getText();
		System.out.println("LikeOfNumber = "+ likeOfNumber);
		// switch to parent page
		driver.switchTo().defaultContent();
		//switch to iframe chat	
		driver.findElement(By.xpath("//iframe[@id='cs_chat_iframe']")).click();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='cs_chat_iframe']")));
		sleepinSeconds(timeinsecond);
		String name = "automation";
		String phonenumber = "0936298123";
		driver.findElement(By.xpath("//label[contains(text(),'Nhập thông tin của bạn')]/following-sibling::input[@placeholder='Nhập tên của bạn']")).sendKeys(name);
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//label[contains(text(),'Nhập thông tin của bạn')]/following-sibling::input[@placeholder='Nhập số điện thoại của bạn']")).sendKeys(phonenumber);
		sleepinSeconds(timeinsecond);
		select = new Select(driver.findElement(By.id("serviceSelect")));
		select.selectByVisibleText("TƯ VẤN TUYỂN SINH");
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys("thong tin tuyen sinh");
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//input[contains(@class,'submit')]")).click();
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='floater_inner_seriously']/label[@class='logged_in_name ng-binding']")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='floater_inner_seriously']/label[@class='logged_in_phone ng-binding']")).getText(), phonenumber);
		// switch to parent page
		driver.switchTo().defaultContent();
		sleepinSeconds(timeinsecond);
		driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
		sleepinSeconds(timeinsecond);
		driver.findElement(By.className("search-button")).click();;
		sleepinSeconds(timeinsecond);
		List<WebElement> allCourses = driver.findElements(By.cssSelector(".content>h4"));
		System.out.println(allCourses.size());
		//Verify result
		for (WebElement Course : allCourses) {
			Assert.assertTrue(Course.getText().contains("Excel"));
		}
	}
	
	@Test
	public void TC_2_frame () {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		String parentpage = driver.getWindowHandle();
		driver.switchTo().frame("login_page");
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("automation");
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//a[@onclick='return fLogon();']/img[@alt='continue']")).click();
		Assert.assertTrue(driver.findElement(By.name("fldPassword")).isDisplayed());
		//switch to footer frame
		driver.switchTo().defaultContent();
		driver.switchTo().frame("footer");
		driver.findElement(By.xpath("//a[text()='Terms and Conditions']")).click();
		sleepinSeconds(timeinsecond);
		//Verify appear other window
		Set<String> allpages = driver.getWindowHandles();
		for (String page : allpages) {
			if (!page.equals(parentpage)) {
				driver.switchTo().window(page);
				break;
			}
		}
		Assert.assertEquals(driver.getCurrentUrl(), "https://v1.hdfcbank.com/htdocs/common/hdfcbank_tnc.htm");	
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
	
	public void sleepinSeconds(long timeoutinsecond) {
		try {
			Thread.sleep(timeoutinsecond*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}