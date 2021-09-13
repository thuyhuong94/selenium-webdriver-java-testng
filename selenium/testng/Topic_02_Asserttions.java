package testng;

import org.testng.Assert;
import org.testng.annotations.Test;


public class Topic_02_Asserttions {
  @Test
  public void TC_01 () {
	  String employeeName = "Nguyen Van A";
	  // dùng để kiểm tra 1 điều kiện mong muốn trả về là đúng (true)
	  Assert.assertTrue(employeeName.equals("Nguyen Van A"));
	  //Assert.assertTrue(employeeName.equals("Nguyen Van B"), "employeeName is not equal");
	  
	  // dùng để kiểm tra 1 điều kiện mong muốn trả về là sai (false)
	  Assert.assertFalse(employeeName.equals("Nguyen Van B"));
	  
	  // dùng để kiểm tra 2 điều kiện là bằng nhau
	  Assert.assertEquals(employeeName, "Nguyen Van A");
	  
	  // kiểm tra dữ liệu bằng null hay khác null
	  Object address = null;
	  Assert.assertNull(address);
	  
	  address = "Ho Chi Mimh City";
	  Assert.assertNotNull(address);
  
  }
}
