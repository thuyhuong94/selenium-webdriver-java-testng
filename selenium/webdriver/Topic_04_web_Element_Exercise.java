package webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_web_Element_Exercise {

	WebDriver driver;
	long timeoutinsecond = 2;
	By mailtextbox = By.id("mail");
	By Radiounder18 = By.id("under_18");
	By edutexterea = By.id("edu");
	By figcaptionUser5 = By.xpath("//h5[contains(text(),'Name: User5')]");
	By javacheckbox = By.id("java");
	By jobrole1 = By.id("job1");
	By devcheckbox = By.id("development");
	By slider1 = By.id("slider-1");
	By password = By.id("password");
	By radiodisable = By.id("radio-disabled");
	By biotextbox = By.id("bio");
	By job3 = By.id("job3");
	By checkdisbaled = By.id("check-disbaled");
	By slider2 = By.id("slider-2");
	
	
	
	
	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		driver = new FirefoxDriver();
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://automationfc.github.io/basic-form/index.html");
	}
	
	// Element	
	@Test
	public void TC_01_verify_Element_isDisplayed_Function () {
		driver.get("https://automationfc.github.io/basic-form/index.html");
			
		if (ElementDisplayed(mailtextbox)) {
			sendKeytoElement(mailtextbox, "Automation FC");
		}
		sleepinSeconds(timeoutinsecond);
		if (ElementDisplayed(Radiounder18)) {
			clicktoElement(Radiounder18);
		}
		sleepinSeconds(timeoutinsecond);
		if (ElementDisplayed(edutexterea)) {
			sendKeytoElement(edutexterea, "Automation FC");
		}
		sleepinSeconds(timeoutinsecond);
		Assert.assertFalse(ElementDisplayed(figcaptionUser5));	
	}
	
	@Test
	public void TC_02_verify_Element_isEnable_Function () {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//Verify elements are enable
		Assert.assertTrue(ElementEnable(mailtextbox));
		Assert.assertTrue(ElementEnable(Radiounder18));
		Assert.assertTrue(ElementEnable(edutexterea));
		Assert.assertTrue(ElementEnable(jobrole1));
		Assert.assertTrue(ElementEnable(devcheckbox));
		Assert.assertTrue(ElementEnable(slider1));
		sleepinSeconds(timeoutinsecond);
		
		//Verify elements are not enable
		Assert.assertFalse(ElementEnable(password));
		Assert.assertFalse(ElementEnable(radiodisable));
		Assert.assertFalse(ElementEnable(biotextbox));
		Assert.assertFalse(ElementEnable(job3));
		Assert.assertFalse(ElementEnable(checkdisbaled));
		Assert.assertFalse(ElementEnable(slider2));
	}
	
	@Test
	public void TC_03_verify_Element_isSelected_Function () {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		clicktoElement(Radiounder18);
		sleepinSeconds(timeoutinsecond);
		clicktoElement(javacheckbox);
		sleepinSeconds(timeoutinsecond);
		
		// verify Radiounder18 and javacheckbox are selected
		Assert.assertTrue(ElementSelected(Radiounder18));
		Assert.assertTrue(ElementSelected(javacheckbox));
		
		clicktoElement(Radiounder18);
		sleepinSeconds(timeoutinsecond);
		clicktoElement(javacheckbox);
		sleepinSeconds(timeoutinsecond);
		
		// verify Radiounder18 is selected
		Assert.assertTrue(ElementSelected(Radiounder18));
		// verify javacheckbox is not selected
		Assert.assertFalse(ElementSelected(javacheckbox));
		
	}
	
	@Test
	public void TC_04_register_at_Mailchimp () {
		driver.get("https://login.mailchimp.com/signup/");
		
		By passwordtextbox = By.id("new_password");
		By marketingchecktbox = By.id("marketing_newsletter");
		By signupbotton = By.cssSelector("#create-account");
		By lowercasecompleted = By.cssSelector(".lowercase-char.completed");
		By uppercasecompleted = By.cssSelector(".uppercase-char.completed");
		By numcasecompleted = By.cssSelector(".number-char.completed");
		By specificcasecompleted = By.cssSelector(".special-char.completed");
		By greatthan8casecompleted = By.cssSelector("li[class='8-char completed']");		
		
		sendKeytoElement(By.id("email"), "ntthuyhuong@gmail.com");
		sendKeytoElement(By.id("new_username"), "ntthuyhuong@gmail.com");
		//Lower case
		sendKeytoElement(passwordtextbox, "huong");
		sleepinSeconds(timeoutinsecond);
		Assert.assertTrue(ElementDisplayed(lowercasecompleted));
		Assert.assertFalse(ElementEnable(signupbotton));
		//Upper case
		sendKeytoElement(passwordtextbox, "HUONG");
		sleepinSeconds(timeoutinsecond);
		Assert.assertTrue(ElementDisplayed(uppercasecompleted));
		Assert.assertFalse(ElementEnable(signupbotton));
		//Number case
		sendKeytoElement(passwordtextbox, "123456");
		sleepinSeconds(timeoutinsecond);
		Assert.assertTrue(ElementDisplayed(numcasecompleted));
		Assert.assertFalse(ElementEnable(signupbotton));
		//specific case
		sendKeytoElement(passwordtextbox, "!@#$%");
		sleepinSeconds(timeoutinsecond);
		Assert.assertTrue(ElementDisplayed(specificcasecompleted));
		Assert.assertFalse(ElementEnable(signupbotton));
		//Great than 8 char case
		sendKeytoElement(passwordtextbox, "thuyhuong94");
		sleepinSeconds(timeoutinsecond);
		Assert.assertTrue(ElementDisplayed(greatthan8casecompleted));
		Assert.assertFalse(ElementEnable(signupbotton));
		//All criteria
		sendKeytoElement(passwordtextbox, "Thuyhuong@123");
		sleepinSeconds(timeoutinsecond);
		Assert.assertFalse(ElementDisplayed(lowercasecompleted));
		Assert.assertFalse(ElementDisplayed(uppercasecompleted));
		Assert.assertFalse(ElementDisplayed(numcasecompleted));
		Assert.assertFalse(ElementDisplayed(specificcasecompleted));
		Assert.assertFalse(ElementDisplayed(greatthan8casecompleted));
		Assert.assertTrue(ElementEnable(signupbotton));
		
		//Verify checkbox is selected
		
		clicktoElement(marketingchecktbox);
		sleepinSeconds(timeoutinsecond);
		Assert.assertTrue(ElementSelected(marketingchecktbox));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public boolean ElementDisplayed (By by) {
		if (driver.findElement(by).isDisplayed()) {
			System.out.println(by + " is displayed");
			return true;
		} else {
			System.out.println(by + " is not displayed");
			return false;
		}
	}
	
	public boolean ElementEnable (By by) {
		if (driver.findElement(by).isEnabled()) {
			System.out.println(by + " is Enabled");
			return true;
		} else {
			System.out.println(by + " is not Enabled");
			return false;
		}
	}
	
	public boolean ElementSelected (By by) {
		if (driver.findElement(by).isSelected()) {
			System.out.println(by + " is selected");
			return true;
		} else {
			System.out.println(by + " is not selected");
			return false;
		}
	}
	
	public void sendKeytoElement (By by, String value) {
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(value);
	}
	
	public void clicktoElement (By by) {
		driver.findElement(by).click();
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