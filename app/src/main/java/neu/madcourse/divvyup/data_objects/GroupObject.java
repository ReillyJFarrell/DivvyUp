package neu.madcourse.divvyup.data_objects;

import java.util.ArrayList;
import java.util.List;

public class GroupObject {
    String IDCode;
    String groupName;
    List<String> membersIDs;
    List<ChoreObject> chores;

    public GroupObject(){
        this.membersIDs = new ArrayList<String>() {
        };
        this.chores = new ArrayList<ChoreObject>();
        this.IDCode = "tempID";
        this.groupName = "tempName";

    }

    public GroupObject(String IDCode, List<String> membersIDs, String groupName){
        this.membersIDs = membersIDs;
        this.chores = new ArrayList<>();
        this.IDCode = IDCode;
        this.groupName = groupName;
    }

    public GroupObject(String IDCode, List<String> membersIDs, List<ChoreObject> chores, String groupName){
        this.membersIDs = membersIDs;
        this.chores = chores;
        this.IDCode = IDCode;
        this.groupName = groupName;
    }

    public void setIDCode(String IDCode) {
        this.IDCode = IDCode;
    }

    public void addChore(ChoreObject chore) {
        this.chores.add(chore);
    }

    public void addMember(String memberID) {
        this.membersIDs.add(memberID);
    }

    public void setMembersIDs(List<String> membersIDs) {
        this.membersIDs = membersIDs;
    }

    public void setChores(List<ChoreObject> chores) {
        this.chores = chores;
    }

    public List<ChoreObject> getChores() {
        return chores;
    }

    public String getIDCode() {
        return IDCode;
    }

    public List<String> getMembersIDs() {
        return membersIDs;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
