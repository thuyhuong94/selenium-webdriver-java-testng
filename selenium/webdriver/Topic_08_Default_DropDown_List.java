package webdriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Default_DropDown_List {

	WebDriver driver;
	Select select;
	JavascriptExecutor jsExecutor;
	List<String> expectedItemTexts;
	long timeinsecond = 2;
	String loginPageUrl, emailaddress, FirstName, LastName, DoB, Gender, Day, Month, Year, Company;
	byte flag;
	
	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		driver = new FirefoxDriver();
		//Ep kieu tuong minh cho driver
		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		FirstName = "Huong";
		LastName = "Thuy";
		Gender = "male";
		Day = "1";
		Month = "May";
		Year = "1980";
		emailaddress = "ntthuong" + generatemail();
		Company = "microsoft";
		flag = 0;
		
		expectedItemTexts = new ArrayList<String>(Arrays.asList("Month","January","February","March","April",
											"May","June","July","August","September","October","November","December"));
		 
	}
	
	// Element	
	@Test
	public void TC_01_Nopcommerce () {
		driver.get("https://demo.nopcommerce.com");
		loginPageUrl = driver.getCurrentUrl();
		clicktoElement(By.className("ico-register"));
		if (Gender.equals("male")) {
			clicktoElement(By.id("gender-male"));
			flag = 1;
		} else if (Gender.equals("female")){
			clicktoElement(By.id("gender-female"));
		}
		sendKeytoElement(By.id("FirstName"), FirstName);
		sendKeytoElement(By.id("LastName"), LastName);
		// chon item trong dropdown
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(Day);
		sleepinSeconds(timeinsecond);
		// Verify dung item nay da duoc chon hay chua
		Assert.assertEquals(select.getFirstSelectedOption().getText(), Day);
		// Verify so luong item trong Dropdown
		Assert.assertEquals(select.getOptions().size(), 32);
		// Verify Dropdown khong chon duoc nhieu item cung luc
		Assert.assertFalse(select.isMultiple());
		
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(Month);
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), Month);
		Assert.assertEquals(select.getOptions().size(), 13);
		Assert.assertFalse(select.isMultiple());
		
		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(Year);
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), Year);
		Assert.assertEquals(select.getOptions().size(), 112);
		Assert.assertFalse(select.isMultiple());
		
		sendKeytoElement(By.id("Email"), emailaddress);
		sendKeytoElement(By.id("Company"), Company);
		sendKeytoElement(By.id("Password"), "12345678");
		sendKeytoElement(By.id("ConfirmPassword"), "12345678");
		// Do func click trong selenium ko work trong case nay nen dung func click trong Javascript (ko nen dung cach nay)
		clickByJS(By.id("register-button"));
		sleepinSeconds(timeinsecond);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='result']")).getText(), "Your registration completed");
		clicktoElement(By.className("ico-account"));
		// Verify info dung voi info da input after register successfully
		if (flag == 1) {
			Assert.assertTrue(driver.findElement(By.id("gender-male")).isSelected());
		} else {
			Assert.assertTrue(driver.findElement(By.id("gender-female")).isSelected());
		}
		Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"), FirstName);
		Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"), LastName);
		Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"), emailaddress);
		Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"), Company);
		// khai bao lai select khi chuyensang trang moi
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), Day);
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), Month);
		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), Year);
		//logout
		clicktoElement(By.className("ico-logout"));
		sleepinSeconds(timeinsecond);
		// Verify back to home page by url
		Assert.assertEquals(driver.getCurrentUrl(), loginPageUrl);	
	}
	
	@Test
	public void TC_02_Printout_Options_of_Dropdown () {
		driver.get(loginPageUrl);
		clicktoElement(By.className("ico-register"));
		sleepinSeconds(timeinsecond);
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		List<WebElement> allItems = select.getOptions();
		List<String> actualItemTexts = new ArrayList<>();
		for (WebElement item : allItems) {
			actualItemTexts.add(item.getText());
		}
		System.out.println(actualItemTexts);
		Assert.assertEquals(actualItemTexts, expectedItemTexts);
	}
	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sendKeytoElement (By by, String value) {
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(value);
	}
	
	public void clicktoElement (By by) {
		driver.findElement(by).click();
	}
	public void clickByJS(By by) {
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(by));
	}
	
	public String generatemail () {
		Random rand = new Random();
		return rand.nextInt(9999) + "@gmail.net";
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