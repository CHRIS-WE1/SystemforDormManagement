package servlet;

import builds.Mission;
import builds.Mission_record;
import dao.MissionRecordDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import users.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "ServletMissionRecordUpdate", value = "/ServletMissionRecordUpdate")
@MultipartConfig
public class ServletMissionRecordUpdate extends HttpServlet {
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
            Mission_record mission_record=new Mission_record();
            if(user.getLevel()==1){
                mission_record = (Mission_record) request.getSession().getAttribute("MRUpdateForStu");
            }else if (user.getLevel()==2){
                mission_record = (Mission_record) request.getSession().getAttribute("MRUpdateForTea");
            }

            for (FileItem fileItem :fileItems){
                if(fileItem.isFormField()){

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
                        //fin.close();
                        fileItem.delete();
                        System.out.println("文件上传成功");
                    }
                }
            }
            MissionRecordDao dao = new MissionRecordDao();
            if(dao.MissionRecordUpdate(mission_record)>0)
            {

            }
            HttpSession session = request.getSession();

            if(user.getLevel()==1){
                session.setAttribute("StuRecList",dao.Mis_RecByNo(user.getNo()));
                response.sendRedirect("/student/MissionList.jsp");
            } else if (user.getLevel()==2) {
                session.setAttribute("MisRecForTea",dao.Mis_RecByNo(user.getNo()));
                response.sendRedirect("/teacher/MissionForTea.jsp");
            }
        } catch (FileUploadException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
