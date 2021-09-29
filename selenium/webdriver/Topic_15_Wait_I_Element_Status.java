package webdriver;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Wait_I_Element_Status {

	WebDriver driver;
	Actions action;
	Select select;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	
	long timeinsecond = 2;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
//		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
//		driver = new FirefoxDriver();
		//set flexible timeout
		expliciWait = new WebDriverWait(driver, 15);
		//Ep kieu tuong minh cho driver
		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
	}
		
	@Test
	public void TC_01_Visible_Displayed () {
		driver.get("https://www.facebook.com/");
		//Wait cho 1 element hien thi trong 15s
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']")));
		//Verify 1 element hien thi
		Assert.assertTrue(driver.findElement(By.xpath("//input[@id='email']")).isDisplayed());

	}
	
	@Test
	public void TC_02_Invisible_Undisplayed () {
		driver.get("https://www.facebook.com/");
		//Wait cho button tao tai khoan co the click
		expliciWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Tạo tài khoản mới']")));
		//Action click
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		
		//khong co tren UI nhung van co trong DOM
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
		
		//khong co trongg UI va khong co trong DOM
		expliciWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")));
		driver.findElement(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")).click();
		
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//form[@id='reg']")));
	}
	
	@Test
	public void TC_03_Presence () {
		driver.get("https://www.facebook.com/");
		//Hien thi tren UI va co trong DOM ==> pass
		expliciWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='email']")));
		expliciWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Tạo tài khoản mới']")));
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		//khong hien thi tren UI va van co trong DOM ==> pass
		expliciWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
		
		expliciWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")));
		driver.findElement(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")).click();
	}
	
	@Test
	public void TC_04_Staleness () {
		driver.get("https://www.facebook.com/");
		//khong hien thi tren UI va khong co trong DOM ==> pass
		expliciWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Tạo tài khoản mới']")));
		driver.findElement(By.xpath("//a[text()='Tạo tài khoản mới']")).click();
		
		//phai xac dinh web element truoc khi action de wait staleness
		//hien thi tren UI va co trong DOM
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='reg']")));
		WebElement registerform = driver.findElement(By.xpath("//form[@id='reg']"));
		//Khong hien thi tren UI va co trong DOM
		WebElement confirmEmail = driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']"));
		
		expliciWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")));
		driver.findElement(By.xpath("//div[text()='Đăng ký']/parent::div/preceding-sibling::img")).click();
		
		//wait register form staleness
		expliciWait.until(ExpectedConditions.stalenessOf(registerform));	
		//wait confirm Email staleness
		expliciWait.until(ExpectedConditions.stalenessOf(confirmEmail));
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