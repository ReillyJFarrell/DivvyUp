package neu.madcourse.divvyup.recap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import neu.madcourse.divvyup.R;
import neu.madcourse.divvyup.chores_list_screen.ChoreCard;
import neu.madcourse.divvyup.data_objects.ChoreObject;
import neu.madcourse.divvyup.data_objects.GroupObject;
import neu.madcourse.divvyup.data_objects.PastGroupObject;

public class RecapActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager rLayoutManager;
    private ArrayList<RecapCard> recapCardsList = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView progressPercent;
    private RecyclerView recapRView;
    private int mProgressStatus = 0;
    private RecapAdapter recapAdapter;
    private String groupId;
    private FirebaseDatabase database;
    private GroupObject selectedGroup;
    private TextView groupNameRecap;



    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupId = "pkEKWW1";
        this.database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        setContentView(R.layout.activity_progress);
        rLayoutManager = new LinearLayoutManager(this);
        progressPercent = findViewById(R.id.progress_percent);
        recapRView = findViewById(R.id.recapRView);
        recapAdapter = new RecapAdapter(recapCardsList);
        recapRView.setAdapter(recapAdapter);
        recapRView.setLayoutManager(rLayoutManager);
        recapRView.setHasFixedSize(true);
        this.groupNameRecap = findViewById(R.id.group_name_recap);


        progressBar = (ProgressBar) findViewById(R.id.choreProgressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        progressPercent.setVisibility(View.VISIBLE);
        RecapCard card = new RecapCard("Wash the Dog", 1, "Sam", Arrays.asList(-1, -1, -1, 2, -1, -1, -1));
        RecapCard card1 = new RecapCard("Take out the Dog", 1, "Bill", Arrays.asList(2, 2, 0, 2, 2, 1, 1));
        RecapCard card2 = new RecapCard("Feed the Dog", 2, "Ted", Arrays.asList(2, 2, 2, 2, 2, 2, 2));
        RecapCard card3 = new RecapCard("Play with the Dog", 0, "Tam", Arrays.asList(0, 0, -1, -1, 0, 0, 0));
        RecapCard card4 = new RecapCard("Train the Dog", 2, "Tyler", Arrays.asList(-1, 2, -1, 2, -1, -1, 2));
        RecapCard card5 = new RecapCard("Let the dog out", 1, "Abby", Arrays.asList(-1, -1, -1, 1, 1, 1, 1));
        RecapCard card6 = new RecapCard("Brush the Dog", 1, "Lily", Arrays.asList(2, -1, -1, -1, 0, -1, -1));
        RecapCard card7 = new RecapCard("Take the Dog to the vet", 0, "Sean", Arrays.asList(-1, -1, -1, 0, -1, -1, -1));
        RecapCard card8 = new RecapCard("Take the dog to the park", 2, "Robert", Arrays.asList(-1, -1, -1, 2, 2, 2, -1));
//        recapCardsList.add(card);
//        recapCardsList.add(card1);
//        recapCardsList.add(card2);
//        recapCardsList.add(card3);
//        recapCardsList.add(card4);
//        recapCardsList.add(card5);
//        recapCardsList.add(card6);
//        recapCardsList.add(card7);
//        recapCardsList.add(card8);
//        recapCardsList.add(card);
//        recapCardsList.add(card1);
//        recapCardsList.add(card2);
//        recapCardsList.add(card3);
//        recapCardsList.add(card4);
//        recapCardsList.add(card5);
//        recapCardsList.add(card6);
//        recapCardsList.add(card7);
//        recapCardsList.add(card8);
//        Collections.sort(recapCardsList, Collections.reverseOrder());
//        recapAdapter.notifyDataSetChanged();


        Query recapQ = ref.child("pastGroups").orderByKey().equalTo(groupId);
        new Thread(new Runnable() {
            @Override
            public void run() {

        recapQ.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exists = false;
                boolean behind = false;
                if(dataSnapshot.exists()) {
                    exists = true;
                    Iterable<DataSnapshot> toCheck = dataSnapshot.getChildren();
                    DataSnapshot target = toCheck.iterator().next();
                    behind = isBehind(target.getValue(PastGroupObject.class).getCreated());
                    PastGroupObject gg = new PastGroupObject();
//                     && dataSnapshot.getValue(PastGroupObject.class
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//
//                        Toast.makeText(getApplicationContext(), "id = " + snapshot.getKey(), Toast.LENGTH_LONG).show();
//                    }

                }

                if(!exists || behind ){
                    Query currentGroup = database.getReference().child("groups").orderByKey().equalTo(groupId);

                    currentGroup.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


//                            GroupObject testGroup = new GroupObject(generatedString, Arrays.asList(this.currentUser), new ArrayList<>(), groupName);
//                            ref.setValue(testGroup);
                            Iterable<DataSnapshot> toSet = snapshot.getChildren();
                            DataSnapshot snap = toSet.iterator().next();
                            DatabaseReference newRef = database.getReference().child("pastGroups").child(groupId);
                            Date today = new Date();
                            newRef.setValue(new PastGroupObject(snap.getValue(GroupObject.class), new Date()));
//                            newRef.setValue(new PastGroupObject(snap.getValue(GroupObject.class), new Date(today.getYear(), today.getMonth(), today.getDate() - 14)));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
            }
        }).start();


        ref.child("pastGroups").orderByKey().equalTo(groupId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                System.out.println(snapshot.getClass());
                selectedGroup = snapshot.getValue(PastGroupObject.class);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        for (ChoreObject chore: selectedGroup.getChores()){
                            RecapCard toAdd = new RecapCard(chore.getName(), calcStatus(chore.getProgressMode()), chore.getUserAssigned(), chore.getProgressMode());
                            recapCardsList.add(toAdd);
                            recapAdapter.notifyItemInserted(recapCardsList.size() - 1);
                        }
//                    }
//                });


                groupNameRecap.setText(selectedGroup.getGroupName());
                recapAdapter.notifyDataSetChanged();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        List<Integer> vals = calcProgressDemo(recapCardsList);
                        float unrounded = (float)vals.get(0) / ((float) vals.get(0) + (float) vals.get(1));
                        int toFill = Math.round(unrounded * 100);
                        while (mProgressStatus < toFill) {
                            mProgressStatus++;
                            SystemClock.sleep(15);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(mProgressStatus);
                                    progressPercent.setText(mProgressStatus + "%");
                                    gradientPercent(mProgressStatus);
                                }
                            });
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RecapActivity.this, "Finished " + mProgressStatus + "% of all chores!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        });






//        RecapCard card = new RecapCard("Wash the Dog", 2, "Sam", Arrays.asList("-1", "-1", "-1", "2", "-1", "-1", "-1"));
//        RecapCard card1 = new RecapCard("Take out the Dog", 1, "Bill", Arrays.asList("2", "2", "0", "2", "2", "1", "1"));
//        RecapCard card2 = new RecapCard("Feed the Dog", 2, "Ted", Arrays.asList("2", "2", "2", "2", "2", "2", "2"));
//        RecapCard card3 = new RecapCard("Play with the Dog", 0, "Tam", Arrays.asList("0", "0", "-1", "-1", "0", "0", "0"));
//        RecapCard card4 = new RecapCard("Train the Dog", 2, "Tyler", Arrays.asList("-1", "2", "-1", "2", "-1", "-1", "2"));
//        RecapCard card5 = new RecapCard("Let the dog out", 1, "Abby", Arrays.asList("-1", "-1", "-1", "1", "1", "1", "1"));
//        RecapCard card6 = new RecapCard("Brush the Dog", 1, "Lily", Arrays.asList("2", "-1", "-1", "-1", "0", "-1", "-1"));
//        RecapCard card7 = new RecapCard("Take the Dog to the vet", -1, "Sean", Arrays.asList("-1", "-1", "-1", "0", "-1", "-1", "-1"));
//        RecapCard card8 = new RecapCard("Take the dog to the park", 2, "Robert", Arrays.asList("-1", "-1", "-1", "2", "2", "2", "-1"));
//        recapCardsList.add(card);
//        recapCardsList.add(card1);
//        recapCardsList.add(card2);
//        recapCardsList.add(card3);
//        recapCardsList.add(card4);
//        recapCardsList.add(card5);
//        recapCardsList.add(card6);
//        recapCardsList.add(card7);
//        recapCardsList.add(card8);
//        recapCardsList.add(card);
//        recapCardsList.add(card1);
//        recapCardsList.add(card2);
//        recapCardsList.add(card3);
//        recapCardsList.add(card4);
//        recapCardsList.add(card5);
//        recapCardsList.add(card6);
//        recapCardsList.add(card7);
//        recapCardsList.add(card8);
//        mLoadingText = (TextView) findViewById(R.id.LoadingCompleteTextView);




    }

    @Override
    public void onBackPressed() {
//        Log.d("CDA", "onBackPressed Called");
//        Intent setIntent = new Intent(Intent.ACTION_MAIN);
//        setIntent.addCategory(Intent.CATEGORY_HOME);
//        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(setIntent);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);
        progressPercent.setVisibility(View.INVISIBLE);
        recapCardsList.clear();
        recapAdapter.notifyDataSetChanged();
        mProgressStatus = 0;
        this.finish();
    }

    public void gradientPercent(int percent) {
        if (percent <= 25) {
            progressPercent.setTextColor(Color.parseColor("#DC143C"));
        } else if (percent <= 50) {
            progressPercent.setTextColor(Color.parseColor("#FFA500"));
        } else if (percent <= 75) {
            progressPercent.setTextColor(Color.parseColor("#DE970B"));
        } else {
            progressPercent.setTextColor(Color.parseColor("#08FF08"));
        }
//        toDoChoresList.add(new ChoreCard(3, 1, "Dishes", "clean dishes", new Date(2022, 5, 12), 1));
    }

    public int calcStatus(List<Integer> prog){
        boolean didOne = false;
        boolean missedOne = false;
        for (Integer item: prog){
            if (item.equals(0) || item.equals(1)){
                missedOne = true;
            }else if (item.equals(2)){
                didOne = true;
            }
        }
        if (didOne && missedOne){
            return 1;
        }else if (didOne){
            return 2;
        }else {
            return 0;
        }
    }

    public List<Integer> calcProgress(List<ChoreObject> chores){
        int missed = 0;
        int completed = 0;
        for (ChoreObject chore: chores){
            for (Integer day: chore.getProgressMode()){
                if (day.equals(2)) {
                    completed++;
                }else if (day.equals(-1)){
                    continue;
                }else {
                    missed ++;
                }
            }
        }
        return Arrays.asList(completed, missed);
    }

    public List<Integer> calcProgressDemo(List<RecapCard> chores){
        int missed = 0;
        int completed = 0;
        for (RecapCard chore: chores){
            for (Integer day: chore.getDayStatus()){
                if (day.equals(2)) {
                    completed++;
                }else if (day.equals(-1)){
                    continue;
                }else {
                    missed ++;
                }
            }
        }
        return Arrays.asList(completed, missed);
    }

    public boolean isBehind(Date date){
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date monday = c.getTime();

        Date lastMonday= new Date(monday.getTime()-7*24*60*60*1000);
        boolean ret = date.before(lastMonday);
        return ret;
    }
}