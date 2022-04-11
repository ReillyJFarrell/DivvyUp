package neu.madcourse.divvyup.chores_list_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import neu.madcourse.divvyup.R;

public class ChoresActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager toDoLayoutManager;
    private RecyclerView.LayoutManager inProgressLayoutManager;
    private RecyclerView.LayoutManager completedLayoutManager;

    private RecyclerView toDoRView;
    private RecyclerView inProgressRView;
    private RecyclerView completedRView;

    private ChoreAdapter toDoChoreAdapter;
    private ChoreAdapter inProgressChoreAdapter;
    private ChoreAdapter completedChoreAdapter;


    private List<ChoreCard> toDoChoresList = new ArrayList<>();
    private List<ChoreCard> inProgressChoresList = new ArrayList<>();
    private List<ChoreCard> completedChoresList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores);

        toDoChoresList.add(new ChoreCard(3, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(2, "Trash", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(1, "Farm", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(3, "Vacuum", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(2, "Living Room", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(2, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(5, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(7, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
        toDoChoresList.add(new ChoreCard(2, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));

        inProgressChoresList.add(new ChoreCard(3, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(2, "Trash", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(1, "Farm", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(3, "Vacuum", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(2, "Living Room", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(2, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(5, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(7, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));
        inProgressChoresList.add(new ChoreCard(2, "Dishes", "clean dishes", new Date(2022, 5, 12), 2));

        completedChoresList.add(new ChoreCard(5, "Dishes", "clean dishes", new Date(2022, 5, 12), 3));
        completedChoresList.add(new ChoreCard(7, "Dishes", "clean dishes", new Date(2022, 5, 12), 3));
        completedChoresList.add(new ChoreCard(2, "Dishes", "clean dishes", new Date(2022, 5, 12), 3));

        toDoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        inProgressLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        completedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        toDoRView = findViewById(R.id.idToDoChoresRecycler);
        inProgressRView = findViewById(R.id.idInProgressChoresRecycler);
        completedRView = findViewById(R.id.idCompletedChoresRecycler);


        ChoreCardClickListener choreCardClickListener = new ChoreCardClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO: Navigate to chores page
            }
        };


        // To Do Chores
        toDoChoreAdapter = new ChoreAdapter(toDoChoresList);

        toDoChoreAdapter.setOnLinkClickListener(choreCardClickListener);
        toDoRView.setAdapter(toDoChoreAdapter);
        toDoRView.setLayoutManager(toDoLayoutManager);
        toDoRView.setHasFixedSize(true);

        // In Progress Chores

        inProgressChoreAdapter = new ChoreAdapter(inProgressChoresList);

        toDoChoreAdapter.setOnLinkClickListener(choreCardClickListener);

        inProgressRView.setAdapter(inProgressChoreAdapter);
        inProgressRView.setLayoutManager(inProgressLayoutManager);
        inProgressRView.setHasFixedSize(true);

        // Completed Chores

        completedChoreAdapter = new ChoreAdapter(completedChoresList);

        completedChoreAdapter.setOnLinkClickListener(choreCardClickListener);

        completedRView.setAdapter(completedChoreAdapter);
        completedRView.setLayoutManager(completedLayoutManager);
        completedRView.setHasFixedSize(true);

    }



}