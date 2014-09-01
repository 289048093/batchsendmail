package utils;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(String cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEquals(String s1,String s2){
        if(s1==null || s2==null){
            return false;
        }
        return s1.equals(s2);
    }

}
