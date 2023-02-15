package servlet;

import builds.*;
import builds.Record;
import dao.*;
import users.Student;
import users.Teacher;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "ServletStuIndex", value = "/ServletStuIndex")
public class ServletStuIndex extends HttpServlet {
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
            if (action.equals("studentupdate")) {
                UpdateStu(request, response);
            } else if(action.equals("recsearch")){
                SearchRecord(request,response);
            }else if(action.equals("missionsearch")){
                SearchMission(request,response);
            }else if(action.equals("misdetails")){
                MissionDetails(request,response);
            }else if (action.equals("misfinish")){
                MissionExecute(request,response);
            }else if (action.equals("mrupdatepre")){
                MissionRecordUpdatePre(request,response);
            }else if (action.equals("mrdetails")){
                MissionRecordDetails(request,response);
            }else if(action.equals("refresh")){
                Refresh(request,response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void Refresh(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String filename= request.getParameter("filename");
        refreshMisList(request, response);
        refreshRecList(request, response);
        refreshMisRecList(request, response);
        response.sendRedirect("/student/"+filename);
    }

    private void MissionRecordDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("missionid"));
        List<Mission_record> missions = (List<Mission_record>) session.getAttribute("MisRecForStu");
        for (Mission_record mission : missions){
            if(mission.getMissionid()==missionid){
                session.setAttribute("MisRecDetailsForStu",mission);
                break;
            }
        }
        response.sendRedirect("/student/MissionRecordDetails.jsp");
    }
    //学生的数据查询？？
    private void SearchMission(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();
        String method =request.getParameter("method");
        String mes = request.getParameter("mes");
        Student stu = (Student)session.getAttribute("user");
//        System.out.println(switchs+"1");
//        System.out.println(date+"2");
        //       System.out.println(mes+1);
        MissionDao dao = new MissionDao();
        if (method.equals("发布者")) {
            List<Mission> list =dao.MissionByPublisherForStu(mes,stu.getBuildname());
            session.setAttribute("resmisforstu",list);
        }  else if (method.equals("类型")){
            List<Mission> list =dao.MissionByTypeForStu(mes,stu.getBuildname());
            session.setAttribute("resmisforstu",list);
        } else if (method.equals("标题")){
            List<Mission> list=dao.MissionByTitleForStu(mes,stu.getBuildname());
            session.setAttribute("resmisforstu",list);
        }
        response.sendRedirect("/student/MissionShow.jsp");
    }
    private void MissionDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("missionid"));
        List<Mission> missions = (List<Mission>) session.getAttribute("MissionListForStu");
        for (Mission mission : missions){
            if(mission.getMissionid()==missionid){
                session.setAttribute("MissionDetailsForStu",mission);
                break;
            }
        }
        response.sendRedirect("/student/MissionDetails.jsp");
    }
    //任务执行？？
    private void MissionExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("missionid"));
        List<Mission> missions = (List<Mission>) session.getAttribute("MissionListForStu");
        for (Mission mission : missions){
            if(mission.getMissionid()==missionid){
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String localdate=formatter.format(date);
                if(localdate.compareTo(mission.getBegintime()) < 0){
                    session.setAttribute("MissionExeError","任务还未开始");
                    response.sendRedirect("/student/MissionList.jsp");
                }else if(localdate.compareTo(mission.getEndtime()) > 0){
                    session.setAttribute("MissionExeError","任务已结束");
                    response.sendRedirect("/student/MissionList.jsp");
                }else {
                    session.setAttribute("MissionExecuteForStu",mission);
                    if(mission.getType().equals("签到")){
                        String method =request.getParameter("method");
                        if(method.equals("signin")){
                            //System.out.println("sigin");
                            Student stu = (Student) session.getAttribute("user");
                            Record record =new Record();
                            Mission_record mr = new Mission_record();
                            MissionRecordDao dao2 =new MissionRecordDao();
                            mr.setMissionid(missionid);
                            mr.setExecutorid(stu.getNo());
                            mr.setExecutorstatus("已完成");
                            dao2.MissionRecordInsert(mr);
                            record.setRoomid(stu.getRoomid());
                            record.setBuildid(stu.getBuildid());
                            record.setName(stu.getName());
                            record.setDate(localdate);
                            record.setNo(stu.getNo());
                            record.setStatus("已完成");
                            RecordDao dao = new RecordDao();
                            dao.Insert(record);
                            refreshRecList(request, response);
                            refreshMisRecList(request, response);
                            response.sendRedirect("/student/MissionList.jsp");
                        } else if (method.equals("leave")) {
                            response.sendRedirect("/student/SignIn.jsp");
                        }

                    } else if (mission.getType().equals("其它")) {
                        response.sendRedirect("/student/MissionExecute.jsp");
                    }
                }
                break;
            }
        }
    }



    //未完成
    private void MissionRecordUpdatePre(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int missionid = Integer.parseInt(request.getParameter("mid"));
        int mrid=Integer.parseInt(request.getParameter("mrid"));
        List<Mission> missions = (List<Mission>) session.getAttribute("MissionListForStu");
        for (Mission mission : missions){
            if(mission.getMissionid()==missionid){
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String localdate=formatter.format(date);
                if(localdate.compareTo(mission.getEndtime()) > 0){
                    session.setAttribute("MRUpdateErrorForStu","任务已结束,无法修改");
                    response.sendRedirect("/student/MissionDetails.jsp");
                }else {
                    List<Mission_record> mr=(List<Mission_record>)session.getAttribute("MisRecForStu");
                    for (Mission_record ms:mr){
                        if(ms.getMission_record_id()==mrid){
                            System.out.println(2);
                            session.setAttribute("MRUpdateForStu",ms);
                            response.sendRedirect("/student/MissionRecordUpdate");
                        }
                    }
                }
            }
        }
    }
    private void SearchRecord(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        String date =request.getParameter("date");
        Student stu = (Student) session.getAttribute("user");
//        System.out.println(switchs+"1");
//        System.out.println(date+"2");
        //       System.out.println(mes+1);
        RecordDao dao =new RecordDao();
        List<Record> list =dao.ListByDate(stu.getNo(),date);
        session.setAttribute("sturesrec",list);
        response.sendRedirect("/student/RecListShowForStu.jsp");
    }

    protected void UpdateStu(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException, ClassNotFoundException {
        HttpSession session = request.getSession();
        Student newStudent = (Student) session.getAttribute("user");
        String pwd = request.getParameter("newpwd");
        if (pwd!=null){
            newStudent.setPassword(pwd);
        }
        else {
            newStudent.setName(request.getParameter("name"));
            newStudent.setSex(request.getParameter("sex"));
            newStudent.setTel(request.getParameter("tel"));
        }
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
        if(dao.Update(newStudent)>0) {
            session.setAttribute("user",newStudent);
            System.out.println("成功");
        }else
        {
            System.out.println("失败");
        }
        response.sendRedirect("/student/StuMesForStu.jsp");
    }



    protected void refreshRecList(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        RecordDao dao = new RecordDao();
        List<Record> list = dao.ListBySno(((Student)request.getSession().getAttribute("user")).getNo());
        session.removeAttribute("StuRecList");
        session.setAttribute("StuRecList",list);
    }

    private void refreshMisRecList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        Student stu = (Student) session.getAttribute("user");
        MissionRecordDao dao =new MissionRecordDao();
        session.setAttribute("MisRecForStu",dao.Mis_RecByNo(stu.getNo()));
    }

    private void refreshMisList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        MissionDao dao6 =new MissionDao();
        Student student =(Student) session.getAttribute("user");
        List<Mission> list1 =dao6.MissionByExcutor("all");
        list1.addAll(dao6.MissionByExcutor(student.getBuildname()));
        list1.addAll(dao6.MissionByExcutor("学生"));
        session.setAttribute("MissionListForStu",list1);
    }
}
