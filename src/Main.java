import service.SendMailThread;
import utils.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
public class Main {

//    private static LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<String>(3);

    private static final int THREAD_NUM = 5;

    public static void main(String[] args) throws InterruptedException {
        try {
            ProjectUtil.init();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return;
        }
        String title = "【易居尚情人节礼物】9999份免费礼物送给“老情人";
        String content = null;
        try {
            content = FileUtils.readFileToString(FileUtils.getClassPathFile("code.txt"));
        } catch (IOException e) {
            LogUtils.error(e);
        }
//        List<String> list = ExcelUtils.getMailReceivers();
//        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_NUM);
//        for (int i = 0; i < list.size(); i++) {
//            threadPool.execute(new SendMailThread(title , content));
//        }
//
//        while (threadPool.getActiveCount()>0){
//            Thread.sleep(100);
//        }
//        threadPool.shutdown();
//        ProjectUtil.logExit();
        FileWriter writer = null;
        try {
           writer =  new FileWriter("D:/workspace/jarvis/mail/sendmail/web/WEB-INF/logs/result.log");
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }    finally {
            try {
                if(writer!=null)writer.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


}
