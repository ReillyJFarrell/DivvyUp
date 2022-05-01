package neu.madcourse.divvyup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import neu.madcourse.divvyup.chores_list_screen.ChoresActivity;
import neu.madcourse.divvyup.data_objects.ChoreObject;
import neu.madcourse.divvyup.data_objects.GroupObject;

public class EditChoreActivity extends AppCompatActivity {

    EditText choreNameEditText;
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
    String groupId = "";
    String choreId = "";
    String group = "";
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chore);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.currentUser = extras.getString("userKey");
            this.groupId = extras.getString("groupId");
            this.choreId = extras.getString("choreId");
            this.position = extras.getInt("position");
            this.group = extras.getString("group");
        }
        else {
            this.groupId = "IDDD";
        }

        choreNameEditText = findViewById(R.id.choreNameEditView);
        // need to read from DB
        assignedSpinner = (Spinner) findViewById(R.id.assignedSpinner);
        // users array should be dynamic, not static
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users_array, R.layout.spinner_selected_item);
//        ArrayList<String> users = new ArrayList<>();
////        users.add("User 1");
////        users.add("User 2");
////        users.add("User 3");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, users);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        assignedSpinner.setAdapter(adapter);


        week = new ArrayList<>();
        sunday = findViewById(R.id.sundayCheckbox);
        week.add(sunday);
        monday = findViewById(R.id.mondayCheckbox);
        week.add(monday);
        tuesday = findViewById(R.id.tuesdayCheckbox);
        week.add(tuesday);
        wednesday = findViewById(R.id.wednesdayCheckbox);
        week.add(wednesday);
        thursday = findViewById(R.id.thursdayCheckbox);
        week.add(thursday);
        friday = findViewById(R.id.fridayCheckbox);
        week.add(friday);
        saturday = findViewById(R.id.saturdayCheckbox);
        week.add(saturday);

        repeating = findViewById(R.id.repeatingCheckBox);

        Activity context = this;

        Query currentGroup = FirebaseDatabase.getInstance().getReference().child("groups").orderByKey().equalTo(groupId);
        currentGroup.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // set values to match what currently exists in the DB
        Query oldChore = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("chores").orderByChild("choreID").equalTo(choreId);
        oldChore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> toSet = snapshot.getChildren();
                DataSnapshot snap = toSet.iterator().next();
                ChoreObject chore = snap.getValue(ChoreObject.class);


                choreNameEditText.setText(chore.getName());
                repeating.setChecked(chore.getRepeat());
                sunday.setChecked(chore.getDays().get(0));
                monday.setChecked(chore.getDays().get(1));
                tuesday.setChecked(chore.getDays().get(2));
                wednesday.setChecked(chore.getDays().get(3));
                thursday.setChecked(chore.getDays().get(4));
                friday.setChecked(chore.getDays().get(5));
                saturday.setChecked(chore.getDays().get(6));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChore(groupId, choreId);
                Intent choresActivityIntent = new Intent(context, ChoresActivity.class);
                choresActivityIntent.putExtra("userKey", currentUser);
                choresActivityIntent.putExtra("group", group);
                choresActivityIntent.putExtra("groupID", groupId);
                startActivity(choresActivityIntent);
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent choresActivityIntent = new Intent(context, ChoresActivity.class);
                choresActivityIntent.putExtra("userKey", currentUser);
                choresActivityIntent.putExtra("group", group);
                choresActivityIntent.putExtra("groupID", groupId);
                startActivity(choresActivityIntent);
            }
        });
    }

    private void saveChore(String groupID, String choreID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child(choreID);
        // save data

        Query currentChore = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("chores").orderByChild("choreID").equalTo(choreID);
        currentChore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String assignedUser = assignedSpinner.getSelectedItem().toString();
                ArrayList<Boolean> days = new ArrayList<Boolean>();
                for (int i = 0; i < week.size(); i++) {
                    if (week.get(i).isChecked()) {
                        days.add(true);
                    }
                    else {
                        days.add(false);
                    }
                }

                //Iterable<DataSnapshot> toSet = snapshot.getChildren();
                //DataSnapshot snap = toSet.iterator().next();
                //GroupObject currentGroup = snap.getValue(GroupObject.class);

                ChoreObject editedChore = new ChoreObject(choreNameEditText.getText().toString(), groupID, choreID, assignedUser, days, repeating.isChecked());
                DatabaseReference newRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("chores").child(Integer.toString(position));
                newRef.setValue(editedChore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}