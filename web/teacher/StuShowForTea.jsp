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

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" >
    <title>学生信息管理</title>
    <link href="../bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<script>
    function del(no){
        if(confirm("确定删除该学生信息吗？")){
            window.location="../ServletTeaIndex?action=deletestudent&no="+no;
        }
    }
    function alter(no) {
        window.location="../ServletTeaIndex?action=studentupdatepre&sno="+no;
    }
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
        }
    }
    function insert(){
        window.location="./StudentInsertForTea.jsp";
    }
    function back(){
        window.location="../ServletTeaIndex?action=studentinsertpre";
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
        <div class="col-10">
            <div class="p-2 border-bottom border-secondary pb-1">
                <p class="h4">查询结果</p>
            </div>
            <div>
                <form method="post" class="p-2 border-bottom border-primary pb-0" action="../ServletTeaIndex?action=stusearch">
                    <div class="container justify-content-between d-flex flex-row">
                        <div class="d-flex justify-content-start p-1 mb-0">
                            <div>
                                <button class="btn btn-sm btn-success" type="button" onclick="insert()">添加学生</button>
                            </div>
                            &nbsp;
                            <div>
                                <button class="btn btn-sm btn-danger" type="button" onclick="back()">返回</button>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end p-1 mb-0" >
                            <div>
                                <select class="form-select-sm border-info" name="method" id="method" style="height: 30px">
                                    <option>学号</option>
                                    <option>姓名</option>
                                    <option>电话</option>
                                    <option>宿舍</option>
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
                    <th>学号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>所在宿舍</th>
                    <th>联系电话</th>
                    <th>操作</th>
                </tr>
                </thead>
                <%
                    ArrayList<Student> students = (ArrayList<Student>)  session.getAttribute("resstufortea");
                    if(students!=null){
                        for (Student student :students){%>
                <tr>
                    <td><%=student.getNo()%></td>
                    <td><%=student.getName()%></td>
                    <td><%=student.getSex()%></td>
                    <td><%=student.getRoomname()%></td>
                    <td><%=student.getTel()%></td>
                    <td>
                        <div class="btn-group-sm">
                            <button type="button" class="btn btn-danger" id="delete" onclick="del(<%=student.getNo()%>)">删除</button>
                            <button type="button" class="btn btn-info text-light" id="alter" onclick="alter(<%=student.getNo()%>)">修改</button>
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
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>
