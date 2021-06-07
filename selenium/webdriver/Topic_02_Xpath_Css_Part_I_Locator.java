package webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Xpath_Css_Part_I_Locator {

	WebDriver driver;
	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		driver = new FirefoxDriver();
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
	}
	@Test
	public void TC_01_ID () {
		// Nhap gia tri vao Firstname textbox
		driver.findElement(By.id("FirstName")).sendKeys("Autmation");
		sleepinSeconds(3);
		driver.findElement(By.id("gender-male")).click();
		sleepinSeconds(3);
	}
	@Test
	public void TC_02_Class () {
		//Refesh lai page
		driver.navigate().refresh();
		driver.findElement(By.className("search-box-text")).sendKeys("Macbook");
		sleepinSeconds(3);
		driver.findElement(By.className("button-1")).click();
		sleepinSeconds(3);
	}
	@Test
	public void TC_03_Name () {
		// back to Rigister page
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		driver.findElement(By.name("Email")).sendKeys("ntthuyhuong@gmail.com");
		sleepinSeconds(3);
		driver.findElement(By.name("Newsletter")).click();
		sleepinSeconds(3);
	}
	@Test
	public void TC_04_Tagname () {
		System.out.println("sum input_tag ="+ driver.findElements(By.tagName("input")).size());
		sleepinSeconds(3);
		System.out.println("sum link_tag ="+ driver.findElements(By.tagName("a")).size());
		sleepinSeconds(3);
	}
	@Test
	public void TC_05_Linktext () {
		driver.findElement(By.linkText("Log in")).click();
		sleepinSeconds(3);
	}
	@Test
	public void TC_06_Partial_Linktext () {
		driver.findElement(By.partialLinkText("Recently viewed")).click();
		sleepinSeconds(3);
		driver.findElement(By.partialLinkText("viewed products")).click();
		sleepinSeconds(3);
		driver.findElement(By.partialLinkText("Recently viewed products")).click();
		sleepinSeconds(3);
	}
	@Test
	public void TC_07_Css () {
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		driver.findElement(By.cssSelector("input[id='FirstName']")).sendKeys("Automation");
		sleepinSeconds(3);
		driver.findElement(By.cssSelector("input[class='search-box-text ui-autocomplete-input']")).sendKeys("Macbook");
		sleepinSeconds(3);
		driver.findElement(By.cssSelector("input[name='Email']")).sendKeys("ntthuyhuong@gmail.com");
		sleepinSeconds(3);
		driver.findElement(By.cssSelector("a[href*='login']")).click();
		sleepinSeconds(3);
		
	}
	@Test
	public void TC_08_Xpath () {
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		driver.findElement(By.xpath("//input[@id='FirstName']")).sendKeys("Automation");
		sleepinSeconds(3);
		driver.findElement(By.xpath("//input[contains(@class,'search-box-text')]")).sendKeys("macbook");
		sleepinSeconds(3);
		driver.findElement(By.xpath("//a[contains(@href,'login')]")).click();
		sleepinSeconds(3);
		driver.findElement(By.xpath("//a[contains(text(),'Recently viewed')]")).click();
		sleepinSeconds(3);
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
