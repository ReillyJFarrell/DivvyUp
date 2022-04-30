package neu.madcourse.divvyup.data_objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoreObject {

    String name;
    List<Boolean> days;
    List<Integer> progressMode;
    String userAssigned;
    String groupID;
    String description;
    String choreID;
    Boolean isRepeat;

    public ChoreObject(){
        this.name = "";
        this.description = "";
        this.isRepeat = false;
        this.days = Arrays.asList(false, false, false, false, false, false, false);
        this.userAssigned = "";
        this.progressMode = Arrays.asList(-1, -1, -1, -1, -1, -1, -1);
        this.groupID = "Place Holder Group ID";
        this.choreID = "Place Holder Chore ID";
    }

    public ChoreObject(String name, String description, Boolean isRepeat){
        this.name = name;
        this.description = description;
        this.isRepeat = isRepeat;
        this.days = Arrays.asList(false, false, false, false, false, false, false);
        this.userAssigned = "";
        this.progressMode = Arrays.asList(-1, -1, -1, -1, -1, -1, -1);
        this.isRepeat = isRepeat;
        this.groupID = "Place Holder Group ID";
        this.choreID = "Place Holder Chore ID";
    }

    public ChoreObject(String name, List<Boolean> days, List<Integer> progressMode, String userAssigned, String groupID, String description, String choreID, Boolean isRepeat) {
        this.name = name;
        this.days = days;
        this.progressMode = progressMode;
        this.userAssigned = userAssigned;
        this.groupID = groupID;
        this.description = description;
        this.choreID = choreID;
        this.isRepeat = isRepeat;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setRepeat(Boolean repeat) {
        isRepeat = repeat;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setChoreID(String choreID) {
        this.choreID = choreID;
    }

    public void setDays(int dayIndex, boolean dayValue){
        this.days.set(dayIndex, dayValue);
    }

    public void setProgressMode(int dayIndex, int progressValue){
        this.progressMode.set(dayIndex, progressValue);
    }

    public String getName() {
        return name;
    }

    public List<Boolean> getDays() {
        return days;
    }

    public List<Integer> getProgressMode() {
        return progressMode;
    }

    public String getUserAssigned() {
        return userAssigned;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getDescription() {
        return description;
    }

    public String getChoreID() {
        return choreID;
    }

    public Boolean getRepeat() {
        return isRepeat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDays(List<Boolean> days) {
        this.days = days;
    }

    public void setProgressMode(List<Integer> progressMode) {
        this.progressMode = progressMode;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }
}
