
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script>
        window.onload = function () {
            document.getElementById("img").onclick = function () {
                this.src="/day16/checkCodeServlet?time="+new Date().getTime();
            }
        }
    </script>

    <style>
        div{
            color: red;
        }
    </style>
</head>
<body>

    <form action="/day16/loginServlet" method="post">
        <table>
            <tr>
                <td>用户名</td>
                <td><input type="text" name="username"/></td>
            </tr>

            <tr>
                <td>密码</td>
                <td><input type="password" name="password"/></td>
            </tr>

            <tr>
                <td>验证码</td>
                <td><input type="text" name="checkCode"/></td>
            </tr>

            <tr>
                <td colspan="2"><img id="img" src="/day16/checkCodeServlet"/></td>
            </tr>

            <tr>
                <td><input type="submit" value="登录"/></td>
            </tr>
        </table>
    </form>

    <div><%=request.getAttribute("msg") == null ? "":request.getAttribute("msg")%></div>
    <div><%=request.getAttribute("msg_login") == null?"":request.getAttribute("msg_login")%></div>
</body>
</html>
