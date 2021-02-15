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

		WebUI.sendKeys(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Google/input_Sign in_q'), Keys.chord(Keys.ENTER))

		WebUI.click(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_kenmore manual - Google Search/span_Kenmore Appliances for Kitchen, Laundry  Home'))

		WebUI.click(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Kenmore Appliances for Kitchen, Laundry  Home/img'))

		WebUI.click(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Kenmore 40349 24 Manual Clean Gas Wall_00ca36/a_User Manual'))

		WebUI.delay(3)

		WebUI.switchToWindowIndex(0)

		WebUI.delay(3)

		WebUI.switchToWindowIndex(1)

		WebUI.delay(3)

		WebUI.closeWindowIndex(1)

		WebUI.delay(3)

		WebUI.closeWindowIndex(0)
	}
}