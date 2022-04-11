package neu.madcourse.divvyup.chores_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import neu.madcourse.divvyup.R;

public class ChoresActivity extends AppCompatActivity {

    private List<ChoreCard> toDoChoresList = new ArrayList<>();
    private List<ChoreCard> inProgressChoresList = new ArrayList<>();
    private List<ChoreCard> completedChoresList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores);
    }
}