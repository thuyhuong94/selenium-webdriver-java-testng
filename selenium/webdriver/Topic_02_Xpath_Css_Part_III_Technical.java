

package webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Xpath_Css_Part_III_Technical {

	WebDriver driver;
	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		driver = new FirefoxDriver();
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://automationfc.github.io/basic-form/");
	}
	@Test
	public void TC_01 () {
		// Nhap gia tri vao Firstname textbox
//		driver.findElement(By.xpath("//a[text()='Basic Form']")).click();
//		sleepinSeconds(3);
		Assert.assertEquals(driver.findElement(By.xpath("//h5[contains(text(),'Hello World!')]")).getText(), "Hello World! (Ignore Me) @04:45 PM");
		sleepinSeconds(3);
		Assert.assertEquals(driver.findElement(By.xpath("//h5[contains(text(),\"I'm a Hacker\")]")).getText(), "I'm a Hacker - 18 years old - living in Viet Nam - 15/03/2020");
		sleepinSeconds(3);
	}
	@Test
	public void TC_02 () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(3);
		driver.findElement(By.id("email")).sendKeys("huong94@gmail.com");
		sleepinSeconds(3);
		driver.findElement(By.id("pass")).sendKeys("123456");
		sleepinSeconds(3);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		sleepinSeconds(3);
		driver.findElement(By.xpath("//p[contains(.,\"huong94@gmail.com\")]")).getText().contains("huong nguyen");
		sleepinSeconds(3);
	}
	@Test
	public void TC_03 () {
		//Refesh lai page
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(3);
		driver.findElement(By.id("email")).sendKeys("huong94@gmail.com");
		sleepinSeconds(3);
		driver.findElement(By.id("pass")).sendKeys("123456");
		sleepinSeconds(3);
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		sleepinSeconds(3);
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class ='actions']/button")).click();
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

