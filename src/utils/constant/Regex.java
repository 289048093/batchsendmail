package utils.constant;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-26
 * Time: 上午10:33
 * To change this template use File | Settings | File Templates.
 */
public enum Regex {
    EMAIL_REGEX("^[\\.\\-\\w]+@(\\w)+\\-?\\w*((\\.\\w{1,20}){1,3})$");

    private String value;
    Regex(String value){
         this.value = value;
    }
    public String getValue(){
        return value;
    }
}
