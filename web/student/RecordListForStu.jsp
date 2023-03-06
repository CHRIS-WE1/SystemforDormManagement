
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
    function logout() {
        if(confirm("确定退出登陆吗？")){
            window.location="../ServletLogout";
        }
    }
    function refreshpage(){
        var arr = location.href.split('/');
        var fileName = arr[arr.length - 1];
        window.location="../ServletStuIndex?action=refresh&filename="+fileName;
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
                <%Student tea = (Student) session.getAttribute("user");%>
                <p class="h4"><%=tea.getNo()%> 的考勤信息</p>
            </div>
            <div>
                <form method="post" class="p-2 border-bottom border-primary pb-0" action="../ServletStuIndex?action=recsearch">
                    <div class="container justify-content-between d-flex flex-row">
                        <div class="d-flex justify-content-start p-1 mb-0">
                            <div class="d-flex justify-content-start p-1 mb-0">
                                <button type="button" class="btn btn-sm btn-primary" onclick="refreshpage()">刷新列表</button>
                            </div>
                            <div>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end p-1 mb-0" >
                            <div>
                                <label class="form-check-label" for="date">时间：</label>
                                <input type="date" name="date"  id="date" class="border-info form-select-sm">&nbsp;
                            </div>
                            <div>
                                &nbsp<button type="submit" class="btn btn-sm btn-info text-light" onclick="check()">搜索</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <%
                int pageSize = 3;
                int pageNow = 1;
                int rowCount = 0;
                int pageCount = 0;
                List<Record> list = (List<Record>)  session.getAttribute("StuRecList");
                String r_pageNow = request.getParameter("pageNow");
                if (r_pageNow!=null){
                    pageNow = Integer.parseInt(r_pageNow);
                }
                int num = list.size();
                if (num!=0) {
                    rowCount = num;
                }

                if (rowCount % pageSize == 0) {
                    pageCount = rowCount / pageSize;
                } else {
                    pageCount = rowCount / pageSize + 1;
                }
                int remain=num-(pageNow-1)*pageSize;
                List<Record> showlist;
                if(remain<pageSize){
                    showlist =(List<Record>) list.subList((pageNow-1)*pageSize+0,(pageNow-1)*pageSize+remain);
                }else {
                    showlist =(List<Record>) list.subList((pageNow-1)*pageSize+0,(pageNow-1)*pageSize+pageSize);
                }
            %>
            <table class="table table-bordered table-hover table-striped">
                <thead class="table-primary">
                <tr>
                    <th>考勤编号</th>
                    <th>学生学号</th>
                    <th>学生姓名</th>
                    <th>宿舍楼名称</th>
                    <th>宿舍名称</th>
                    <th>签到时间</th>
                    <th>考勤状况</th>
                    <th>备注</th>
                </tr>
                </thead>
                <%for (Record record :showlist){%>
                <tr>
                    <td><%=record.getRecordid()%></td>
                    <td><%=record.getNo()%></td>
                    <td><%=record.getName()%></td>
                    <td><%=record.getBuildname()%></td>
                    <td><%=record.getRoomname()%></td>
                    <td><%=record.getDate()%></td>
                    <td><%=record.getStatus()%></td>
                    <td><%=record.getDetails()%></td>
                </tr>
                <%
                }
                %>
            </table>
            <%
                out.print("<ul class='pagination justify-content-end'>");
                if(pageNow!=1){
                    out.print("<li class='page-item'><a class='page-link' href='RecordListForStu.jsp?pageNow=1'>首页</a></li>");
                    out.print("<li class='page-item'><a class='page-link' href='RecordListForStu.jsp?pageNow="+(pageNow-1)+"'>上一页</a></li>");
                }
                //显示分页
                if (pageCount != 1){
                    for(int i=1 ; i<=pageCount;i++){
                        out.print("<li class='page-item'><a class='page-link' href='RecordListForStu.jsp?pageNow="+i+"'>"+i+"</a></li>");
                    }
                }
                if(pageNow<pageCount){
                    out.print("<li class='page-item'><a class='page-link' href='RecordListForStu.jsp?pageNow="+(pageNow+1)+"'>下一页</a></li>");
                    out.print("<li class='page-item'><a class='page-link' href='RecordListForStu.jsp?pageNow="+pageCount+"'>尾页</a></li>");
                }
                out.print("</ul>");
            %>
        </div>
    </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>
