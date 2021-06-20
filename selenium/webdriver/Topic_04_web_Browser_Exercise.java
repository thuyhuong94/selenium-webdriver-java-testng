package webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_web_Browser_Exercise {

	WebDriver driver;
	byte timeoutinsecond = 2;
	
	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		driver = new FirefoxDriver();
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://live.guru99.com/");
	}
	
	// Browser
	@Test
	public void TC_01_verify_url_by_getCurrentUrl () {	
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepinSeconds(timeoutinsecond);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/login/");
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
		sleepinSeconds(timeoutinsecond);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/create/");
		
		
	}
	@Test
	public void TC_02_verify_Title () {
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepinSeconds(timeoutinsecond);
		Assert.assertEquals(driver.getTitle(), "Customer Login");
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
		sleepinSeconds(timeoutinsecond);
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
		
	}
	@Test
	public void TC_03_verify_navigate () {
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepinSeconds(timeoutinsecond);
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
		sleepinSeconds(timeoutinsecond);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/create/");
		driver.navigate().back();
		sleepinSeconds(timeoutinsecond);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/login/");
		driver.navigate().forward();
		sleepinSeconds(timeoutinsecond);
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}
	@Test
	public void TC_04_verify_page_source_code () {
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepinSeconds(timeoutinsecond);
		Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
		sleepinSeconds(timeoutinsecond);
		Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
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