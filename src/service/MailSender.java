package service;

import com.sun.mail.smtp.SMTPSendFailedException;
import entity.User;
import utils.ProjectUtil;
import utils.StringUtils;
import utils.UserUtils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class MailSender {

    private User user;

    private Session mailSession;

    public User getUser() {
        return user;
    }

    public MailSender() {
        user = UserUtils.getCurrentUser().clone();
        Properties props = new Properties();
        props.put("mail.smtp.host", user.getSmtp());//指定SMTP服务器
        props.put("mail.smtp.auth", "true");//指定是否需要SMTP验证
        props.setProperty("mail.smtp.port", user.getPort());
        if (ProjectUtil.getSSL_SMTPS().contains(user.getSmtp())) {   //需要ssl验证的邮箱
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", StringUtils.isBlank(user.getSSLPort()) ? user.getPort() : user.getSSLPort());
        }
        mailSession = Session.getDefaultInstance(props, new MailAuthenticator(user.getUsername(), user.getPassword()));
       // mailSession.setDebug(true);
    }

    /**
     * 邮件发送
     *
     * @param mailTo  邮件接收人
     * @param title   主题
     * @param content 内容
     * @return 邮件发送人
     * @throws MessagingException
     */
    public User sendMail(String mailTo, String title, String content) throws MessagingException {

        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(user.getSendEmail()));//发件人
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));//收件人
        message.setSubject(title);//邮件主题
        // message.setText(content);//邮件内容
        message.setContent(content, "text/html;charset = utf-8");
        message.saveChanges();
        Transport.send(message);
        return user;
    }

    private class MailAuthenticator extends Authenticator {
        private String userName;
        private String password;

        public MailAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }


    public static void main(String[] args) throws MessagingException {
        UserUtils.initUsers();
        MailSender sender = new MailSender();
        sender.sendMail("289048093@qq.com", "test", "testcontent<a href='http://www.baidu.com'>baidu</a>");
        System.out.println(sender.getUser().getUsername());
    }
}
