package webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Xpath_Css_Part_II_Technical {

	WebDriver driver;
	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		driver = new FirefoxDriver();
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://live.guru99.com/");
	}
	@Test
	public void TC_01_Empty_Email_Password () {
		// Nhap gia tri vao Firstname textbox
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(3);
		driver.findElement(By.id("email")).sendKeys("");
		sleepinSeconds(3);
		driver.findElement(By.id("pass")).sendKeys("");
		sleepinSeconds(3);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(3);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='advice-required-entry-email']")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='advice-required-entry-pass']")).getText(), "This is a required field.");
	}
	@Test
	public void TC_02_Invalid_Email () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(3);
		driver.findElement(By.id("email")).sendKeys("adadh@123");
		sleepinSeconds(3);
		driver.findElement(By.id("pass")).sendKeys("123456");
		sleepinSeconds(3);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(3);
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}
	@Test
	public void TC_03_Invalid_Password () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(3);
		driver.findElement(By.id("email")).sendKeys("ntthuyhuong@gmail.com");
		sleepinSeconds(3);
		driver.findElement(By.id("pass")).sendKeys("123");
		sleepinSeconds(3);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(3);
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	@Test
	public void TC_04_Incorrect_Password () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(3);
		driver.findElement(By.id("email")).sendKeys("ntthuyhuong@gmail.com");
		sleepinSeconds(3);
		driver.findElement(By.id("pass")).sendKeys("123546");
		sleepinSeconds(3);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(3);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(), "Invalid login or password.");
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
