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
    <title>宿舍信息修改</title>
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
    function check() {
        var roomname =document.getElementById("roomname").value;
        var flag=0;
        <% List<Room> rooms = (List<Room>) session.getAttribute("TeaRoomList");%>
        <%for (Room r : rooms){%>
        var rname="<%=r.getRoomname()%>"
        if (roomname==rname){
            flag=1;
        }
        <%}%>
        if(flag==1){
            alert("该宿舍名称已被使用，请重新命名");
        }
    }
</script>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg bg-primary rounded-bottom">
        <div class="container-fluid align-text-bottom">
            <h3 class="navbar-brand display-6 text-light align-text-bottom">学生宿舍管理系统</h3>
            <form  class="d-flex">
                <button class="btn btn-outline-light" type="button" onclick="logout()">登出</button>
            </form>
        </div>
    </nav>
    <div class="container-fluid col-12 row">
        <div class="container-fluid col-2 border-top " style="margin-top: 50px">
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
                        <a class="nav-link text-center text-light" href="./MissionForTea">任务发布</a>
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
            <form action="../ServletTeaIndex?action=roomupdate" method="post" class="was-validated">
                <%
                    Room room =(Room) session.getAttribute("UpdateRoomForTea");
                %>
                <div class="mb-3">
                    <label class="form-label" for="roomid">宿舍编号：</label>
                    <input type="text" class="form-control" id="roomid" name="roomid" value="<%=room.getRoomid()%>" readonly>
                </div>
                <div class="mb-3 mt-3">
                    <label for="buildname">所属宿舍楼：</label>
                    <input type="text" class="form-control" id="buildname" name="buildname" value="<%=room.getBuildname()%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="roomname">宿舍名称:</label>
                    <input type="text" class="form-control" id="roomname" name="roomname" value="<%=room.getRoomname()%>" required>
                </div>
                </br>
                <div class=" d-flex justify-content-around flex-row">
                    <div><button type="submit" class="btn btn-primary btn-lg p-2" style="align-content: center">保存</button></div>
                    <div> <button type="submit" class="btn btn-success btn-lg p-2" style="align-content: center" onclick="cancel()">取消</button></div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>
