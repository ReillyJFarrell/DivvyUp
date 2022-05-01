package neu.madcourse.divvyup.data_objects;

import java.util.Date;
import java.util.List;

public class PastGroupObject extends GroupObject {
    private Date created;

    public PastGroupObject(String IDCode, List<String> membersIDs, List<ChoreObject> chores, String groupName, String created) {
        super(IDCode, membersIDs, chores, groupName);
        this.created = new Date(created);
    }

    public PastGroupObject(String created) {
        this.created = new Date(created);
    }

    public PastGroupObject(GroupObject groupObject, Date created){
        super(groupObject.IDCode, groupObject.membersIDs, groupObject.chores, groupObject.groupName);
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public PastGroupObject(){
        super();
        this.created = new Date();
    }
}
