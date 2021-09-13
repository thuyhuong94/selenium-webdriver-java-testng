package testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Topic_03_Group {
	
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass(alwaysRun = true)
	public void beforClass () {
		System.out.println("Open browser");
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
//		Assert.assertTrue(false);
	}
	
	@Test(groups = "user")
	public void TC_01_Create_User () {
		System.out.println("Run TC_01");
	}
  
	@Test(groups = {"user"})
	public void TC_02_View_User () {
		System.out.println("Run TC_02");
	}
  
	@Test(groups = {"user", "admin"})
	public void TC_03_Edit_User () {
		System.out.println("Run TC_03");
	}
  
	@Test(groups = {"admin"})
	public void TC_04_Delete_User () {
		System.out.println("Run TC_04");
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		System.out.println("Close browser");
		driver.quit();
	}
}
