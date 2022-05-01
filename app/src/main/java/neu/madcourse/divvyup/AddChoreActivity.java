package neu.madcourse.divvyup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
    }

    private void addChore(String groupID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child("chores");
        DatabaseReference postRef = mDatabase.push();

        mDatabase.child("userAssigned").setValue(choreName.getText().toString());

        String assignedUser = assignedSpinner.getSelectedItem().toString();
        mDatabase.child("userAssigned").setValue(assignedUser);

        ArrayList<Boolean> days = new ArrayList<Boolean>();
        for (int i = 0; i < week.size(); i++) {
            if (week.get(i).isChecked()) {
                days.add(true);
            }
            else {
                days.add(false);
            }
        }
        mDatabase.child("days").setValue(days);

        mDatabase.child("isRepeat").setValue(repeating.isChecked());

        // TODO: generate id for chore I think?
        // mDatabase.child("choreID").setValue("test chore id");

    }
}