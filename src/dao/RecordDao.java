package dao;

import java.lang.*;
import builds.Record;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import users.Student;
import users.Teacher;


import java.sql.SQLException;
import java.util.List;

public class RecordDao {
    public List<Record> ListByBuild(String buildname,String date) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where buildname=?";
        if (date!=null){
            sql=sql.concat(" and date like '"+date+"%'");
        }
        //System.out.println(sql);
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class),buildname);
        return list;
    }

    public  int GetMaxRecordId() throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "SELECT MAX(recordid) as maxrecordid from record";
        Record record = (Record) runner.query(sql,new BeanHandler<Record>(Record.class));
        //System.out.println(record.getMaxrecordid()+" "+record.getRecordid());
        return record.getMaxrecordid()+1;
    }
    public List<Record> RecordMesList () throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes";
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class));
        return list;
    }

    public List<Record> RecordMesListTea (Teacher teacher) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where buildname = ?";
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class),teacher.getBuildname());
        return list;
    }

    public List<Record> ListBySno(String buildname,String Sno,String date) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where no=? ";
        if(buildname!=null)
        {
            sql=sql.concat(" and buildname = '"+buildname+"'");
        }
        if (date!=null){
            sql=sql.concat(" and date like '"+date+"%'");
        }
        //System.out.println(sql);
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class),Sno);
        return list;
    }

    public List<Record> ListByDate(String date) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where date like '"+date+"%'";
        //System.out.println(sql);
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class));
        return list;
    }

    public List<Record> ListByDate(String no, String date) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where date like '"+date+"%' and no=?";
        //System.out.println(sql);
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class),no);
        return list;
    }

    public List<Record> ListBySno(String Sno) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where no=?";
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class),Sno);
        return list;
    }

    public List<Record> ListBySname(String buildname,String name,String date) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where name=?";
        if(buildname!=null)
        {
            sql=sql.concat(" and buildname = '"+buildname+"'");
        }
        if (date!=null){
            sql=sql.concat(" and date like '"+date+"%'");
            //System.out.println("更改");
        }
        System.out.println(sql);
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class),name);
        return list;
    }

    public Record QueryByRecordId (int RecordId) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where recordid=?";
        Record list= (Record) runner.query(sql,new BeanHandler<Record>(Record.class),RecordId);
        return list;
    }

    public int Insert(Record record) throws Exception {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "insert into record values(null,?,?,?,?,?,?,?)";
        int num = runner.update(sql,record.getNo(),record.getName(),record.getBuildid(),
                    record.getRoomid(),record.getDate(),record.getStatus(),record.getDetails());
        return num;
    }

    public int Delete(int recordId) throws Exception {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "delete from record where recordid=?";
        int num = runner.update(sql,recordId);
        return num;
    }

    public int Update(Record record) throws Exception {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql = "update record set no=?,name=?,buildid=?,roomid=?,date=?,details=? where recordid=?";
        int num = runner.update(sql,record.getNo(),record.getName(),record.getBuildid(),
                record.getRoomid(),record.getDate(),record.getDetails(),record.getRecordid());
        return num;
    }


    public List<Record> ListByRoom(String buildname,String mes, String date) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="SELECT * from record_mes where buildname=? and roomname=?";
        if (date!=null){
            sql=sql.concat(" and date like '"+date+"%'");
        }
        //System.out.println(sql);
        List<Record> list= (List<Record>) runner.query(sql,new BeanListHandler<Record>(Record.class),buildname,mes);
        return list;
    }
}
