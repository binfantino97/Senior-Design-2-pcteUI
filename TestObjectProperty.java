public class TestObjectProperty
{
    private String name;
    private ConditionType condition;
    private String value;
    private boolean isActive;
    
    public TestObjectProperty() {
        this.isActive = true;
    }
    
    public TestObjectProperty(final String name, final ConditionType condition, final String value) {
        this.name = name;
        this.condition = condition;
        this.value = value;
        this.isActive = true;
    }
    
    public TestObjectProperty(final String name, final ConditionType condition, final String value, final boolean isActive) {
        this.name = name;
        this.condition = condition;
        this.value = value;
        this.isActive = isActive;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public ConditionType getCondition() {
        return this.condition;
    }
    
    public void setCondition(final ConditionType condition) {
        this.condition = condition;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public boolean isActive() {
        return this.isActive;
    }
    
    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }
}
