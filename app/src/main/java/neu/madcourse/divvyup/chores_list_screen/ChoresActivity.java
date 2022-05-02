package neu.madcourse.divvyup.chores_list_screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import neu.madcourse.divvyup.AddChoreActivity;
import neu.madcourse.divvyup.EditChoreActivity;
import neu.madcourse.divvyup.R;
import neu.madcourse.divvyup.data_objects.ChoreObject;
import neu.madcourse.divvyup.data_objects.GroupObject;
import neu.madcourse.divvyup.notifications.NotificationSender;
import neu.madcourse.divvyup.recap.RecapActivity;

public class ChoresActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager toDoLayoutManager;
    private RecyclerView.LayoutManager inProgressLayoutManager;
    private RecyclerView.LayoutManager completedLayoutManager;

    private RecyclerView toDoRView;
    private RecyclerView inProgressRView;
    private RecyclerView completedRView;
    private BarChart chart;
    private BarDataSet barDataSet;
    private BarData barData;

    private FirebaseDatabase choreDatabase;

    private ChoreAdapter toDoChoreAdapter;
    private ChoreAdapter inProgressChoreAdapter;
    private ChoreAdapter completedChoreAdapter;


    private List<ChoreCard> toDoChoresList = new ArrayList<>();
    private List<ChoreCard> inProgressChoresList = new ArrayList<>();
    private List<ChoreCard> completedChoresList = new ArrayList<>();

    int[] colorClassArray = new int[]{Color.GREEN, Color.YELLOW, Color.RED};

    public static final String TODO_CHORE_COUNT = "TODO_CHORE_COUNT";
    public static final String TODO_CHORE_KEY = "TODO_CHORE_KEY";
    public static final String IN_PROGRESS_CHORE_COUNT = "IN_PROGRESS_CHORE_COUNT";
    public static final String IN_PROGRESS_CHORE_KEY = "IN_PROGRESS_CHORE_KEY";
    public static final String COMPLETED_CHORE_COUNT = "COMPLETED_CHORE_COUNT";
    public static final String COMPLETED_CHORE_KEY = "COMPLETED_CHORE_KEY";

    String currentUser;
    String groupId;
    String group;

    private List<ChoreObject> allChoresList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores);

        NotificationSender notificationSender = new NotificationSender(this);
        notificationSender.createNotificationChannel();

        choreDatabase = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.currentUser = extras.getString("userKey");
            this.groupId = extras.getString("groupID");
            this.group = extras.getString("group");
        }

        TextView nameID = findViewById(R.id.nameIDTextView);
        nameID.setText(group + ": " + groupId);


        AlertDialog.Builder editAlert = new AlertDialog.Builder(this);
        editAlert.setTitle("Options");
        editAlert.setMessage("Select Your Option");
        editAlert.setPositiveButton("Edit", null);
        editAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        editAlert.setNeutralButton("Increment", null);
        Activity context = this;

        ChoreCardClickListener choreCardClickListener = new ChoreCardClickListener() {
            @Override
            public void onItemClick(int position) { //THIS IS THE PROBLEM
                editAlert.setNeutralButton("Increment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DayOfWeek day = toDoChoresList.get(position).getRepeatedDay();
                        int index = getDayOfWeekRev(day);






                        Query currentChoreQ = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("chores").orderByChild("choreID").equalTo(toDoChoresList.get(position).getChoreID());

                        currentChoreQ.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Iterable<DataSnapshot> currentChore = snapshot.getChildren();
                                    ChoreObject choreFound = currentChore.iterator().next().getValue(ChoreObject.class);
                                    choreFound.setProgressMode(index, 1);
                                    DatabaseReference newRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("chores").child((toDoChoresList.get(position).getChoreID()));
                                    newRef.setValue(choreFound);
//                                    newRef.removeValue();
//                                    newRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("chores").child(Integer.toString(position));
//                                    newRef.setValue(choreFound);
                                    notificationSender.sendNotification(group, choreFound.getName(), R.drawable.final_logo);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                editAlert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent editChoreIntent = new Intent(context, EditChoreActivity.class);
                        editChoreIntent.putExtra("groupId", groupId);
                        editChoreIntent.putExtra("position", position);
                        editChoreIntent.putExtra("userKey", currentUser);
                        String choreId = toDoChoresList.get(position).getChoreID();
                        editChoreIntent.putExtra("choreId", choreId);
                        System.out.println("open edit chore");
                        startActivity(editChoreIntent);
                    }
                });

                // TODO: Navigate to chores page

                editAlert.show();

            }
        };


//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("chores").child(choreID).child("name").getValue();

        Query allChores = choreDatabase.getReference("groups").orderByChild("idcode").equalTo(this.groupId);

        allChores.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        addChore(snapshot);
                        updateChart();
