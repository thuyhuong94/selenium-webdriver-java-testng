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

import com.sun.glass.events.KeyEvent;

public class Topic_15_Wait_III_Implicit {

	WebDriver driver;
//	Actions action;
//	Select select;
//	WebDriverWait expliciWait;
//	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	By startButton = By.cssSelector("#start>button");
	By loadingIcon = By.cssSelector("div#loading");
	By helloWorldtext = By.xpath("//h4[text()='Hello World!']");
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
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
	}
		
	@Test
	public void TC_01_Dont_Set () {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(startButton).click();
		
		Assert.assertTrue(driver.findElement(helloWorldtext).isDisplayed());

	}
	
	@Test
	public void TC_02_Set_2s () {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		driver.findElement(startButton).click();
		
		Assert.assertTrue(driver.findElement(helloWorldtext).isDisplayed());
	}
	
	@Test
	public void TC_03_Set_6s () {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		
		driver.findElement(startButton).click();
		
		Assert.assertTrue(driver.findElement(helloWorldtext).isDisplayed());
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