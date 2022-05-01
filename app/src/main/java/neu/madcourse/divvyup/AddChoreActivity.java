package neu.madcourse.divvyup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import neu.madcourse.divvyup.data_objects.ChoreObject;
import neu.madcourse.divvyup.data_objects.GroupObject;
import neu.madcourse.divvyup.data_objects.PastGroupObject;

public class AddChoreActivity extends AppCompatActivity {

    EditText choreName;
    Spinner assignedSpinner;

    CheckBox sunday;
    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox saturday;

    ArrayList<CheckBox> week;

    CheckBox repeating;

    String currentUser;
    String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.currentUser = extras.getString("userKey");
            this.groupId = extras.getString("groupId");
        }
        else {
            this.groupId = "IDDD";
        }

        System.out.println(groupId);

        choreName = findViewById(R.id.addChoreNameEditView);
        // need to read from DB
        assignedSpinner = (Spinner) findViewById(R.id.addAssignedSpinner);
        // users array should be dynamic, not static
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users_array, R.layout.spinner_selected_item);
        ArrayList<String> users = new ArrayList<>();
        users.add("User 1");
        users.add("User 2");
        users.add("User 3");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedSpinner.setAdapter(adapter);


        week = new ArrayList<>();
        sunday = findViewById(R.id.addSundayCheckbox);
        week.add(sunday);
        monday = findViewById(R.id.addMondayCheckbox);
        week.add(monday);
        tuesday = findViewById(R.id.addTuesdayCheckbox);
        week.add(tuesday);
        wednesday = findViewById(R.id.addWednesdayCheckbox);
        week.add(wednesday);
        thursday = findViewById(R.id.addThursdayCheckbox);
        week.add(thursday);
        friday = findViewById(R.id.addFridayCheckbox);
        week.add(friday);
        saturday = findViewById(R.id.addSaturdayCheckbox);
        week.add(saturday);

        repeating = findViewById(R.id.addRepeatingCheckBox);

        Button saveButton = findViewById(R.id.addSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChore(groupId);
            }
        });
    }

    private void addChore(String groupID) {
        // DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("chores"); //.child(choreId);

        Activity context = this;

        Query currentGroup = FirebaseDatabase.getInstance().getReference().child("groups").orderByKey().equalTo(groupId);
        currentGroup.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // mDatabase.child("choreID").setValue("test chore id");
                // Taken from https://www.baeldung.com/java-random-string
                // refactored from GroupListActivity
                int leftLimit = 48; // numeral '0'
                int rightLimit = 122; // letter 'z'
                int targetStringLength = 10;
                Random random = new Random();

                String choreId = random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(targetStringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();


                String assignedUser = assignedSpinner.getSelectedItem().toString();
                // mDatabase.child("userAssigned").setValue(assignedUser);


                Iterable<DataSnapshot> toSet = snapshot.getChildren();
                DataSnapshot snap = toSet.iterator().next();
                GroupObject currentGroup = snap.getValue(GroupObject.class);

                ArrayList<String> users = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_selected_item, users);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                assignedSpinner.setAdapter(adapter);

                for (String id : currentGroup.getMembersIDs()) {
                    users.add(id);
                    adapter.notifyDataSetChanged();
                }

                ArrayList<Boolean> days = new ArrayList<Boolean>();
                for (int i = 0; i < week.size(); i++) {
                    if (week.get(i).isChecked()) {
                        days.add(true);
                    }
                    else {
                        days.add(false);
                    }
                }
                // mDatabase.child("days").setValue(days);

                boolean isRepeat = repeating.isChecked();
                // mDatabase.child("isRepeat").setValue(repeating.isChecked());

                ChoreObject newChore = new ChoreObject(choreName.getText().toString(), groupId, choreId, assignedUser, days, isRepeat);
                List<ChoreObject> updatedChores = currentGroup.getChores();
                updatedChores.add(newChore);
                currentGroup.setChores(updatedChores);

                DatabaseReference newRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID);
                newRef.setValue(currentGroup);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}