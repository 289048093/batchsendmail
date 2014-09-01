package action;

import service.SendMailService;
import utils.ExcelUtils;
import utils.LogUtils;
import utils.ProjectUtil;
import utils.StringUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-20
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
public class SendMailServlet extends javax.servlet.http.HttpServlet {
    private SendMailService sendMailService = new SendMailService();
    private static final String RESULT = "result";

    protected void service(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf(".do"));

        if (action.equals("sendmail")) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            if (!validFormToken(request, response)) {
                return;
            }  //表单重复验证
            if (ProjectUtil.isRunning()) {
                request.setAttribute(RESULT, "系统已在发送邮件，请等邮件发送完毕再操作，或者终止发送。。。");
                request.getRequestDispatcher("result.jsp").forward(request, response);
                return;
            }
            LogUtils.clearLogs();
            ExcelUtils.loadExcel();//重新加载收件人
            sendMailService.massMail(title, content);
            request.setAttribute(RESULT, "操作成功，系统正在玩命发送邮件中，请等待。。。");
            request.getRequestDispatcher("result.jsp").forward(request, response);
            return;
        } else if (action.equals("stop")) {
            ProjectUtil.setRunning(false);
            request.setAttribute(RESULT, "操作成功，系统正在停止发送，请等待。。。");
            request.getRequestDispatcher("result.jsp").forward(request, response);
            return;
        }

    }

    /**
     * 表单重复提交验证
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean validFormToken(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        String formToken = request.getParameter("formToken");
        String serverToken = ProjectUtil.getFormToken();
        ProjectUtil.nextFormToken();//重置TOKEN
        if (!StringUtils.isEquals(formToken, serverToken)) {  //表单重复提交验证
            request.setAttribute(RESULT, "请勿重复提交，系统已在发送邮件，请等邮件发送完毕再操作，或者终止发送。。。");
            request.getRequestDispatcher("result.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}
