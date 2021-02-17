@Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59')
import org.openqa.selenium.Keys as Keys

import static WebUI.findTestObject

public class TestScript 
{
	public static void main(String[] args)
	{
		WebUI.openBrowser('')

		WebUI.navigateToUrl('https://www.google.com/?gws_rd=ssl')

		WebUI.setText(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Google/input_Sign in_q'), 'kenmore manual')

		WebUI.submit(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Google/input_Sign in_q'))
	}
}