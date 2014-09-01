package service;

import entity.User;
import utils.ExcelUtils;
import utils.LogUtils;
import utils.ProjectUtil;
import utils.UserUtils;

import javax.mail.MessagingException;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public class SendMailThread extends Thread {

    private String title;
    private String content;
    private static MailSender mailSender = new MailSender();

    private String receiver = ExcelUtils.nextReceiversMail();

    /**
     *
     * @param title   邮件标题
     * @param content 邮件内容
     */
    public SendMailThread(String title, String content) {
        init(title, content);
    }


    private void init( String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public void run() {
            while (receiver !=null && ProjectUtil.isRunning()){
                try {
                    mailSender.sendMail(receiver, title, content);
                    LogUtils.success("邮件发送成功:" + mailSender.getUser().getUsername() + "-->" + receiver);
                } catch (Exception e) {
                    LogUtils.failure(mailSender.getUser().getUsername() + "-->" + receiver + ", 邮件发送失败：" + e.getMessage());
                    ProjectUtil.sendFailureIncrement();
                    errorHandler(e);
                    e.printStackTrace();
                }
                receiver =   ExcelUtils.nextReceiversMail();
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
            }

    }

    private void errorHandler(Exception e) {
        UserUtils.getCurrentUser().getErrorCount().incrementAndGet();
        String msg = e.getMessage();
        if (msg != null) {
            /**
             554 MI:STC		:163 已经超过数量限制，换一个帐号,重新发送（仅限163邮箱）
             553 5.7.1 SENDER REJECT    :SOHU 邮件数量超过限制
             550 RP:TRC	:sina
             451 MI:SFQ	:163	 发信人在15分钟内的发信数量超过限制
             550 Too many connect	: sina 链接数超过限制
             452 Too many recipients received this hour	:
             550 Sender frequency limited
             535 5.7.0 Invalid state	:sohu  发送失败，该帐号基本gameover
             451 4.3.2 Internal server error	:sohu  发送失败，该帐号基本gameover
             */
            if (msg.contains("554 MI:STC")
                    || msg.contains("553 5.7.1 SENDER REJECT")
                    || msg.contains("550 RP:TRC")
                    || msg.contains("451 MI:SFQ")
                    || msg.contains("550 Too many connect")
                    || msg.contains("452 Too many recipients received this hour")
                    || msg.contains("550 Sender frequency limited")
                    || msg.contains("535 5.7.0 Invalid state")
                    || msg.contains("451 4.3.2 Internal server error")) {
                nexUserAndResendMail();
            }
        }
        if (e instanceof MessagingException) {
            Exception next = ((MessagingException) e).getNextException();
            if (next != null) {
                msg = next.getMessage();
                if (msg != null && msg.contains("Connection timed out")) {  //163链接超时
                    try {
                        mailSender.sendMail(receiver, title, content);
                    } catch (MessagingException e1) {
                        LogUtils.error(e);
                    }
                }
            }
        }
    }

    /**
     * 切换到下一个账户，并重新发送邮件
     */
    private void nexUserAndResendMail() {
        LogUtils.result("帐号" + mailSender.getUser().getUsername() + " 发送数量已经超过上限了，当前邮件接收人：" + receiver + "。。。。。。。。");
        User userTmp = UserUtils.nextUser(mailSender.getUser());
        if (userTmp == null) {
            return;
        }
        mailSender = new MailSender();
        try {
            mailSender.sendMail(receiver,title, content);
        } catch (MessagingException e1) {
            LogUtils.failure(mailSender.getUser().getUsername() + "-->" + receiver + ", 邮件重发失败：" + e1.getMessage());
        }
    }
}
