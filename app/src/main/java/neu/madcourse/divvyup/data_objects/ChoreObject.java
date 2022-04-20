package neu.madcourse.divvyup.data_objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoreObject {

    String name;
    List<Integer> days;
    List<Integer> progressMode;
    List<String> userAssigned;
    String groupID;
    String description;
    String choreID;
    Boolean isRepeat;

    public ChoreObject(String name, String description, Boolean isRepeat){
        this.name = name;
        this.description = description;
        this.isRepeat = isRepeat;
        this.days = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        this.userAssigned = Arrays.asList("", "", "", "", "", "", "");
        this.progressMode = Arrays.asList(-1, -1, -1, -1, -1, -1, -1);
        this.isRepeat = isRepeat;
        this.groupID = "Place Holder Group ID";
        this.choreID = "Place Holder Chore ID";
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

    public void setDays(int dayIndex, int dayValue){
        this.days.set(dayIndex, dayValue);
    }

    public void setProgressMode(int dayIndex, int progressValue){
        this.progressMode.set(dayIndex, progressValue);
    }

    public void setUserAssigned(int dayIndex, String user){
        this.userAssigned.set(dayIndex, user);
    }

    public String getName() {
        return name;
    }

    public List<Integer> getDays() {
        return days;
    }

    public List<Integer> getProgressMode() {
        return progressMode;
    }

    public List<String> getUserAssigned() {
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

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public void setProgressMode(List<Integer> progressMode) {
        this.progressMode = progressMode;
    }

    public void setUserAssigned(List<String> userAssigned) {
        this.userAssigned = userAssigned;
    }
}
