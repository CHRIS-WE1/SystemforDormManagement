package builds;

public class Mission_record {
    int mission_record_id;
    int missionid;
    String executorid;
    String executorstatus;
    String filepath;

    public int getMission_record_id() {
        return mission_record_id;
    }

    public void setMission_record_id(int mission_record_id) {
        this.mission_record_id = mission_record_id;
    }

    public int getMissionid() {
        return missionid;
    }

    public void setMissionid(int missionid) {
        this.missionid = missionid;
    }

    public String getExecutorid() {
        return executorid;
    }

    public void setExecutorid(String executorid) {
        this.executorid = executorid;
    }

    public String getExecutorstatus() {
        return executorstatus;
    }

    public void setExecutorstatus(String executorstatus) {
        this.executorstatus = executorstatus;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
