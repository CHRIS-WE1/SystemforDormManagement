package dao;

import builds.Build;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import users.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    public int Delete(String no) throws SQLException, ClassNotFoundException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="delete from student where no= ?";
        return runner.update(sql,no);
    }

    public int Insert(Student student) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into student (no,name,sex,username,password,buildid,roomid,tel) values(?,?,?,?,?,?,?,?)";
        int num= runner.update(sql,student.getNo(),student.getName(),student.getSex(),student.getUsername()
                ,student.getPassword(),student.getBuildid(),student.getRoomid(),student.getTel());
        return num;
    }

    public Student GetBuiRoomId(String buildname,String roomname) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "SELECT buildid as buildid from build WHERE buildname=?";
        Student BuiId = runner.query(sql,new BeanHandler<Student>(Student.class),buildname);
        System.out.println(BuiId.getBuildid()+"sushelou");
//        System.out.println(BuiId.getBuildid());
        String sql2 ="SELECT roomid  from room  WHERE room.roomname=? and room.buildid=?";
        Student RoomId=runner.query(sql2,new BeanHandler<Student>(Student.class),roomname,BuiId.getBuildid());
        RoomId.setBuildid(BuiId.getBuildid());
        return  RoomId;
    }
    public Student Query(Student student) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from student where username=?";
        return runner.query(sql, new BeanHandler<Student>(Student.class),student.getUsername());
    }

    public Student QueryMes(Student student) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select student_mes.*,student.username,student.password from student_mes,student where student_mes.no=student.no and student.username=?";
        return runner.query(sql, new BeanHandler<Student>(Student.class),student.getUsername());
    }

    public int Update(Student students) throws SQLException, ClassNotFoundException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
//        String sql ="update student set student.name=?,sex=?,username=?,student.password=?," +
//                "buildid=?,roomid=?,tel=?where student.no=?";
//        int num= runner.update(sql,students.getName(),students.getSex(),students.getUsername()
//                ,students.getPassword(),students.getBuildid(),students.getRoomid(),students.getTel(),students.getNo());
        int num=Delete(students.getNo());
        int num1=Insert(students);
        return num+num1;
    }

    public List<Student> StudentMesList() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from student_mes";
        List <Student> list=(List<Student>) runner.query(sql, new BeanListHandler<Student>(Student.class));
        return list;
    }

    public List<Student> StudentBySearch(String con,String mes) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from student_mes where "+con+" = '"+mes+"';";
//        if (buildname!=null){
//            sql=sql.concat(" and student_mes.buildname = '"+buildname+"'");
//        }

      System.out.println(sql);
        List<Student> list= (List<Student>) runner.query(sql, new BeanListHandler<Student>(Student.class));
        for (Student stu:list){
            System.out.println(stu.getName()+stu.getNo());
        }
        return list;
    }

    public List<Student> StudentBySearch(String buildname,String con,String mes) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from student_mes where "+con+"=?";
        if (buildname!=null){
            sql=sql.concat(" and student_mes.buildname = '"+buildname+"'");
        }

       System.out.println(sql);
        List <Student> list=(List<Student>) runner.query(sql, new BeanListHandler<Student>(Student.class),mes);
        for (Student stu:list){
            System.out.println(stu.getName()+stu.getNo());
        }
        return list;
    }

    public List<Student> ListByBuildname(String buildname) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select student_mes.*,student.username,student.password,student.buildid,student.roomid " +
                "from student_mes,student where student_mes.buildname=? and student.no=student_mes.no";
        return runner.query(sql, new BeanListHandler<Student>(Student.class),buildname);
    }

    public List<Student> UsernameList() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select username from student";
        List <Student> list=(List<Student>) runner.query(sql, new BeanListHandler<Student>(Student.class));
        return list;
    }
}
