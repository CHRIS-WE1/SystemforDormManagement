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
    <div class="container-fluid align-text-bottom d-flex flex-lg-row flex-sm-column">
      <div><h3 class="navbar-brand display-6 text-light align-text-bottom">学生宿舍管理系统</h3></div>
      <div class="navbar navbar-expand-xl rounded navbar-dar">
        <button class="navbar-toggler border-white text-center" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
          <p class="text-light">操作列表</p>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar" >
          <ul class="navbar-nav container-fluid rounded">
            <li class="nav-item border-white">
              <a class="nav-link text-center text-light" href="../ManagerIndex.html">首页</a>
            </li>

            <li class="nav-item dropdowm">
              <a class="nav-link dropdown-toggle text-white text-center" href="#" id="navbardrop" data-bs-toggle="dropdown">
                信息管理
              </a>
              <ul class="dropdown-menu">
                <li class="nav-link dropdown-item text-center "><a  href="TeaMessageforMan.jsp">教师信息管理</a></li>
                <li class="nav-link dropdown-item text-center "><a  href="StuMessageforMan.jsp">学生信息管理</a></li>
                <li class="nav-link dropdown-item text-center "><a  href="BuiMesforMan.jsp">宿舍楼信息管理</a></li>
                <li class="nav-link dropdown-item text-center "><a  href="RoomMesforMan.jsp">宿舍信息管理</a></li>
              </ul>
            </li>
            <li class="nav-item">
              <a class="nav-link text-center text-light" href="AllRecordForMan.jsp">考勤管理</a>
            </li>
            <li class="nav-item">
              <a class="nav-link text-center text-light" href="MissionListForMan.jsp">任务发布</a>
            </li>
            <li class="nav-item">
              <a class="nav-link text-center text-light" href="ManMessage.jsp">管理员信息</a>
            </li>
            <li class="nav-item">
              <a class="nav-link text-center text-light" href="ManPasswordUpdate.jsp">密码修改</a>
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
      <form action="../ServletManIndex?action=manupdate" method="post" class="was-validated" onsubmit="return on()">
        <% Manager manager = (Manager) session.getAttribute("user");
        %>
        <div class="mb-3">
          <label class="form-label" for="oldpwd">原密码：</label>
          <input type="password" class="form-control" id="oldpwd" value="<%=manager.getPassword()%>" readonly>
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
        <button type="submit" class="btn btn-primary" style="align-content: center" onmouseover="check()"">提交</button>
      </form>
    </div>
  </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>