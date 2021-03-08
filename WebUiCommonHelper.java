public class WebUiCommonHelper {
    
    private static final KeywordLogger logger = KeywordLogger.getInstance(WebUiCommonHelper.class);
    
    public static final String CSS_LOCATOR_PROPERTY_NAME = "css";
    
    public static final String XPATH_LOCATOR_PROPERTY_NAME = "xpath";

    public static final String WEB_ELEMENT_TAG = "tag";

    public static final String WEB_ELEMENT_ATTRIBUTE_LINK_TEXT = "link_text";

    public static final String WEB_ELEMENT_ATTRIBUTE_TEXT = "text";

    public static final String WEB_ELEMENT_XPATH = "xpath";

    public static WebElement findWebElement(TestObject testObject, int timeout) throws Exception 
    {
        WebElement cachedWebElement = testObject.getCachedWebElement();
        if (cachedWebElement != null) {
            logger.logDebug("Using cached web element");
            return cachedWebElement;
        }
        // logger.logDebug("Finding web element from Test Object's properties");
        // List<WebElement> elements = findWebElements(testObject, timeOut);
        // if (elements != null && elements.size() > 0) {
        //     return elements.get(0);
        // } else {
        //     throw Exception;
        //     //String locator = getSelectorValue(testObject);
        //     //throw new WebElementNotFoundException(testObject.getObjectId(), locator);
        // }
    }

    // public static List<WebElement> findWebElements(TestObject testObject, int timeout) throws Exception 
    // {
    //     boolean shouldApplySelfHealing = RunConfiguration.shouldApplySelfHealing();
    //     return findElementsByDefault(testObject, timeout);
    // }
}
