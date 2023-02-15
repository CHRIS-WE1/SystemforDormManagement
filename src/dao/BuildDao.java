package dao;

import builds.Build;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import users.Student;
import users.Teacher;

import java.sql.SQLException;
import java.util.List;

public class BuildDao {
    public int Delete(Build build) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="delete from build where buildid= ?";
        int num=runner.update(sql,build.getBuildid());
        return num;
    }


    public int Insert(Build build) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into build (buildid,buildname,details,teacherno,teachername) values (?,?,?,?,?)";
        int num = runner.update(sql,build.getBuildid(),build.getBuildname(),build.getDetails(),build.getTeacherno(),build.getTeachername());
        return num;
    }

    public Build Query(Build build) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from build where buildid=?";
        Build existBuild =(Build) runner.query(sql,new BeanHandler<Build>(Build.class),build.getBuildid());
        return existBuild;
    }

    public int Update(Build build) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="update build set buildname=?,details=?,teacherno=?,teachername=? where buildid=?";
        int num =runner.update(sql,build.getBuildname(),build.getDetails(),build.getTeacherno(),build.getTeachername(),build.getBuildid());
        return num;
    }

    public List<Build> BuildList() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from build";
        List <Build> list=(List<Build>) runner.query(sql,new BeanListHandler<Build>(Build.class));
        return list;
    }

    public List<Teacher> TeacherWithoutBuild() throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * FROM teacher_without_build";
        List<Teacher> list=(List<Teacher>) runner.query(sql,new BeanListHandler<Teacher>(Teacher.class));
        return list;
    }

    public Teacher TeacherWithBuildId(int buildid) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * FROM teacher where buildid = ?";
        Teacher teacher=(Teacher) runner.query(sql,new BeanHandler<Teacher>(Teacher.class),buildid);
        return teacher;
    }

    public int getNewBuildid() throws  SQLException{
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "SELECT MAX(buildid) AS  buildid from build";
        Build build =runner.query(sql,new BeanHandler<Build>(Build.class));
        return build.getBuildid();
    }

    public int SetTeacherBuildid(int buildid,String teacherno) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql="UPDATE teacher set buildid=? where no=?";
        int num=runner.update(sql,buildid,teacherno);
        return num;
    }

    public int RemoveTeacherBuildid(String no) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql="UPDATE teacher set buildid=0 where no=?";
        int num=runner.update(sql,no);
        return num;
    }

    public int SetBuildTeacherMes(int buildid,String teacherno,String teachername) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql="UPDATE build set teacherno=?,teachername=? where buildid=?";
        int num=runner.update(sql,teacherno,teachername,buildid);
        return num;
    }

    public List<Build> BuildBySearch(String condition, String mes) throws SQLException{
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from build where "+condition+"=?";
        List <Build> list=(List<Build>) runner.query(sql,new BeanListHandler<Build>(Build.class),mes);
        return list;
    }

    public List<Build> BuildNameList() throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select buildid,buildname from build";
        List <Build> list=(List<Build>) runner.query(sql,new BeanListHandler<Build>(Build.class));
        return list;
    }
}
