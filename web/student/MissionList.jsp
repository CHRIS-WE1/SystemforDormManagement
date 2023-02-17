
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
<body onbeforeunload="refreshpage()">
<script type="text/javascript">
    function refreshpage(){
        var arr = location.href.split('/');
        var fileName = arr[arr.length - 1];
        window.location="../ServletStuIndex?action=refresh&filename="+fileName;
    }
    function details2(no){
        window.location="../ServletStuIndex?action=mrdetails&missionid="+no;
    }
    function logout(){
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
        }
    }
    function getdetail(missionid){
        window.location="../ServletStuIndex?action=misdetails&missionid="+missionid;
    }
    function finish(id,method){
        window.location="../ServletStuIndex?action=misfinish&missionid="+id+"&method="+method;
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
                            <a class="nav-link text-center text-light" href="../StudentIndex.html">首页</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-center text-light" href="./RecordListForStu.jsp">考勤记录</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-center text-light" href="./MissionList.jsp">任务</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-center text-light" href="./StuMesForStu.jsp">学生信息</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-center text-light" href="./StuPasswordUpdate.jsp">密码修改</a>
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
                <form method="post" class="p-2 border-bottom border-primary pb-0" action="../ServletStuIndex?action=missionsearch">
                    <div class="container justify-content-between d-flex flex-row">
                        <div class="d-flex justify-content-start p-1 mb-0">
                            <button type="button" class="btn btn-sm btn-primary" onclick="refreshpage()">刷新列表</button>
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
                    ArrayList<Mission> missions = (ArrayList<Mission>) session.getAttribute("MissionListForStu");
                    ArrayList<Mission_record> mrs = (ArrayList<Mission_record>) session.getAttribute("MisRecForStu");
                    Student user =(Student) session.getAttribute("user");
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
                                if(mission.getExecutor().equals("学生")||mission.getExecutor().equals("all")||mission.getExecutor().equals(user.getBuildname())){
                                    if(mrs!=null){
                                        for (Mission_record mr:mrs){
                                            if (mr.getMissionid()==mission.getMissionid()&&mr.getExecutorid().equals(user.getNo())){
                                                flag=1;break;
                                            }
                                        }
                                    }
                                    if(flag==1){
                                        if(mission.getType().equals("签到")){%>
                            <button type="button" class="btn btn-secondary text-light" id="alter" disabled="disabled">已完成</button>
                                        <%} else if (mission.getType().equals("其它")) {%>
                            <button type="button" class="btn btn-warning text-light" id="alter" onclick="details2(<%=mission.getMissionid()%>)">已完成/查看结果</button>
                                        <%}
                                    }else if(flag==0){%>
                                <button type="button" class="btn btn-primary text-light" id="alter" onclick="finish(<%=mission.getMissionid()%>,'signin')">执行</button>
                            <button type="button" class="btn btn-danger text-light" id="alter" onclick="finish(<%=mission.getMissionid()%>,'leave')">请假申请</button>
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
<script type="text/javascript">
    alert("<%=error%>");
</script>
<%}session.removeAttribute("MissionExeError");%>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>
