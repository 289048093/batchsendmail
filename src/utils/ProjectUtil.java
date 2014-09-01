package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-13
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */
public class ProjectUtil {

    private static String formToken = null;

    private static String currentReserver;

    private static int sendCount =0;

    private static AtomicInteger failCount = new AtomicInteger(0);

    private static List<String> SSL_SMTPS = new ArrayList<String>();

    private static volatile boolean running;

    public static String getFormToken() {
        return formToken;
    }
    public static String nextFormToken(){
        formToken = UUID.randomUUID().toString();
        return formToken;
    }

    private static int sendIndex;
    static {
        String[] smtps = PropertiesUtils.getString("SSL_SMTPS").split(",");
        for (String s : smtps) {
            SSL_SMTPS.add(s.trim());
        }
    }

    public static int getSendIndex() {
        return sendIndex;
    }

    public static void setSendIndex(int sendIndex) {
        ProjectUtil.sendIndex = sendIndex;
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        ProjectUtil.running = running;
    }

    public static List<String> getSSL_SMTPS() {
        return SSL_SMTPS;
    }

    public static void sendCountPlus() {
        sendCount++;
    }
    public static void sendFailureIncrement(){
        failCount.incrementAndGet();
    }

    public static String getCurrentReserver() {
        return currentReserver;
    }

    public static void setCurrentReserver(String currentReserver) {
        ProjectUtil.currentReserver = currentReserver;
    }

    public static int getThreadNum(){
        String res =PropertiesUtils.getString("THREAD_NUM");
        if(res!=null){
            try {
                return Integer.parseInt(res);
            } catch (NumberFormatException e) {
                LogUtils.error(e);
                return 0;
            }
        }
        return 0;
    }

    /**
     * 初始化
     *
     * @throws IOException
     */
    public static void init() throws IOException {
        System.setProperty("web.root",new File(FileUtils.getClassesPath()).getParent());
        ExcelUtils.loadExcel();
        UserUtils.initUsers();
    }

    public static void exit(){
        running = false;
    }
    /**
     * 手动退出
     */
    public static void logExit(){
        LogUtils.result("=============================================");
        LogUtils.result("System exit !!!");
        LogUtils.result("当前邮件接收人：" + currentReserver);
        LogUtils.result("已发送邮件：" + sendCount + "；其中，邮件发送失败数量：" +failCount + ",发送成功数量：" + (sendCount - failCount.intValue()));
        LogUtils.result("正确邮件地址：" + (ExcelUtils.getMailReceivers() != null ? ExcelUtils.getMailReceivers().size() : 0));
        LogUtils.result("=============================================");
    }

    public static void main(String[] args) {
        for(String s:SSL_SMTPS){
            System.out.println(s);
        }
    }
}
