package testng;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class Topic_05_DataProvider {
	WebDriver driver;
	WebDriverWait expliciWait;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet Chrome
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		expliciWait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	
	@Test(dataProvider = "new_user_pass")
	public void TC_01_Create_a_new_Account (String firstname, String middlename, String lastname, String email, String password) {
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys(firstname);
		driver.findElement(By.id("middlename")).sendKeys(middlename);
		driver.findElement(By.id("lastname")).sendKeys(lastname);
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		String info = driver.findElement(By.xpath("//a[text()='Change Password']/parent::p")).getText();
		System.out.println(info);
		Assert.assertTrue(info.contains(email));
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();	
	}
	
	@Test(dataProvider = "user_pass")
	public void TC_02_Login_System (String username, String password) {
		driver.get("http://live.demoguru99.com/index.php/customer/account/login/");
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("pass")).sendKeys(password);
		driver.findElement(By.xpath("//button[@name='send']")).click();
		expliciWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h2[text()='Account Information']")));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//p")).getText().contains(username));
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();
	}
	
	@DataProvider(name = "user_pass")
	public Object[][] UserAndPasswordData() {
		return new Object[][] {
			{"Thuyhuong_0004@gmail.com","123456"},
			{"Thuyhuong_0005@gmail.com","123456"},
			{"Thuyhuong_0006@gmail.com","123456"},
		};
	}
	
	@DataProvider(name = "new_user_pass")
	public Object[][] NewUserAndPasswordData() {
		return new Object[][] {
			{"Thuy","Huong","Nguyen","Thuyhuong_0007@gmail.com","123456"},
			{"Thuy","Huong","Nguyen","Thuyhuong_0008@gmail.com","123456"},
			{"Thuy","Huong","Nguyen","Thuyhuong_0009@gmail.com","123456"},
		};
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
}
