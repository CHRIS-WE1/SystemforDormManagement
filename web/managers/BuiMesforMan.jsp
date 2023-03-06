<%--
  Created by IntelliJ IDEA.
  User: 冯俊威
  Date: 2023/1/28
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="java.sql.*" %>
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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>宿舍楼信息</title>
    <link href="../bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <!-- <script src="/bootstrap5/js/bootstrap.bundle.min.js"></script> -->
</head>
<body>
<script>
    function refreshpage() {
        var arr = location.href.split('/');
        var fileName = arr[arr.length - 1];
        window.location = "../ServletManIndex?action=refresh&filename=" + fileName;
    }

    function del(buildid) {
        if (confirm("确定删除该宿舍楼信息吗？")) {
            window.location = "../ServletManIndex?action=deletebuild&buildid=" + buildid;
        }
    }

    function alter(buildid) {
        window.location = "../ServletManIndex?action=buildupdatepre&buildid=" + buildid;
    }

    function logout() {
        if (confirm("确定退出登陆吗？")) {
            window.location = "../ServletLogout";
        }
    }

    function insert() {
        window.location = "./BuildsInsert.jsp";
    }
</script>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg bg-primary rounded-bottom">
        <div class="container-fluid align-text-bottom d-flex flex-lg-row flex-sm-column">
            <div><h3 class="navbar-brand display-6 text-light align-text-bottom">学生宿舍管理系统</h3></div>
            <div class="navbar navbar-expand-xl rounded navbar-dar">
                <button class="navbar-toggler border-white text-center" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapsibleNavbar">
                    <p class="text-light">操作列表</p>
                </button>
                <div class="collapse navbar-collapse" id="collapsibleNavbar">
                    <ul class="navbar-nav container-fluid rounded">
                        <li class="nav-item border-white">
                            <a class="nav-link text-center text-light" href="../ManagerIndex.html">首页</a>
                        </li>

                        <li class="nav-item dropdowm">
                            <a class="nav-link dropdown-toggle text-white text-center" href="#" id="navbardrop"
                               data-bs-toggle="dropdown">
                                信息管理
                            </a>
                            <ul class="dropdown-menu">
                                <li class="nav-link dropdown-item text-center "><a
                                        href="TeaMessageforMan.jsp">教师信息管理</a></li>
                                <li class="nav-link dropdown-item text-center "><a
                                        href="StuMessageforMan.jsp">学生信息管理</a></li>
                                <li class="nav-link dropdown-item text-center "><a
                                        href="BuiMesforMan.jsp">宿舍楼信息管理</a></li>
                                <li class="nav-link dropdown-item text-center "><a
                                        href="RoomMesforMan.jsp">宿舍信息管理</a></li>
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
        <div class="col container">
            <div class="p-2 border-bottom border-secondary pb-1">
                <p class="h4">宿舍楼信息</p>
            </div>
            <div>
                <form method="post" class="p-2 border-bottom border-primary pb-0"
                      action="../ServletManIndex?action=buisearch">
                    <div class="container justify-content-between d-flex flex-row">
                        <div class="d-flex justify-content-start p-1 mb-0">
                            <div>
                                <button class="btn btn-sm btn-success" type="button" onclick="insert()">添加宿舍楼
                                </button>
                            </div>&nbsp;
                            <div>
                                <button type="button" class="btn btn-sm btn-primary" onclick="refreshpage()">刷新列表
                                </button>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end p-1 mb-0">
                            <div>
                                <select class="form-select-sm border-info" name="method" id="method"
                                        style="height: 30px">
                                    <option>名称</option>
                                    <option>宿管老师</option>
                                </select>&nbsp
                            </div>
                            <div>
                                <input class="form-control form-control-sm border-info" type="text" name="mes"
                                       id="tname" style="width: 120px;height: 30px;">
                            </div>
                            <div>
                                &nbsp
                                <button type="submit" class="btn btn-sm btn-info text-light">搜索</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <%
                int pageSize = 4;
                int pageNow = 1;
                int rowCount = 0;
                int pageCount = 0;

                List<Build> list = (List<Build>)  session.getAttribute("ABuiList");
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
                List<Build> showlist =(List<Build>) list.subList((pageNow-1)*pageSize+0,(pageNow-1)*pageSize+pageSize);
            %>
            <table class="table table-bordered table-hover table-striped">
                <thead class="table-primary">
                <tr>
                    <th>宿舍楼编号</th>
                    <th>宿舍楼名称</th>
                    <th>宿管老师</th>
                    <th>宿舍楼描述</th>
                    <th>操作</th>
                </tr>
                </thead>

                <%for(Build bui:showlist){%>
                <tr>
                    <td><%=bui.getBuildid() %>
                    </td>
                    <td><%=bui.getBuildname() %>
                    </td>
                    <td><%=bui.getTeachername() %>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                data-bs-target="#myModal<%=bui.getBuildid() %>">
                            点击查看详细信息
                        </button>
                    <td><div class="btn-group-sm">
                        <button type="submit" class="btn btn-danger" onclick="del(<%=bui.getBuildid()%>)">删除</button>
                        <button type="submit" class="btn btn-info text-light" onclick="alter(<%=bui.getBuildid()%>)">修改</button>
                    </div>
                    </td>
                </tr>
                <%--                    </td>
                                    <td><div class="btn-group-sm">
                                        <button type="submit" class="btn btn-danger" onclick="del(<%=rs.getString("no")%>)">删除</button>
                                        <button type="submit" class="btn btn-info text-light" onclick="alter(<%=rs.getString("no")%>)">修改</button>
                                    </div>
                                    </td>
                                </tr>
                                <%} %>

                <%--                <%--%>
                <%--                    ArrayList<Build> builds = (ArrayList<Build>)  session.getAttribute("ABuiList");--%>
                <%--                    if(builds!=null){--%>
                <%--                        for (Build build :builds){--%>
                <%--                        if (build.getBuildid()==0)--%>
                <%--                            continue;--%>
                <%--                %>--%>
                <%--                <tr>--%>
                <%--                    <td><%=build.getBuildid()%></td>--%>
                <%--                    <td><%=build.getBuildname()%></td>--%>
                <%--                    <td><%=build.getTeachername()%></td>--%>
                <%--                    <td>--%>
                <%--                        <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#myModal<%=build.getBuildid()%>">--%>
                <%--                            点击查看详细信息--%>
                <%--                        </button>--%>

                <%--                    </td>--%>
                <%--                    <td>--%>
                <%--                        <div class="btn-group-sm">--%>
                <%--                            <button type="submit" class="btn btn-danger" id="delete" onclick="del(<%=build.getBuildid()%>)">删除</button>--%>
                <%--                            <button type="submit" class="btn btn-info text-light" id="alter" onclick="alter(<%=build.getBuildid()%>)">修改</button>--%>
                <%--                        </div>--%>
                <%--                    </td>--%>
                <%--                </tr>--%>
                <div class="modal" id="myModal<%=bui.getBuildid()%>">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <!-- 模态框头部 -->
                            <div class="modal-header">
                                <h4 class="modal-title"><%=bui.getBuildname()%>详细信息</h4>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <!-- 模态框内容 -->
                            <div class="modal-body">
                                <p><%=bui.getDetails()%>
                                </p>
                            </div>
                            <!-- 模态框底部 -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">关闭</button>
                            </div>

                        </div>
                    </div>
                </div>
                <%
                    }
                %>

            </table>
            <%
                out.print("<ul class='pagination justify-content-end'>");
                if(pageNow!=1){
                    out.print("<li class='page-item'><a class='page-link' href='BuiMesforMan.jsp?pageNow=1'>首页</a></li>");
                    out.print("<li class='page-item'><a class='page-link' href='BuiMesforMan.jsp?pageNow="+(pageNow-1)+"'>上一页</a></li>");
                }
                //显示分页
                for(int i=1; i<=pageCount;i++){
                    out.print("<li class='page-item'><a class='page-link' href='BuiMesforMan.jsp?pageNow="+i+"'>"+i+"</a></li>");
                }
                if(pageNow<pageCount){
                    out.print("<li class='page-item'><a class='page-link' href='BuiMesforMan.jsp?pageNow="+(pageNow+1)+"'>下一页</a></li>");
                    out.print("<li class='page-item'><a class='page-link' href='BuiMesforMan.jsp?pageNow="+pageCount+"'>尾页</a></li>");
                }
                out.print("</ul>");
            %>
        </div>
    </div>
</div>
<script src="../bootstrap5/js/bootstrap.bundle.min.js"></script>
</body>
</html>
