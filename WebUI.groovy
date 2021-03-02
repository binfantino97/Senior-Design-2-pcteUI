@Grab(group='org.codehaus.groovy', module='groovy-all', version='3.0.7', type='pom')
import groovy.transform.CompileStatic

@Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59')
import org.openqa.selenium.By
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions

import org.junit.Assert
import com.google.common.base.Function
import java.util.concurrent.TimeUnit

public class WebUI 
{
	private static int webUITimeout = 10;
	private static FailureHandling defaultFailure = FailureHandling.STOP_ON_FAILURE;

	public static void setWebUITimeout(int timeout)
	{
		this.webUITimeout = timeout;
	}

	public static void setDefaultFailureHandling(FailureHandling failure)
	{
		this.defaultFailure = failure;
	}

	private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>() {
        @Override
        protected WebDriver initialValue() {
            return null;
        }
    };

	//public static TestObject findTestObject(String path, Map) // need to make this function

	// PASSING TO FAILURE HANDLING METHODS //
	//public static TestObject findTestObject(String path) // no need for FailureHandling
	public static void openBrowser(String browser) { openBrowser(browser, defaultFailure); }
	public static void navigateToUrl(String url) { navigateToUrl(url, defaultFailure); }
	public static void click(TestObject to) { click(to, defaultFailure); }
	public static void setText(TestObject to, String text) { setText(to, text, defaultFailure); }
	public static void sendKeys(TestObject to, String keys) { sendKeys(to, keys, defaultFailure); }
	public static void deleteAllCookies() { deleteAllCookies(defaultFailure); }
	public static void delay(long delayTime) { delay(delayTime, defaultFailure); }
	public static void closeBrowser() {	closeBrowser(defaultFailure); }
	public static void maximizeWindow() { maximizeWindow(defaultFailure); }
	public static void switchToWindowTitle(String title) { switchToWindowTitle(title, defaultFailure); }
	public static void closeWindowTitle(String title) { closeWindowTitle(title, defaultFailure); }
	public static void switchToWindowIndex(Object index) { switchToWindowIndex(index, defaultFailure); }
	public static void closeWindowIndex(Object index) { closeWindowIndex(index, defaultFailure); }
	public static boolean waitForElementClickable(TestObject to, int timeout) { waitForElementClickable(to, timeout, defaultFailure); }
	public static boolean verifyElementAttributeValue(TestObject to, String attributeName, String attributeValue, int timeout) { verifyElementAttributeValue(to, attributeName, attributeValue, defaultFailure); }
	public static boolean verifyElementPresent(TestObject to, int timeout) { verifyElementPresent(to, timeout, defaultFailure); }
	public static boolean verifyElementNotPresent(TestObject to, int timeout) { verifyElementNotPresent(to, timeout, defaultFailure); }
	public static boolean waitForElementPresent(TestObject to, int timeout) { waitForElementPresent(to, timeout, defaultFailure); }
	public static boolean verifyElementText(TestObject to, String text) { verifyElementText(to, text, defaultFailure); }
	public static void submit(TestObject to) { submit(to, defaultFailure); }
	public static int getElementHeight(TestObject to) { getElementHeight(to, defaultFailure); }
	public static int getElementWidth(TestObject to) { getElementWidth(to, defaultFailure); }
	public static void refresh() { refresh(defaultFailure); }
	public static void scrollToElement(TestObject to, int timeout) { scrollToElement(to, timeout, defaultFailure); }
	public static void focus(TestObject to) { focus(to, defaultFailure); }
	public static boolean verifyTextPresent(String text, boolean isRegex) { verifyTextPresent(text, isRegex, defaultFailure); }
	// public static void setEncryptedText(TestObject to, String text) { setEncryptedText(to, text, defaultFailure) }
	// END OF FAILURE HANDLING METHODS //

