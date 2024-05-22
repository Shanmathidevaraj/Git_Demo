package SeleniumPractice;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.devtools.v109.runtime.model.WebDriverValue;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "G:\\Drivers\\chromedriver.exe");
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
String ProductName="ZARA COAT 3";
		WebDriver driver = new ChromeDriver(co);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("https://rahulshettyacademy.com/client/");
		driver.findElement(By.id("userEmail")).sendKeys("sanjud@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Sh@nm@thi8*");
		driver.findElement(By.id("login")).click();
		
		LandingPage landingpage=new LandingPage(driver);
		
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));
		
		List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod=products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(ProductName)).findFirst().orElse(null);
		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		
		List<WebElement> CartItems=driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
		
		Boolean match=CartItems.stream().anyMatch(product->product.getText().equalsIgnoreCase(ProductName));
		
		Assert.assertTrue(match);
driver.findElement(By.xpath("//li[@class='totalRow']/button")).click();

driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("ind");

wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

driver.findElement(By.xpath("//button[contains(@class,'ta-item')][2]")).click();
driver.findElement(By.xpath("//a[contains(@class,'action__submit ')]")).click();

String confrimMsg=driver.findElement(By.xpath("//h1[contains(@class,'hero-primary')]")).getText();

Assert.assertTrue(confrimMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

driver.close();


  
	}

}
