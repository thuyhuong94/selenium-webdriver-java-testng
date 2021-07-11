package webdriver;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_User_Interactions_part2 {

	WebDriver driver;
	Actions action;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String jsHelper = projectPath + "\\dragAndDrop\\drag_and_drop_helper.js";
	//String jqueryPath = projectPath + "\\dragAndDrop\\jquery_load_helper.js";
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
		driver.manage().window().maximize();
	}
		
	@Test
	public void TC_01_Double_Click () {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		//scroll to Element
		WebElement item = driver.findElement(By.xpath("//button[text()='Double click me']"));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
		
		//Double click to Element
		action.doubleClick(item).perform();
		sleepinSeconds(timeinsecond);
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Hello Automation Guys!']")).isDisplayed());
	}
	
	@Test
	public void TC_02_Right_Click () {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		//Right click	
		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		sleepinSeconds(timeinsecond);
		//Hover to Quit menu
		action.moveToElement(driver.findElement(By.cssSelector(".context-menu-icon-quit"))).perform();
		
		//quit menu updated
		Assert.assertTrue(driver.findElement(By.cssSelector(".context-menu-icon-quit.context-menu-visible.context-menu-hover")).isDisplayed());
		
		//Click to Quit menu
		action.click(driver.findElement(By.cssSelector(".context-menu-icon-quit"))).perform();
		sleepinSeconds(timeinsecond);
		
		//Alert is presence
		driver.switchTo().alert().accept();
		
		//Verify Quit menu without "hover"
		Assert.assertFalse(driver.findElement(By.cssSelector(".context-menu-icon-quit")).isDisplayed());
	}
	
	@Test
	public void TC_03_DragandDrop_HTML4 () {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		
		WebElement origSmallCircle = driver.findElement(By.id("draggable"));
		WebElement termBigCircle = driver.findElement(By.id("droptarget"));
		
		action.dragAndDrop(origSmallCircle, termBigCircle).perform();
		sleepinSeconds(timeinsecond);
		
		Assert.assertEquals(termBigCircle.getText(), "You did great!");
		//Verify backround color of termBigCircle
		
		Assert.assertEquals(Color.fromString(termBigCircle.getCssValue("background-color")).asHex(), "#03a9f4");
	}
	
	@Test
	public void TC_04_DragandDrop_HTML5_JS_JQuery_Css () throws IOException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		// Inject Jquery lib to site
		//String jqueryscript = readFile(jqueryPath);
		//jsExecutor.executeScript(jqueryscript);
		//A to B
		String javascript = readFile(jsHelper) + "$('#column-a').simulateDragDrop({ dropTarget:'#column-b'});";
		jsExecutor.executeScript(javascript);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
		// B to A
		jsExecutor.executeScript(javascript);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());
	}
	
	@Test
	public void TC_05_DragandDrop_HTML5_Robot_Xpath_Css () throws AWTException {
		driver.get("https://automationfc.github.io/drag-drop-html5/");
		//A to B
		drag_the_and_drop_html5_by_xpath("//div[@id='column-a']", "//div[@id='column-b']");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
		// B to A
		drag_the_and_drop_html5_by_xpath("//div[@id='column-a']", "//div[@id='column-b']");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String readFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}
	
	public void drag_the_and_drop_html5_by_xpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
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