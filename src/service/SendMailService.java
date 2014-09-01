package service;

import utils.LogUtils;
import utils.ProjectUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-20
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public class SendMailService {

    public void massMail(String title, String content){
          new Thread(new massMailer(title,content)).start();
    }

    private  class massMailer implements Runnable{
        private String title;
        private String content;
        private massMailer(String title,String content){
                this.title = title;
            this.content = content;
        }
        @Override
        public void run() {
            ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(ProjectUtil.getThreadNum());
            ProjectUtil.setRunning(true);
            for(int i=0;i<ProjectUtil.getThreadNum();i++){
                threadPool.execute(new SendMailThread(title , content));
            }
            while (threadPool.getActiveCount()>0){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    LogUtils.error(e);
                }
            }
            threadPool.shutdown();
            ProjectUtil.logExit();
            ProjectUtil.setRunning(false);
        }
    }
}
