package servlet;

import builds.Mission;
import builds.Mission_record;
import builds.Record;
import dao.MissionDao;
import dao.MissionRecordDao;
import dao.RecordDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import users.Student;
import users.Teacher;
import users.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "ServletMissionExecute", value = "/ServletMissionExecute")
@MultipartConfig
public class ServletMissionExecute extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=utf-8");
            request.setCharacterEncoding("utf-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            String savePath = request.getServletContext().getRealPath("/WEB-INF/uploadFile");
            File f = new File(savePath);
            if(!f.exists()&&!f.isDirectory()){
                f.mkdir();
                System.out.println(savePath+"已创建");
            }else {
                System.out.println(savePath+"已存在");
            }
            User user = (User)request.getSession().getAttribute("user");
            factory.setRepository(f);
            ServletFileUpload fileUpload =new ServletFileUpload(factory);
            fileUpload.setHeaderEncoding("utf-8");
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            Mission_record mission_record = new Mission_record();
            mission_record.setExecutorstatus("已完成");
            if(user.getLevel()==1){
                mission_record.setMissionid(((Mission)request.getSession().getAttribute("MissionExecuteForStu")).getMissionid());
            } else if (user.getLevel()==2) {
                mission_record.setMissionid(((Mission)request.getSession().getAttribute("MissionExecuteForTea")).getMissionid());
            }
            mission_record.setExecutorid(user.getNo());
            for (FileItem fileItem :fileItems){
                if(fileItem.isFormField()){
                    String name = fileItem.getFieldName();
                    if(name.equals("reason")){
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String localdate=formatter.format(date);
                        String reason = fileItem.getString("utf-8");
                        Record record =new Record();
                        Student stu=(Student) user;
                        record.setRoomid(stu.getRoomid());
                        record.setBuildid(stu.getBuildid());
                        record.setName(stu.getName());
                        record.setDetails(reason);
                        record.setDate(localdate);
                        record.setNo(stu.getNo());
                        record.setStatus("请假");
                        RecordDao dao = new RecordDao();
                        dao.Insert(record);
                    }
                }else {
                    String filename =fileItem.getName();
                    if(filename!=null&&!filename.equals("")){
                        System.out.println("文件名称"+filename);
                        filename = filename.substring(filename.lastIndexOf("\\")+1);
                        filename= UUID.randomUUID().toString()+"_"+filename;
                        String webPath = "/uploadFile/";
                        String filePath = getServletContext().getRealPath(webPath+filename);
                        mission_record.setFilepath(filename);
                        File file = new File(filePath);
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                        InputStream in = fileItem.getInputStream();
                        FileOutputStream out = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len=in.read(buffer))>0)
                            out.write(buffer,0,len);
                        in.close();
                        out.close();
                        fileItem.delete();
                        System.out.println("文件上传成功");
                    }
                }
            }
            MissionRecordDao dao = new MissionRecordDao();
            RecordDao dao1 = new RecordDao();
            if(dao.MissionRecordInsert(mission_record)>0)
            {

            }
            if (user.getLevel()==2) {
                HttpSession session = request.getSession();
                session.setAttribute("MisRecForTea",dao.Mis_RecByNo(user.getNo()));
                session.setAttribute("TeaRecList",dao1.RecordMesListTea((Teacher) user));
                response.sendRedirect("/teacher/MissionForTea.jsp");
            } else if (user.getLevel()==1) {
                HttpSession session = request.getSession();
                session.setAttribute("StuRecList",dao1.ListBySno(user.getNo()));
                session.setAttribute("MisRecForStu",dao.Mis_RecByNo(user.getNo()));
                response.sendRedirect("/student/MissionList.jsp");
            }
        } catch (FileUploadException | SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
