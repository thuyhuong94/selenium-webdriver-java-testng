package webdriver;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.sun.glass.events.KeyEvent;

public class Topic_15_Wait_V_Mixing_Explicit_Implicit {

	WebDriver driver;
	WebDriverWait expliciWait;
	String projectPath = System.getProperty("user.dir");

	long timeinsecond = 2;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet Chrome
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}
		
	@Test
	public void TC_01_Element_Found_Implicit_Explicit () {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		expliciWait = new WebDriverWait(driver, 10);
		
		driver.get("https://www.facebook.com/");
		
		showDateTimeNow ("Start Explicit");
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']")));
		showDateTimeNow ("End Explicit");
		showDateTimeNow ("Start Implicit");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("ntthuyhuong@gmail.com");
		showDateTimeNow ("End Implicit");
	}
	
	@Test
	public void TC_02_Element_Not_Found_Implicit () {
		// -Chờ hết timeout của implicit
		// -Trong thời gian chờ cứ mỗi nửa s sẽ tìm lại một lần
		// -Khi nào chờ hết timeout của implicit sẽ đánh fail testcase và throw out Exception: NosuchElementException
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.facebook.com/");
		showDateTimeNow ("Start Implicit");
		try {
			driver.findElement(By.xpath("//input[@id='tao khong co']")).sendKeys("ntthuyhuong@gmail.com");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Element is not exist");
		}
		showDateTimeNow ("End Implicit");
	}
	
	@Test
	public void TC_03_Element_Not_Found_Implicit_Explicit () {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		expliciWait = new WebDriverWait(driver, 10);
		driver.get("https://www.facebook.com/");
		showDateTimeNow ("Start Explicit");
		// find Element trước
		// apply điều kiện
		// implicit sẽ ảnh hưởng đến các step có dùng explicit
		try {
			expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='tao khong co']")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		showDateTimeNow ("End Explicit");
	}
	
	@Test
	public void TC_04_Element_Not_Found_Explicit_Param_By () {
		expliciWait = new WebDriverWait(driver, 10);
		driver.get("https://www.facebook.com/");
		showDateTimeNow ("Start Explicit");
		//Explicit = "By": tham số truyền vào luôn đúng (luôn có nghĩa). Function nó sẽ tìm hết timeout mới đánh fail TC
		try {
			expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='tao khong co']")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		showDateTimeNow ("End Explicit");
	}
	
	@Test
	public void TC_05_Element_Not_Found_Explicit_Param_WebElement () {
		expliciWait = new WebDriverWait(driver, 10);
		driver.get("https://www.facebook.com/");
		showDateTimeNow ("Start Explicit");
		//Explicit = "WebElement": tham số truyền vào mà sai (=null), function nó sẽ không work ngay từ đầu và fail TC luôn
		try {
			expliciWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@id='tao khong co']"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		showDateTimeNow ("End Explicit");
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void showDateTimeNow (String status) {   
		Date date = new Date();
		System.out.println("------------- " + status + ": " + date.toString() + "------------- ");
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