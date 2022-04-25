package neu.madcourse.divvyup.chores_list_screen;

import java.time.DayOfWeek;
import java.util.Date;

public class ChoreCard implements ChoreCardClickListener {

    private Integer assignedUserId;
    private Integer groupId;

    private String title;
    private String description;

    // 1 = To Do, 2 = In Progress, 3 = Done
    private Integer status;

    private Date deadline;

    private DayOfWeek repeatedDates;
    private boolean isRepeating;


    public ChoreCard() {
    }

    public ChoreCard(Integer assignedUser, Integer groupId, String title, String description, Date dueTime, Integer status) {
        this.assignedUserId = assignedUser;
        this.groupId = groupId;
        this.title = title;
        this.description = description;
        this.deadline = dueTime;
        this.status = status;
        repeatedDates = null;
        isRepeating = false;
    }

    public ChoreCard(Integer assignedUserId, Integer groupId, String title, String description, Integer status, Date deadline, DayOfWeek repeatedDates, boolean isRepeating, Integer totalCompleted, Integer totalTasks) {
        this.assignedUserId = assignedUserId;
        this.groupId = groupId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.repeatedDates = repeatedDates;
        this.isRepeating = isRepeating;
    }

    public Integer getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Integer assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public DayOfWeek getRepeatedDates() {
        return repeatedDates;
    }

    public void setRepeatedDates(DayOfWeek repeatedDates) {
        this.repeatedDates = repeatedDates;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }

    @Override
    public void onItemClick(int position) {

    }

}