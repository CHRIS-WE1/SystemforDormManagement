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
    <title>学生信息修改</title>
    <link href="../bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<script>
    function cancel(){
        window.location="./StuMesForTea.jsp";
    }
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
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
            <form action="../ServletTeaIndex?action=studentupdate" method="post" class="was-validated">
                <%
                    Student student =(Student) session.getAttribute("UpdateStuForTea");
                %>
                <div class="d-flex justify-content-between flex-row ">
                    <div class="col-5">
                        <label class="form-label" for="sno">学号：</label>
                        <input type="text" class="form-control" id="sno" name="sno" value="<%=student.getNo()%>" readonly>
                    </div>
                    <div class="col-4">
                        <label class="form-label" for="name">姓名：</label>
                        <input type="text" class="form-control" id="name" name="name" value="<%=student.getName()%>">
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
                    <input type="text" class="form-control" id="username" name="username" value="<%=student.getUsername()%>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="pwd">密码：</label>
                    <input type="text" class="form-control" id="pwd" name="pwd" value="<%=student.getPassword()%>" required>
                    <div class="valid-feedback">验证成功</div>
                    <div class="invalid-feedback">密码不能为空</div>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="tel">电话：</label>
                    <input type="text" class="form-control" id="tel" name="tel" value="<%=student.getTel()%>">
                </div>
                <div class="d-flex justify-content-between flex-row">
                    <div class="col-5">
                        <label class="form-label" for="locbuild">所在宿舍楼：</label>
                        <input type="text" class="form-control" name="buildname" id="locbuild" value="<%=student.getBuildname()%>" readonly>
                    </div>
                    <div class="col-4">
                        <label class="form-label" for="locroom">所在宿舍：</label>
                        <select type="text" class="form-control" id="locroom" name="roomname" value="<%=student.getRoomname()%>">
                            <%ArrayList<Room> rooms = (ArrayList<Room>) session.getAttribute("TeaRoomList");
                                for (Room room:rooms){
                                if(room.getRoomname().equals(student.getRoomname())){%>
                                     <option selected="selected"><%=room.getRoomid()%>|<%=room.getRoomname()%></option>
                               <% }else{%>
                                    <option><%=room.getRoomid()%>|<%=room.getRoomname()%></option>
                            <% }
                            }%>
                            <option>0|null</option>
                        </select>
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
