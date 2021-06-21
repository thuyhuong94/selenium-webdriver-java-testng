package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Textbox_TextArea {

	WebDriver driver;
	String emailaddress, loginPageUrl, userid, password, name, gender, DoB, address, city, state, pin, phone, custID;
	String editemailaddress, editaddress, editcity, editstate, editpin, editphone;
	long timeinsecond = 2;
	// UI (New customer/ Edit customer)
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
	
	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		driver = new FirefoxDriver();
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://demo.guru99.com/v4");
		// New customer info
		name = "Thuy Huong";
		gender = "female";
		DoB = "1994-08-08";
		address = "QuangTrung str";
		city = "Thu Duc";
		state = "Ho chi Minh city";
		pin = "123456";
		phone = "4410102020";
		emailaddress = "ntthuong" + generatemail();
		// edit customer info
		editaddress = "QuangTrung1 str";
		editcity = "Dong Anh";
		editstate = "Ha Noi";
		editpin = "654321";
		editphone = "4420203030";
		editemailaddress = "huongthuy" + generatemail(); 
	}
	
	// Element	
	@Test
	public void TC_01_Register () {
		loginPageUrl = driver.getCurrentUrl();
		clicktoElement(By.xpath("//a[text()='here']"));
		sendKeytoElement(By.name("emailid"), emailaddress);
		clicktoElement(By.name("btnLogin"));
		sleepinSeconds(timeinsecond);
		userid = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		sleepinSeconds(timeinsecond);
		password = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();	
	}
	
	@Test
	public void TC_02_Login () {
		driver.get(loginPageUrl);
		sendKeytoElement(By.name("uid"), userid);
		sleepinSeconds(timeinsecond);
		sendKeytoElement(By.name("password"), password);
		clicktoElement(By.name("btnLogin"));
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.cssSelector("marquee.heading3")).getText(), "Welcome To Manager's Page of Guru99 Bank");
	}
	
	@Test
	public void TC_03_Create_New_Customer () {
		clicktoElement(By.xpath("//a[text()='New Customer']"));
		sendKeytoElement(CusNametextbox, name);
		if (gender.equals("male")) {
			clicktoElement(Gender_maleradio);
		} else {
			clicktoElement(Gender_femaleradio);
		}
		sendKeytoElement(DoBtextbox, DoB);
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
		
		custID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		
	}
	
	@Test
	public void TC_04_Edit_customer () {
		clicktoElement(By.xpath("//a[text()='Edit Customer']"));
		sendKeytoElement(By.name("cusid"), custID);
		clicktoElement(By.name("AccSubmit"));
		sleepinSeconds(timeinsecond);
		Assert.assertFalse(driver.findElement(By.name("name")).isEnabled());
		Assert.assertFalse(driver.findElement(By.name("gender")).isEnabled());
		Assert.assertFalse(driver.findElement(By.name("dob")).isEnabled());
		
		Assert.assertEquals(driver.findElement(CusNametextbox).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(By.name("gender")).getAttribute("value"), gender);
		Assert.assertEquals(driver.findElement(DoBtextbox).getAttribute("value"), DoB);
		Assert.assertEquals(driver.findElement(Addresstextarea).getText(), address);
		Assert.assertEquals(driver.findElement(Citytextbox).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(Statetextbox).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(pintextbox).getAttribute("value"), pin);
		Assert.assertEquals(driver.findElement(phonetextbox).getAttribute("value"), phone);
		Assert.assertEquals(driver.findElement(emailidtextbox).getAttribute("value"), emailaddress);
		
		// Edit customer
		sendKeytoElement(Addresstextarea, editaddress);
		sendKeytoElement(Citytextbox, editcity);
		sendKeytoElement(Statetextbox, editstate);
		sendKeytoElement(pintextbox, editpin);
		sendKeytoElement(phonetextbox, editphone);
		sendKeytoElement(emailidtextbox, editemailaddress);
		clicktoElement(By.name("sub"));
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.cssSelector(".heading3")).getText(), "Customer details updated Successfully!!!");
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), DoB);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), editaddress);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editcity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), editstate);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editpin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editphone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), editemailaddress);
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