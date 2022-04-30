package neu.madcourse.divvyup.groups;

import android.app.NotificationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import neu.madcourse.divvyup.R;


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
    private static final String FIELD_USERS = "users";
    private static final String FIELD_GROUP = "group";
    private static final String FIELD_GROUPS = "groups";
    private static final String FIELD_GROUPNAME = "groupName";


    ArrayList<String> groups = new ArrayList<>();
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

        createRecyclerView();

        groupDatabase = FirebaseDatabase.getInstance();

        DatabaseReference allUsers = groupDatabase.getReference().child(FIELD_USERS);
        DatabaseReference allGroups = groupDatabase.getReference().child(FIELD_GROUPS);



        allUsers.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String groupId = snapshot.child(FIELD_GROUP).getValue(String.class);

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        addMessage(snapshot);
//                        sendNotification();
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