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
    <title>管理员密码修改</title>
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
    function check() {
        var oldpassword =document.getElementById("oldpwd").value;
        var inputeoldpwd = document.getElementById("inputoldpwd").value;
        var newpwd =document.getElementById("newpwd").value;
        var renewpwd =document.getElementById("confirmnewpwd").value;
        if(oldpassword!=inputeoldpwd){
            window.alert("原密码错误！");
        }else if(newpwd!=renewpwd){
            window.alert("新密码不一致");
        }
    }
    function on(){
        var renewpwd =document.getElementById("confirmnewpwd").value;
        if(confirm("你确定将密码更改为"+renewpwd+"吗？")){
            return true;
        }
        return false;
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
                        <a class="nav-link text-center text-light" href="../TeacherIndex.html">首页</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./StuMesForTea.jsp">学生信息管理</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./RoomMesForTea.jsp">宿舍信息管理</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./RecordForTea.jsp">考勤管理</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./MissionForTea.jsp">任务发布</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./BuildMessageForTea.jsp">宿舍楼信息</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./TeaMessage.jsp">教师信息</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="./TeaPasswordUpdate.jsp">密码修改</a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="col-10"style="padding: 30px 40px;border: #0b5ed7 solid ;border-radius: 10px">
            <form action="../ServletTeaIndex?action=teacherupdate" method="post" class="was-validated" onsubmit="return on()">
                <% Teacher teacher = (Teacher) session.getAttribute("user");
                %>
                <div class="mb-3">
                    <label class="form-label" for="oldpwd">原密码：</label>
                    <input type="password" class="form-control" id="oldpwd" value="<%=teacher.getPassword()%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="inputoldpwd">输入原密码：</label>
                    <input type="password" class="form-control" id="inputoldpwd" placeholder="输入原密码">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="newpwd">新密码：</label>
                    <input type="password" class="form-control" id="newpwd" placeholder="输入新密码">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="confirmnewpwd">确认新密码：</label>
                    <input type="password" class="form-control" id="confirmnewpwd" name="newpwd" placeholder="输入新密码">
                </div>
                <button type="submit" class="btn btn-primary" style="align-content: center" onmouseover="check()">提交</button>
            </form>
        </div>
    </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>