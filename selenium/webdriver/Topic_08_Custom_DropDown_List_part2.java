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

public class Topic_08_Custom_DropDown_List_part2 {

	WebDriver driver;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	List<String> expectedItemTexts;
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
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);	 
	}
		
//	@Test
	public void TC_01_Editable () {
		driver.get("http://indrimuska.github.io/jquery-editable-select/");
		String item = "Opel";
		EnterAndSelectItemInCustomDropdown ("//div[@id='default-place']/input", "//div[@id='default-place']//li", item);
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='default-place']/input")).getAttribute("value"), item);
		item = "Volkswagen";
		EnterAndSelectItemInCustomDropdown ("//div[@id='default-place']/input", "//div[@id='default-place']//li", item);
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='default-place']/input")).getAttribute("value"), item);
	}
	
//	@Test
	public void TC_02_Editable_with_TAB () {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		String item = "Andorra";
		EnterAndTabItemInCustomDropdown ("//input[@class='search']", item);
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), item);
		item = "Afghanistan";
		EnterAndTabItemInCustomDropdown ("//input[@class='search']", item);
		sleepinSeconds(timeinsecond);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), item);
	}
	
	@Test
	public void TC_03_MultipleSelect () {
		driver.get("http://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
		String[] selectedmonthlessthan4 = {"February","September","December"};
		String[] selectedmonthgt3andlt12 = {"May","July","February","September","December"};
		String[] selectedAll = {"[Select all]"};
		String parentxpath = "//label[contains(text(),'Group Select')]/parent::div/preceding-sibling::div//button";
		String parentxpathtogettext = "//label[contains(text(),'Group Select')]/parent::div/preceding-sibling::div//button/span";
		String childxpath = "//label[contains(text(),'Group Select')]/parent::div/preceding-sibling::div//span";
		String chilxpathtaginput = "//label[contains(text(),'Group Select')]/parent::div/preceding-sibling::div//input";
		String selecteditempath = "//li[@class='selected']//input";
		SelectMultipleItemInCustomDropdown(parentxpath, childxpath, selecteditempath, selectedmonthlessthan4);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(areItemSelected( parentxpathtogettext, selecteditempath, selectedmonthlessthan4));
		DeSelectMultipleItemInCustomDropdown (parentxpath, chilxpathtaginput);

		sleepinSeconds(timeinsecond);
		SelectMultipleItemInCustomDropdown(parentxpath, childxpath, selecteditempath, selectedmonthgt3andlt12);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(areItemSelected( parentxpathtogettext, selecteditempath, selectedmonthgt3andlt12));
		DeSelectMultipleItemInCustomDropdown (parentxpath, chilxpathtaginput);
        
		SelectMultipleItemInCustomDropdown(parentxpath, childxpath, selecteditempath, selectedAll);
		sleepinSeconds(timeinsecond);
		Assert.assertTrue(areItemSelected( parentxpathtogettext, selecteditempath, selectedAll));
		DeSelectMultipleItemInCustomDropdown (parentxpath, chilxpathtaginput);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void EnterAndSelectItemInCustomDropdown (String parentxpath, String childxpath, String expectedItem) {
		// sendkey to parent textbox
		driver.findElement(By.xpath(parentxpath)).clear();
		driver.findElement(By.xpath(parentxpath)).sendKeys(expectedItem);
		sleepinSeconds(3);
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
	
	public void EnterAndTabItemInCustomDropdown (String parentxpath, String expectedItem) {
		// sendkey to parent textbox
		driver.findElement(By.xpath(parentxpath)).clear();
		driver.findElement(By.xpath(parentxpath)).sendKeys(expectedItem);
		sleepinSeconds(1);
		driver.findElement(By.xpath(parentxpath)).sendKeys(Keys.TAB);
		sleepinSeconds(1);
	}
	
	public void SelectMultipleItemInCustomDropdown (String parentxpath, String childxpath, String selectedItemxpath, String[] expectedItems) {
		// click element to list out all item
		driver.findElement(By.xpath(parentxpath)).click();
		//wait to all element are loaded and save all item into List element
		List<WebElement> allElements = expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childxpath)));
		System.out.println(allElements);
		// find item that is required
		// duyet qua tung item
		for (WebElement childElement : allElements) {
			for (String expecteditem : expectedItems) {
				//get text cua item va kiem tra xem no bang voi expected item khong
				if (childElement.getText().trim().equals(expecteditem)) {
					// Scroll den item can chon (neu thay item thi khong can scroll, neu ko thay thi scroll)
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
					sleepinSeconds(1);	
					childElement.click();
					sleepinSeconds(1);
					
					List<WebElement> allSelectedItems = driver.findElements(By.xpath(selectedItemxpath));
					if (expectedItems.length == allSelectedItems.size()) {
						break;
					}
					
				}
			}
		}
	}
	
	public void DeSelectMultipleItemInCustomDropdown (String parentxpath, String chilxpathtaginput) {
		// click element to list out all item
		driver.findElement(By.xpath("//label[contains(text(),'Group Select')]")).click();
		driver.findElement(By.xpath(parentxpath)).click();
		//wait to all element are loaded and save all item into List element
		List<WebElement> allElements = expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(chilxpathtaginput)));
		// find item that is required
		// duyet qua tung item
		for (WebElement childElement : allElements) {
			if (childElement.isSelected()) {
				// Scroll den item can chon (neu thay item thi khong can scroll, neu ko thay thi scroll)
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
				sleepinSeconds(1);	
				childElement.click();
				sleepinSeconds(1);
			}
		}
		driver.findElement(By.xpath("//label[contains(text(),'Group Select')]")).click();
	}
	
	public boolean areItemSelected (String parentxpath, String selectedItemxpath, String[] months) {
		List<WebElement> ItemSelected = driver.findElements(By.xpath(selectedItemxpath));
		int numberofItemSelected = ItemSelected.size();
		
		String allItemSelected = driver.findElement(By.xpath(parentxpath)).getText();
		System.out.println("Text da chon =" + allItemSelected);
		
		if (numberofItemSelected <= 3 && numberofItemSelected > 0) {
			boolean status = true;
			for (String item : months) {
				if (!allItemSelected.contains(item)) {
					status = false;
					return status;
				}
			}
			return status;
		} else if (numberofItemSelected >= 12) {	
			return driver.findElement(By.xpath(parentxpath)).getText().equals("All selected");
		} else if (numberofItemSelected > 3 && numberofItemSelected < 12) {
			return driver.findElement(By.xpath(parentxpath)).getText().equals(numberofItemSelected + " of 12 selected");
		} else {
			return false;
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