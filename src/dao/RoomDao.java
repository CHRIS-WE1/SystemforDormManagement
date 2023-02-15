package dao;

import builds.Build;
import builds.Room;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import java.sql.SQLException;
import java.util.List;

public class RoomDao {
    public int Delete(int roomid) throws SQLException, ClassNotFoundException {
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql ="delete from room where roomid= ?";
        int num=runner.update(sql,roomid);
        return num;
    }

    public int Insert(Room room) throws SQLException{
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql ="insert into room (roomid,buildid,roomname) values (?,?,?)";
        int num = runner.update(sql,room.getRoomid(),room.getBuildid(),room.getRoomname());
        return num;
    }

    public Room Query(Room room) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from room where roomid=?";
        Room existRoom =(Room) runner.query(sql,new BeanHandler<Room>(Room.class),room.getBuildid());
        return existRoom;
    }

    public int getNewRoomNo() throws SQLException{
        QueryRunner runner=new QueryRunner(C3p0Utils.getDataSource());
        String sql = "SELECT MAX(roomid) as roomid from room";
        Room tea =(Room) runner.query(sql,new BeanHandler<Room>(Room.class));
        return tea.getRoomid()+1;
    }

    public int GetBuildid(String buildname) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="select buildid from build where buildname=?";

        Room existRoom =(Room) runner.query(sql,new BeanHandler<Room>(Room.class),buildname);
        return existRoom.getBuildid();
    }

    public Room QueryMes(Room room) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from room where roomid=?";
        Room existRoom =(Room) runner.query(sql,new BeanHandler<Room>(Room.class),room.getRoomid());
        String sql2="select buildname from build where buildid=?";
        Room room1 = (Room) runner.query(sql2,new BeanHandler<Room>(Room.class),existRoom.getBuildid());
        existRoom.setBuildname(room1.getBuildname());
        return existRoom;
    }

    public int Update(Room room) throws SQLException {
        QueryRunner runner =new QueryRunner(C3p0Utils.getDataSource());
        String sql="update room set roomname=?,buildid=? where roomid=?";
        int num =runner.update(sql,room.getRoomname(),room.getBuildid(),room.getRoomid());
        return num;
    }

    public List<Room> RoomList() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from room";
        List <Room> list=(List<Room>) runner.query(sql,new BeanListHandler<Room>(Room.class));
        return list;
    }
    public List<Room> RoomMesList() throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from room_mes";
        List <Room> list=(List<Room>) runner.query(sql,new BeanListHandler<Room>(Room.class));
        return list;
    }

    public List<Room> GetRoomByBuild(String buildname) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select room.roomid,room.buildid,room.roomname,room_mes.buildname from room_mes,room where buildname=? and room_mes.roomid=room.roomid";
        List <Room> list=(List<Room>) runner.query(sql,new BeanListHandler<Room>(Room.class),buildname);
        return list;
    }

    public List<Room> RoomBySearch(String condition, String mes) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from room_mes where "+condition+"=?";
        List <Room> list=(List<Room>) runner.query(sql,new BeanListHandler<Room>(Room.class),mes);
        return list;
    }
    public List<Room> RoomBySearch(String buildname,String condition, String mes) throws SQLException {
        QueryRunner runner = new QueryRunner(C3p0Utils.getDataSource());
        String sql="select * from room_mes where "+condition+"=? and buildname = ?";
        List <Room> list=(List<Room>) runner.query(sql,new BeanListHandler<Room>(Room.class),mes,buildname);
        return list;
    }
}
