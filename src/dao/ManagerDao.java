package dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import users.Manager;

import java.sql.SQLException;

public class ManagerDao {
    public int Delete(String no) throws SQLException, ClassNotFoundException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="delete from manager where no= ?";
        int num=runner.update(sql,no);
        return num;
    }

    public int Insert(Manager manager) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into teacher (no,name,sex,username,password,tel) values(?,?,?,?,?,?,?)";
        int num= runner.update(sql,manager.getNo(),manager.getName(),manager.getSex(),manager.getUsername()
                ,manager.getPassword(),manager.getTel());
        return num;
    }

    public Manager Query(Manager manager) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql ="select * from manager where username=?";
        Manager existManager=(Manager) runner.query(sql,new BeanHandler<Manager>(Manager.class),manager.getUsername());
        return existManager;
    }

    public int Update(Manager manager) throws SQLException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="update manager set name=?,sex=?,username=?,password=?,tel=? where no=?";
        int num= runner.update(sql,manager.getName(),manager.getSex(),manager.getUsername()
                ,manager.getPassword(),manager.getTel(),manager.getNo());
        return num;
    }
}
