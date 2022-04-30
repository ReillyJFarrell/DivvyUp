package neu.madcourse.divvyup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class EditChoreActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chore);

        choreName = findViewById(R.id.choreNameEditView);
        // need to read from DB
        assignedSpinner = (Spinner) findViewById(R.id.assignedSpinner);
        // users array should be dynamic, not static
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users_array, R.layout.spinner_selected_item);
        ArrayList<String> users = new ArrayList<>();
        users.add("User 1");
        users.add("User 2");
        users.add("User 3");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedSpinner.setAdapter(adapter);

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

        // should be getting this from bundle extras
        String groupID = "testID";
        String choreID = "choreID";

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChore(groupID, choreID);
            }
        });
    }

    private void saveChore(String groupID, String choreID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID).child(choreID);
        // save data
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
    }
}