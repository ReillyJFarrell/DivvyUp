package neu.madcourse.divvyup.groups;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.security.acl.Group;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import neu.madcourse.divvyup.R;
import neu.madcourse.divvyup.data_objects.GroupObject;
import neu.madcourse.divvyup.data_objects.UserObject;


public class GroupListActivity extends AppCompatActivity {

    private ArrayList<GroupCard> groupCardList;
    private RecyclerView recyclerView;
    private GroupListAdapter groupListRviewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase groupDatabase;

    private NotificationManager notificationManager;

    private static final String CONVERSATION_COUNT = "CONVERSATION_COUNT";
    private static final String CONVERSATION_KEY = "CONVERSATION_KEY";

    //TODO: Double Check Field Names
    public static final String FIELD_USERS = "users";
    public static final String FIELD_GROUP = "group";
    public static final String FIELD_GROUPS = "groups";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_GROUPNAME = "groupName";

    private Button addButton;
    private Button createButton;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newItem_name, newItem_url;
    private Button newItem_save, newItem_cancel;


    List<GroupObject> allGroupsList;
    String currentUser;
    private Integer indexCount = 0;
    private Integer loadingState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.currentUser = extras.getString("userKey");
        }

        groupCardList = new ArrayList<>();
        allGroupsList = new ArrayList<>();

        createRecyclerView();

        addButton = findViewById(R.id.addGroupBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAddDialog();
            }

        });

        createButton = findViewById(R.id.createGroupBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCreateDialog();
            }
        });

        groupDatabase = FirebaseDatabase.getInstance();

        DatabaseReference allGroups = groupDatabase.getReference(FIELD_GROUPS);

//        allGroups.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        groupCardList.clear();
//                        allGroupsList.clear();
//                        if (snapshot.exists()) {
//                            for (DataSnapshot snap : snapshot.getChildren()) {
//                                GroupObject group = snap.getValue(GroupObject.class);
//                                List<String> memberIDs = group.getMembersIDs();
//                                for (String memberID : memberIDs) {
//                                    if (memberID.equals(currentUser)) {
//                                        addItem(0, group.getGroupName(), group.getIDCode());
//                                    }
//                                    allGroupsList.add(group);
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                }
//        );

        allGroups.addChildEventListener(
                new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        groupCardList.clear();
//                        allGroupsList.clear();
                        if (snapshot.exists()) {
//                            for (DataSnapshot snap : snapshot.getChildren()) {
                            DataSnapshot snap = snapshot;
                                GroupObject group = snap.getValue(GroupObject.class);
                                List<String> memberIDs = group.getMembersIDs();
                                for (String memberID : memberIDs) {
                                    if (memberID.equals(currentUser)) {
                                        addItem(0, group.getGroupName(), group.getIDCode());
                                    }
                                    allGroupsList.add(group);
                                }
//                            }
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        groupCardList.clear();
//                        allGroupsList.clear();
//                        if (snapshot.exists()) {
////                            for (DataSnapshot snap : snapshot.getChildren()) {
//                                DataSnapshot snap = snapshot;
//                                GroupObject group = snap.getValue(GroupObject.class);
//                                List<String> memberIDs = group.getMembersIDs();
//                                for (String memberID : memberIDs) {
//                                    if (memberID.equals(currentUser)) {
//                                        addItem(0, group.getGroupName(), group.getIDCode());
//                                    }
//                                    allGroupsList.add(group);
//                                }
////                            }
//                        }
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

    }


    private String addGroupToDB(String groupName){

        // Random String for groupID
        // Taken from https://www.baeldung.com/java-random-string
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 7;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);

        DatabaseReference ref = this.groupDatabase.getReference("groups").child(generatedString);
//        DatabaseReference newPostRef = ref.push();

        GroupObject testGroup = new GroupObject(generatedString, Arrays.asList(this.currentUser), new ArrayList<>(), groupName);
        ref.setValue(testGroup);
        return generatedString;
    }

    public void generateAddDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View itemPopupView = getLayoutInflater().inflate(R.layout.popup_add, null);
        newItem_name = (EditText) itemPopupView.findViewById(R.id.newItem_name);

        newItem_save = (Button) itemPopupView.findViewById(R.id.save_button);
        newItem_cancel = (Button) itemPopupView.findViewById(R.id.cancel_button);

        dialogBuilder.setView(itemPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newItem_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItemNameString = newItem_name.getText().toString();
                String snackMessage = "";
                //TODO: QUERY DATABASE TO CROSS-REFERENCE CODE

                GroupObject canAdd = null;
                for (GroupObject group : allGroupsList) {
                    if (group.getIDCode().equals(newItemNameString)) {
                        canAdd = group;
                    }
                }
                for (GroupCard group : groupCardList) {
                    if (newItemNameString.equals(group.getGroupID())) {{
                        canAdd = null;
                    }}
                }
                if(canAdd != null) {
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("groups").child(newItemNameString).child("membersIDs");
                    List<String> members = canAdd.getMembersIDs();
                    members.add(currentUser);
                    mDatabase.setValue(members);
                    addItem(0, canAdd.getGroupName(), newItemNameString);
                    snackMessage = "New Group Added";
                } else {
                    snackMessage = "Please re-enter a proper groupID";
                }
                dialog.dismiss();

                Snackbar.make(findViewById(android.R.id.content), snackMessage, Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show();
            }
        });

        newItem_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void generateCreateDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View itemPopupView = getLayoutInflater().inflate(R.layout.popup_create, null);
        newItem_name = (EditText) itemPopupView.findViewById(R.id.newItem_name);

        newItem_save = (Button) itemPopupView.findViewById(R.id.save_button);
        newItem_cancel = (Button) itemPopupView.findViewById(R.id.cancel_button);

        dialogBuilder.setView(itemPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newItem_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItemName = newItem_name.getText().toString();
                String code = addGroupToDB(newItemName);
                String snackMessage = "";
//                addItem(0, newItemName, code);
                snackMessage = "New Group Created";
                dialog.dismiss();

                Snackbar.make(findViewById(android.R.id.content), snackMessage, Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show();
            }
        });

        newItem_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void addItem(int position, String name, String groupID) {
        groupCardList.add(position, new GroupCard(name, groupID));
//        Toast.makeText(LinkCollectorActivity.this, "Add an item", Toast.LENGTH_SHORT).show();

        groupListRviewAdapter.notifyItemInserted(position);
    }



    private void createRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.groupListRecyclerView);
        recyclerView.setHasFixedSize(true);
        groupListRviewAdapter = new GroupListAdapter(groupCardList, this, this.currentUser);
        recyclerView.setAdapter(groupListRviewAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void onBackPressed (){
        this.currentUser = "";
        this.finish();
    }
}