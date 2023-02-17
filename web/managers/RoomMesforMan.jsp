<%--
  Created by IntelliJ IDEA.
  User: 冯俊威
  Date: 2023/1/28
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="users.*" %>
<%@page import="builds.*" %>
<%@page import="java.lang.*" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="renderer" content="webkit">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="IE=edge" >
  <title>管理员主页</title>
  <link href="../bootstrap5/css/bootstrap.min.css" rel="stylesheet">
  <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<script>
  function refreshpage(){
    var arr = location.href.split('/');
    var fileName = arr[arr.length - 1];
    window.location="../ServletManIndex?action=refresh&filename="+fileName;
  }
  function del(roomid){
    if(confirm("确定删除该宿舍信息吗？")){
      window.location="../ServletManIndex?action=deleteroom&roomid="+roomid;
    }
  }
  function alter(roomid){
    window.location="../ServletManIndex?action=roomupdatepre&roomid="+roomid;
  }
  function logout() {
    if(confirm("确定退出登陆吗？")){
      window.location="../ServletLogout";
    }
  }
  function insert(){
    window.location="./RoomInsert.jsp";
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
  <div class="container-fluid col row">
    <div class="col">
      <div class="p-2 border-bottom border-secondary pb-1">
        <p class="h4">宿舍信息</p>
      </div>
      <div>
        <form method="post" class="p-2 border-bottom border-primary pb-0" action="../ServletManIndex?action=roomsearch">
          <div class="container justify-content-between d-flex flex-row">
            <div class="d-flex justify-content-start p-1 mb-0">
              <div>
                <button class="btn btn-sm btn-success" type="button" onclick="insert()">添加宿舍</button>
              </div>&nbsp;
              <div>
                <button type="button" class="btn btn-sm btn-primary" onclick="refreshpage()">刷新列表</button>
              </div>
            </div>
            <div class="d-flex justify-content-end p-1 mb-0" >
              <div>
                <select class="form-select-sm border-info" name="method" id="method" style="height: 30px">
                  <option>宿舍楼名称</option>
                  <option>宿舍名称</option>
                </select>&nbsp
              </div>
              <div>
                <input class="form-control form-control-sm border-info" type="text" name="mes" id="tname" style="width: 120px;height: 30px;">
              </div>
              <div>
                &nbsp<button type="submit" class="btn btn-sm btn-info text-light">搜索</button>
              </div>
            </div>
          </div>
        </form>
      </div>
      <table class="table table-bordered table-hover table-striped">
        <thead class="table-primary">
        <tr>
          <th>宿舍编号</th>
          <th>所属宿舍楼</th>
          <th>宿舍名称</th>
          <th>操作</th>
        </tr>
        </thead>
        <%
          ArrayList<Room> rooms = (ArrayList<Room>)  session.getAttribute("ARoomList");
          if(rooms!=null){
            for (Room room :rooms){
        if (room.getRoomid()==0)
          continue;%>
        <tr>
          <td><%=room.getRoomid()%></td>
          <td><%=room.getBuildname()%></td>
          <td><%=room.getRoomname()%></td>
          <td>
            <div class="btn-group-sm">
              <button type="submit" class="btn btn-danger" id="delete" onclick="del(<%=room.getRoomid()%>)">删除</button>
              <button type="submit" class="btn btn-info text-light" id="alter" onclick="alter(<%=room.getRoomid()%>)">修改</button>
            </div>
          </td>
        </tr>
        <% }
        }
        %>

      </table>

    </div>
  </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js">

</script>
</body>
</html>
