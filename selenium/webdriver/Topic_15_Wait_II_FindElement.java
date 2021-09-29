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


public class Topic_15_Wait_II_FindElement {

	WebDriver driver;
//	Actions action;
//	Select select;
//	WebDriverWait expliciWait;
//	JavascriptExecutor jsExecutor;
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
//		expliciWait = new WebDriverWait(driver, 15);
		//Ep kieu tuong minh cho driver
//		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
	}
		
	@Test
	public void TC_01_Find_Element () {
		driver.get("https://www.facebook.com/");
		//Case1: - tim ra co duy nhat 1 Element, 
		// - ko can cho het timeout implicit ma tuong tac voi Element luon
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("thuyhuong@gmail.com");
		
		//Case3: - tim ra nhieu hon 1 Element
		// - khong can cho het timeout cua implicit, no se lay Element dau tien de tuong tac
		// - khong quan tam co bao nhieu matching node
		System.out.println(driver.findElement(By.xpath("//input")).getAttribute("name"));
		System.out.println(driver.findElement(By.xpath("//input")).getAttribute("type"));
		System.out.println(driver.findElement(By.xpath("//input")).getAttribute("value"));
		
		//Case2: - khong tim ra Element nao
		// - cho het timeout cua implicit
		// - trong thoi gian cho cu moi 0,5s se tim lai 1 lan
		// - khi nao cho het timeout cua implicit thi se fail testcase va throw exception: NoSuchElementException
		driver.findElement(By.xpath("//input[@id='address']")).sendKeys("VietNam");
		
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("123456");

	}
	
	@Test
	public void TC_02_Find_Elements () {
		driver.navigate().refresh();
		//Case1: tim ra co duy nhat 1 Element, ko can cho het timeout implicit ma tuong tac voi Element luon
		driver.findElements(By.xpath("//input[@id='email']")).get(0).sendKeys("thuyhuong@gmail.com");
		System.out.println(driver.findElements(By.xpath("//input[@id='email']")).size());
		
		//Case2: - khong tim ra Element nao --> De test 1 Element ko co tren UI va ko co trong DOM
		// - cho het timeout cua implicit
		// - trong thoi gian cho cu moi 0,5s se tim lai 1 lan
		// - khi nao cho het timeout cua implicit thi KHONG fail testcase, tra ve 1 list empty
		// - chuyen qua step tiep theo
		System.out.println(driver.findElements(By.xpath("//input[@id='address']")).size());
		
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("123456");
		
		//Case3: - tim ra nhieu hon 1 Element
		// - khong can cho het timeout cua implicit
		// - luu het tat ca cac Element vao trong List
		List<WebElement> footerlinks = driver.findElements(By.cssSelector("ul.pageFooterLinkList a"));
		System.out.println(footerlinks.size());
		for (WebElement link : footerlinks) {
			System.out.println(link.getText());
		}
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