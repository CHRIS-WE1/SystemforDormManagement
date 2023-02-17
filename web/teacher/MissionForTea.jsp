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
<%@ page import="builds.Mission_record" %>

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
    function refreshpage(){
        var arr = location.href.split('/');
        var fileName = arr[arr.length - 1];
        window.location="../ServletTeaIndex?action=refresh&filename="+fileName;
    }
    function del(missionid){
        if(confirm("确定删除该任务吗？")){
            window.location="../ServletTeaIndex?action=missiondelete&missionid="+missionid;
        }
    }
    function alter(no) {
        window.location="../ServletTeaIndex?action=missionupdatepre&missionid="+no;
    }
    function details2(no) {
        window.location="../ServletTeaIndex?action=mrdetails&missionid="+no;
    }
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
        }
    }
    function insert(){
        window.location="./MissionInsertForTea.jsp";
    }
    function getdetail(missionid){
        window.location="../ServletTeaIndex?action=misdetails&missionid="+missionid;
    }
    function finish(missionid){
        window.location="../ServletTeaIndex?action=misfinish&missionid="+missionid;
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
        <div class="col">
            <div class="p-2 border-bottom border-secondary pb-1">
                <p class="h4">任务列表</p>
            </div>
            <div>
                <form method="post" class="p-2 border-bottom border-primary pb-0" action="../ServletTeaIndex?action=missionsearch">
                    <div class="container justify-content-between d-flex flex-row">
                        <div class="d-flex justify-content-start p-1 mb-0">
                            <div>
                                <button class="btn btn-sm btn-success" type="button" onclick="insert()">添加任务</button>
                            </div>&nbsp;
                            <div>
                                <button class="btn btn-sm btn-primary" type="button" onclick="refreshpage()">刷新列表</button>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end p-1 mb-0" >
                            <div>
                                <select class="form-select-sm border-info" name="method" id="method" style="height: 30px">
                                    <option>发布者</option>
                                    <option>类型</option>
                                    <option>标题</option>
                                    <option>执行者</option>
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
                    ArrayList<Mission> missions = (ArrayList<Mission>)  session.getAttribute("AMissionList");
                    ArrayList<Mission_record> mrs = (ArrayList<Mission_record>) session.getAttribute("MisRecForTea");
                    Teacher user =(Teacher) session.getAttribute("user");
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
                            <%  int flag=0;
                                if(user.getNo().equals(mission.getPublisherid())){%>
                            <button type="submit" class="btn btn-danger" id="delete" onclick="del(<%=mission.getMissionid()%>)">删除</button>
                            <button type="submit" class="btn btn-info text-light" id="alter" onclick="alter(<%=mission.getMissionid()%>)">修改</button>
                               <%}
                            if(mission.getExecutor().equals("老师")){
                                if(mrs!=null){
                                    for (Mission_record mr:mrs){
                                        if (mr.getMissionid()==mission.getMissionid()&&mr.getExecutorid().equals(user.getNo())){
                                            flag=1;break;
                                        }
                                    }
                                }
                                if(flag==1){%>
                            <button type="submit" class="btn btn-warning text-light" id="alter" onclick="details2(<%=mission.getMissionid()%>)">已完成/查看结果</button>
                                <%}else if(flag==0){%>
                            <button type="submit" class="btn btn-primary text-light" id="alter" onclick="finish(<%=mission.getMissionid()%>)">执行</button>
                                <%}
                            }
                        }
                    }%>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<%String error = (String) session.getAttribute("MissionExeError");
    if (error!=null){%>
<script type="text/javascript" >
    alert("<%=error%>");
</script>
<%}
session.removeAttribute("MissionExeError");%>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>
