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
    <title>管理员信息修改</title>
    <link href="../bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<script>
    function cancel(){
        window.history.back();
    }
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
        }
    }
    function check(){
        var username = document.getElementById("username").value;
        var no = document.getElementById("tno").value;
        var flag=0;
        <% List<Teacher> tea = (List<Teacher>) session.getAttribute("TeaUsernameList");%>
        <%for (Teacher t : tea){%>
        var tno = "<%=t.getNo()%>";
        var tusername ="<%=t.getUsername()%>"
        if (tno!=no && username==tusername){
            flag=1;
        }
        <%}%>
        if(flag==1){
            alert("该用户名已存在，请重新输入用户名");
        }
    }
</script>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg bg-primary rounded-bottom">
        <div class="container-fluid align-text-bottom d-flex flex-lg-row flex-sm-column">
            <div><h3 class="navbar-brand display-6 text-light align-text-bottom">学生宿舍管理系统</h3></div>
            <div class="navbar navbar-expand-xl rounded navbar-dar">
                <button class="navbar-toggler border-white text-center" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                    <p class="text-light">操作列表</p>
                </button>
                <div class="collapse navbar-collapse" id="collapsibleNavbar" >
                    <ul class="navbar-nav container-fluid rounded">
                        <li class="nav-item border-white">
                            <a class="nav-link text-center text-light" href="../TeacherIndex.html">首页</a>
                        </li>

                        <li class="nav-item dropdowm">
                            <a class="nav-link dropdown-toggle text-white text-center" href="#" id="navbardrop" data-bs-toggle="dropdown">
                                信息管理
                            </a>
                            <ul class="dropdown-menu">
                                <li class="nav-link dropdown-item text-center "><a  href="./StuMesForTea.jsp">学生信息管理</a></li>
                                <li class="nav-link dropdown-item text-center "><a  href="./RoomMesForTea.jsp">宿舍信息管理</a></li>
                            </ul>
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
                </div>

            </div>

            <div>
                <form class="d-flex">
                    <button class="btn btn-outline-light" type="button" onclick="logout()">登出</button>
                </form>
            </div>
        </div>
    </nav>
    <div class="container-fluid col row" style="margin-top: 50px;">
        <div class="col"style="padding: 30px 40px;border: #0b5ed7 solid ;border-radius: 10px">
            <form action="../ServletTeaIndex?action=teacherupdate" method="post" class="was-validated">
                <%
                    Teacher teacher =(Teacher) session.getAttribute("user");
                %>
                <div class="d-flex justify-content-between flex-row ">
                    <div class="col-5">
                        <label class="form-label" for="tno">职工号：</label>
                        <input type="text" class="form-control" id="tno" name="tno" value="<%=teacher.getNo()%>" readonly>
                    </div>
                    <div class="col-4">
                        <label class="form-label" for="name">姓名：</label>
                        <input type="text" class="form-control" id="name" name="name" value="<%=teacher.getName()%>">
                    </div>
                </div>
                </br>
                <div class="mb-3">
                    性别：
                    <label class="form-check-label">
                        <input class="form-check-input" type="radio" name="sex"  value="男" checked>男
                    </label>
                    <label class="form-check-label">
                        <input class="form-check-input" type="radio" name="sex" value="女" >女
                    </label>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="username">用户名:</label>
                    <input type="text" class="form-control" id="username" name="username" value="<%=teacher.getUsername()%>" onmouseleave="check()" required>
                    <div class="valid-feedback">验证成功</div>
                    <div class="invalid-feedback">用户名不能为空</div>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="tel">电话：</label>
                    <input type="text" class="form-control" id="tel" name="tel" value="<%=teacher.getTel()%>">

                </div>
                <div class="d-flex justify-content-between flex-row">
                    <div class="col-5">
                        <label class="form-label" for="locbuild">所在宿舍楼：</label>
                        <input type="text" class="form-control" name="buildname" id="locbuild" value="<%=teacher.getBuildname()%>" readonly>
                        <p class="text-sm-start text-danger">您无权限修改，若修改，请联系管理员</p>
                    </div>
                </div>
                </br>
                <div class=" d-flex justify-content-around flex-row">
                    <div><button type="submit" class="btn btn-primary btn-lg p-2" style="align-content: center">保存</button></div>
                    <div> <button type="button" class="btn btn-success btn-lg p-2" style="align-content: center" onclick="cancel()">取消</button></div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>