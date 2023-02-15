package builds;

public class Room {
    int roomid;
    int buildid;
    String roomname;

    String buildname;

    public String getBuildname() {
        return buildname;
    }

    public void setBuildname(String buildname) {
        this.buildname = buildname;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public int getBuildid() {
        return buildid;
    }

    public void setBuildid(int buildid) {
        this.buildid = buildid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }
}