	public static void openBrowser(String browser, FailureHandling failure) 
	{
		WebDriver webDriver = null;

		try
		{
			switch(browser) 
			{
				case "firefox":
					System.setProperty("webdriver.gecko.driver", "./" + "\\geckodriver.exe");
					webDriver = new FirefoxDriver();
					threadLocal.set(webDriver);
					break;
				case "chrome":
					System.setProperty("webdriver.chrome.driver", "./" + "\\chromedriver.exe");
					webDriver = new ChromeDriver();
					threadLocal.set(webDriver);
					break;
				case "":
				default:
					System.setProperty("webdriver.chrome.driver", "./" + "\\chromedriver.exe");
					webDriver = new ChromeDriver();
					threadLocal.set(webDriver);
					break;
			}
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}
	
	public static TestObject findTestObject(String path)
	{
		String userPath = "C:/Users/cunod/Katalon Studio/GOLDTEST/";
		String rs = ".rs";
		
		if(!path.contains("Object Repository/"))
			path = "Object Repository/" + path;

		return new TestObject(ObjectRepositoryParser.getXpath(userPath + path + rs));
	}

	public static void navigateToUrl(String url, FailureHandling failure) 
	{
		try
		{
			if(threadLocal.get() == null)
			{
				WebUI.openBrowser("")
			}
			
			WebDriver webDriver = threadLocal.get();
			webDriver.navigate().to(url);
		
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void click(TestObject to, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.elementToBeClickable(By.xpath(to.getXpath())));
			webElement.click();
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void setText(TestObject to, String text, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(to.getXpath())));
			webElement.sendKeys(text);
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void sendKeys(TestObject to, String keys, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(to.getXpath())));
			webElement.sendKeys(keys);
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void deleteAllCookies(FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			if (webDriver != null)
				webDriver.manage().deleteAllCookies();
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	//@CompileStatic // disallows groovy methods for some reason
	public static void delay(long delayTime, FailureHandling failure) 
	{
		try
		{
			sleep(delayTime*1000)
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void closeBrowser(FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			if (webDriver != null)
				webDriver.quit();
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void maximizeWindow(FailureHandling failure) 
	{
		try
		{
			// need to manually resize the window if test is being run on Mac
			WebDriver webDriver = threadLocal.get();
			if (webDriver != null)
				webDriver.manage().window().maximize();
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void switchToWindowTitle(String title, FailureHandling failure)
	{
		try
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
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void closeWindowTitle(String title, FailureHandling failure)
	{
		try
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
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void switchToWindowIndex(Object index, FailureHandling failure)
	{
		try
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
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void closeWindowIndex(Object index, FailureHandling failure)
	{
		try
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
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static boolean waitForElementClickable(TestObject to, int timeout, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(timeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.elementToBeClickable(By.xpath(to.getXpath())));
			return true;
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return false;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return false;
			}
		}
	}

	@CompileStatic
	public static boolean verifyElementAttributeValue(TestObject to, String attributeName, String attributeValue, int timeout, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(timeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.elementToBeClickable(By.xpath(to.getXpath())));
			if ((webElement.getAttribute(attributeName) != null) && (webElement.getAttribute(attributeName).equals(attributeValue)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return false;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return false;
			}
		}
	}

	@CompileStatic
	public static boolean verifyElementPresent(TestObject to, int timeout, FailureHandling failure) 
	{
		try
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
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return false;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return false;
			}
		}
	}

	@CompileStatic
	public static boolean verifyElementNotPresent(TestObject to, int timeout, FailureHandling failure) 
	{
		try
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
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return false;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return false;
			}
		}
	}

	// This function in Katalon simply finds any webElement in the TestObject. Not anywhere on the web page and not a specific web element.
	@CompileStatic
	public static boolean waitForElementPresent(TestObject to, int timeout, FailureHandling failure) 
	{
		try
		{
			if(!to.getXpath().isEmpty())
				return true;
			else
				return false;
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static boolean verifyElementText(TestObject to, String text, FailureHandling failure) 
	{
		try
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
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return false;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return false;
			}
		}
	}

	@CompileStatic
	public static void submit(TestObject to, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement;

			webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(to.getXpath())))
			webElement.submit();
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static int getElementHeight(TestObject to, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement;

			webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(to.getXpath())))
			return webElement.getSize().height;
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static int getElementWidth(TestObject to, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement;

			webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(to.getXpath())))
			return webElement.getSize().width;
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void refresh(FailureHandling failure) 
	{
		try
		{
			threadLocal.get().navigate().refresh();
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void scrollToElement(TestObject to, int timeout, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement;

			webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(timeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(to.getXpath())))
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].scrollIntoView();", webElement);
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static void focus(TestObject to, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement;

			webElement = new FluentWait<WebDriver>(webDriver)
				.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(to.getXpath())))
			if ("input".equals(webElement.getTagName()))
			{
				webElement.sendKeys("");
			}
			else
			{
				new Actions(webDriver).moveToElement(webElement).perform()
			}
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return;
			}
		}
	}

	@CompileStatic
	public static boolean verifyTextPresent(String text, boolean isRegex, FailureHandling failure) 
	{
		try
		{
			WebDriver webDriver = threadLocal.get();
			WebElement webElement = webDriver.findElement(By.tagName("body"));
			String pageText = webElement.getText();
			boolean isPresent;

			if (pageText != null && !pageText.isEmpty()) 
			{
				if (isRegex) 
				{
					Pattern pattern = Pattern.compile(text);
					Matcher matcher = pattern.matcher(pageText);
					while (matcher.find()) 
					{
						isPresent = true;
						break;
					}
				} else {
					isPresent = pageText.contains(text);
				}
			}
			return isPresent;
		}
		catch (Exception e)
		{
			if (failure == FailureHandling.STOP_ON_FAILURE)
			{
				Assert.fail(e.toString());
			}
			else if (failure == FailureHandling.CONTINUE_ON_FAILURE)
			{
				// Logger?
				return false;
			}
			else // FailureHandling.OPTIONAL
			{
				// Logger?
				return false;
			}
		}
	}

	// @CompileStatic
	// public static void setEncryptedText(TestObject to, String text) 
	// {
	// 	WebDriver webDriver = threadLocal.get();
	// 	WebElement webElement = new FluentWait<WebDriver>(webDriver)
	// 		.pollingEvery(500, TimeUnit.MILLISECONDS).withTimeout(webUITimeout, TimeUnit.SECONDS)
	// 		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(to.getXpath())));

	// 	Cryptographer c = new Cryptographer("E:/David_Main_Folder/Projects/Senior-Design-2-pcteUI/PCTE-Gold-AES.txt");
	// 	String encrypted = c.encrypt(text);

	// 	String decrypted = c.decrypt(encrypted);
		
	// 	webElement.sendKeys(decrypted);
	// }
}