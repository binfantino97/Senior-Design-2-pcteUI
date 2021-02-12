@Grab(group='org.codehaus.groovy', module='groovy-all', version='3.0.7', type='pom')
import groovy.transform.CompileStatic

@Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59')
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions

import java.time.Duration;

// This might not work to pull TestObject class. May need to import locally.
// Should probably find a way to use TestObjects without katalon's class
// @Grab(group='com.katalon', module='utils', version='1.0.15')
// import com.kms.katalon.core.testobject.*

public class WebUI 
{
	private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>() {
        @Override
        protected WebDriver initialValue() {
            return null;
        }
    };
		
	public static void main(String[] args)
	{
		// WebUI.maximizeWindow();
		// WebUI.delay(1);
		// WebUI.closeWindowTitle("Google");
		//ObjectRepositoryParser O = new ObjectRepositoryParser();

		WebUI.openBrowser('');
		WebUI.navigateToUrl('https://www.tomshardware.com/');
		WebUI.click(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Google/div_media only screen and (max-width380px)g_4bbbd6'));
		//WebUI.setText(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Google/input_Sign in_q'), 'cheese');
	}

	public static void openBrowser(String browser) 
	{
		WebDriver webDriver = null;

		switch(browser) 
		{
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "E:/David_Main_Folder/Projects/Katalon_Studio_Windows_64-7.7.1/configuration/resources/drivers/firefox_win64" + "\\geckodriver.exe")
				webDriver = new FirefoxDriver()
				threadLocal.set(webDriver);
				break
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "E:/David_Main_Folder/Projects/Katalon_Studio_Windows_64-7.7.1/configuration/resources/drivers/chromedriver_win32" + "\\chromedriver.exe")
				webDriver = new ChromeDriver()
				threadLocal.set(webDriver);
				break
			case "":
			default:
				System.setProperty("webdriver.chrome.driver", "E:/David_Main_Folder/Projects/Katalon_Studio_Windows_64-7.7.1/configuration/resources/drivers/chromedriver_win32" + "\\chromedriver.exe")
				webDriver = new ChromeDriver()
				threadLocal.set(webDriver);
				break
		}
	}

	public static void navigateToUrl(String url) 
	{
		if(threadLocal.get() == null)
		{
			WebUI.openBrowser()
		}
		
		WebDriver webDriver = threadLocal.get();
		webDriver.navigate().to(url);
	}

	@CompileStatic
	public static void click(TestObject to) 
	{
		WebDriver webDriver = threadLocal.get();
		WebElement webElement = new WebDriverWait(webDriver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='exitintentform']/div[4]/input")));
  
		webElement.click();
	}

	@CompileStatic
	public static void setText(TestObject to, String text) 
	{
		WebDriver webDriver = threadLocal.get();
		
		WebElement webElement = new WebDriverWait(webDriver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(to.xpaths[0])));
		webElement.sendKeys(text);
	}

	//@CompileStatic // disallows groovy methods for some reason
	public static void delay(long delayTime) 
	{
		sleep(delayTime*1000)
	}

	@CompileStatic
	public static void closeBrowser() 
	{
		WebDriver webDriver = threadLocal.get();
		webDriver.quit();
	}

	@CompileStatic
	public static void maximizeWindow() 
	{
		// need to manually resize the window if test is being run on Mac
		WebDriver webDriver = threadLocal.get();
		webDriver.manage().window().maximize();
	}

	@CompileStatic
	public static void closeWindowTitle(String title)
	{
		WebDriver webDriver = threadLocal.get();

		if(webDriver == null)
		{
			return;
		}

		Set<String> windows = webDriver.getWindowHandles();
		for (String windowTitle : windows) 
		{
			webDriver = webDriver.switchTo().window(windowTitle);
			if (webDriver.getTitle().equals(title)) 
			{
				webDriver.close();
			}
		}
	}

	@CompileStatic
	public static void switchToWindowTitle(String title)
	{
		WebDriver webDriver = threadLocal.get();

		if(webDriver == null)
		{
			return;
		}

		Set<String> windows = webDriver.getWindowHandles();
		for (String windowTitle : windows) 
		{
			webDriver = webDriver.switchTo().window(windowTitle);
			if (webDriver.getTitle().equals(title)) 
			{
				break;
			}
		}
	}

	@CompileStatic
	public static void switchToWindowIndex(Object index)
	{
		WebDriver webDriver = threadLocal.get();

		if(webDriver == null || index == null)
		{
			return;
		}
		
		Integer parsedIndex = Integer.parseInt(String.valueOf(index));

		List<String> windows = new ArrayList<String>(webDriver.getWindowHandles());
		if (parsedIndex >= 0 && parsedIndex < windows.size()) 
		{
			webDriver.switchTo().window(windows.get(parsedIndex));
		}
	}

	public static TestObject findTestObject(String path)
	{
		String userPath = "C:/Users/cunod/Katalon Studio/PCTE Tests/";
		String rs = ".rs";
		
		return new TestObject(ObjectRepositoryParser.getXpath(userPath + path + rs));
	}
}