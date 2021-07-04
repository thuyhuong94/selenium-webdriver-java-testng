package webdriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Alert {

	WebDriver driver;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	Alert alert;
	String projectPath = System.getProperty("user.dir");
	String authautoIT = projectPath + "/autoIT/authen_chrome.exe";
	long timeinsecond = 2;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		//set flexible timeout
		expliciWait = new WebDriverWait(driver, 15);
		//switch driver to alert
		//Ep kieu tuong minh cho driver
		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);	 
	}
		
//	@Test
	public void TC_01_Accept_Alert () {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		//Wait for alert appear and switch to alert
		alert = expliciWait.until(ExpectedConditions.alertIsPresent());
		sleepinSeconds(timeinsecond);
		//Verify alert text
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		//Accept alert
		alert.accept();
		//Verify messagge at result
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked an alert successfully");	
	}
	
	@Test
	public void TC_02_Confirm_Alert () {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		//Wait for alert appear and switch to alert
		alert = expliciWait.until(ExpectedConditions.alertIsPresent());
		sleepinSeconds(timeinsecond);
		//Verify alert text
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		//Cancel alert
		alert.dismiss();
		//Verify messagge at result
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked: Cancel");
	}
	
	@Test
	public void TC_03_Confirm_Alert () {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		//Wait for alert appear and switch to alert
		alert = expliciWait.until(ExpectedConditions.alertIsPresent());
		sleepinSeconds(timeinsecond);
		//Verify alert text
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		//nhap text vao alert
		String fullname = "ThuyHuong";
		alert.sendKeys(fullname);
		sleepinSeconds(timeinsecond);
		//Cancel alert
		alert.accept();
		sleepinSeconds(timeinsecond);
		//Verify messagge at result
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You entered: " + fullname);
	}
	
	@Test
	public void TC_04_Authentication_Alert_bypassoverlink () {
		driver.get("https://the-internet.herokuapp.com/");
		String hrefValue = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		String username = "admin";
		String password = "admin";
		String url = passValuetoUrl(hrefValue, username, password);
		driver.get(url);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());
	}
	
	@Test
	public void TC_05_Authentication_Alert_useAutoIT () throws IOException {
		driver.get("https://the-internet.herokuapp.com/");
		// Bat alert: Execute script truoc
		String username = "admin";
		String password = "admin";
		Runtime.getRuntime().exec(new String[] {authautoIT, username, password});
		//Mo app sau
		driver.findElement(By.xpath("//a[text()='Basic Auth']")).click();;
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String passValuetoUrl (String url, String username, String password) {
		String[] hrefValue = url.split("//");
		url = hrefValue[0] + "//" + username + ":" + password + "@" + hrefValue[1];
		System.out.println("New url = " + url);
		return url;
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