package servlet;

import builds.Mission;
import dao.MissionDao;
import users.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "ServletUpload", value = "/ServletUpload")
@MultipartConfig
public class ServletUpload extends HttpServlet {

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
            factory.setRepository(f);
            ServletFileUpload fileUpload =new ServletFileUpload(factory);
            fileUpload.setHeaderEncoding("utf-8");
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            Mission mission = new Mission();
            mission.setMissionid((Integer) request.getSession().getAttribute("NewMissionId"));
            mission.setPublisherid(((User)request.getSession().getAttribute("user")).getNo());
            mission.setBegintime(null);
            mission.setEndtime(null);
            mission.setDetails(null);
            for (FileItem fileItem :fileItems){
                if(fileItem.isFormField()){
                    String name =fileItem.getFieldName();
                    if(name.equals("title")){
                        String value = fileItem.getString("utf-8");
                        System.out.println("上传者"+value);
                        mission.setTitle(value);
                    }else if (name.equals("details")) {
                        mission.setDetails(fileItem.getString("utf-8"));
                    }else if (name.equals("begintime")) {
                        mission.setBegintime(fileItem.getString("utf-8").replace("T"," "));
                    }else if (name.equals("endtime")) {
                        mission.setEndtime(fileItem.getString("utf-8").replace("T"," "));
                    }else if (name.equals("executor")) {
                        mission.setExecutor(fileItem.getString("utf-8"));
                    }else if (name.equals("type")) {
                        mission.setType(fileItem.getString("utf-8"));
                    }
                }else {
                    String filename =fileItem.getName();
                    if(filename!=null&&!filename.equals("")){
                        System.out.println("文件名称"+filename);
                        filename = filename.substring(filename.lastIndexOf("\\")+1);
                        filename= UUID.randomUUID().toString()+"_"+filename;
                        String webPath = "/uploadFile/";
                        String filePath = getServletContext().getRealPath(webPath+filename);
                        mission.setFilepath(filename);
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
            MissionDao dao = new MissionDao();
            if(dao.MissionInsert(mission)>0)
            {
                request.getSession().setAttribute("NewMissionId",dao.GetNewId());
            }
            request.getSession().setAttribute("AMissionList",dao.AllMission());
            User user = (User)request.getSession().getAttribute("user");
            if(user.getLevel()==3){
                response.sendRedirect("/managers/MissionListForMan.jsp");
            } else if (user.getLevel()==2) {
                response.sendRedirect("/teacher/MissionForTea.jsp");
            }
        } catch (FileUploadException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
