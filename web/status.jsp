<%@ page import="utils.FileUtils" %>
<%@ page import="utils.ProjectUtil" %>
<%@ page import="java.io.File" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-2-20
  Time: 下午4:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var running = <%=ProjectUtil.isRunning()%>;
    </script>
</head>
<body>
<a href="index.jsp">返回首页</a> <br>
<% if (ProjectUtil.isRunning()) { %>
系统正在发送邮件。。。。<br>
当前收件人：<%=ProjectUtil.getCurrentReserver()%>
<a href="stop.do">停止</a>
<% } else {%>
发送结果:<br>

<div style="overflow: auto;height: 600px;width:1000px;border:1px solid black">
    <%
        String logDir = FileUtils.getLogDir();
        //  System.out.println(logDir);
        if (logDir != null) {
            String res = FileUtils.readFileToString(new File(logDir, "result.log"));
            res = res.replace("\r\n", "<br>");
            out.write(res);
        } else {
            out.write("日志无法读取");
        }

    %>
</div>

成功记录:<br>
<div style="overflow: auto;height: 600px;width:1000px;border:1px solid black">
    <%
        //  System.out.println(logDir);
        if (logDir != null) {
            String res = FileUtils.readFileToString(new File(logDir, "successlist.log"));
            res = res.replace("\r\n", "<br>");
            out.write(res);
        } else {
            out.write("日志无法读取");
        }

    %>
</div>

失败记录:<br>
<div style="overflow: auto;height: 600px;width:1000px;border:1px solid black">
    <%
        //  System.out.println(logDir);
        if (logDir != null) {
            String res = FileUtils.readFileToString(new File(logDir, "failurelist.log"));
            res = res.replace("\r\n", "<br>");
            out.write(res);
        } else {
            out.write("日志无法读取");
        }

    %>
</div>
<% }%>

</body>
</html>