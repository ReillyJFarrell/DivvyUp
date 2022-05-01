package neu.madcourse.divvyup.login;

import static neu.madcourse.divvyup.groups.GroupListActivity.FIELD_NAME;
import static neu.madcourse.divvyup.groups.GroupListActivity.FIELD_USERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import neu.madcourse.divvyup.R;
import neu.madcourse.divvyup.data_objects.UserObject;
import neu.madcourse.divvyup.groups.GroupListActivity;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase userDatabase;

    DatabaseReference dbUsers;
    private List<UserObject> userList;
    private List<UserObject> usersList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView userInput = findViewById(R.id.loginEditText);

        userDatabase = FirebaseDatabase.getInstance();
        dbUsers = userDatabase.getReference(FIELD_USERS);
        userList = new ArrayList<>();
        usersList = new ArrayList<>();

        dbUsers.addListenerForSingleValueEvent(valueEventListenerList);

        Button loginButton =findViewById(R.id.loginButton);
        Intent intent = new Intent(this, GroupListActivity.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userInput.getText().toString();

                UserObject actualUser = null;
                for (UserObject user : usersList) {
                    String userName = user.getName();
                    boolean matches = userName.equals(name);
                    if (matches) {
                        actualUser = user;
                        break;
                    }
                }
                if (actualUser == null) {
                    DatabaseReference newPostRef = dbUsers.push();
                    newPostRef.setValue(new UserObject(name, "Device ID"));
                    intent.putExtra("groups", new String[0]);
                } else {
                    intent.putExtra("groups", actualUser.getGroupIDs().toArray());
                }

                intent.putExtra("userKey", name);

                startActivity(intent);
            }
        });
    }

    ValueEventListener valueEventListenerList = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            usersList.clear();
            if (snapshot.exists()) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    UserObject user = snap.getValue(UserObject.class);
                    usersList.add(user);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}