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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_DropDown_List_part1 {

	WebDriver driver;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	List<String> expectedItemTexts;
	long timeinsecond = 2;

	@BeforeClass
	public void beforClass () {
		// Mo trinh duyet firefox
		driver = new FirefoxDriver();
		//set flexible timeout
		expliciWait = new WebDriverWait(driver, 15);
		//Ep kieu tuong minh cho driver
		jsExecutor = (JavascriptExecutor) driver;
		// set thoi gian cho de tim thay element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	 
	}
	
	// Element	
	@Test
	public void TC_01_JQuery () {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		
		selectItemInCustomDropdown("//span[@id='number-button']/span[contains(@class,'ui-selectmenu-icon')]","//ul[@id='number-menu']//div", "2");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']/span[@class ='ui-selectmenu-text' and text()='2']")).isDisplayed());
		selectItemInCustomDropdown("//span[@id='number-button']/span[contains(@class,'ui-selectmenu-icon')]","//ul[@id='number-menu']//div", "15");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']/span[@class ='ui-selectmenu-text' and text()='15']")).isDisplayed());
		selectItemInCustomDropdown("//span[@id='number-button']/span[contains(@class,'ui-selectmenu-icon')]","//ul[@id='number-menu']//div", "3");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']/span[@class ='ui-selectmenu-text' and text()='3']")).isDisplayed());
		
	}
	
	@Test
	public void TC_02_ReactJS () {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		
		selectItemInCustomDropdown("//i[@class='dropdown icon']","//div[@role='option']/span", "Jenny Hess");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='divider text' and text() = 'Jenny Hess']")).isDisplayed());
		selectItemInCustomDropdown("//i[@class='dropdown icon']","//div[@role='option']/span", "Justen Kitsune");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='divider text' and text() = 'Justen Kitsune']")).isDisplayed());
		selectItemInCustomDropdown("//i[@class='dropdown icon']","//div[@role='option']/span", "Stevie Feliciano");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='divider text' and text() = 'Stevie Feliciano']")).isDisplayed());
	}
	@Test
	public void TC_03_VueJS () {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInCustomDropdown("//li[@class='dropdown-toggle']","//ul[@class='dropdown-menu']//a", "First Option");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'First Option')]")).isDisplayed());
		selectItemInCustomDropdown("//li[@class='dropdown-toggle']","//ul[@class='dropdown-menu']//a", "Second Option");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'Second Option')]")).isDisplayed());
		selectItemInCustomDropdown("//li[@class='dropdown-toggle']","//ul[@class='dropdown-menu']//a", "Third Option");
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'Third Option')]")).isDisplayed());
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void selectItemInCustomDropdown (String parentxpath, String childxpath, String expectedItem) {
		// click element to list out all item
		driver.findElement(By.xpath(parentxpath)).click();
		//wait to all element are loaded and save all item into List element
		List<WebElement> allItems = expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childxpath)));
		// find item that is required
		// duyet qua tung item
		for (WebElement item : allItems) {
			//get text cua item va kiem tra xem no bang voi expected item khong
			if (item.getText().trim().equals(expectedItem)) {
				if(!item.isDisplayed()) {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
					sleepinSeconds(3);
				}
				item.click();
				break;
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