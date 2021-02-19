@Grab(group='org.codehaus.groovy', module='groovy-all', version='3.0.7', type='pom')
import groovy.transform.CompileStatic

@Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59')
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.NoSuchElementException
// Cuno: Haven't implemented TimeoutException, but should consider it for error control. 
// Maybe continuing past some function errors? Maybe for the runtime engine running Test Suites to continue past failed Test Cases.
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.ExpectedConditions

import com.google.common.base.Function
import java.util.concurrent.TimeUnit



public class WebUI 
{
	private static int webUITimeout = 10;

	private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>() {
        @Override
        protected WebDriver initialValue() {
            return null;
        }
    };

	public static void openBrowser(String browser) 
	{
		WebDriver webDriver = null;

		switch(browser) 
		{
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "./" + "\\geckodriver.exe")
				webDriver = new FirefoxDriver()
				threadLocal.set(webDriver);
				break
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "./" + "\\chromedriver.exe")
				webDriver = new ChromeDriver()
				threadLocal.set(webDriver);
				break
			case "":
			default:
				System.setProperty("webdriver.chrome.driver", "./" + "\\geckodriver.exe")
				webDriver = new FirefoxDriver()
				threadLocal.set(webDriver);
				break
		}
	}
	
	public static TestObject findTestObject(String path)
	{
		String userPath = "C:/Users/cunod/Katalon Studio/PCTE Tests/";
		String rs = ".rs";
		
		if(!path.contains("Object Repository/"))
			path = "Object Repository/" + path;

		return new TestObject(ObjectRepositoryParser.getXpath(userPath + path + rs));
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
		WebElement webElement = new WebDriverWait(webDriver, webUITimeout).until(ExpectedConditions.elementToBeClickable(By.xpath(to.getXpath())));
  
		webElement.click();
	}

	@CompileStatic
	public static void setText(TestObject to, String text) 
	{
		WebDriver webDriver = threadLocal.get();
		WebElement webElement = new WebDriverWait(webDriver, webUITimeout).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(to.getXpath())));
		webElement.sendKeys(text);
	}

	@CompileStatic
	public static void sendKeys(TestObject to, String keys) 
	{
		WebDriver webDriver = threadLocal.get();
		WebElement webElement = new WebDriverWait(webDriver, webUITimeout).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(to.getXpath())));
		webElement.sendKeys(keys);
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

	@CompileStatic
	public static void closeWindowIndex(Object index)
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
			webDriver.close();
		}
	}

	@CompileStatic
	public static boolean waitForElementClickable(TestObject to, int timeout) 
	{
		WebDriver webDriver = threadLocal.get();
		WebElement webElement = new WebDriverWait(webDriver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(to.getXpath())));
		return true;
	}

	@CompileStatic
	public static boolean verifyElementAttributeValue(TestObject to, String attributeName, String attributeValue, int timeout) 
	{
		WebDriver webDriver = threadLocal.get();
		WebElement webElement = new WebDriverWait(webDriver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(to.getXpath())));
		if ((webElement.getAttribute(attributeName) != null) && (webElement.getAttribute(attributeName).equals(attributeValue)))
			return true;
		else
			return false;
	}

	@CompileStatic
	public static boolean verifyElementPresent(TestObject to, int timeout) 
	{
		WebDriver webDriver = threadLocal.get();
		boolean elementFound = false;
		elementFound = new FluentWait<WebDriver>(webDriver)
			.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(timeout, TimeUnit.SECONDS)
			.until(new Function<WebDriver, Boolean>() 
			{
				@Override
				public Boolean apply(WebDriver webDriverFun) {
					try {
						webDriverFun.findElement(By.xpath(to.getXpath()))
						return true;
					} catch (NoSuchElementException e) {
						return false;
					}
				}
			})
	}

	@CompileStatic
	public static boolean verifyElementNotPresent(TestObject to, int timeout) 
	{
		WebDriver webDriver = threadLocal.get();
		boolean elementNotFound = false;
		elementNotFound = new FluentWait<WebDriver>(webDriver)
			.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(timeout, TimeUnit.SECONDS)
			.until(new Function<WebDriver, Boolean>()
			{
				@Override
				public Boolean apply(WebDriver webDriverFun) {
					try {
						webDriverFun.findElement(By.xpath(to.getXpath()));
						return false;
					} catch (NoSuchElementException e) {
						return true;
					}
				}
			})
	}

	// This function in Katalon simply finds any webElement in the TestObject. Not anywhere on the web page and not a specific web element.
	@CompileStatic
	public static boolean waitForElementPresent(TestObject to, int timeout) 
	{
		if(!to.getXpath().isEmpty())
			return true;
		else
			return false;
	}

	@CompileStatic
	public static boolean verifyElementText(TestObject to, String text) 
	{
		WebDriver webDriver = threadLocal.get();
		boolean elementFound = false;
		try
		{
			elementFound = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.textToBe(By.xpath(to.getXpath()), text))
			return true;
		}
		catch(TimeoutException)
		{
			return false;
		}
	}

	@CompileStatic
	public static void submit(TestObject to) 
	{
		WebDriver webDriver = threadLocal.get();
		WebElement webElement;
		try
		{
			webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(to.getXpath())))
			webElement.submit();
		}
		catch(TimeoutException)
		{
			// timeout. do nothing
		}
	}
}