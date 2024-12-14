import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class FDcalculator {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.moneycontrol.com/fixed-income/calculator/state-bank-of-india/fixed-deposit-calculator-SBI-BSB001.html?classic=true");
	String filepath=System.getProperty("user.dir")+"\\testdata\\caldata.xlsx";
	//we need to get the rowcount
	int row=ExcelUtils.getRowCount(filepath,"Sheet1");
	for(int r=1;r<=row;r++)
	{
		//read data from excel
		String Principle=ExcelUtils.getCellData(filepath,"Sheet1",r, 0);
		String roi =ExcelUtils.getCellData(filepath, "Sheet1", r,1);
		String period1=ExcelUtils.getCellData(filepath, "Sheet1", r, 2);
		String period2=ExcelUtils.getCellData(filepath, "Sheet1", r, 3);
		String frequency=ExcelUtils.getCellData(filepath, "Sheet1", r, 4);
		String Expmatvalue=ExcelUtils.getCellData(filepath, "Sheet1", r, 5);
	//pass above values to application
		

		
driver.findElement(By.xpath("//input[@id='principal']")).sendKeys(Principle);
driver.findElement(By.xpath("//input[@id='interest']")).sendKeys(roi);
driver.findElement(By.xpath("//input[@id='tenure']")).sendKeys(period1);
Select drpdwn=new Select(driver.findElement(By.xpath("//*[@id=\"tenurePeriod\"]")));
drpdwn.selectByVisibleText(period2);
Select freqdrp=new Select(driver.findElement(By.xpath("//*[@id='frequency']")));
freqdrp.selectByVisibleText(frequency)
;
driver.findElement(By.xpath("//*[@id=\"fdMatVal\"]/div[2]/a[1]")).click();

//validation //both r string values so we need to convrt to double/numeric format
String Act_matvalue=driver.findElement(By.xpath("//span[@id='resp_matval']//strong")).getText();
if(Double.parseDouble(Expmatvalue)==Double.parseDouble(Act_matvalue))
{
	System.out.println("testpassed");
	ExcelUtils.setCellData(filepath, "Sheet1",r,7,"passed");
}
else
{
	System.out.println("test failed");
	ExcelUtils.setCellData(filepath, "Sheet1", r,7, "failed");
	
}
//after entering one round of execution delete existing info
//so click clear then again go for the next round
Thread.sleep(5000);

driver.findElement(By.xpath("//*[@id=\"fdMatVal\"]/div[2]/a[1]")).click();



	}

}
}
