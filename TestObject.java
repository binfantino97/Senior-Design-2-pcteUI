public class TestObject {
    private String xpath;
    private List<TestObjectProperty> properties;
    private WebElement cachedWebElement;

    public TestObject(String xpath)
    {
        this.xpath = xpath;
        this.properties = new ArrayList<TestObjectProperty>();
        
    }

    public String getXpath()
    {
        return this.xpath;
    }

    public List<TestObjectProperty> getProperties() {
        return this.properties;
    }
    
    public List<TestObjectProperty> getActiveProperties() {
        final List<TestObjectProperty> activeProperties = new ArrayList<TestObjectProperty>();
        for (final TestObjectProperty property : this.properties) {
            if (property.isActive()) {
                activeProperties.add(property);
            }
        }
        return activeProperties;
    }
    
    public void setProperties(final List<TestObjectProperty> properties) {
        this.properties = properties;
    }

    public TestObject addProperty(final TestObjectProperty property) {
        this.properties.add(property);
        return this;
    }
    
    public TestObject addProperty(final String name, final ConditionType condition, final String value) {
        this.properties.add(new TestObjectProperty(name, condition, value));
        return this;
    }
    
    public TestObject addProperty(final String name, final ConditionType condition, final String value, final boolean isActive) {
        this.properties.add(new TestObjectProperty(name, condition, value, isActive));
        return this;
    }

    public void setCachedWebElement(final WebElement cachedWebElement) {
        this.cachedWebElement = cachedWebElement;
    }
    
    public WebElement getCachedWebElement() {
        return this.cachedWebElement;
    }
}
