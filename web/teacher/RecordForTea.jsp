
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
<%@ page import="builds.Record" %>
<!DOCTYPE html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" >
    <title>考勤记录</title>
    <link href="../bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<script type="text/javascript">
    function refreshpage(){
        var arr = location.href.split('/');
        var fileName = arr[arr.length - 1];
        window.location="../ServletTeaIndex?action=refresh&filename="+fileName;
    }
    function del(recordid) {
        if (confirm("确定删除本条记录吗？"))
        {
            window.location="../ServletTeaIndex?action=deleterecord&recordid="+recordid;
        }
    }
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
        }
    }
    function check(){
        var mes = document.getElementById("tname").value;
        println(mes);
        if(mes==null){
            alert("搜索框信息不为空");
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
        <div class="col-10">
            <div class="p-2 border-bottom border-secondary pb-1">
                <%Teacher tea = (Teacher) session.getAttribute("user");%>
                <p class="h4"><%=tea.getBuildname()%> 考勤信息</p>
            </div>
            <div>
                <form method="post" class="p-2 border-bottom border-primary pb-0" action="../ServletTeaIndex?action=recsearch">
                    <div class="container justify-content-between d-flex flex-row">
                        <div class="d-flex justify-content-start p-1 mb-0">
                            <%--                            <div><p class="p-0"></p></div>--%>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="mySwitch" name="switch" value="yes" checked>
                                <label class="form-check-label" for="mySwitch">选择搜索时间：</label>
                            </div>
                            <div>
                                <input type="date" name="date" class="border-info">&nbsp;
                            </div>&nbsp;
                                <div>
                                    <button class="btn btn-sm btn-primary" type="button" onclick="refreshpage()">刷新列表</button>
                                </div>

                        </div>
                        <div class="d-flex justify-content-end p-1 mb-0" >

                            <div>
                                <select class="form-select-sm border-info" name="method" id="method" style="height: 30px">
                                    <option>学号</option>
                                    <option>姓名</option>
                                    <option>宿舍名称</option>
                                </select>&nbsp
                            </div>
                            <div>
                                <input class="form-control form-control-sm border-info" type="text" name="mes" id="tname" style="width: 120px;height: 30px;">
                            </div>
                            <div>
                                &nbsp<button type="submit" class="btn btn-sm btn-info text-light" onclick="check()">搜索</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <table class="table table-bordered table-hover table-striped">
                <thead class="table-primary">
                <tr>
                    <th>考勤编号</th>
                    <th>学生学号</th>
                    <th>学生姓名</th>
                    <th>宿舍名称</th>
                    <th>签到时间</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
                </thead>
                <%
                    ArrayList<Record> records = (ArrayList<Record>)  session.getAttribute("TeaRecList");
                    if(records!=null){
                        for (Record record :records){
                %>
                <tr>
                    <td><%=record.getRecordid()%></td>
                    <td><%=record.getNo()%></td>
                    <td><%=record.getName()%></td>
                    <td><%=record.getRoomname()%></td>
                    <td><%=record.getDate()%></td>
                    <td><%=record.getDetails()%></td>
                    <td>
                        <button type="submit" class="btn btn-danger" id="delete" onclick="del(<%=record.getRecordid()%>)">删除</button>
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
