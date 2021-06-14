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
	byte timeinsecond = 1;
	String firstname = "nguyen";
	String middlename = "thuy";
	String lastname = "huong";
	String fullname = firstname + ' ' + middlename + ' ' + lastname;
	String email = "thuyhuong07@gmail.com";
	String password = "123456";
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
		this.timeinsecond = timeinsecond;
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("email")).sendKeys("");
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("pass")).sendKeys("");
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(this.timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='advice-required-entry-email']")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='advice-required-entry-pass']")).getText(), "This is a required field.");
	}
	@Test
	public void TC_02_Invalid_Email () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("email")).sendKeys("adadh@123");
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("pass")).sendKeys("123456");
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(this.timeinsecond);
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}
	@Test
	public void TC_03_Invalid_Password () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("email")).sendKeys("ntthuyhuong@gmail.com");
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("pass")).sendKeys("123");
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(this.timeinsecond);
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	@Test
	public void TC_04_Incorrect_Password () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("email")).sendKeys("ntthuyhuong@gmail.com");
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("pass")).sendKeys("123546");
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(this.timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(), "Invalid login or password.");
	}
	@Test
	public void TC_05_Create_a_new_Account () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		this.timeinsecond = timeinsecond;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("firstname")).sendKeys(this.firstname);
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("middlename")).sendKeys(this.middlename);
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("lastname")).sendKeys(this.lastname);
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("email_address")).sendKeys(this.email);
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("password")).sendKeys(this.password);
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("confirmation")).sendKeys(this.password);
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		sleepinSeconds(this.timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		String info = driver.findElement(By.xpath("//a[text()='Change Password']/parent::p")).getText();
		System.out.println(info);
		Assert.assertTrue(info.contains(this.fullname));
		Assert.assertTrue(info.contains(this.email));
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//body[contains(@class,'cms-index-index cms-home')]")).isDisplayed();
		sleepinSeconds(this.timeinsecond);
	}
	
	@Test
	public void TC_06_Login_valid_email_password () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		this.timeinsecond = timeinsecond;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("email")).sendKeys(this.email);
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.id("pass")).sendKeys(this.password);
		sleepinSeconds(this.timeinsecond);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(this.timeinsecond);
		String dashboad = driver.findElement(By.xpath("//h1[text()='My Dashboard']")).getText();
		System.out.println(dashboad);
		Assert.assertEquals(dashboad, "My Dashboard".toUpperCase());
		Assert.assertEquals(driver.findElement(By.xpath("//strong[contains(text(),\"Hello\")]")).getText(), "Hello, "+this.fullname+"!");
		String info = driver.findElement(By.xpath("//a[text()='Change Password']/parent::p")).getText();
		System.out.println(info);
		Assert.assertTrue(info.contains(this.fullname));
		Assert.assertTrue(info.contains(this.email));
		sleepinSeconds(this.timeinsecond);
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