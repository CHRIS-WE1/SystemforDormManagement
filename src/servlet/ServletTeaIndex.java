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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "ServletTeaIndex", value = "/ServletTeaIndex")
public class ServletTeaIndex extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if(action.equals("deleterecord")){
                DeleteReocord(request, response);
            } else if (action.equals("deleteroom")) {
                DeleteRoom(request, response);
            } else if (action.equals("deletestudent")) {
                DeleteStudent(request, response);
            } else if(action.equals("studentupdatepre")){
                UpdateStuPre(request, response);
            } else if (action.equals("studentupdate")) {
                UpdateStu(request, response);
            }else if(action.equals("teaupdatepre")){
                UpdateTeaPre(request, response);
            } else if (action.equals("teacherupdate")) {
                UpdateTea(request, response);
            }else if(action.equals("buildupdatepre")){
                UpdateBuildPre(request, response);
            } else if (action.equals("buildupdate")) {
                UpdateBuild(request, response);
            }else if(action.equals("roomupdatepre")){
                UpdateRoomPre(request, response);
            } else if (action.equals("roomupdate")) {
                UpdateRoom(request, response);
            } else if(action.equals("studentinsertpre")){
                InsertStudentPre(request, response);
            }else if(action.equals("stuinsert")){
                InsertStudent(request, response);
            }else if (action.equals("roominsert")) {
                InsertRoom(request, response);
            }else if(action.equals("stusearch")){
                SearchStu(request,response);
            }else if(action.equals("roomsearch")){
                SearchRoom(request,response);
            }else if(action.equals("recsearch")){
                SearchRecord(request,response);
            }else if(action.equals("missionsearch")){
                SearchMission(request,response);
            }else if(action.equals("missiondelete")){
                DeleteMission(request,response);
            }else if(action.equals("misdetails")){
                MissionDetails(request,response);
            }else if(action.equals("missionupdatepre")){
                MissionUpdatePre(request,response);
            }else if(action.equals("missionupdate")){
                MissionUpdate(request,response);
            }else if(action.equals("missioninsert")){
                MissionInsert(request,response);
            }else if (action.equals("misfinish")){
                MissionExecute(request,response);
            }else if (action.equals("mrupdatepre")){
                MissionRecordUpdatePre(request,response);
            }else if (action.equals("mrdetails")){
                MissionRecordDetails(request,response);
            }else if(action.equals("refresh")){
                Refresh(request,response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void Refresh(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String filename= request.getParameter("filename");
        refreshMissionList(request, response);
        refreshRecList(request, response);
        refreshStuList(request, response);
        refreshRoomList(request, response);
        refreshMisRecList(request, response);
        response.sendRedirect("/teacher/"+filename);
    }



    private void MissionRecordDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("missionid"));
        List<Mission_record> missions = (List<Mission_record>) session.getAttribute("MisRecForTea");
        for (Mission_record mission : missions){
            if(mission.getMissionid()==missionid){
                session.setAttribute("MisRecDetailsForTea",mission);
                break;
            }
        }
        response.sendRedirect("/teacher/MisRecDetailsForTea.jsp");
    }

    private void MissionRecordUpdatePre(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("mid"));
        int mrid=Integer.parseInt(request.getParameter("mrid"));
        List<Mission> missions = (List<Mission>) session.getAttribute("AMissionList");
        for (Mission mission : missions){
            if(mission.getMissionid()==missionid){
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String localdate=formatter.format(date);
                if(localdate.compareTo(mission.getEndtime())==1){
                    session.setAttribute("MRUpdateError","任务已结束,无法修改");
                    response.sendRedirect("/teacher/MisRecDetailsForTea.jsp");
                }else {
                    List<Mission_record> mr=(List<Mission_record>)session.getAttribute("MisRecForTea");
                    for (Mission_record ms:mr){
                        if(ms.getMission_record_id()==mrid){
                            session.setAttribute("MRUpdateForTea",ms);
                            response.sendRedirect("/teacher/MisRecUpdate.jsp");
                        }
                    }
                }
                break;
            }
        }
    }

    private void MissionExecute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("missionid"));
        List<Mission> missions = (List<Mission>) session.getAttribute("AMissionList");
        for (Mission mission : missions){
            if(mission.getMissionid()==missionid){
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String localdate=formatter.format(date);
                if(localdate.compareTo(mission.getBegintime())==-1){
                    session.setAttribute("MissionExeError","任务还未开始");
                    response.sendRedirect("/teacher/MissionForTea.jsp");
                }else if(localdate.compareTo(mission.getEndtime())==1){
                    session.setAttribute("MissionExeError","任务已结束");
                    response.sendRedirect("/teacher/MissionForTea.jsp");
                }else {
                    session.setAttribute("MissionExecuteForTea",mission);
                    response.sendRedirect("/teacher/MissionExecute.jsp");
                }
                break;
            }
        }
    }

    private void MissionInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getRequestDispatcher("/ServletUpload").forward(request,response);
            return;
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void MissionUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/ServletMissionUpdate").forward(request,response);
    }

    private void MissionUpdatePre(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("missionid"));
        List<Mission> missions = (List<Mission>) session.getAttribute("AMissionList");
        for (Mission mission : missions){
            if(mission.getMissionid()==missionid){
                session.setAttribute("UpdateMissionForTea",mission);
                break;
            }
        }
        response.sendRedirect("/teacher/MissionUpdateForTea.jsp");
    }

    private void MissionDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("missionid"));
        List<Mission> missions = (List<Mission>) session.getAttribute("AMissionList");
        for (Mission mission : missions){
            if(mission.getMissionid()==missionid){
                session.setAttribute("MissionDetailsForTea",mission);
                break;
            }
        }
        response.sendRedirect("/teacher/MissionDetailsForTea.jsp");
    }

    private void DeleteMission(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int missionid= Integer.parseInt(request.getParameter("missionid"));
        MissionDao dao = new MissionDao();
        try {
            dao.Delete(missionid);
            refreshMissionList(request, response);
            response.sendRedirect("/teacher/MissionForTea.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void SearchMission(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
//        System.out.println(switchs+"1");
//        System.out.println(date+"2");
        //       System.out.println(mes+1);
        MissionDao dao = new MissionDao();
        if (method.equals("发布者")) {
            List<Mission> list =dao.MissionByPublisher(mes);
            session.setAttribute("resmisfortea",list);
        }  else if (method.equals("类型")){
            List<Mission> list =dao.MissionByType(mes);
            session.setAttribute("resmisfortea",list);
        } else if (method.equals("标题")){
            List<Mission> list=dao.MissionByTitle(mes);
            session.setAttribute("resmisfortea",list);
        } else if (method.equals("执行者")) {
            List<Mission> list=dao.MissionByExcutor(mes);
            session.setAttribute("resmisfortea",list);
        }
        response.sendRedirect("/teacher/MissionShowForTea.jsp");
    }

    private void InsertStudentPre(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        StudentDao dao = new StudentDao();
        List<Student> list =dao.UsernameList();
        session.setAttribute("SnoList",list);
        response.sendRedirect("/teacher/StuInsertForTea.jsp");
    }

    private void SearchRecord(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        String date =request.getParameter("date");
        String switchs =request.getParameter("switch");
        Teacher tea = (Teacher) session.getAttribute("user");
        if(switchs==null){
            date=null;
        }
//        System.out.println(switchs+"1");
//        System.out.println(date+"2");
        //       System.out.println(mes+1);
        RecordDao dao =new RecordDao();
        if(mes.equals("")){
            List<Record> list =dao.ListByDate(date);
            session.setAttribute("tearesrec",list);
        }else if (method.equals("学号")) {
            List<Record> list =dao.ListBySno(tea.getBuildname(),mes,date);
            session.setAttribute("tearesrec",list);
        }  else if (method.equals("姓名")){
            List<Record> list =dao.ListBySname(tea.getBuildname(),mes,date);
            session.setAttribute("tearesrec",list);
        } else if (method.equals("宿舍名称")){
            List<Record> list=dao.ListByRoom(tea.getBuildname(),mes,date);
            session.setAttribute("tearesrec",list);
        }
        response.sendRedirect("/teacher/RecordShowForTea.jsp");
    }

    private void SearchRoom(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        String condition = "roomname";
        RoomDao dao =new RoomDao();
        List<Room> list = dao.RoomBySearch(((Teacher)session.getAttribute("user")).getBuildname(),condition,mes);
        session.setAttribute("resroomfortea",list);
        response.sendRedirect("/teacher/RoomShowForTea.jsp");
    }


    private void SearchStu(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        System.out.println("mes"+mes);
        String condition = "";
        if (method.equals("学号")){
            condition="no";
        } else if (method.equals("姓名")) {
            condition="name";
        }  else if (method.equals("电话")){
            condition="tel";
        } else if (method.equals("宿舍")){
            condition="roomname";
        }
        StudentDao dao =new StudentDao();
        List<Student> list = dao.StudentBySearch(((Teacher)session.getAttribute("user")).getBuildname(),condition,mes);
        for (Student stu:list){
            System.out.println(stu.getName()+stu.getNo());
        }
        session.setAttribute("resstufortea",list);
        response.sendRedirect("/teacher/StuShowForTea.jsp");
    }


    private void InsertRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session= request.getSession();
        int roomid= Integer.parseInt(request.getParameter("roomid"));
        String build = request.getParameter("build");
        String roomname =request.getParameter("roomname");
        String[] buildmes =request.getParameter("build").split("\\|");
        int buildid = Integer.parseInt(buildmes[0]);

        RoomDao dao= new RoomDao();
        Room newroom = new Room();
        newroom.setRoomid(roomid);
        newroom.setBuildid(buildid);
        newroom.setRoomname(roomname);
        if (dao.Insert(newroom)>0){
            session.setAttribute("NewRoomid",roomid+1);
            refreshRoomList(request, response);
        }
        response.sendRedirect("/teacher/RoomMesForTea.jsp");
    }


    protected void InsertStudent(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        String sno =request.getParameter("sno");
        String sex = request.getParameter("sex");
        String name =request.getParameter("name");
        String pwd= request.getParameter("pwd");
        String tel = request.getParameter("tel");
        String[] buildmes =request.getParameter("build").split("\\|");
        int buildid = Integer.parseInt(buildmes[0]);
        String[] roommes =request.getParameter("room").split("\\|");
        int roomid = Integer.parseInt(roommes[0]);

        Student student= new Student();
        student.setName(name);
        student.setPassword(pwd);
        student.setUsername(sno);
        student.setSex(sex);
        student.setTel(tel);
        student.setNo(sno);
        student.setBuildid(buildid);
        student.setRoomid(roomid);
        StudentDao dao=new StudentDao();
        if(dao.Insert(student)>0){
            refreshStuList(request, response);

        }
        response.sendRedirect("/teacher/StuMesForTea.jsp");
    }

    protected void UpdateStuPre(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        StudentDao dao= new StudentDao();
        String sno = request.getParameter("sno");
        List<Student> list = (List<Student>) session.getAttribute("TeaStuList");
        for (Student stu :list){
            if (!Objects.equals(stu.getNo(), sno)){
                continue;
            }
            if(stu.getNo().equals(sno)){
                session.setAttribute("UpdateStuForTea", stu);
               // System.out.println(stu.getName()+stu.getNo());
                break;
            }
        }
        response.sendRedirect("/teacher/StuMesUpdateForTea.jsp");
    }

    protected void UpdateStu(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException, ClassNotFoundException {
        HttpSession session = request.getSession();
        Student newStudent = (Student) session.getAttribute("UpdateStuForTea");
        newStudent.setName(request.getParameter("name"));
        newStudent.setSex(request.getParameter("sex"));
        newStudent.setPassword(request.getParameter("pwd"));
        newStudent.setTel(request.getParameter("tel"));
        String roommes = request.getParameter("roomname");
        String[] rooms=roommes.split("\\|");

        StudentDao dao= new StudentDao();
//        System.out.println(request.getParameter("sno"));
//        System.out.println(request.getParameter("name"));
//        System.out.println(request.getParameter("sex"));
//        System.out.println(request.getParameter("username"));
//        System.out.println(request.getParameter("pwd"));
//        System.out.println(request.getParameter("buildname"));
//        System.out.println(request.getParameter("roomname"));
//        System.out.println(request.getParameter("tel"));
//        System.out.println(request.getParameter("roomname"));
        newStudent.setRoomid(Integer.parseInt(rooms[0]));
        if(dao.Update(newStudent)>0) {
            System.out.println("成功");
        }else
        {
            System.out.println("失败");
        }
        session.removeAttribute("UpdateStuForTea");
        refreshStuList(request, response);
        response.sendRedirect("/teacher/StuMesForTea.jsp");
    }

    protected void UpdateTeaPre(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        TeacherDao dao= new TeacherDao();
        List<Teacher> usernamelist = dao.Usernamelist();
        session.setAttribute("TeaUsernameList",usernamelist);
        response.sendRedirect("/teacher/TeaMesUpdate.jsp");
    }

    protected void UpdateTea(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        Teacher newTeacher = (Teacher) session.getAttribute("user");
        String pwd = request.getParameter("newpwd");
        //System.out.println(pwd);
        if(pwd==null){
            newTeacher.setName(request.getParameter("name"));
            newTeacher.setSex(request.getParameter("sex"));
            newTeacher.setUsername(request.getParameter("username"));
            newTeacher.setTel(request.getParameter("tel"));
        }
        else {
            newTeacher.setPassword(pwd);
        }
        TeacherDao dao= new TeacherDao();
//        System.out.println(request.getParameter("tno"));
//        System.out.println(request.getParameter("name"));
//        System.out.println(request.getParameter("sex"));
//        System.out.println(request.getParameter("username"));
//        System.out.println(request.getParameter("pwd"));
//        System.out.println(request.getParameter("buildname"));
//        System.out.println(request.getParameter("roomname"));
//        System.out.println(request.getParameter("tel"));
//        System.out.println(request.getParameter("roomname"));
        //     System.out.println(buildroomid.getBuildid());
        if(dao.Update(newTeacher)>0) {
            System.out.println("成功");
            session.removeAttribute("user");
            session.setAttribute("user",newTeacher);
        }else
        {
            System.out.println("失败");
        }
        session.removeAttribute("TeaUsernameList");
        response.sendRedirect("/teacher/TeaMessage.jsp");
    }

    protected void UpdateBuildPre(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        BuildDao dao= new BuildDao();
        List<Build> list = dao.BuildNameList();
        session.setAttribute("BuildNameList",list);
        response.sendRedirect("/teacher/BuiMesUpdateForTea.jsp");
    }

    protected void UpdateBuild(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        Build newBuild = (Build) session.getAttribute("TeaBui");;
        BuildDao dao= new BuildDao();
        newBuild.setBuildname(request.getParameter("buildname"));
        newBuild.setDetails(request.getParameter("details"));
        if(dao.Update(newBuild)>0) {
            session.removeAttribute("TeaBui");
            session.setAttribute("TeaBui",newBuild);
        }else
        {
            System.out.println("失败");
        }
        session.removeAttribute("BuildNameList");
        response.sendRedirect("/teacher/BuildMessageForTea.jsp");
    }

    protected void UpdateRoomPre(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        RoomDao dao= new RoomDao();
        int roomid = Integer.parseInt(request.getParameter("roomid"));
        List<Room> list = (List<Room>) session.getAttribute("TeaRoomList");
        for (Room stu :list){
            if (!Objects.equals(stu.getRoomid(), roomid)){
                continue;
            }
            if(stu.getRoomid()==roomid){
                session.setAttribute("UpdateRoomForTea", stu);
                // System.out.println(stu.getName()+stu.getNo());
                break;
            }
        }
        response.sendRedirect("/teacher/RoomUpdateForTea.jsp");
    }

    protected void UpdateRoom(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        Room newRoom = (Room)session.getAttribute("UpdateRoomForTea");
        newRoom.setRoomname(request.getParameter("roomname"));
        RoomDao dao= new RoomDao();
        if(dao.Update(newRoom)>0) {
            session.removeAttribute("UpdateRoomForTea");
            refreshRoomList(request, response);
        }else
        {
            System.out.println("失败");
        }
        response.sendRedirect("/teacher/RoomMesForTea.jsp");
    }

    protected void DeleteReocord(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        int recordid= Integer.parseInt(request.getParameter("recordid"));
        RecordDao dao = new RecordDao();
        try {
            dao.Delete(recordid);
            refreshRecList(request, response);
            response.sendRedirect("/teacher/RecordForTea.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void DeleteRoom(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        int roomid= Integer.parseInt(request.getParameter("roomid"));
        RoomDao dao = new RoomDao();
        try {
            dao.Delete(roomid);
            refreshRoomList(request, response);
            response.sendRedirect("/teacher/RoomMesForTea.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void DeleteStudent(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String  no = request.getParameter("no");
        StudentDao dao = new StudentDao();
        try {
            dao.Delete(no);
            refreshStuList(request, response);
            response.sendRedirect("/teacher/StuMesForTea.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void refreshRecList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        RecordDao dao = new RecordDao();
        List<Record> list = dao.RecordMesListTea((Teacher) request.getSession().getAttribute("user"));
        session.removeAttribute("TeaRecList");
        session.setAttribute("TeaRecList",list);
    }

    protected void refreshStuList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        StudentDao dao= new StudentDao();
        List<Student> list = dao.ListByBuildname(((Teacher)session.getAttribute("user")).getBuildname());
        session.removeAttribute("TeaStuList");
        session.setAttribute("TeaStuList",list);
    }

    protected void refreshRoomList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        RoomDao dao= new RoomDao();
        List<Room> list = dao.GetRoomByBuild(((Teacher)session.getAttribute("user")).getBuildname());
        session.setAttribute("TeaRoomList",list);
    }

    protected void refreshMissionList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session =request.getSession();
        MissionDao dao = new MissionDao();
        List<Mission> list =dao.AllMission();
        session.setAttribute("AMissionList",list);
    }

    private void refreshMisRecList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        MissionRecordDao dao= new MissionRecordDao();
        List<Mission_record> list = dao.Mis_RecByNo(((Teacher)session.getAttribute("user")).getNo());
        session.setAttribute("MisRecForTea",list);
    }
}
