package webdriver;

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

public class Topic_13_Javascript_Executor {

	WebDriver driver;
	Actions action;
	Select select;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	long timeinsecond = 2;
	long timeoutdefault = 15;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		//set flexible timeout
		expliciWait = new WebDriverWait(driver, 15);
		//ep kieu driver into action
		action = new Actions(driver);
		//Ep kieu tuong minh cho driver
		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(timeoutdefault, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
	}
		
//	@Test
	public void TC_01_Guru99 () {
		// Truy cap vao trang Guru99
		navigateToUrlByJS("http://live.demoguru99.com/");
		//get domain cua page
		String Guru99domain = (String) executeForBrowser ("return document.domain;");
		Assert.assertEquals(Guru99domain, "live.demoguru99.com");
		//get URL cua page
		String Guru99URL = (String) executeForBrowser ("return document.URL;");
		Assert.assertEquals(Guru99URL, "http://live.demoguru99.com/");
		//Open MOBILE page
		clickToElementByJS ("//a[text()='Mobile']");
		clickToElementByJS ("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div//span[text()='Add to Cart']");
		Assert.assertTrue(areExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));
		
		//Open customer service page
		clickToElementByJS ("//a[text()='Customer Service']");
		String pagetitle = (String) executeForBrowser ("return document.title;");
		Assert.assertEquals(pagetitle, "Customer Service");
		
		// Scroll to bottom page
		scrollToBottomPage();
		sendkeyToElementByJS("//input[@name='email']","thuyhuong" + generatemail());
		clickToElementByJS ("//button[@title='Subscribe']");
		Assert.assertTrue(areExpectedTextInInnerText("Thank you for your subscription."));
		
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		String domain = (String) executeForBrowser ("return document.domain;");
		Assert.assertEquals(domain, "demo.guru99.com");
	}
	
//	@Test
	public void TC_02_Validation_Message () {
		String name = "Thuy Huong";
		String password = "123456";
		String prefixemail = "thuy";
		String emailwithoutsuffix = "thuy@";
		String emailwithwrongsuffix = "thuy@123";
		String suffixemailwithsysbol = "#";
		String rightemail = "thuy@123.net";
		// Truy cap vao trang HTML 5
		navigateToUrlByJS("https://automationfc.github.io/html5/index.html");
		// submit without input name
		clickToElementByJS ("//input[@type='submit']");
		Assert.assertEquals(getElementValidationMessage ("//input[@type='name']"), "Please fill out this field.");
		// submit without input pass
		sendkeyToElementByJS("//input[@type='name']", name);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//input[@type='submit']");
		Assert.assertEquals(getElementValidationMessage ("//input[@type='password']"), "Please fill out this field.");
		
		// submit without input email
		sendkeyToElementByJS("//input[@type='password']", password);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//input[@type='submit']");
		Assert.assertEquals(getElementValidationMessage ("//input[@type='email']"), "Please fill out this field.");
		// send wrong email
		sendkeyToElementByJS("//input[@type='email']",prefixemail);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//input[@type='submit']");
		Assert.assertEquals(getElementValidationMessage ("//input[@type='email']"), "Please include an '@' in the email address. '" + prefixemail + "' is missing an '@'.");
		
		sendkeyToElementByJS("//input[@type='email']",emailwithoutsuffix);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//input[@type='submit']");
		Assert.assertEquals(getElementValidationMessage ("//input[@type='email']"), "Please enter a part following '@'. '" + emailwithoutsuffix + "' is incomplete.");
		
		sendkeyToElementByJS("//input[@type='email']",emailwithwrongsuffix);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//input[@type='submit']");
		Assert.assertEquals(getElementValidationMessage ("//input[@type='email']"), "Please match the requested format.");
		
		sendkeyToElementByJS("//input[@type='email']",emailwithwrongsuffix+suffixemailwithsysbol);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//input[@type='submit']");
		Assert.assertEquals(getElementValidationMessage ("//input[@type='email']"), "A part following '@' should not contain the symbol '" + suffixemailwithsysbol + "'.");
		
		sendkeyToElementByJS("//input[@type='email']",rightemail);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//input[@type='submit']");
		Assert.assertEquals(getElementValidationMessage ("//select"), "Please select an item in the list.");	
	}
	
