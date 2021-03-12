@Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59')
import org.openqa.selenium.Keys as Keys

import static WebUI.findTestObject

public class TestScript 
{
	public static void main(String[] args)
	{
		WebUI.openBrowser('')

		WebUI.navigateToUrl('https://www.google.com/?gws_rd=ssl')

		WebUI.setEncryptedText(findTestObject('Page_Google/input__q'), 'BRB8geN7c9U=')

		WebUI.click(findTestObject('Page_Google/input__q'))
	}
}