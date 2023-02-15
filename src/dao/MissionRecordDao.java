package dao;

import builds.Mission_record;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class MissionRecordDao {
    public int MissionRecordInsert(Mission_record mission_record) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "INSERT INTO mission_record values (null,?,?,?,?)";
        return runner.update(sql,mission_record.getMissionid(),mission_record.getExecutorid(),mission_record.getExecutorstatus(),mission_record.getFilepath());
    }

    public List<Mission_record> Mis_RecByNo(String no) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "SELECT * from mission_record where executorid=?";
        List<Mission_record> list = runner.query(sql,new BeanListHandler<Mission_record>(Mission_record.class),no);
        return list;
    }

    public int MissionRecordUpdate(Mission_record mission_record) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql = "update mission_record set filepath=? where mission_record_id=?";
        return runner.update(sql,mission_record.getFilepath(),mission_record.getMission_record_id());
    }
}
