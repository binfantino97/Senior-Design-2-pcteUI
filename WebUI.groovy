@Grab(group='org.codehaus.groovy', module='groovy-all', version='3.0.7', type='pom')
import groovy.transform.CompileStatic

@Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59')
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.Keys
import org.openqa.selenium.By

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

	public static void openBrowser() 
	{
		ChromeDriver webDriver = null;
		System.setProperty("webdriver.chrome.driver", "E:/David_Main_Folder/Projects/Senior-Design-2-pcteUI" + "\\chromedriver.exe")
		webDriver = new ChromeDriver()
		threadLocal.set(webDriver);
	}

	public static void navigateToUrl(String url) 
	{

		if(threadLocal.get() == null){
			WebUI.openBrowser()
		}
		
		WebDriver webDriver = threadLocal.get();

		webDriver.navigate().to(url);
		// this needs to get the WebDriver that's currently running before
		// it can navigate to a page
		//WebDriver webDriver =
		//webDriver.navigate().to(url)
	}

	// @CompileStatic
	// public static void click(TestObject to) 
	// {

	// }

	// @CompileStatic
	// public static void setText(TestObject to, String text) 
	// {
	// 	webElement.sendKeys(text);
	// }

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
		Thread.sleep(3000);
		Set<String> windows = webDriver.getWindowHandles();
		Thread.sleep(3000);
		print (windows)
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
}