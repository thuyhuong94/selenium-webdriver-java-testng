package webdriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Button_Radio_Checkbox {

	WebDriver driver;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	long timeinsecond = 2;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		//set flexible timeout
		expliciWait = new WebDriverWait(driver, 15);
		//Ep kieu tuong minh cho driver
		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	 
	}
		
	@Test
	public void TC_01_Button () {
		driver.get("https://www.fahasa.com/customer/account/create");
		driver.findElement(By.xpath("//li[@class='popup-login-tab-item popup-login-tab-login']")).click();
		By xpathofDangnhapButton = By.xpath("//button[@class='fhs-btn-login']");
		// Verify button "Dang nhap" is Disable
		boolean status = driver.findElement(xpathofDangnhapButton).isEnabled();
		System.out.println("Status of Button 'Dang nhap' is " + status);
		Assert.assertFalse(status);
		String phonenumber = "0373111111";
		String password = "123456";
		driver.findElement(By.xpath("//input[@id='login_username']")).sendKeys(phonenumber);
		driver.findElement(By.xpath("//input[@id='login_password']")).sendKeys(password);
		sleepinSeconds(timeinsecond);
		// Verify button "Dang nhap" is Enable
		status = driver.findElement(xpathofDangnhapButton).isEnabled();
		System.out.println("Status of Button 'Dang nhap' after input user info is " + status);
		Assert.assertTrue(status);
		driver.navigate().refresh();
		driver.findElement(By.xpath("//li[@class='popup-login-tab-item popup-login-tab-login']")).click();
		sleepinSeconds(timeinsecond);
		//Trick: 
		//Remove attribute Disable of button "Dang nhap"
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled');", driver.findElement(xpathofDangnhapButton));
		sleepinSeconds(timeinsecond);
		// Verify button "Dang nhap" is Enable
		status = driver.findElement(xpathofDangnhapButton).isEnabled();
		System.out.println("Status of Button 'Dang nhap' after remove attribute 'disable' is " + status);
		Assert.assertTrue(status);
		//Verify error message after click button 'dang nhap'
		driver.findElement(xpathofDangnhapButton).click();
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Số điện thoại/Email']/following-sibling::div[text()='Thông tin này không thể để trống']")).getText(),"Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Mật khẩu']/following-sibling::div[text()='Thông tin này không thể để trống']")).getText(),"Thông tin này không thể để trống");
		// Fron tab "Dang ky", remove attribute va click button 'Dang nhap'cau tab 'Dang nhap'
		driver.navigate().refresh();
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled');", driver.findElement(xpathofDangnhapButton));
		sleepinSeconds(timeinsecond);
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(xpathofDangnhapButton));
		sleepinSeconds(timeinsecond);
		driver.findElement(By.xpath("//li[@class='popup-login-tab-item popup-login-tab-login']")).click();
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Số điện thoại/Email']/following-sibling::div[text()='Thông tin này không thể để trống']")).getText(),"Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Mật khẩu']/following-sibling::div[text()='Thông tin này không thể để trống']")).getText(),"Thông tin này không thể để trống");
		
	}
	
	@Test
	public void TC_02_Default_Checkbox_Radio () {
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
		//handle banner
		//cach1: Close panel/banner
		if (driver.findElement(By.xpath("//button[text()='Accept Cookies']")).isDisplayed()) {
			driver.findElement(By.xpath("//button[text()='Accept Cookies']")).click();
			sleepinSeconds(timeinsecond);
		}
		// click checkbox 'Dual-zone air conditioning', verify no da duoc chon, uncheck
		By xpathofCheckbox = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");
		checktoCheckboxOrRadioButton(xpathofCheckbox);
		sleepinSeconds(timeinsecond);
		// Verify checkbox is selected
		Assert.assertTrue(driver.findElement(xpathofCheckbox).isSelected());
		//uncheck checkbox
		unchecktoCheckbox(xpathofCheckbox);
		sleepinSeconds(timeinsecond);
		Assert.assertFalse(driver.findElement(xpathofCheckbox).isSelected());
		
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		By xpathofRadioButton = By.xpath("//label[text()='1.4 Petrol, 92kW']/preceding-sibling::input");
		checktoCheckboxOrRadioButton(xpathofRadioButton);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(xpathofRadioButton).isSelected());
		xpathofRadioButton = By.xpath("//label[text()='2.0 Diesel, 103kW']/preceding-sibling::input");
		checktoCheckboxOrRadioButton(xpathofRadioButton);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(xpathofRadioButton).isSelected());
	}
	
	@Test
	public void TC_03_Custom_Checkbox_Radio () {
		driver.get("https://material.angular.io/components/radio/examples");
		// check Radio 'Summer'with tag input bi an (the input ko click duoc nhung co the verify duoc)
		// Cach 1:dung the span de click + the input de verify
		By xpathofRadio_input = By.xpath("//input[@value='Summer']");
		By xpathofRadio_span = By.xpath("//span[contains(text(),'Summer')]");
		if (!driver.findElement(xpathofRadio_input).isSelected()) {
			driver.findElement(xpathofRadio_span).click();
			sleepinSeconds(timeinsecond);
		}
		Assert.assertTrue(driver.findElement(xpathofRadio_input).isSelected());
		
		// Cach 2:dung jsExecutor click the input + the input de verify
		driver.navigate().refresh();
		sleepinSeconds(timeinsecond);
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(xpathofRadio_input));
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(xpathofRadio_input).isSelected());
		
		driver.get("https://material.angular.io/components/checkbox/examples");
		String[] xpathArrays = {"//span[contains(text(),'Checked')]/preceding-sibling::span/input[@type='checkbox']",
				"//span[contains(text(),'Indeterminate')]/preceding-sibling::span/input[@type='checkbox']"};
		checktoMultiCheckbox(xpathArrays);
		sleepinSeconds(timeinsecond);
		for (String string : xpathArrays) {
			Assert.assertTrue(driver.findElement(By.xpath(string)).isSelected());
		}
		unchecktoMultiCheckbox(xpathArrays);
		for (String string : xpathArrays) {
			Assert.assertFalse(driver.findElement(By.xpath(string)).isSelected());
		}
	}
	@Test
	public void TC_04_Custom_Checkbox_Radio () {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		// Verify Radio 'Can Tho' is deselected
		Assert.assertTrue(driver.findElement(By.xpath("//div[@data-value='Cần Thơ' and @aria-checked='false']")).isDisplayed());
		// click to select 'Can Tho'
		driver.findElement(By.xpath("//div[@data-value='Cần Thơ']")).click();
		sleepinSeconds(timeinsecond);
		// Verify Radio 'Can Tho' is selected
		Assert.assertTrue(driver.findElement(By.xpath("//div[@data-value='Cần Thơ' and @aria-checked='true']")).isDisplayed());
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void checktoCheckboxOrRadioButton (By by) {
		WebElement checkbox = driver.findElement(by);
		if (!checkbox.isSelected()) {
			checkbox.click();
		}
	}
	
	public void unchecktoCheckbox (By by) {
		WebElement checkbox = driver.findElement(by);
		if (checkbox.isSelected()) {
			checkbox.click();
		}
	}
	
	public void checktoMultiCheckbox (String[] xpathArrays) {
		for (String xpathCheckbox : xpathArrays) {
			WebElement webElement = driver.findElement(By.xpath(xpathCheckbox));
			if (!webElement.isSelected()) {
				jsExecutor.executeScript("arguments[0].click();", webElement);
				sleepinSeconds(timeinsecond);
			}
		}
	}
	
	public void unchecktoMultiCheckbox (String[] xpathArrays) {
		for (String xpathCheckbox : xpathArrays) {
			WebElement webElement = driver.findElement(By.xpath(xpathCheckbox));
			if (webElement.isSelected()) {
				jsExecutor.executeScript("arguments[0].click();", webElement);
				sleepinSeconds(timeinsecond);
			}
		}
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