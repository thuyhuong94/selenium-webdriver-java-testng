package webdriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
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

public class Topic_10_User_Interactions_part1 {

	WebDriver driver;
	Actions action;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	long timeinsecond = 2;

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
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);	 
	}
		
	@Test
	public void TC_01_HoverElement_Tooltip () {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		//Hover chuot vao textbox
		action.moveToElement(driver.findElement(By.id("age"))).perform();
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ui-tooltip-content' and text()='We ask for your age only for statistical purposes.']")).isDisplayed());
	}
	
	@Test
	public void TC_02_HoverToElement () {
		driver.get("http://www.myntra.com/");
		//Hover chuot vao menu
		action.moveToElement(driver.findElement(By.xpath("//a[@class='desktop-main' and text()='Kids']"))).perform();
		driver.findElement(By.xpath("//a[@class='desktop-categoryName' and text()='Home & Bath']")).click();
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='breadcrumbs-crumb' and text()='Kids Home Bath']")).isDisplayed());
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.myntra.com/kids-home-bath");
	}
	
	@Test
	public void TC_03_HoverToElement () {
		driver.get("https://hn.telio.vn/");
		//Hover chuot vao menu
		action.moveToElement(driver.findElement(By.xpath("//main[@class='page-main']//span[text()='Bánh kẹo']"))).perform();
		List<WebElement> submenu = driver.findElements(By.xpath("//main[@class='page-main']//span[text()='Bánh kẹo']/parent::a/following-sibling::ul//a"));
		String[] expectedsubmenu = {"Lương khô","Bánh quy - Bánh xốp","Bánh gạo","Kẹo","Bánh bông lan","Bánh hộp tết","Hạt"};
		int count = 0;
		if (expectedsubmenu.length == submenu.size()) {
			for (WebElement childElement : submenu) {
				for (String expecteditem : expectedsubmenu) {
					//get text cua item va kiem tra xem no bang voi expected item khong
					if (childElement.getText().trim().equals(expecteditem)) {
						count += 1;	
					}
				}
			}
			System.out.println("so phan tu bang nhau =" + count);
			Assert.assertEquals(count, expectedsubmenu.length);
		} else {
			Assert.assertEquals(submenu.size(), expectedsubmenu.length);
		}
	}
	
	@Test
	public void TC_04_ClickAndHold_SelectMulti () {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> rectangleNumber = driver.findElements(By.cssSelector("#selectable>li"));
		System.out.println("so luong phan tu =" + rectangleNumber.size());
		
		//Click chuot va hold vao Element dau tien -> hover chuot den element dich
		action.clickAndHold(rectangleNumber.get(0)).moveToElement(rectangleNumber.get(3)).release().perform();
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElements(By.cssSelector("#selectable>li.ui-selected")).size(), 4);
	}
	
	@Test
	public void TC_05_ClickAndHold_SelectRandom () {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> rectangleNumber = driver.findElements(By.cssSelector("#selectable>li"));
		System.out.println("so luong phan tu =" + rectangleNumber.size());
		
		//Press down and hold Key Control -> click random Elements (1-6-11)
		action.keyDown(Keys.CONTROL).perform();
		action.click(rectangleNumber.get(0)).click(rectangleNumber.get(5)).click(rectangleNumber.get(10)).perform();
		action.keyUp(Keys.CONTROL).perform();
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElements(By.cssSelector("#selectable>li.ui-selected")).size(), 3);
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