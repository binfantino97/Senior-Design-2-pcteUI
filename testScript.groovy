import static WebUI.findTestObject

public class TestScript 
{
	public static void main(String[] args)
	{
		WebUI.openBrowser('');
		WebUI.navigateToUrl('https://www.google.com/');
		//WebUI.click(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Google/div_media only screen and (max-width380px)g_4bbbd6'));
		WebUI.setText(findTestObject('Object Repository/Testing/Click-SetText-Delay/Page_Google/input_Sign in_q'), 'cheese');
	}
}