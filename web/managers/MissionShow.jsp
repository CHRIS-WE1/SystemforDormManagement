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
<%@ page import="builds.Mission" %>
<%@ page import="javax.imageio.stream.MemoryCacheImageInputStream" %>

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
    function del(missionid){
        if(confirm("确定删除该任务吗？")){
            window.location="../ServletManIndex?action=missiondelete&missionid="+missionid;
        }
    }
    function back() {
        window.location="./MissionListForMan.jsp";
    }
    function alter(no) {
        window.location="../ServletManIndex?action=missionupdatepre&missionid="+no;
    }
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
        }
    }
    function insert(){
        window.location="./MissionInsert.jsp";
    }
    function getdetail(missionid){
        window.location="../ServletManIndex?action=misdetails&missionid="+missionid;
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
                        <a class="nav-link text-center text-light" href="../ManagerIndex.html">首页</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="TeaMessageforMan.jsp">教师信息管理</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="StuMessageforMan.jsp">学生信息管理</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="BuiMesforMan.jsp">宿舍楼信息管理</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-center text-light" href="RoomMesforMan.jsp">宿舍信息管理</a>
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
            </nav>
        </div>
        <div class="col-10">
            <div class="p-2 border-bottom border-secondary pb-1">
                <p class="h4">查询结果</p>
            </div>
            <div>
                <form method="post" class="p-2 border-bottom border-primary pb-0" action="../ServletManIndex?action=missionsearch">
                    <div class="container justify-content-between d-flex flex-row">
                        <div class="d-flex justify-content-start p-1 mb-0">
                            <div>
                                <button class="btn btn-sm btn-success" type="button" onclick="insert()">添加任务</button>
                                <button class="btn btn-sm btn-danger" type="button" onclick="back()">返回</button>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end p-1 mb-0" >
                            <div>
                                <select class="form-select-sm border-info" name="method" id="method" style="height: 30px">
                                    <option>发布者</option>
                                    <option>类型</option>
                                    <option>标题</option>
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
                    <th>发布者</th>
                    <th>标题</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>任务类型</th>
                    <th>任务执行者</th>
                    <th>操作</th>
                </tr>
                </thead>
                <%
                    ArrayList<Mission> missions = (ArrayList<Mission>)  session.getAttribute("resmis");
                    if(missions!=null){
                        for (Mission mission :missions){%>
                <tr>
                    <td><%=mission.getName()%></td>
                    <td><%=mission.getTitle()%></td>
                    <td><%=mission.getBegintime()%></td>
                    <td><%=mission.getEndtime()%></td>
                    <td><%=mission.getType()%></td>
                    <td><%=mission.getExecutor()%></td>
                    <td>
                        <div class="btn-group-sm">
                            <button type="submit" class="btn btn-success" id="details" onclick="getdetail(<%=mission.getMissionid()%>)">查看详情</button>
                            <button type="submit" class="btn btn-danger" id="delete" onclick="del(<%=mission.getMissionid()%>)">删除</button>
                            <button type="submit" class="btn btn-info text-light" id="alter" onclick="alter(<%=mission.getMissionid()%>)">修改</button>
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
