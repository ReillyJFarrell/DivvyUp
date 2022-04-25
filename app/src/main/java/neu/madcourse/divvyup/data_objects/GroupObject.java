package neu.madcourse.divvyup.data_objects;

import java.util.ArrayList;
import java.util.List;

public class GroupObject {
    String IDCode;
    List<String> membersIDs;
    List<String> choreIDs;

    public GroupObject(){
        this.membersIDs = new ArrayList<String>();
        this.choreIDs = new ArrayList<String>();
        this.IDCode = "tempID";
    }

    public GroupObject(String IDCode, ArrayList<String> membersIDs, ArrayList<String> choreIDs){
        this.membersIDs = membersIDs;
        this.choreIDs = choreIDs;
        this.IDCode = IDCode;
    }

    public void setIDCode(String IDCode) {
        this.IDCode = IDCode;
    }

    public void addChore(String chore) {
        this.choreIDs.add(chore);
    }

    public void addMember(String memberID) {
        this.membersIDs.add(memberID);
    }

    public void setMembersIDs(List<String> membersIDs) {
        this.membersIDs = membersIDs;
    }

    public void setChoreIDs(List<String> choreIDs) {
        this.choreIDs = choreIDs;
    }

    public List<String> getChoreIDs() {
        return choreIDs;
    }

    public String getIDCode() {
        return IDCode;
    }

    public List<String> getMembersIDs() {
        return membersIDs;
    }
}
