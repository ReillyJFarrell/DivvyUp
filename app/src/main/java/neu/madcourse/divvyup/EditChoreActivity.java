package neu.madcourse.divvyup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditChoreActivity extends AppCompatActivity {

    EditText choreName;
    Spinner assignedSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chore);

        choreName = findViewById(R.id.choreNameEditView);
        // need to read from DB
        assignedSpinner = findViewById(R.id.assignedSpinner);

        CheckBox sunday = findViewById(R.id.sundayCheckbox);
        CheckBox monday = findViewById(R.id.mondayCheckbox);
        CheckBox tuesday = findViewById(R.id.tuesdayCheckbox);
        CheckBox wednesday = findViewById(R.id.wednesdayCheckbox);
        CheckBox thursday = findViewById(R.id.thursdayCheckbox);
        CheckBox friday = findViewById(R.id.fridayCheckbox);
        CheckBox saturday = findViewById(R.id.saturdayCheckbox);

        CheckBox repeating = findViewById(R.id.repeatingCheckBox);


        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChore();
            }
        });
    }

    // TODO: add an ID as param (need to figure out the data type)
    private void saveChore() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        // save data
        // mDatabase.child("chores")
    }
}