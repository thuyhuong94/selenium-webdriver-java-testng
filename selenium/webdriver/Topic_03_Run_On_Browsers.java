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

public class Topic_03_Run_On_Browsers {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@Test
	public void TC_01_Run_On_Firefox () {
		// Older firefox version (47.0.2)
		driver = new FirefoxDriver();
		driver.get("http://live.guru99.com/");
		driver.quit();
	}
	@Test
	public void TC_02_Run_On_Chrome () {
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://live.guru99.com/");
		driver.quit();
	}
	@Test
	public void TC_03_Run_On_Edge_Chromium () {
		System.setProperty("webdriver.edge.driver", projectPath + "/browserDrivers/msedgedriver.exe");
		driver = new EdgeDriver();
		driver.get("http://live.guru99.com/");
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
