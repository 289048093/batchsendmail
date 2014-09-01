package entity;

import utils.LogUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
public class User implements Cloneable {

    private String username;

    private String password;

    private String smtp;

    private String sendEmail;

    private String port;

    private String SSLPort;

    private AtomicInteger errorCount = new AtomicInteger(0);

    public AtomicInteger getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(AtomicInteger errorCount) {
        this.errorCount = errorCount;
    }

    public String getSSLPort() {
        return SSLPort;
    }

    public void setSSLPort(String SSLPort) {
        this.SSLPort = SSLPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            LogUtils.error(e);
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return (user.getUsername() == null ? false : user.getUsername().equals(username));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return username == null ? 0 : username.hashCode() << 5 - 1;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("aaa");
        user.setPassword("password");
        user.setSmtp("sssss");
        User clone = user.clone();
        System.out.println(clone.getUsername());
    }
}