//                        String receiver = snapshot.child("groupId").getValue(String.class);
//
//                        if (currentUser.equals(receiver)) {
//                            System.out.println("SENDING NOTIFICATON");
////                            notificationSender.sendNotification(sender);
//                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        addChore(snapshot);
                        updateChart();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );



        toDoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        inProgressLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        completedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        toDoRView = findViewById(R.id.idToDoChoresRecycler);
        inProgressRView = findViewById(R.id.idInProgressChoresRecycler);
        completedRView = findViewById(R.id.idCompletedChoresRecycler);



//        ChoreCardClickListener choreCardClickListener = new ChoreCardClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                // TODO: Navigate to chores page
//                Intent editChoreIntent = new Intent(context, EditChoreActivity.class);
//                editChoreIntent.putExtra("groupId", groupId);
//                editChoreIntent.putExtra("position", position);
//                editChoreIntent.putExtra("userKey", currentUser);
//                String choreId = toDoChoresList.get(position).getChoreID();
//                editChoreIntent.putExtra("choreId", choreId);
//                editChoreIntent.putExtra("group", group);
//                System.out.println("open edit chore");
//                startActivity(editChoreIntent);
//            }
//        };



        ChoreCardClickListener choreCardClickListener2 = new ChoreCardClickListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder alertTwo = new AlertDialog.Builder(context);
                alertTwo.setTitle("Options");
                alertTwo.setMessage("Select Your Option");

//                // TODO: Navigate to chores page
//                Intent editChoreIntent = new Intent(context, EditChoreActivity.class);
//                editChoreIntent.putExtra("groupId", groupId);
//                editChoreIntent.putExtra("position", position);
//                editChoreIntent.putExtra("userKey", currentUser);
//                String choreId = inProgressChoresList.get(position).getChoreID();
//                editChoreIntent.putExtra("choreId", choreId);
//                startActivity(editChoreIntent);


                alertTwo.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });



                    alertTwo.setNeutralButton("Increment", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DayOfWeek day = inProgressChoresList.get(position).getRepeatedDay();
                            int index = getDayOfWeekRev(day);






                            Query currentChoreQ = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("chores").orderByChild("choreID").equalTo(inProgressChoresList.get(position).getChoreID());

                            currentChoreQ.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Iterable<DataSnapshot> currentChore = snapshot.getChildren();
                                        ChoreObject choreFound = currentChore.iterator().next().getValue(ChoreObject.class);
                                        choreFound.setProgressMode(index, 2);
                                        DatabaseReference newRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("chores").child((inProgressChoresList.get(position).getChoreID()));
                                        newRef.setValue(choreFound);
//                                    newRef.removeValue();
//                                    newRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("chores").child(Integer.toString(position));
//                                    newRef.setValue(choreFound);
                                        notificationSender.sendNotification(group, choreFound.getName(), R.drawable.final_logo);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    });

             alertTwo.show();
            }
        };

//        ChoreCardClickListener choreCardClickListener3 = new ChoreCardClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                // TODO: Navigate to chores page
//                Intent editChoreIntent = new Intent(context, EditChoreActivity.class);
//                editChoreIntent.putExtra("groupId", groupId);
//                editChoreIntent.putExtra("position", position);
//                editChoreIntent.putExtra("userKey", currentUser);
//                String choreId = completedChoresList.get(position).getChoreID();
//                editChoreIntent.putExtra("choreId", choreId);
//                editChoreIntent.putExtra("group", group);
//                startActivity(editChoreIntent);
//            }
//        };


        // To Do Chores
        toDoChoreAdapter = new ChoreAdapter(toDoChoresList);

        toDoChoreAdapter.setOnLinkClickListener(choreCardClickListener);
        toDoRView.setAdapter(toDoChoreAdapter);
        toDoRView.setLayoutManager(toDoLayoutManager);
        toDoRView.setHasFixedSize(true);

        // In Progress Chores

        inProgressChoreAdapter = new ChoreAdapter(inProgressChoresList);

        inProgressChoreAdapter.setOnLinkClickListener(choreCardClickListener2);

        inProgressRView.setAdapter(inProgressChoreAdapter);
        inProgressRView.setLayoutManager(inProgressLayoutManager);
        inProgressRView.setHasFixedSize(true);

        // Completed Chores

        completedChoreAdapter = new ChoreAdapter(completedChoresList);

