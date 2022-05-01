package neu.madcourse.divvyup.data_objects;

import java.util.ArrayList;
import java.util.List;

public class UserObject {

    String name;
    String deviceID;
    List<String> groupIDs;

    public UserObject(){
        this.name = "";
        this.deviceID = "";
        this.groupIDs = new ArrayList<String>();
    }

    public UserObject(String name, String deviceID){
        this.name = name;
        this.deviceID = deviceID;
        this.groupIDs = new ArrayList<String>();
    }

    public void setGroupIDs(List<String> groupIDs) {
        this.groupIDs = groupIDs;
    }

    public  void addGroupID(String id){
        this.groupIDs.add(id);
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getName() {
        return name;
    }

    public List<String> getGroupIDs() {
        return groupIDs;
    }
}
