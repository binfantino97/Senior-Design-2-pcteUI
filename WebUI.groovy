@Grab(group='org.codehaus.groovy', module='groovy-all', version='3.0.7', type='pom')
import groovy.transform.CompileStatic

@Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59')
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

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

		
	//public static void main(String[] args)
	//{
	//	WebUI.delay(5)
	//	WebUI.openBrowser()
	//	WebUI.navigateToUrl("https://www.google.com")
	//}

	public static void openBrowser() 
	{
		WebDriver webDriver = null;
		System.setProperty("webdriver.chrome.driver", "C:/SDII_WebUI" + "\\chromedriver.exe")
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
}