//	@Test
	public void TC_03_Validation_Message_Ubuntu () {
		String prefixemail = "thuy";
		String emailwithoutsuffix = "thuy@";

		// Truy cap vao trang HTML 5
		navigateToUrlByJS("https://login.ubuntu.com/");
		if (driver.findElement(By.xpath("//button[text()='Accept all and visit site']")).isDisplayed()) {
			clickToElementByJS ("//button[text()='Accept all and visit site']");
		}
		// send wrong email
		String emailXpath = "//div[@class='login-form']//input[@id='id_email']";
		sendkeyToElementByJS(emailXpath, prefixemail);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//button[@type='submit']");
		Assert.assertEquals(getElementValidationMessage (emailXpath), "Please include an '@' in the email address. '" +prefixemail + "' is missing an '@'.");
		
		sendkeyToElementByJS(emailXpath, emailwithoutsuffix);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//button[@type='submit']");
		Assert.assertEquals(getElementValidationMessage (emailXpath), "Please enter a part following '@'. '" + emailwithoutsuffix +"' is incomplete.");
	}
	
//	@Test
	public void TC_04_Remove_Attribute () {
		String firstname, middlename, lastname, fullname, email, emailaddress, loginPageUrl, userid, password, passwordorig, name, gender, DoB, address, city, state, pin, phone;
		firstname = "nguyen";
		middlename = "thuy";
		lastname = "huong";
		fullname = firstname + ' ' + middlename + ' ' + lastname;
		email = "thuyhuong" + generatemail();
		passwordorig = "123456";
		By CusNametextbox = By.name("name");
		By Gender_maleradio = By.xpath("//input[@value='m']");
		By Gender_femaleradio = By.xpath("//input[@value='f']");
		By DoBtextbox = By.name("dob");
		By Addresstextarea = By.name("addr");
		By Citytextbox = By.name("city");
		By Statetextbox = By.name("state");
		By pintextbox = By.name("pinno");
		By phonetextbox = By.name("telephoneno");
		By emailidtextbox = By.name("emailid");
		By passwordTextbox = By.name("password");
		name = "Thuy Huong";
		gender = "female";
		DoB = "1994-08-08";
		address = "QuangTrung str";
		city = "Thu Duc";
		state = "Ho chi Minh city";
		pin = "123456";
		phone = "4410102020";
		emailaddress = "thuyhuong" + generatemail();
		// Truy cap vao trang Guru99
		navigateToUrlByJS("http://live.guru99.com/");
		//Create new account
		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepinSeconds(timeinsecond);
		driver.findElement(By.id("firstname")).sendKeys(firstname);
		sleepinSeconds(timeinsecond);
		driver.findElement(By.id("middlename")).sendKeys(middlename);
		sleepinSeconds(timeinsecond);
		driver.findElement(By.id("lastname")).sendKeys(lastname);
		sleepinSeconds(timeinsecond);
		driver.findElement(By.id("email_address")).sendKeys(email);
		sleepinSeconds(timeinsecond);
		driver.findElement(By.id("password")).sendKeys(passwordorig);
		sleepinSeconds(timeinsecond);
		driver.findElement(By.id("confirmation")).sendKeys(passwordorig);
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		String info = driver.findElement(By.xpath("//a[text()='Change Password']/parent::p")).getText();
		System.out.println(info);
		Assert.assertTrue(info.contains(fullname));
		Assert.assertTrue(info.contains(email));
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//body[contains(@class,'cms-index-index cms-home')]")).isDisplayed());		
		
		// Register user
		navigateToUrlByJS("http://demo.guru99.com/v4");
		loginPageUrl = driver.getCurrentUrl();
		clickToElementByJS ("//a[text()='here']");
		sendkeyToElementByJS("//input[@name='emailid']", email);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//input[@type='submit']");
		sleepinSeconds(timeinsecond);
		userid = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		sleepinSeconds(timeinsecond);
		password = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
		// Login page
		navigateToUrlByJS(loginPageUrl);
		sendkeyToElementByJS("//input[@name='uid']", userid);
		sleepinSeconds(timeinsecond);
		sendkeyToElementByJS("//input[@name='password']", password);
		clickToElementByJS ("//input[@name='btnLogin']");
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.cssSelector("marquee.heading3")).getText(), "Welcome To Manager's Page of Guru99 Bank");
		
		//Create new customer - remove attribute 'type=date' of DoB
		clickToElementByJS("//a[text()='New Customer']");
		sendKeytoElement(CusNametextbox, name);
		sleepinSeconds(timeinsecond);
		if (gender.equals("male")) {
			clicktoElement(Gender_maleradio);
		} else {
			clicktoElement(Gender_femaleradio);
		}
		// remove attribute 'type=date' of DoB
		removeAttributeInDOM("//input[@name='dob']", "type");
		sleepinSeconds(timeinsecond);
		sendKeytoElement(DoBtextbox, DoB);
		sleepinSeconds(timeinsecond);
		sendKeytoElement(Addresstextarea, address);
		sendKeytoElement(Citytextbox, city);
		sendKeytoElement(Statetextbox, state);
		sendKeytoElement(pintextbox, pin);
		sendKeytoElement(phonetextbox, phone);
		sendKeytoElement(emailidtextbox, emailaddress);
		sendKeytoElement(passwordTextbox, password);
		clicktoElement(By.name("sub"));
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.cssSelector(".heading3")).getText(), "Customer Registered Successfully!!!");
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), DoB);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), address);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), emailaddress);	
	}
	
	@Test
	public void TC_05_action_with_hiddenElement () {
		String firstname, middlename, lastname, fullname, email, passwordorig;
		firstname = "nguyen";
		middlename = "thuy";
		lastname = "huong";
		fullname = firstname + ' ' + middlename + ' ' + lastname;
		email = "thuyhuong" + generatemail();
		passwordorig = "123456";
		// Truy cap vao trang Guru99
		navigateToUrlByJS("http://live.guru99.com/");
		// Click to Hidden My account on header
		clickToElementByJS ("//div[@id='header-account']//a[text()='My Account']");
		sleepinSeconds(timeinsecond);
		//Create new account
		clickToElementByJS ("//a[@title='Create an Account']");
		sleepinSeconds(timeinsecond);
		sendkeyToElementByJS ("//input[@id='firstname']", firstname);
		sleepinSeconds(timeinsecond);
		sendkeyToElementByJS ("//input[@id='middlename']", middlename);
		sleepinSeconds(timeinsecond);
		sendkeyToElementByJS ("//input[@id='lastname']", lastname);
		sleepinSeconds(timeinsecond);
		sendkeyToElementByJS ("//input[@id='email_address']", email);
		sleepinSeconds(timeinsecond);
		sendkeyToElementByJS ("//input[@id='password']", passwordorig);
		sleepinSeconds(timeinsecond);
		sendkeyToElementByJS ("//input[@id='confirmation']", passwordorig);
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//button[@title='Register']");
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		String info = driver.findElement(By.xpath("//a[text()='Change Password']/parent::p")).getText();
		System.out.println(info);
		Assert.assertTrue(info.contains(fullname));
		Assert.assertTrue(info.contains(email));
		sleepinSeconds(timeinsecond);
		clickToElementByJS ("//a[text()='Log Out']");
		sleepinSeconds(timeinsecond);
//		Assert.assertTrue(driver.findElement(By.xpath("//body[contains(@class,'cms-index-index cms-home')]")).isDisplayed());		
		String domain = (String) executeForBrowser ("return document.domain;");
		Assert.assertEquals(domain, "live.demoguru99.com");
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
	
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepinSeconds(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElement(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getElement(locator));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
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