//        completedChoreAdapter.setOnLinkClickListener(choreCardClickListener3);

        completedRView.setAdapter(completedChoreAdapter);
        completedRView.setLayoutManager(completedLayoutManager);
        completedRView.setHasFixedSize(true);


        chart = findViewById(R.id.idBarChart);

        barDataSet = new BarDataSet(dataValues1(), "Bar Set");
        barDataSet.setColors(colorClassArray);

        barData = new BarData(barDataSet);
        chart.setData(barData);

        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setDrawValueAboveBar(false);
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);


        LegendEntry l1 = new LegendEntry("Completed", Legend.LegendForm.DEFAULT,10f,2f,null, Color.GREEN);
        LegendEntry l2 = new LegendEntry("In Progress", Legend.LegendForm.DEFAULT,10f,2f,null, Color.YELLOW);
        LegendEntry l3 = new LegendEntry("To Do", Legend.LegendForm.DEFAULT,10f,2f,null, Color.RED);

        chart.getLegend().setCustom(new LegendEntry[]{l1, l2, l3});

        Button addButton = findViewById(R.id.addChoreButton);
        Intent intent = new Intent(this, AddChoreActivity.class);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intent.putExtra("userKey", currentUser);
                intent.putExtra("groupId", groupId);
                intent.putExtra("group", group);
                startActivity(intent);
            }
        });

        Button recap = findViewById(R.id.recapButton);
        Intent intent2 = new Intent(this, RecapActivity.class);
        recap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intent2.putExtra("userKey", currentUser);
                intent2.putExtra("groupId", groupId);

                startActivity(intent2);
            }
        });


    }


    private void updateChart() {
        barDataSet.removeEntry(0);
        barData.addEntry(dataValues1().get(0), 0);
        barData.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();

    }

    private ArrayList<BarEntry> dataValues1() {
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        int completed = completedChoresList.size();
        int inProgress = inProgressChoresList.size();
        int toDo = toDoChoresList.size();
        dataVals.add(new BarEntry(0, new float[]{completed, inProgress, toDo}));
        return dataVals;
    }

    private void addChore(DataSnapshot snapshot) {
        GroupObject group = snapshot.getValue(GroupObject.class);
        allChoresList.clear();
        toDoChoresList.clear();
        toDoChoreAdapter.notifyDataSetChanged();
        inProgressChoresList.clear();
        inProgressChoreAdapter.notifyDataSetChanged();
        completedChoresList.clear();
        completedChoreAdapter.notifyDataSetChanged();
        for  (ChoreObject chore : group.getChores()) {
//        ChoreObject chore = snapshot.getValue(ChoreObject.class);
            allChoresList.add(chore);

            if (snapshot.getKey() != null) {
                List<Boolean> days = chore.getDays();
                List<Integer> progresses = chore.getProgressMode();
                for (int i = 0; i < days.size(); i++) {
                    if (days.get(i)) {
                        DayOfWeek day = getDayOfWeek(i);
                        String title = chore.getName();
                        String assigned = chore.getUserAssigned();
                        String choreId = chore.getChoreID();
                        int progress = progresses.get(i);
                        assignList(day, title, assigned, progress, choreId);
                    }
                }
            }
        }
    }

    public DayOfWeek getDayOfWeek(int number) {
        // changed to + 1 to fix this
        switch (number + 1) {
            case 1:
                return DayOfWeek.SUNDAY;
            case 2:
                return DayOfWeek.MONDAY;
            case 3:
                return DayOfWeek.TUESDAY;
            case 4:
                return DayOfWeek.WEDNESDAY;
            case 5:
                return DayOfWeek.THURSDAY;
            case 6:
                return DayOfWeek.FRIDAY;
            case 7:
                return DayOfWeek.SATURDAY;
            default: throw new IllegalArgumentException("Not a valid day number");
        }
    }

    public int getDayOfWeekRev(DayOfWeek number) {
        // changed to + 1 to fix this
        switch (number) {
            case SUNDAY:
                return 0;
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            case SATURDAY:
                return 6;
            default: throw new IllegalArgumentException("Not a valid day number");
        }
    }

    public void assignList(DayOfWeek day, String title, String assigned, int progress, String choreID) {
        ChoreCard newChoreCard = new ChoreCard(day, title, assigned, progress, choreID);
        switch (progress) {
            case 0:
                toDoChoresList.add(newChoreCard);
                toDoChoreAdapter.notifyItemInserted(toDoChoresList.size() - 1);
                break;
            case 1:
                inProgressChoresList.add(newChoreCard);
                inProgressChoreAdapter.notifyItemInserted(inProgressChoresList.size() - 1);
                break;
            case 2:
                completedChoresList.add(newChoreCard);
                completedChoreAdapter.notifyItemInserted(completedChoresList.size() - 1);
                break;
            default: break;
        }
    }
//
//    private int[] getColors() {
//        // have as many colors as stack-values per entry
//        int[] colors = new int[3];
//        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);
//        return colors;
//    }

//    @Override
//    public void onBackPressed() {
//        this.finish();
//    }

}