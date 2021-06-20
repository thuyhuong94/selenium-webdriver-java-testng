package webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Web_Browser_Methods {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	public void TC_01_Browser () {
		
		driver = new FirefoxDriver();
		
		// Open a page (url)
		driver.get("http://live.guru99.com/");
		
		// Get url of current page
		String CurrentPage = driver.getCurrentUrl();
		
		// Get title of current page
		driver.getTitle();
		
		//Get HTML code of current page
		driver.getPageSource();
		
		//Process on tab/window
		driver.getWindowHandle();
		driver.getWindowHandles();
		
//		driver.manage().addCookie(cookie);
		
		// wait a while to find out element 
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		// Back to previous page
		driver.navigate().back();
		
		//Close current tab /process switch/tab window
		driver.close();
		
		//Quit browser 
		driver.quit();
		
		// Alert
		driver.switchTo().alert();
		
		driver.switchTo().frame(1);
		
		driver.switchTo().window("");
		
		driver.manage().window().fullscreen();
		
		driver.manage().window().maximize();
		
		// Get position of browser (so voi do phan giai cua man hinh)
		driver.manage().window().getPosition();
		
		driver.manage().window().getSize();	
				
	}
}
