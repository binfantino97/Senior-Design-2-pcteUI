public enum ConditionType
{
    EQUALS("EQUALS", 0, "equals"), 
    NOT_EQUAL("NOT_EQUAL", 1, "not equal"), 
    CONTAINS("CONTAINS", 2, "contains"), 
    NOT_CONTAIN("NOT_CONTAIN", 3, "not contain"), 
    STARTS_WITH("STARTS_WITH", 4, "starts with"), 
    ENDS_WITH("ENDS_WITH", 5, "ends with"), 
    MATCHES_REGEX("MATCHES_REGEX", 6, "matches regex"), 
    NOT_MATCH_REGEX("NOT_MATCH_REGEX", 7, "not match regex"), 
    EXPRESSION("EXPRESSION", 8, "expression");
    
    private String text;
    
    private ConditionType(final String name, final int ordinal, final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return this.text;
    }
    
    public static ConditionType fromValue(final String value) {
        ConditionType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final ConditionType condition = values[i];
            if (condition.toString().equals(value)) {
                return condition;
            }
        }
        return null;
    }
}