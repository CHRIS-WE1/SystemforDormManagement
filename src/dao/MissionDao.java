package dao;

import builds.Mission;
import builds.Record;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class MissionDao {
    public List<Mission> AllMission() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from mission_list";
        List<Mission> list =(List<Mission>) runner.query(sql,new BeanListHandler<Mission>(Mission.class));
        return list;
    }

    public List<Mission> MissionByPublisher(String publisher) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from mission_list where name = ?";
        List<Mission> list =(List<Mission>) runner.query(sql,new BeanListHandler<Mission>(Mission.class),publisher);
        return list;
    }
    public List<Mission> MissionByPublisherForStu(String publisher,String buildname) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from mission_list where name = ? and (executor='all' or executor='学生' ";
        if (buildname!=null&&!buildname.equals("null")){
            sql=sql.concat("or executor = "+buildname);
        }
        sql=sql.concat(")");
        List<Mission> list =(List<Mission>) runner.query(sql,new BeanListHandler<Mission>(Mission.class),publisher);
        return list;
    }

    public int MissionInsert(Mission mission) throws SQLException, IOException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "insert into mission values (?,?,?,?,?,?,?,?,?)";
        return runner.update(sql,mission.getMissionid(),mission.getTitle(),mission.getDetails(),mission.getBegintime(),mission.getEndtime(),
                mission.getPublisherid(),mission.getExecutor(),mission.getType(),mission.getFilepath());
    }

    public List<Mission> MissionByType(String type) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from mission_list where type = ?";
        List<Mission> list =(List<Mission>) runner.query(sql,new BeanListHandler<Mission>(Mission.class),type);
        return list;
    }

    public List<Mission> MissionByExcutor(String excutor) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from mission_list where executor like '%"+excutor+"%'";
        List<Mission> list =(List<Mission>) runner.query(sql,new BeanListHandler<Mission>(Mission.class));
        return list;
    }

    public int GetNewId() throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "SELECT MAX(missionid) as missionid from mission";
        Mission mission = (Mission) runner.query(sql,new BeanHandler<Mission>(Mission.class));
        //System.out.println(record.getMaxrecordid()+" "+record.getRecordid());
        return mission.getMissionid()+1;
    }

    public int InsertFileById(int missionid, String filepath) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "update mission set filepath=? where missionid=?";
        return runner.update(sql,filepath,missionid);
    }

    public int Delete(int missionid) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "delete from mission where missionid=?";
        return runner.update(sql,missionid);
    }

    public String GetFilePathById(int missionid) throws  SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql ="select filepath from mission where missionid = ?";
        Mission mission = (Mission) runner.query(sql,new BeanHandler<Mission>(Mission.class));
        return  mission.getFilepath();
    }

    public int MissionUpdate(Mission mission) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql ="update mission set title=?,details=?,begintime=?,endtime=?,publisherid=?,executor=?,type=?,filepath=? where missionid = ?";
        return runner.update(sql,mission.getTitle(),mission.getDetails(),mission.getBegintime(),mission.getEndtime(),mission.getPublisherid(),
                mission.getExecutor(),mission.getType(),mission.getFilepath(),mission.getMissionid());
    }

    public List<Mission> MissionByTitle(String mes) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from mission_list where title like '%"+mes+"%'";
        List<Mission> list =(List<Mission>) runner.query(sql,new BeanListHandler<Mission>(Mission.class));
        return list;
    }

    public List<Mission> MissionByTypeForStu(String mes, String buildname) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from mission_list where type = ? and (executor='all' or executor='学生' ";
        if (buildname!=null&&!buildname.equals("null")){
            sql=sql.concat("or executor = "+buildname);
        }
        sql=sql.concat(")");
        List<Mission> list =(List<Mission>) runner.query(sql,new BeanListHandler<Mission>(Mission.class),mes);
        return list;
    }

    public List<Mission> MissionByTitleForStu(String mes, String buildname) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "select * from mission_list where title like '%"+mes+"%' and (executor='all' or executor='学生' ";
        if (buildname!=null){
            sql=sql.concat("or executor = "+buildname);
        }
        sql=sql.concat(")");
        List<Mission> list =(List<Mission>) runner.query(sql,new BeanListHandler<Mission>(Mission.class));
        return list;
    }
}
