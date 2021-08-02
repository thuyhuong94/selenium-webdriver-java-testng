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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sun.glass.events.KeyEvent;

public class Topic_14_Upload_Sendkeys {

	WebDriver driver;
	Actions action;
	Select select;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String filename = "01.PNG";
	String filename1 = "02.PNG";
	String filename2 = "03.PNG";
	String filename3 = "04.PNG";
	String uploadOneFilePath = projectPath + "\\uploadFiles\\" + filename;
	String uploadFilePath1 = projectPath + "\\uploadFiles\\" + filename1;
	String uploadFilePath2 = projectPath + "\\uploadFiles\\" + filename2;
	String uploadFilePath3 = projectPath + "\\uploadFiles\\" + filename3;
	String firefoxOneFileAutoIT = projectPath + "\\autoIT\\firefoxUploadOneTime.exe";
	String firefoxMultiFileAutoIT = projectPath + "\\autoIT\\firefoxUploadMultiple.exe";
	String chromeOneFileAutoIT = projectPath + "\\autoIT\\chromeUploadOneTime.exe";
	String chromeMulltiFileAutoIT = projectPath + "\\autoIT\\chromeUploadMultiple.exe";
	
	long timeinsecond = 2;
	long timeoutdefault = 15;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		//set flexible timeout
		expliciWait = new WebDriverWait(driver, 15);
		//Ep kieu tuong minh cho driver
		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(timeoutdefault, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
	}
		
	@Test
	public void TC_01_Sendkey_One_File () {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		//Load file ko can bat Open File Dialog
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(uploadOneFilePath);
		sleepinSeconds(timeinsecond);
		// Verify file load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename +"']")).isDisplayed());
		//Click start upload button
		driver.findElement(By.xpath("//table//button[@class='btn btn-primary start']")).click();
		sleepinSeconds(timeinsecond);
		//Verify file upload success
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename +"']")).isDisplayed());	
	}
	
	@Test
	public void TC_02_Sendkey_Multi_Files () {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		//Load file ko can bat Open File Dialog
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(uploadFilePath1 +"\n" + uploadFilePath2 + "\n" + uploadFilePath3);
		sleepinSeconds(timeinsecond);
		//Verify files load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename1 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename2 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename3 +"']")).isDisplayed());
		//Click start upload button
		List<WebElement> StartUploadbuttons = driver.findElements(By.xpath("//table//button[@class='btn btn-primary start']"));
		for (WebElement Startbutton : StartUploadbuttons) {
			Startbutton.click();
			sleepinSeconds(timeinsecond);
		}
		//Verify files upload success
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename1 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename2 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename3 +"']")).isDisplayed());
	}
	
	@Test
	public void TC_03_Upload_OneFile_By_AutoIT () throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		//Click button to Open File Dialog
		driver.findElement(By.cssSelector(".btn-success")).click();
		//AutoIT work on Open File Dialog
		if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] {firefoxOneFileAutoIT, uploadOneFilePath});
		} else if (driver.toString().contains("chrome")) {
			Runtime.getRuntime().exec(new String[] {chromeOneFileAutoIT, uploadOneFilePath});
		}
		sleepinSeconds(timeinsecond);
		// Verify file load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename +"']")).isDisplayed());
		//Click start upload button
		driver.findElement(By.xpath("//table//button[@class='btn btn-primary start']")).click();
		sleepinSeconds(timeinsecond);
		//Verify file upload success
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename +"']")).isDisplayed());	
	}
	
	@Test
	public void TC_04_Upload_MultiFile_By_AutoIT () throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		//Click button to Open File Dialog
		driver.findElement(By.cssSelector(".btn-success")).click();
		//AutoIT work on Open File Dialog
		if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] {firefoxMultiFileAutoIT, uploadFilePath1, uploadFilePath2, uploadFilePath3});
		} else if (driver.toString().contains("chrome")) {
			Runtime.getRuntime().exec(new String[] {chromeMulltiFileAutoIT, uploadFilePath1, uploadFilePath2, uploadFilePath3});
		}
		sleepinSeconds(timeinsecond);
		//Verify files load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename1 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename2 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename3 +"']")).isDisplayed());
		//Click start upload button
		List<WebElement> StartUploadbuttons = driver.findElements(By.xpath("//table//button[@class='btn btn-primary start']"));
		for (WebElement Startbutton : StartUploadbuttons) {
			Startbutton.click();
			sleepinSeconds(timeinsecond);
		}
		//Verify files upload success
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename1 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename2 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename3 +"']")).isDisplayed());
	}
	
	@Test
	public void TC_05_Upload_OneFile_By_RoBot () {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		//Click button to Open File Dialog
		driver.findElement(By.cssSelector(".btn-success")).click();
		//Robot work on Open File Dialog
		uploadFileByRobot(uploadOneFilePath);
		sleepinSeconds(timeinsecond);
		// Verify file load success
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ filename +"']")).isDisplayed());
		//Click start upload button
		driver.findElement(By.xpath("//table//button[@class='btn btn-primary start']")).click();
		sleepinSeconds(timeinsecond);
		//Verify file upload success
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ filename +"']")).isDisplayed());	
	}
	
	@Test
	public void TC_06_Upload_File () {
		driver.get("https://gofile.io/?t=uploadFiles");
		String parentTabID = driver.getWindowHandle();
		//Load file ko can bat Open File Dialog
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(uploadFilePath1 +"\n" + uploadFilePath2 + "\n" + uploadFilePath3);
		sleepinSeconds(timeinsecond);
		//Wait for download page is visible
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='rowUploadSuccess-downloadPage']")));
		//Verify file upload success
		Assert.assertTrue(driver.findElement(By.xpath("//a[@id='rowUploadSuccess-downloadPage']")).isDisplayed());
		driver.findElement(By.xpath("//a[@id='rowUploadSuccess-downloadPage']")).click();
		SwitchtoWindowByID (parentTabID);
		Assert.assertFalse(driver.getCurrentUrl().equals(parentTabID));
		//Verify file uploaded
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='"+ filename1 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='"+ filename2 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='"+ filename3 +"']")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void uploadFileByRobot (String path)  {
		try {
			//Specify the file location with extension
			StringSelection select = new StringSelection(path);
			//Copy to clipboard
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);
			
			Robot robot = new Robot();
			sleepinSeconds(1);
			//Nhan phim enter go to textbox
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			//Nhan Ctrl-V
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			// nha Ctrl-V
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			//Nhan phim enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void SwitchtoWindowByID (String WindowID) {
		// Get all ID tab/Window at switch time
		Set<String> allWindowIDs = driver.getWindowHandles();
		//Duyet qua all gia tri trong allTabID
		for (String id : allWindowIDs) {
			if (!id.equals(WindowID)) {
				driver.switchTo().window(id);
				break;
			}
		}
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