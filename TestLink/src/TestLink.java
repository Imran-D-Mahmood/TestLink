import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

public class TestLink{
	public static WebDriver driver;
	//Project API key 
	public static String DEVKEY = "3e6de53edefe6d9e428f06bacc75e1bf"; 
  
	//Test Link URL
	public static String URL = "http://localhost/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
 
	//Test Project Name 
	String testProject = "SLU_Portal";
 
	//Test Plan
	String testPlan = "SLU Functional Test";
 
	//Test Build
	String build = "V1.0";
 

	@BeforeSuite

	public void setUp() throws Exception{
		//Path for browser driver exe file
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Acer\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test(priority = 0)

	public void validTest()throws Exception{
		String result = "";
		String exception = "";

		try{
			driver.manage().window().maximize();
			//Test website URL.
			driver.get("https://i.slu.edu.ph/"); 
			//Usename box and username test data
			driver.findElement(By.xpath("/html/body/center/div/div/div[2]/form/div[1]/div/input")).sendKeys("2161367");
			//Password box and password test data
			driver.findElement(By.xpath("/html/body/center/div/div/div[2]/form/div[2]/div/input")).sendKeys("0 1 2 3 4 5");
			//Xpath of click button
			driver.findElement(By.xpath("/html/body/center/div/div/div[2]/form/div[3]/input")).click();
			
			// fetch the title of the web page and save it into a string variable
			String actualTitle = driver.getTitle();
			// compare the expected title of the page with the actual title of the page and print the result
			if (driver.getPageSource().contains("Home")){
				System.out.println("Verification Successful - You are on the homepage of SLU portal.");
			}else{
				System.out.println("Verification Failed - You are not on the homepage of SLU portal.");
			}

			driver.switchTo().defaultContent();

			result= TestLinkAPIResults.TEST_PASSED;
			//Test case ID
			updateTestLinkResult("SLP-1", null, result);
		}
		
		catch(Exception e){
			result = TestLinkAPIResults.TEST_FAILED;
			exception = e.getMessage();
			updateTestLinkResult("SLP-1", exception, result);
		}
		driver.quit();
	}
	
	@Test(priority = 1)

	public void invalidTest()throws Exception{
		String result = "";
		String exception = "";

		try{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			//Test website URL.
			driver.get("https://i.slu.edu.ph/"); 
			//Usename box and username test data
			driver.findElement(By.xpath("/html/body/center/div/div/div[2]/form/div[1]/div/input")).sendKeys("1234567");
			//Password box and password test data
			driver.findElement(By.xpath("/html/body/center/div/div/div[2]/form/div[2]/div/input")).sendKeys("passwd");
			//Xpath of click button
			driver.findElement(By.xpath("/html/body/center/div/div/div[2]/form/div[3]/input")).click();

			driver.switchTo().defaultContent();

			result= TestLinkAPIResults.TEST_PASSED;
			//Test case ID
			updateTestLinkResult("SLP-2", null, result);
		}
		
		catch(Exception e){
			result = TestLinkAPIResults.TEST_FAILED;
			exception = e.getMessage();
			updateTestLinkResult("SLP-2", exception, result);
		}
	}


	public void updateTestLinkResult(String testCase, String exception, String result) throws TestLinkAPIException{
		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEVKEY,URL);
		testlinkAPIClient.reportTestCaseResult(testProject, testPlan, testCase, build, exception, result);
	}

	@AfterSuite

	public void close(){
		driver.quit();
	}
}