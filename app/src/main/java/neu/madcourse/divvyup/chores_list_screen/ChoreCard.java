package neu.madcourse.divvyup.chores_list_screen;

import java.time.DayOfWeek;
import java.util.Date;

public class ChoreCard implements ChoreCardClickListener {

    private String assignedUserId;
    private String choreID;

    private String title;

    // 0 = To Do, 1 = In Progress, 2 = Done
    private Integer status;
    private DayOfWeek repeatedDay;


    public ChoreCard() {
    }

    public ChoreCard(DayOfWeek repeatedDay, String title, String assignedUser, Integer status) {
        this.assignedUserId = assignedUser;
        this.title = title;
        this.status = status;
        this.repeatedDay = repeatedDay;

    }


    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public DayOfWeek getRepeatedDay() {
        return repeatedDay;
    }

    public void setRepeatedDay(DayOfWeek repeatedDay) {
        this.repeatedDay = repeatedDay;
    }


    @Override
    public void onItemClick(int position) {

    }

}