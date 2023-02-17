<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" >
    <title>学生信息管理</title>
    <link href="${pageContext.request.contextPath}/bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/html/login.css" rel="stylesheet">
    <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<%--    <div class="container mt-3">--%>
        <div class="edu-container-new">
            <div class="edu-content-new mt-3">
                <div class="lf">
                    <div class="edu-map-new">
                        <div style="height: 20px;">
                        </div>
                        <div class="layui-carousel" lay-anim style="width: 100%; height:100%;">
                            <img src="img/3.jpg" style="width: 415px; height:467px;" alt="">
                        </div>
                    </div>
                </div>

                <%String error = (String) session.getAttribute("loginerror");
                    if (error!=null){%>
                <script type="text/javascript" >
                    alert("<%=error%>");
                </script>
                <%}%>

                <form action="/ServletLogin2" method="post" class="was-validated">
                    <div class="rt login-form-new">
                        <h2>用户登录</h2>
                        <p style="margin-bottom: 12px">&nbsp;&nbsp;</p>
                        <div class="mb-3 mt-3">
                            <label for="username" class="form-label">Username/StudentNo:</label>
                            <input type="text" class="form-control" id="username" placeholder="输入用户名/学号" name="username" required>
                            <div class="valid-feedback">验证成功</div>
                            <div class="invalid-feedback">请输入用户名或学号</div>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password:</label>
                            <input type="password" class="form-control" id="password" placeholder="输入密码" name="password" required>
                            <div class="valid-feedback">验证成功</div>
                            <div class="invalid-feedback">请输入密码</div>
                        </div>
                        <div class="col-12">
                            身份选择：
                            <label class="form-check-label">
                                <input class="form-check-input" type="radio" name="role" value="manager">管理员
                            </label>
                            <label class="form-check-label">
                                <input class="form-check-input" type="radio" name="role" value="teacher">老师
                            </label>
                            <label class="form-check-label">
                                <input class="form-check-input" type="radio" name="role" value="student" checked="checked">学生
                            </label>
                        </div>
                        <br>
                        <div class="d-flex col-12 flex-row justify-content-center">
                            <div>
                                <button type="submit" class="btn btn-primary" >登录</button>
                            </div>

                        </div>

                    </div>
                </form>
            </div>

        </div>

<%--    </div>--%>
    <script src="./bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>