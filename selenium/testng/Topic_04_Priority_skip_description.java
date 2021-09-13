package testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Topic_04_Priority_skip_description {
	
	@Test(priority = 2, description = "Create a  new user for login page")
	public void TC_01_Create_User () {
		System.out.println("Run TC_01");
	}
  
	@Test(priority = 1)
	public void TC_02_View_User () {
		System.out.println("Run TC_02");
	}
  
	@Test
	public void TC_03_Edit_User () {
		System.out.println("Run TC_03");
	}
  
	@Test
	public void TC_04_Delete_User () {
		System.out.println("Run TC_04");
	}
}
