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

public class Topic_15_Wait_IV_Explicit {

	WebDriver driver;
	WebDriverWait expliciWait;
	String projectPath = System.getProperty("user.dir");
	By startButton = By.cssSelector("#start>button");
	By loadingIcon = By.cssSelector("div#loading");
	By helloWorldtext = By.xpath("//h4[text()='Hello World!']");
	
	String filename1 = "02.PNG";
	String filename2 = "03.PNG";
	String uploadFilePath1 = projectPath + "\\uploadFiles\\" + filename1;
	String uploadFilePath2 = projectPath + "\\uploadFiles\\" + filename2;

	long timeinsecond = 2;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
//		System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");
//		driver = new FirefoxDriver();
		//set flexible timeout
		expliciWait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
	}
		
	@Test
	public void TC_01_Visible () {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(startButton).click();
		
		// Wait for display text "Hello World!"
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(helloWorldtext));
		
		Assert.assertTrue(driver.findElement(helloWorldtext).isDisplayed());

	}
	
	@Test
	public void TC_02_Invisible () {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(startButton).click();
		
		// Wait for loading Icon is undisplayed
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		
		Assert.assertTrue(driver.findElement(helloWorldtext).isDisplayed());
	}
	
	@Test
	public void TC_03_Ajax_loading () {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		// Wait Date Picker is displayed
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceholder1_Panel1")));
		// in ra ngay da chon: khongg co ngay nao duoc chon
		String outputText = driver.findElement(By.id("ctl00_ContentPlaceholder1_Label1")).getText();
		Assert.assertEquals(outputText, "No Selected Dates to display.");
		System.out.println("Selected Date: " + outputText + "\n");
		
		// Click today
		expliciWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[text()='27']")));
		driver.findElement(By.xpath("//td/a[text()='27']")).click();
		
		// Wait for Ajax loading is invisible
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[not(@style='display:none;')]/div[@class='raDiv']")));
	
		//Verify today duoc select va update tren Select Date
		expliciWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td[@class='rcSelected']/a[text()='27']")));
		Assert.assertTrue(driver.findElement(By.xpath("//td[@class='rcSelected']/a[text()='27']")).isDisplayed());
		
		outputText = driver.findElement(By.id("ctl00_ContentPlaceholder1_Label1")).getText();
		Assert.assertEquals(outputText, "Friday, August 27, 2021");
		System.out.println("Selected Date: " + outputText + "\n");
	}
	
	@Test
	public void TC_04_Upload_File () {
		driver.get("https://gofile.io/?t=uploadFiles");

		//Wait Add flie button visible
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add files']")));
		
		//Load file ko can bat Open File Dialog
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(uploadFilePath1 +"\n" + uploadFilePath2);
		
		// Wait Select Server undisplayed
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#rowUploadProgress-selectServer")));
		
		//Wait progess bar icons invisible
		expliciWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.xpath("div.progress"))));
	
		//Wait success messagge display
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Your files have been successfully uploaded']")));
		Assert.assertTrue(driver.findElement(By.xpath("//h5[text()='Your files have been successfully uploaded']")).isDisplayed());
		
		//Wait show file button clickable
		expliciWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#rowUploadSuccess-showFiles")));
		driver.findElement(By.cssSelector("button#rowUploadSuccess-showFiles")).click();
		
		//Verify files are uploaded
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + filename1 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='contentName' and text()='" + filename2 + "']")).isDisplayed());
	
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