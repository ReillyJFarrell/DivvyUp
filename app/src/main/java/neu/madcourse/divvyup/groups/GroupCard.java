package neu.madcourse.divvyup.groups;

public class GroupCard {
    private String name;
    private String groupID;

    public GroupCard(String name, String groupID) {
        this.name = name;
        this.groupID = groupID;
    }

    public String getName() {
        return this.name;
    }

    public String getGroupID() {
        return this.groupID;
    }
}
