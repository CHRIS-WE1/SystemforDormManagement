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
    <title>创建任务</title>
    <link href="../bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<script>
    function cancel(){
        window.location="./MissionForTea.jsp";
    }
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
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
    <div class="container-fluid col row">
        <div class="col"style="padding: 30px 40px;border: #0b5ed7 solid ;border-radius: 10px">
            <%Mission mission = (Mission) session.getAttribute("UpdateMissionForTea");%>
            <form action="../ServletMissionUpdate" method="post" class="was-validated"  enctype="multipart/form-data">
                <div class="d-flex flex-column justify-content-center">
                    <div class="">
                        <label class="form-label" for="title">标题：</label>
                        <input  type="text" class="form-control" id="title" name="title" value="<%=mission.getTitle()%>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label" for="type">任务类型：</label>
                        <select  class="form-select" id="type" name="type" >
                            <option selected="selected">签到</option>
                            <option>其它</option>
                        </select>
                    </div>
                    <div class="justify-content-center">
                        <label class="form-label " for="details">内容：</label>
                        <textarea  type="text" class="form-control" id="details" name="details" ><%=mission.getDetails()%></textarea></br>
                    </div>
                    <div class="mb-3">
                        任务对象：
                        <label class="form-check-label">
                            <input  class="form-check-input" type="radio" name="executor"  value="teacher">老师
                        </label>
                        <label class="form-check-label">
                            <input  class="form-check-input" type="radio" name="executor" value="student" checked>学生
                        </label>
                        <label class="form-check-label">
                            <input  class="form-check-input" type="radio" name="executor" value="all">所有人
                        </label>
                    </div>
                    <div class="d-flex justify-content-between flex-sm-column flex-lg-row">
                        <div class="mb-3">
                            <label for="begintime" class="form-check-label">开始时间:</label>
                            <input  type="datetime-local" id="begintime" name="begintime" class="form-select" value="<%=mission.getBegintime()%>">
                        </div>
                        <div class="mb-3">
                            <label for="endtime" class="form-check-label" >截止时间:</label>
                            <input  type="datetime-local" id="endtime" name="endtime" class="form-select" value="<%=mission.getEndtime()%>">
                        </div>
                    </div>
                    <%if(mission.getFilepath()!=null){%>
                    原始文件名称：
                    <p class="text-info-emphasis"><%=mission.getFilepath()%></p>
                    <%}%>
                    <label class="form-label" for="newfile">更新文件(可选)：</label>
                    <input type="file" class="form-control" id="newfile" name="file" ></br>
                    <div class=" d-flex justify-content-around flex-row">
                        <div><button type="submit" class="btn btn-primary btn-lg p-2" style="align-content: center" >确定</button></div>
                        <div> <button type="button" class="btn btn-success btn-lg p-2" style="align-content: center" onclick="cancel()">取消</button></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>
