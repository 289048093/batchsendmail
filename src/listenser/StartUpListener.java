package listenser;

import utils.LogUtils;
import utils.ProjectUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-20
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public class StartUpListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            LogUtils.clearLogs();
            ProjectUtil.init();
        } catch (IOException e) {
            LogUtils.error(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
