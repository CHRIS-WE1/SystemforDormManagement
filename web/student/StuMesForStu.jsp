<%--
  Created by IntelliJ IDEA.
  User: 冯俊威
  Date: 2023/1/27
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="users.*" %>
<%@page import="java.lang.*" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" >
    <title>教师信息</title>
    <link href="../bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<script>
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
        }
    }
    function alter() {
        if(confirm("确定修改信息吗？")){
            window.location="./StuMesUpdateForStu.jsp";
        }
    }

</script>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg bg-primary rounded-bottom">
        <div class="container-fluid align-text-bottom">
            <h3 class="navbar-brand display-6 text-light align-text-bottom">学生宿舍管理系统</h3>
            <form class="d-flex">
                <button class="btn btn-outline-light" type="button" onclick="logout()">登出</button>
            </form>
        </div>
    </nav>
    <div class="container-fluid col-12 row" style="margin-top: 50px;">
        <div class="container-fluid col-2 border-top ">
            <nav class="navbar navbar-inverse bg-primary text-light rounded">
                <ul class="navbar-nav container-fluid rounded">
                    <li class="nav-item border-white">
                        <a class="nav-link text-center text-light" href="../StudentIndex.html">首页</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./RecordListForStu.jsp">考勤记录</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./MissionList.jsp">任务</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./StuMesForStu.jsp">学生信息</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./StuPasswordUpdate.jsp">密码修改</a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="col-10"style="padding: 30px 40px;border: #0b5ed7 solid ;border-radius: 10px">
            <form  method="post">
                <%
                    Student user =(Student) session.getAttribute("user");
                %>
                <div class="mb-3">
                    <label class="form-label" for="name">姓名：</label>
                    <input type="text" class="form-control" id="name" name="name" value="<%=user.getName()%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="sex">性别：</label>
                    <input type="text" class="form-control" id="sex" name="sex" value="<%=user.getSex()%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="username">用户名/学号:</label>
                    <input type="text" class="form-control" id="username" value="<%=user.getUsername()%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="build">所在宿舍楼：</label>
                    <input type="text" class="form-control" id="build" value="<%=user.getBuildname()%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="room">所在宿舍：</label>
                    <input type="text" class="form-control" id="room" value="<%=user.getRoomname()%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="tel">电话：</label>
                    <input type="text" class="form-control" id="tel" value="<%=user.getTel()%>" readonly>
                </div>
                <button type="button" class="btn btn-primary" style="align-content: center" onclick="alter()">修改</button>
            </form>
        </div>
    </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>