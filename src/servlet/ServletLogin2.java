package servlet;

import builds.*;
import builds.Record;
import dao.*;
import users.Manager;
import users.Student;
import users.Teacher;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletLogin2", value = "/ServletLogin2")
public class ServletLogin2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //response.getWriter().println("ServletLogin2");
        String role=request.getParameter("role");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
       // response.getWriter().println("username:"+teacher.getUsername()+" password"+teacher.getPassword());
        int flag=0;
        try{
            if(role.equals("teacher")){
                Teacher teacher=new Teacher();
                teacher.setUsername(username);
                teacher.setPassword(password);
                TeacherDao dao=new TeacherDao();
                Teacher existTeacher=dao.QueryMes(teacher);
                System.out.println(existTeacher.getNo()+existTeacher.getName()+existTeacher.getUsername()+existTeacher.getPassword());
                if(existTeacher!=null){
                    if(existTeacher.getPassword().equals(password))
                    {
                        flag=2;
                        HttpSession session = request.getSession();
                        session.setAttribute("user", existTeacher);
                        Cookie cookie = new Cookie("JSESSIONID", session.getId());
                        cookie.setMaxAge(60 * 60);
                        response.addCookie(cookie);
                        Initial(2,request,response);
                        request.getRequestDispatcher("TeacherIndex.html").forward(request,response);
                    }
                    else
                    {
                        flag=1;
                    }
                }
            }else if(role.equals("manager")){
                Manager manager=new Manager();
                manager.setUsername(username);
                manager.setPassword(password);
                ManagerDao dao=new ManagerDao();
                Manager existManager=dao.Query(manager);
                if(existManager!=null){
                    if(existManager.getPassword().equals(password))
                    {
                        flag=2;
                        HttpSession session = request.getSession();
                        session.setAttribute("user", existManager);
                        Cookie cookie = new Cookie("JSESSIONID", session.getId());
                        cookie.setMaxAge(60 * 60);
                        response.addCookie(cookie);
                        Initial(1,request,response);
                        request.getRequestDispatcher("ManagerIndex.html").forward(request,response);
                    }
                    else
                    {
                        flag=1;
                    }
//                    response.getWriter().println("查询成功！！");
//                    response.getWriter().println(existTeacher.getName()+existTeacher.getPassword()+existTeacher.getUsername());
                }
            }else if(role.equals("student")){
                Student student=new Student();
                student.setUsername(username);
                student.setPassword(password);
                StudentDao dao=new StudentDao();
                Student existStudent=dao.QueryMes(student);
                if(existStudent!=null){
                    if(existStudent.getPassword().equals(password))
                    {
                        flag=2;
                        HttpSession session = request.getSession();
                        session.setAttribute("user", existStudent);
                        Cookie cookie = new Cookie("JSESSIONID", session.getId());
                        cookie.setMaxAge(60 * 60);
                        response.addCookie(cookie);
                        Initial(3,request,response);
                        request.getRequestDispatcher("StudentIndex.html").forward(request,response);
                    }
                    else
                    {
                        flag=1;
                    }
//                    response.getWriter().println("查询成功！！");
//                    response.getWriter().println(existTeacher.getName()+existTeacher.getPassword()+existTeacher.getUsername());
                }
            }
            if(flag==1){
                request.getSession().setAttribute("loginerror","密码错误！！！请重新登录");
                response.sendRedirect("/Login.jsp");
                //response.getWriter().println("密码错误！！");
            }else if(flag==0){
                request.getSession().setAttribute("loginerror","账户不存在！！请重新登录");
                response.sendRedirect("/Login.jsp");
               // response.getWriter().println("账户不存在！！");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void Initial(int level,HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session =request.getSession();
        //response.getWriter().println("Initial"+level);
        switch (level){
            case 1:
                TeacherDao dao1 = new TeacherDao();
                List<Teacher> teacherList=dao1.TeacherMesList();
                List<Build> buinotea=dao1.BuildWithoutTea();
                String newTeaNo = dao1.getNewTeaNo();
                session.setAttribute("BuildWithoutTea",buinotea);
                session.setAttribute("NewTeaNo",newTeaNo);
                session.setAttribute("ATeaList",teacherList);
            case 2:
                BuildDao dao2 =new BuildDao();
                if (level==1){
                    List<Build> buildList=dao2.BuildList();
                    int newBuildId = dao2.getNewBuildid()+1;
                    List<Teacher> teawithoutbui=dao2.TeacherWithoutBuild();
                    session.setAttribute("TeaWithoutBuild",teawithoutbui);
                    session.setAttribute("NewBuiId",newBuildId);
                    session.setAttribute("ABuiList",buildList);
                } else {
                    Build bui = new Build();
                    bui.setBuildid(((Teacher)session.getAttribute("user")).getBuildid());
                    bui = dao2.Query(bui);
                    session.setAttribute("TeaBui",bui);
                }
                StudentDao dao3 =new StudentDao();
                if (level==1){
                    List<Student> studentList=dao3.StudentMesList();
                    session.setAttribute("AStuList",studentList);
                }else {
                    List<Student> stulist=dao3.ListByBuildname(((Teacher)session.getAttribute("user")).getBuildname());
                    session.setAttribute("TeaStuList",stulist);
                }
                RoomDao dao4 =new RoomDao();
                if (level==1){
                    List<Room> roomList=dao4.RoomMesList();
                    session.setAttribute("ARoomList",roomList);
                    session.setAttribute("roombybui",roomList);
                } else {
                    List<Room> roomList =dao4.GetRoomByBuild(((Teacher)session.getAttribute("user")).getBuildname());
                    session.setAttribute("TeaRoomList",roomList);
                }
                int newroomid =dao4.getNewRoomNo();
                session.setAttribute("NewRoomid",newroomid);
            case 3:
                RecordDao dao5 =new RecordDao();
                if (level==3){
                    Student existStudent= (Student) session.getAttribute("user");
                    List<Record> existStuRecords = dao5.ListBySno(existStudent.getNo());
                    session.setAttribute("StuRecList",existStuRecords);
                }else if(level==2) {
                    Teacher existTeacher=(Teacher) session.getAttribute("user");
                    List<Record> records = dao5.RecordMesListTea(existTeacher);
                    session.setAttribute("TeaRecList",records);
                } else if (level==1){
                    List<Record> recordList =dao5.RecordMesList();
                    session.setAttribute("ARecList",recordList);
                }
                MissionDao dao6 = new MissionDao();
                session.setAttribute("NewMissionId",dao6.GetNewId());
                if (level==1||level==2){
                    List<Mission> list = dao6.AllMission();
                    session.setAttribute("AMissionList",list);
                }else if (level==3){
                    Student student =(Student) session.getAttribute("user");
                    List<Mission> list1 =dao6.MissionByExcutor("all");
                    list1.addAll(dao6.MissionByExcutor(student.getBuildname()));
                    list1.addAll(dao6.MissionByExcutor("学生"));
                    session.setAttribute("MissionListForStu",list1);
                }
                MissionRecordDao dao7 =new MissionRecordDao();
                if(level==2){
                    List<Mission_record> list = dao7.Mis_RecByNo(((Teacher)session.getAttribute("user")).getNo());
                    session.setAttribute("MisRecForTea",list);
                }else if (level==3){
                    List<Mission_record> list = dao7.Mis_RecByNo(((Student)session.getAttribute("user")).getNo());
                    session.setAttribute("MisRecForStu",list);
                }
        }
    }
}

