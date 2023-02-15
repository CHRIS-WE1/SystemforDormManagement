package dao;

import users.*;
import builds.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;


public class Dao {
    public int Delete(String no,String role) throws SQLException, ClassNotFoundException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="delete from "+role+" where no= ?";
        int num=runner.update(sql,no);
        return num;
    }

    public int Delete(Room room) throws SQLException, ClassNotFoundException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="delete from room where roomid= ?";
        int num=runner.update(sql,room.getRoomid());
        return num;
    }

    public int Delete(Build build) throws SQLException, ClassNotFoundException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="delete from room where buildid= ?";
        int num=runner.update(sql,build.getBuildid());
        return num;
    }

    public int Insert(Room room) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into room (roomid,buildid,roomname) values (?,?,?)";
        int num = runner.update(sql,room.getRoomid(),room.getBuildid(),room.getRoomname());
        return num;
    }

    public int Insert(Build build) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into build (buildid,buildname,details) values (?,?,?)";
        int num = runner.update(sql,build.getBuildid(),build.getBuildname(),build.getDetails());
        return num;
    }

    public int Insert(Teacher teacher) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into teacher (no,name,sex,username,password,buildid,tel) values(?,?,?,?,?,?,?)";
        int num= runner.update(sql,teacher.getNo(),teacher.getName(),teacher.getSex(),teacher.getUsername()
                ,teacher.getPassword(),teacher.getBuildid(),teacher.getTel());
        return num;
    }

    public int Insert(Student student) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into student (no,name,sex,username,password,buildid,roomid,tel) values(?,?,?,?,?,?,?)";
        int num= runner.update(sql,student.getNo(),student.getName(),student.getSex(),student.getUsername()
                ,student.getPassword(),student.getBuildid(),student.getRoomid(),student.getTel());
        return num;
    }

    public int Insert(Manager manager) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into teacher (no,name,sex,username,password,tel) values(?,?,?,?,?,?,?)";
        int num= runner.update(sql,manager.getNo(),manager.getName(),manager.getSex(),manager.getUsername()
                ,manager.getPassword(),manager.getTel());
        return num;
    }

    public Teacher Query(Teacher teacher) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from teacher where username=?";
        Teacher existTeacher=(Teacher) runner.query(sql, new BeanHandler<Teacher>(Teacher.class),teacher.getUsername());
        return existTeacher;
    }

    public Student Query(Student student) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from student where username=?";
        Student existStudent=(Student) runner.query(sql, new BeanHandler<Student>(Student.class),student.getUsername());
        return existStudent;
    }

    public Manager Query(Manager manager) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql ="select * from manager where username=?";
        Manager existManager=(Manager) runner.query(sql,new BeanHandler<Manager>(Manager.class),manager.getUsername());
        return existManager;
    }

    public Build Query(Build build) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from build where buildid=?";
        Build existBuild =(Build) runner.query(sql,new BeanHandler<Build>(Build.class),build.getBuildid());
        return existBuild;
    }

    public Room Query(Room room) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from room where roomid=?";
        Room existRoom =(Room) runner.query(sql,new BeanHandler<Room>(Room.class),room.getBuildid());
        return existRoom;
    }

    public int Update(Teacher teacher) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="update teacher set name=?,sex=?,username=?,password=?,buildid=?,tel=? where no=?";
        int num= runner.update(sql,teacher.getName(),teacher.getSex(),teacher.getUsername()
                ,teacher.getPassword(),teacher.getBuildid(),teacher.getTel(),teacher.getNo());
        return num;
    }

    public int Update(Student student) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="update student set name=?,sex=?,username=?,password=?,buildid=?,roomid=?,tel=? where no=?";
        int num= runner.update(sql,student.getName(),student.getSex(),student.getUsername()
                ,student.getPassword(),student.getBuildid(),student.getRoomid(),student.getTel(),student.getNo());
        return num;
    }

    public int Update(Manager manager) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="update teacher set name=?,sex=?,username=?,password=?,tel=? where no=?";
        int num= runner.update(sql,manager.getName(),manager.getSex(),manager.getUsername()
                ,manager.getPassword(),manager.getTel(),manager.getNo());
        return num;
    }

    public int Update(Build build) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="update build set buildname=?,details=? where buildid=?";
        int num =runner.update(sql,build.getBuildname(),build.getDetails(),build.getBuildid());
        return num;
    }

    public int Update(Room room) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="update room set roomname=?,buildid=? where roomid=?";
        int num =runner.update(sql,room.getRoomname(),room.getBuildid(),room.getRoomid());
        return num;
    }
}
