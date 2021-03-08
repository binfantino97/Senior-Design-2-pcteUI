public class KeywordUtil
{
    private static final KeywordLogger logger;
    
    static {
        //logger = KeywordLogger.getInstance((Class)KeywordUtil.class);
    }
    
    public static void markFailed(final String message) {
        //KeywordUtil.logger.logFailed(message);
        //ErrorCollector.getCollector().addError((Throwable)new StepFailedException(message));
    }
    
    public static void markFailedAndStop(final String message) {
        //throw new StepFailedException(message);
    }
    
    public static void logInfo(final String message) {
        //KeywordUtil.logger.logInfo(message);
    }
    
    public static void markWarning(final String message) {
        //KeywordUtil.logger.logWarning(message);
    }
    
    public static void markPassed(final String message) {
        //KeywordUtil.logger.logPassed(message);
        //ErrorCollector.getCollector().setKeywordPassed(true);
    }
    
    public static void markError(final String message) {
        //ErrorCollector.getCollector().addError((Throwable)new StepErrorException(message));
    }
    
    public static void markErrorAndStop(final String message) {
        //throw new StepErrorException(message);
    }
}