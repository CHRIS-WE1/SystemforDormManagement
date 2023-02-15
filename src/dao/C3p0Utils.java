package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

public class C3p0Utils {
    private static DataSource dataSource;
    static {
        dataSource=new ComboPooledDataSource("project");
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
}