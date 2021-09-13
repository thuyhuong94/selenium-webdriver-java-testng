package testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

//@Listeners(testng.Topic_09_Listener.class)
public class Topic_08_DependenciesTest {
	
	@Test
	public void TC_01_Create_User () {
		System.out.println("Run TC_01");
		Assert.assertTrue(false);
	}
  
	@Test(dependsOnMethods = "TC_01_Create_User")
	public void TC_02_View_User () {
		System.out.println("Run TC_02");
	}
  
	@Test(dependsOnMethods = "TC_01_Create_User")
	public void TC_03_Edit_User () {
		System.out.println("Run TC_03");
	}
  
	@Test(dependsOnMethods = "TC_01_Create_User")
	public void TC_04_Delete_User () {
		System.out.println("Run TC_04");
	}
}
