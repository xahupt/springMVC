<%--
  Created by IntelliJ IDEA.
  User: percy
  Date: 19-1-29
  Time: 下午11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JavaWeb</title>
</head>
<body>
<h1 style="color: brown;">Hello java</h1>
<form action="login.action" method="post">
    <table border='1' align="center">
        <caption>USER LOGIN</caption>
        <tr>
            <th>username</th>
            <th><input type="text" name="username"/></th>
        </tr>
        <tr>
            <th>password</th>
            <th><input type="password" name="password"/></th>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="submit"/></td>
        </tr>
    </table>
</form>
</body>
</html>
