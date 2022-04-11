package neu.madcourse.divvyup.chores_list_screen;

import java.util.Date;

public class ChoreCard implements ChoreCardClickListener {

    private Integer assignedUser;
    private String title;
    private String description;
    private Date dueTime;
    // 1 = To Do, 2 = In Progress, 3 = Done
    private Integer status;

    public ChoreCard() {
    }

    public ChoreCard(Integer assignedUser, String title, String description, Date dueTime, Integer status) {
        this.assignedUser = assignedUser;
        this.title = title;
        this.description = description;
        this.dueTime = dueTime;
        this.status = status;
    }

    public Integer getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(Integer assignedUser) {
        this.assignedUser = assignedUser;
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

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public void onItemClick(int position) {

    }

}