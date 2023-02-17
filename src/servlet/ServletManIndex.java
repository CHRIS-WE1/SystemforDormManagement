package servlet;

import builds.*;
import builds.Record;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import users.*;
import dao.*;
import users.Student;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "ServletManIndex", value = "/ServletManIndex")
@MultipartConfig
public class ServletManIndex extends HttpServlet {
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
            } else if (action.equals("deletebuild")) {
                DeleteBuild(request, response);
            } else if (action.equals("deleteteacher")) {
                DeleteTeacher(request,response);
            } else if (action.equals("deletestudent")) {
                DeleteStudent(request, response);
            } else if(action.equals("studentupdatepre")){
                    UpdateStuPre(request, response);
            } else if (action.equals("studentupdate")) {
                UpdateStu(request, response);
            }else if(action.equals("teacherupdatepre")){
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
            } else if (action.equals("manupdate")) {
                UpdateMan(request, response);
            }else if (action.equals("buildinsert")){
                InsertBuild(request, response);
            }else if(action.equals("stuinsert")){
                InsertStudent(request, response);
            }else if (action.equals("teainsert")){
                InsertTeacher(request, response);
            } else if (action.equals("roominsert")) {
                InsertRoom(request, response);
            }else if(action.equals("teasearch")){
                SearchTea(request,response);
            }else if(action.equals("stusearch")){
                SearchStu(request,response);
            }else if(action.equals("buisearch")){
                SearchBui(request,response);
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
            }else if(action.equals("refresh")){
                Refresh(request,response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void Refresh(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String filename= request.getParameter("filename");
        refreshMissionList(request, response);
        refreshStuList(request, response);
        refreshRoomList(request, response);
        refreshTeaList(request, response);
        refreshBuiList(request, response);
        refreshRecList(request,response);
        response.sendRedirect("/managers/"+filename);
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
                session.setAttribute("UpdateMission",mission);
                break;
            }
        }
        response.sendRedirect("/managers/MissionUpdate.jsp");
    }

    private void MissionDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("missionid"));
        List<Mission> missions = (List<Mission>) session.getAttribute("AMissionList");
        for (Mission mission : missions){
            if(mission.getMissionid()==missionid){
                session.setAttribute("MissionDetails",mission);
                break;
            }
        }
        response.sendRedirect("/managers/MissionDetails.jsp");
    }

    private void DeleteMission(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int missionid= Integer.parseInt(request.getParameter("missionid"));
        MissionDao dao = new MissionDao();
        try {
            dao.Delete(missionid);
            refreshMissionList(request, response);
            response.sendRedirect("/managers/MissionListForMan.jsp");
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
            session.setAttribute("resmis",list);
        }  else if (method.equals("类型")){
            List<Mission> list =dao.MissionByType(mes);
            session.setAttribute("resmis",list);
        } else if (method.equals("标题")){
            List<Mission> list=dao.MissionByTitle(mes);
            session.setAttribute("resmis",list);
        }
        response.sendRedirect("/managers/MissionShow.jsp");
    }


    private void SearchRecord(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        String date =request.getParameter("date");
        String switchs =request.getParameter("switch");
        if(switchs==null){
            date=null;
        }
//        System.out.println(switchs+"1");
//        System.out.println(date+"2");
 //       System.out.println(mes+1);
        RecordDao dao =new RecordDao();
        if(mes.equals("")){
            List<Record> list =dao.ListByDate(date);
            session.setAttribute("resrec",list);
        }else if (method.equals("学号")) {
            List<Record> list =dao.ListBySno(null,mes,date);
            session.setAttribute("resrec",list);
        }  else if (method.equals("姓名")){
            List<Record> list =dao.ListBySname(null,mes,date);
            session.setAttribute("resrec",list);
        } else if (method.equals("宿舍名称")){
            List<Record> list=dao.ListByBuild(mes,date);
            session.setAttribute("resrec",list);
        }
        response.sendRedirect("/managers/RecordShow.jsp");
    }

    private void SearchRoom(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        String condition = "";
        if (method.equals("宿舍楼名称")) {
            condition="buildname";
        }  else if (method.equals("宿舍名称")){
            condition="roomname";
        }
        RoomDao dao =new RoomDao();
        List<Room> list = dao.RoomBySearch(condition,mes);
        session.setAttribute("resroom",list);
        response.sendRedirect("/managers/RoomShow.jsp");
    }

    private void SearchBui(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        String condition = "";
        if (method.equals("名称")) {
            condition="buildname";
        }  else if (method.equals("宿管老师")){
            condition="teachername";
        }
        BuildDao dao =new BuildDao();
        List<Build> list = dao.BuildBySearch(condition,mes);
        session.setAttribute("resbui",list);
        response.sendRedirect("/managers/BuiShow.jsp");
    }

    private void SearchStu(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        String condition = "";
        if (method.equals("学号")){
            condition="no";
        } else if (method.equals("姓名")) {
            condition="name";
        }  else if (method.equals("电话")){
            condition="tel";
        }
        StudentDao dao =new StudentDao();
        List<Student> list = dao.StudentBySearch(condition,mes);
        session.setAttribute("resstu",list);
        response.sendRedirect("/managers/StuShow.jsp");
    }

    private void SearchTea(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        TeacherDao dao = new TeacherDao();
        if(method.equals("职工号")){
            List<Teacher> list = dao.ListByNo(mes);
            session.setAttribute("restea",list);
        } else if (method.equals("姓名")) {
            List<Teacher> list = dao.ListByMes(mes);
            session.setAttribute("restea",list);
        }
        response.sendRedirect("/managers/TeaShow.jsp");
    }

    private void InsertRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session= request.getSession();
        int roomid= Integer.parseInt(request.getParameter("roomid"));
        String buildname = request.getParameter("buildname");
        String roomname =request.getParameter("roomname");
        RoomDao dao= new RoomDao();
        int buildid = dao.GetBuildid(buildname);
        Room newroom = new Room();
        newroom.setRoomid(roomid);
        newroom.setBuildid(buildid);
        newroom.setRoomname(roomname);
        if (dao.Insert(newroom)>0){
            session.setAttribute("NewRoomid",roomid+1);
            refreshRoomList(request, response);
        }
        response.sendRedirect("/managers/RoomMesforMan.jsp");
    }

    protected void InsertTeacher(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session =request.getSession();
        String tno =request.getParameter("tno");
        String sex = request.getParameter("sex");
        String name =request.getParameter("name");
        String username =request.getParameter("username");
        String pwd= request.getParameter("pwd");
        String tel = request.getParameter("tel");
        String[] buildmes =request.getParameter("build").split("\\|");
        int buildid = Integer.parseInt(buildmes[0]);
        Teacher teacher= new Teacher();
        teacher.setName(name);
        teacher.setPassword(pwd);
        teacher.setUsername(username);
        teacher.setSex(sex);
        teacher.setTel(tel);
        teacher.setNo(tno);
        teacher.setBuildid(buildid);
        TeacherDao dao=new TeacherDao();
        if(dao.Insert(teacher)>0){
            BuildDao dao1 =new BuildDao();
            if(buildid!=0){
                dao1.SetBuildTeacherMes(buildid,tno,name);
            }
            session.removeAttribute("NewTeaNo");
            session.setAttribute("NewTeaNo",tno+1);
            refreshBuiList(request, response);
            refreshTeaList(request, response);
        }
        response.sendRedirect("/managers/TeaMessageforMan.jsp");
    }

    protected void InsertStudent(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session =request.getSession();
        String sno =request.getParameter("sno");
        String sex = request.getParameter("sex");
        String name =request.getParameter("name");
        String pwd= request.getParameter("pwd");
        String tel = request.getParameter("tel");
        String[] buildmes =request.getParameter("build").split("\\|");
        int buildid = Integer.parseInt(buildmes[0]);
        Student student= new Student();
        student.setName(name);
        student.setPassword(pwd);
        student.setUsername(sno);
        student.setSex(sex);
        student.setTel(tel);
        student.setNo(sno);
        student.setBuildid(buildid);
        student.setRoomid(0);
        StudentDao dao=new StudentDao();
        if(dao.Insert(student)>0){
            refreshStuList(request, response);
            response.sendRedirect("managers/StuMessageforMan.jsp");
        }

    }

    protected void InsertBuild(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        int buildid= Integer.parseInt(request.getParameter("buildid"));
        String buildname=request.getParameter("buildname");
        String teacher= request.getParameter("buildteacher");
//        System.out.println(teacher);
        String[] teachermes=teacher.split("\\|");
        String teacherno=teachermes[0];
        String teachername=teachermes[1];
        String details= request.getParameter("details");
        Build build= new Build();
        build.setTeachername(teachername);
        build.setBuildname(buildname);
        build.setBuildid(buildid);
        build.setTeacherno(teacherno);
        build.setDetails(details);
        BuildDao dao=new BuildDao();
        dao.SetTeacherBuildid(buildid,teacherno);
        if(dao.Insert(build)>0){
            session.removeAttribute("NewBuiId");
            session.setAttribute("NewBuiId",buildid+1);
            refreshBuiList(request, response);
            response.sendRedirect("/managers/BuiMesforMan.jsp");
        }else {
            System.out.println("插入失败");
        }

    }

    protected void UpdateStuPre(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        StudentDao dao= new StudentDao();
        String Sno = request.getParameter("sno");
        Student student = new Student();
        student.setUsername(Sno);
        Student UpdateStu = dao.QueryMes(student);
        session.setAttribute("UpdateStu",UpdateStu);
        response.sendRedirect("/managers/StuMesUpdateForMan.jsp");
    }

    protected void UpdateMan(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException, ClassNotFoundException {
        HttpSession session = request.getSession();
        Manager newManager = (Manager) session.getAttribute("user");
        String pwd= request.getParameter("newpwd");
        if(pwd!=null){
            newManager.setPassword(pwd);
        }else{
            newManager.setName(request.getParameter("name"));
            newManager.setSex(request.getParameter("sex"));
            newManager.setUsername(request.getParameter("username"));
            newManager.setTel(request.getParameter("tel"));
        }
        ManagerDao dao= new ManagerDao();
//        System.out.println(request.getParameter("sno"));
//        System.out.println(request.getParameter("name"));
//        System.out.println(request.getParameter("sex"));
//        System.out.println(request.getParameter("username"));
//        System.out.println(request.getParameter("pwd"));
//        System.out.println(request.getParameter("buildname"));
//        System.out.println(request.getParameter("roomname"));
//        System.out.println(request.getParameter("tel"));
//        System.out.println(request.getParameter("roomname"));
        if(dao.Update(newManager)>0) {
            //System.out.println("成功");
            session.removeAttribute("user");
            session.setAttribute("user",newManager);
        }else
        {
            System.out.println("失败");
        }

        response.sendRedirect("/managers/ManMessage.jsp");
    }

    protected void UpdateStu(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException, ClassNotFoundException {
        HttpSession session = request.getSession();
        Student newStudent = new Student();
        newStudent.setNo(request.getParameter("sno"));
        newStudent.setName(request.getParameter("name"));
        newStudent.setSex(request.getParameter("sex"));
        newStudent.setUsername(request.getParameter("username"));
        newStudent.setPassword(request.getParameter("pwd"));
        newStudent.setBuildname(request.getParameter("buildname"));
        newStudent.setRoomname(request.getParameter("roomname"));
        newStudent.setTel(request.getParameter("tel"));
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
        Student buildroomid = dao.GetBuiRoomId(newStudent.getBuildname(), newStudent.getRoomname());
        System.out.println(buildroomid.getBuildid());
        System.out.println(buildroomid.getRoomid());
        newStudent.setBuildid(buildroomid.getBuildid());
        newStudent.setRoomid(buildroomid.getRoomid());
        if(dao.Update(newStudent)>0) {
            System.out.println("成功");
        }else
        {
            System.out.println("失败");
        }
        session.removeAttribute("UpdateStu");
        refreshStuList(request, response);
        response.sendRedirect("/managers/StuMessageforMan.jsp");
    }

    protected void UpdateTeaPre(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        TeacherDao dao= new TeacherDao();
        String tno = request.getParameter("tno");
        Teacher teacher = new Teacher();
        teacher.setNo(tno);
        Teacher UpdateTea = dao.QueryMes(teacher);
//        System.out.println(UpdateTea.getNo());
//        System.out.println(UpdateTea.getName());
//        System.out.println(UpdateTea.getSex());
//        System.out.println(UpdateTea.getUsername());
//        System.out.println(UpdateTea.getPassword());
//        System.out.println(UpdateTea.getBuildid());
//        System.out.println(UpdateTea.getBuildname());
        session.setAttribute("UpdateTea",UpdateTea);
        response.sendRedirect("/managers/TeaMesUpdateForMan.jsp");
    }

    protected void UpdateTea(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        Teacher newTeacher = new Teacher();
        newTeacher.setNo(request.getParameter("tno"));
        newTeacher.setName(request.getParameter("name"));
        newTeacher.setSex(request.getParameter("sex"));
        newTeacher.setUsername(request.getParameter("username"));
        newTeacher.setPassword(request.getParameter("pwd"));
        newTeacher.setBuildname(request.getParameter("buildname"));
        newTeacher.setTel(request.getParameter("tel"));
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
        Teacher buildroomid = dao.GetBuiId(newTeacher.getBuildname());
   //     System.out.println(buildroomid.getBuildid());
        newTeacher.setBuildid(buildroomid.getBuildid());
        if(dao.Update(newTeacher)>0) {
            System.out.println("成功");
        }else
        {
            System.out.println("失败");
        }
        session.removeAttribute("UpdateTea");
        refreshTeaList(request, response);
        response.sendRedirect("/managers/TeaMessageforMan.jsp");
    }

    protected void UpdateBuildPre(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        BuildDao dao= new BuildDao();
        int buildid = Integer.parseInt(request.getParameter("buildid"));
        Build build = new Build();
        build.setBuildid(buildid);
        Build UpdateBui = dao.Query(build);
        List<Teacher> list = dao.TeacherWithoutBuild();
        session.setAttribute("UpdateBui",UpdateBui);
        session.setAttribute("TeaWithoutBuild",list);
        response.sendRedirect("/managers/BuildMesUpdateForMan.jsp");
    }

    protected void UpdateBuild(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        Build newBuild = new Build();
        Build updatebui=(Build)session.getAttribute("UpdateBui");
        BuildDao dao= new BuildDao();
        String teacher= request.getParameter("buildteacher");
//        System.out.println(teacher);
        String[] teachermes=teacher.split("\\|");
        String teacherno=teachermes[0];
        String teachername=teachermes[1];
//        System.out.println(teacherno);
//        System.out.println(teachername);
        int buildid=Integer.parseInt(request.getParameter("buildid"));
        if(teacherno.equals("0")||teachername==null){
            dao.RemoveTeacherBuildid(updatebui.getTeacherno());
        }else {
            dao.RemoveTeacherBuildid(updatebui.getTeacherno());
            dao.SetTeacherBuildid(buildid,teacherno);
        }
        newBuild.setBuildid(buildid);
        newBuild.setBuildname(request.getParameter("buildname"));
        newBuild.setTeacherno(teacherno);
        newBuild.setTeachername(teachername);
        newBuild.setDetails(request.getParameter("details"));
        if(dao.Update(newBuild)>0) {
        //    System.out.println("成功");
            session.removeAttribute("UpdateBui");
            session.removeAttribute("TeaWithoutBuild");
            refreshTeaList(request, response);
            refreshBuiList(request, response);
        }else
        {
            System.out.println("失败");
        }
        response.sendRedirect("/managers/BuiMesforMan.jsp");

    }

    protected void UpdateRoomPre(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        RoomDao dao= new RoomDao();
        int roomid = Integer.parseInt(request.getParameter("roomid"));
        Room room = new Room();
        room.setRoomid(roomid);
        Room UpdateRoom = dao.QueryMes(room);
        session.setAttribute("UpdateRoom",UpdateRoom);
        response.sendRedirect("/managers/RoomMesUpdateForMan.jsp");
    }

    protected void UpdateRoom(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        Room newRoom = new Room();
        newRoom.setRoomid(Integer.parseInt(request.getParameter("roomid")));
        newRoom.setBuildname(request.getParameter("buildname"));
        newRoom.setRoomname(request.getParameter("roomname"));
        RoomDao dao= new RoomDao();
        newRoom.setBuildid(dao.GetBuildid(newRoom.getBuildname()));
//        System.out.println(request.getParameter("tno"));
//        System.out.println(request.getParameter("name"));
//        System.out.println(request.getParameter("sex"));
//        System.out.println(request.getParameter("username"));
//        System.out.println(request.getParameter("pwd"));
//        System.out.println(request.getParameter("Roomname"));
//        System.out.println(request.getParameter("roomname"));
//        System.out.println(request.getParameter("tel"));
//        System.out.println(request.getParameter("roomname"));
        //     System.out.println(Roomroomid.getRoomid());
        if(dao.Update(newRoom)>0) {
            //    System.out.println("成功");
            session.removeAttribute("UpdateRoom");
            refreshRoomList(request, response);
        }else
        {
            System.out.println("失败");
        }
        response.sendRedirect("/managers/RoomMesforMan.jsp");

    }

    protected void DeleteReocord(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        int recordid= Integer.parseInt(request.getParameter("recordid"));
        RecordDao dao = new RecordDao();
        try {
            dao.Delete(recordid);
            List<Record> list = dao.RecordMesList();
            session.removeAttribute("ARecList");
            session.setAttribute("ARecList",list);
            response.sendRedirect("/managers/AllRecordForMan.jsp");
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
            List<Room> list = dao.RoomMesList();
            session.removeAttribute("ARoomList");
            session.setAttribute("ARoomList",list);
            response.sendRedirect("/managers/RoomMesforMan.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void DeleteBuild(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        int buildid= Integer.parseInt(request.getParameter("buildid"));
        BuildDao dao = new BuildDao();
        Build build = new Build();
        build.setBuildid(buildid);
        try {
            build = dao.Query(build);
            dao.RemoveTeacherBuildid(build.getTeacherno());
            dao.Delete(build);
            refreshBuiList(request, response);
            refreshTeaList(request, response);
            response.sendRedirect("/managers/BuiMesforMan.jsp");
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
            List<Student> list = dao.StudentMesList();
            session.removeAttribute("AStuList");
            session.setAttribute("AStuList",list);
            response.sendRedirect("/managers/StuMessageforMan.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void DeleteTeacher(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String no= request.getParameter("no");
        TeacherDao dao = new TeacherDao();
        try {
            dao.Delete(no);
            List<Teacher> list = dao.TeacherMesList();
            session.removeAttribute("ATeaList");
            session.setAttribute("ATeaList",list);
            response.sendRedirect("/managers/TeaMessageforMan.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void refreshStuList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        StudentDao dao= new StudentDao();
        List<Student> list = dao.StudentMesList();
        session.removeAttribute("AStuList");
        session.setAttribute("AStuList",list);
    }

    protected void refreshTeaList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        TeacherDao dao= new TeacherDao();
        List<Teacher> list = dao.TeacherMesList();
        List<Build> list1=dao.BuildWithoutTea();

        session.removeAttribute("BuildWithoutTea");
        session.setAttribute("BuildWithoutTea",list1);
        session.removeAttribute("ATeaList");
        session.setAttribute("ATeaList",list);
    }

    protected void refreshBuiList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        BuildDao dao= new BuildDao();
        List<Build> list = dao.BuildList();
        List<Teacher> list2 =dao.TeacherWithoutBuild();
        session.removeAttribute("ABuiList");
        session.removeAttribute("TeaWithoutBuild");
        session.setAttribute("TeaWithoutBuild",list2);
        session.setAttribute("ABuiList",list);
    }

    protected void refreshRoomList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        RoomDao dao= new RoomDao();
        List<Room> list = dao.RoomMesList();
        session.removeAttribute("ARoomList");
        session.setAttribute("ARoomList",list);
    }

    protected void refreshMissionList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session =request.getSession();
        MissionDao dao = new MissionDao();
        List<Mission> list =dao.AllMission();
        session.setAttribute("AMissionList",list);
    }

    protected void refreshRecList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        RecordDao dao = new RecordDao();
        List<Record> list = dao.RecordMesList();
        session.removeAttribute("ARecList");
        session.setAttribute("ARecList",list);
    }
}
