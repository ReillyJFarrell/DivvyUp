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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import neu.madcourse.divvyup.R;
import neu.madcourse.divvyup.data_objects.GroupObject;


public class GroupListActivity extends AppCompatActivity {

    private ArrayList<GroupCard> groupCardList = new ArrayList<>();
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

    ArrayList<String> groups = new ArrayList<>();
    String currentUser;
    String[] groupIDs;
    private Integer indexCount = 0;
    private Integer loadingState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.currentUser = extras.getString("userKey");
            this.groupIDs = extras.getStringArray("groups");
        }

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

        createRecyclerView();

        groupDatabase = FirebaseDatabase.getInstance();

        DatabaseReference allUsers = groupDatabase.getReference().child(FIELD_USERS);
        DatabaseReference allGroups = groupDatabase.getReference().child(FIELD_GROUPS);

//        allUsers.addChildEventListener(
//                new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        String groupId = snapshot.child(FIELD_GROUP).getValue(String.class);
//
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
////                        addMessage(snapshot);
////                        sendNotification();
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                }
//        );



//        allGroups.addValueEventListener(new ValueEventListener() {
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (loadingState != 0){
//                    return;
//                }
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                        String receiver = ds.child("receiver").getValue(String.class);
//                        String sender = ds.child("sender").getValue(String.class);
//                        if (receiver == null || sender == null){
//                            continue;
//                        }
//                        if (groups.contains(receiver) && groups.contains(sender)){
//                            continue;
//                        }else {
//                            if (!groups.contains(receiver)) {
//                                if (receiver.equals(currentUser)){
//                                    newUser = 0;
//                                    continue;
//                                }
//
//                                GroupCard conversationCardReceiver = new GroupCard(receiver);
//                                groupCardList.add(conversationCardReceiver);
//                                groupListRviewAdapter.notifyItemInserted(groupCardList.size() - 1);
//                                groups.add(receiver);
//                            }else {
//                                if (currentUser.equals(sender)){
//                                    newUser = 0;
//                                    continue;
//                                }
//                                if (sender.equals("00000000")){
//                                    continue;
//                                }
//                                GroupCard conversationCardReceiver = new GroupCard(sender);
//                                groupCardList.add(conversationCardReceiver);
//                                groupListRviewAdapter.notifyItemInserted(groupCardList.size() - 1);
//                                groups.add(sender);
//                            }
//                        }
//
//
//                }
//
//                if (groups.size() == 0){
//
//                    FirebaseDatabase chatDatabase = FirebaseDatabase.getInstance();
//                    DatabaseReference ref = chatDatabase.getReference().child("messages");
//                    DatabaseReference pRef = ref.push();
//                }
//                loadingState = 1;
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });
    }

    private String addGroupToDB(String groupName){
        DatabaseReference ref = this.groupDatabase.getReference().child("groups");
        DatabaseReference newPostRef = ref.push();
        // Random String for groupID
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

        GroupObject testGroup = new GroupObject(generatedString, Arrays.asList(this.currentUser), new ArrayList<>(), "Test Group Name");
        newPostRef.setValue(testGroup);
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
//                if() {
//
//                    addItem(0, newItemNameString, newItemUrlString);
//                    snackMessage = "New Item Created";
//                } else {
//                    System.out.println("URL not valid");
//                    System.out.println(newItemUrlString);
//                    snackMessage = "Please re-enter URL in proper format";
//                }
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
                addItem(0, newItemName, code);
                snackMessage = "New Item Created";
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