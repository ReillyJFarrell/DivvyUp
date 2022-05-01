package neu.madcourse.divvyup.recap;

import java.util.List;

public class RecapCard implements recapClickListener, Comparable<RecapCard> {
    private String choreName;
    private int status;
    private String assignedUser;
    private List<Integer> dayStatus;


    public RecapCard(String choreName, int status, String assignedUser, List<Integer> dayStatus) {
        this.choreName = choreName;
        this.status = status;
        this.assignedUser = assignedUser;
        this.dayStatus = dayStatus;
    }

    public String getChoreName() {
        return choreName;
    }

    public int getStatus() {
        return status;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public List<Integer> getDayStatus() {
        return dayStatus;
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public int compareTo(RecapCard e) {
        if (this.status == e.status){
            return 0;
        }else if (this.status < e.status){
            return -1;
        }else {
            return 1;
        }
    }
}
