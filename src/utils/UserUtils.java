package utils;

import entity.User;

import java.util.*;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 下午5:45
 * To change this template use File | Settings | File Templates.
 */
public class UserUtils {
    /**
     * 配置文件中所有配置的用户
     */
    private static List<User> users = null;
    /**
     * 当前用户
     */
    private static User currentUser = new User();

    private static Iterator<User> userIterator = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void initUsers() {
        Set<String> keys = PropertiesUtils.getKeys();
        Map<String, User> map = new HashMap<String, User>();
        for (String s : keys) {
            if (s.matches("^username\\d*$")) {
                String key = s.replace("username", "");
                User user = map.get(key);
                if (user == null) user = new User();
                user.setUsername(PropertiesUtils.getString(s));
                map.put(key, user);
            }
            if (s.matches("^password\\d*$")) {
                String key = s.replace("password", "");
                User user = map.get(key);
                if (user == null) user = new User();
                user.setPassword(PropertiesUtils.getString(s));
                map.put(key, user);
            }
            if (s.matches("^sendMail\\d*$")) {
                String key = s.replace("sendMail", "");
                User user = map.get(key);
                if (user == null) user = new User();
                user.setSendEmail(PropertiesUtils.getString(s));
                map.put(key, user);
            }
            if (s.matches("^smtp\\d*$")) {
                String key = s.replace("smtp", "");
                User user = map.get(key);
                if (user == null) user = new User();
                user.setSmtp(PropertiesUtils.getString(s));
                map.put(key, user);
            }
            if (s.matches("^port\\d*$")) {
                String key = s.replace("port", "");
                User user = map.get(key);
                if (user == null) user = new User();
                user.setPort(PropertiesUtils.getString(s));
                map.put(key, user);
            }
            if (s.matches("^SSLPort\\d*$")) {
                String key = s.replace("SSLPort", "");
                User user = map.get(key);
                if (user == null) user = new User();
                user.setSSLPort(PropertiesUtils.getString(s));
                map.put(key, user);
            }
        }
        users = new ArrayList<User>();
        for (User u : map.values()) {
            if (validUser(u)) users.add(u);
            else LogUtils.error("用户配置不合法：" + u.getUsername());
        }
        userIterator = users.iterator();
        if (userIterator.hasNext())
            currentUser = userIterator.next();
        else {
            LogUtils.result("没有配置可用的用户");
            ProjectUtil.exit();
        }
    }

    private static boolean validUser(User user) {
        if (StringUtils.isBlank(user.getPort())) user.setPort("25");
        return !StringUtils.isBlank(user.getUsername()) && !StringUtils.isBlank(user.getPassword()) && !StringUtils.isBlank(user.getSendEmail()) && !StringUtils.isBlank(user.getSmtp());
    }

    /**
     * 获取下一个用户
     *
     * @param oldUser 切换前用户
     * @return 切换后的用户
     */
    public static synchronized User nextUser(User oldUser) {
        if (oldUser.equals(currentUser))//如果没有切换则迭代到下一个用户
            if (userIterator.hasNext())
                return currentUser = userIterator.next();
            else {
                LogUtils.result("邮件发送数量超过限制，没有更多的账户,程序退出");
                // 　终止进程
                ProjectUtil.exit();
            }
        return currentUser;
    }

    public static void main(String[] args) {
        initUsers();
//        System.out.println(nextUser(currentUser));
//        System.out.println(nextUser(currentUser));
//        System.out.println(nextUser(currentUser));
        System.out.println("adsaf" + null);
        //System.out.println(userIterator.next().getUsername());

//        for(User user:users){
//        System.out.println(user.getUsername());
//        }
    }
}
