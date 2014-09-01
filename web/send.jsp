<%@ page import="utils.ProjectUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-2-20
  Time: 下午1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <script type="text/javascript">
        function $(id) {
            return document.getElementById(id);
        }
        function view() {
            var win = window.open();
            var content = $('content').value;
            win.document.write(content);
        }
    </script>
</head>
<body>
<form action="sendmail.do" method="post">
    <input type="hidden" name="formToken" value="<%=ProjectUtil.nextFormToken()%>"/>
    <table>
        <tr>
            <td>邮件标题</td>
            <td><input type="text" style="width: 450px;" name="title"></td>
        </tr>
        <tr>
            <td>邮件内容</td>
            <td><textarea cols="60" rows="30" id="content" name="content"></textarea></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;"><input type="submit" value="提交"><input type="button" value="预览"
                                                                                               onclick="view()"> ||  <a href="index.jsp">返回首页</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>