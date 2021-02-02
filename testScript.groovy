public class testScript 
{

	public static void main(String[] args)
	{
		WebUI.openBrowser()
		WebUI.navigateToUrl("https://www.google.com")
		WebUI.openBrowser()
		WebUI.navigateToUrl("https://www.bing.com")
		WebUI.maximizeWindow()
		WebUI.switchToWindowTitle("Google")
	}


}