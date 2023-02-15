package dao;

import builds.Build;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import users.Student;
import users.Teacher;

import java.sql.SQLException;
import java.util.List;

public class TeacherDao {
    public int Delete(String no) throws SQLException, ClassNotFoundException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="delete from teacher where no = ?";
        int num=runner.update(sql,no);
        return num;
    }

    public String getNewTeaNo() throws SQLException{
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "SELECT MAX(no) as no from teacher";
        Teacher tea =(Teacher) runner.query(sql,new BeanHandler<Teacher>(Teacher.class));
        tea.setNo(String.valueOf(Integer.parseInt(tea.getNo())+1));
        return tea.getNo();
    }

    public int Insert(Teacher teacher) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into teacher (no,name,sex,username,password,buildid,tel) values(?,?,?,?,?,?,?)";
        int num= runner.update(sql,teacher.getNo(),teacher.getName(),teacher.getSex(),teacher.getUsername()
                ,teacher.getPassword(),teacher.getBuildid(),teacher.getTel());
        return num;
    }

    public Teacher Query(Teacher teacher) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from teacher where username=?";
        Teacher existTeacher=(Teacher) runner.query(sql, new BeanHandler<Teacher>(Teacher.class),teacher.getUsername());
        return existTeacher;
    }

    public Teacher GetBuiId(String buildname) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "SELECT buildid as buildid from build WHERE buildname=?";
        Teacher BuiId = runner.query(sql,new BeanHandler<Teacher>(Teacher.class),buildname);
//        System.out.println(BuiId.getBuildid());
        return  BuiId;
    }

    public Teacher QueryMes(Teacher teacher) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from teacher where teacher.username=?";
        Teacher existTeacher=(Teacher) runner.query(sql, new BeanHandler<>(Teacher.class),teacher.getUsername());
        String sql2 ="select teacher_mes.buildname as buildname from teacher_mes where teacher_mes.no=?";
        Teacher teacher1=(Teacher)runner.query(sql2,new BeanHandler<Teacher>(Teacher.class),existTeacher.getNo());
        existTeacher.setBuildname(teacher1.getBuildname());
        return existTeacher;
    }

    public int Update(Teacher teacher) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="update teacher set name=?,sex=?,username=?,password=?,buildid=?,tel=? where no=?";
        int num= runner.update(sql,teacher.getName(),teacher.getSex(),teacher.getUsername()
                ,teacher.getPassword(),teacher.getBuildid(),teacher.getTel(),teacher.getNo());
        return num;
    }

    public List<Teacher> TeacherList() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from teacher";
        List <Teacher> list=(List<Teacher>) runner.query(sql,new BeanListHandler<Teacher>(Teacher.class));
        return list;
    }

    public List<Teacher> ListByMes(String mes) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from teacher where name like '%"+mes+"%'";
        List <Teacher> list=(List<Teacher>) runner.query(sql,new BeanListHandler<Teacher>(Teacher.class));
        return list;
    }

    public List<Teacher> ListByNo(String no) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from teacher where no = ?";
        List <Teacher> list=(List<Teacher>) runner.query(sql,new BeanListHandler<Teacher>(Teacher.class),no);
        return list;
    }
    public List<Build> BuildWithoutTea() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from build_without_teacher";
        List <Build> list=(List<Build>) runner.query(sql,new BeanListHandler<Build>(Build.class));
        return list;
    }

    public List<Teacher> TeacherMesList() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from teacher_mes";
        List <Teacher> list=(List<Teacher>) runner.query(sql, new BeanListHandler<Teacher>(Teacher.class));
        return list;
    }

    public List<Teacher> Usernamelist() throws SQLException{
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql ="select no,username from teacher";
        List<Teacher> list =(List<Teacher>) runner.query(sql,new BeanListHandler<Teacher>(Teacher.class));
        return list;
    }
}
