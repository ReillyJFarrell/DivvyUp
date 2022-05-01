package neu.madcourse.divvyup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import neu.madcourse.divvyup.data_objects.ChoreObject;
import neu.madcourse.divvyup.data_objects.GroupObject;
import neu.madcourse.divvyup.data_objects.UserObject;

public class DatabaseTestActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);
        this.database = FirebaseDatabase.getInstance();

        Button addUserButton = findViewById(R.id.createUser);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAddUserToDB("Test User", "Test User ID");
            }
        });

        Button addGroupButton = findViewById(R.id.createGroup);
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAddGroupToDB();
            }
        });

        Button addChoreButton = findViewById(R.id.createChore);
        addChoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAddChoreToDB("Test Chore Name", "Test Chore Description", false);
            }
        });
    }



    private void doAddUserToDB(String user, String userID){
        DatabaseReference ref = this.database.getReference().child("users");
        DatabaseReference newPostRef = ref.push();
        newPostRef.setValue(new UserObject(user, userID));
    }

    private void doAddGroupToDB(){
        DatabaseReference ref = this.database.getReference().child("pastGroups");
        DatabaseReference newPostRef = ref.push();
        ChoreObject testChore = new ChoreObject("Test chore", "This is a test chore", true);
        GroupObject testGroup = new GroupObject("IDDD", Arrays.asList("user1", "user2"), Arrays.asList(testChore), "Test Group Name");
        newPostRef.setValue(testGroup);
    }

    private void doAddChoreToDB(String choreName, String choreDescription, boolean isRepeating){
        DatabaseReference ref = this.database.getReference().child("chores");
        DatabaseReference newPostRef = ref.push();
        newPostRef.setValue(new ChoreObject(choreName, choreDescription,isRepeating));
    }
}