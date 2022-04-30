package neu.madcourse.divvyup.groups;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import neu.madcourse.divvyup.R;
import neu.madcourse.divvyup.chores_list_screen.ChoresActivity;

public class GroupListRviewHolder extends RecyclerView.ViewHolder {

    public TextView groupNameTextView;
    public TextView groupIdView;
    public CardView groupCardView;
    private String currentUser;

    public GroupListRviewHolder(View view, Activity context, String currentUser) {
        super(view);
        this.currentUser = currentUser;
        //TODO: FIX
        groupNameTextView = view.findViewById(R.id.groupTextView);
        groupIdView = view.findViewById(R.id.groupId);
        groupCardView = view.findViewById(R.id.groupCardView);

        groupCardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent conversationIntent = new Intent(context, ChoresActivity.class);
               conversationIntent.putExtra("userKey", currentUser);
               conversationIntent.putExtra("group", groupNameTextView.getText().toString());
               conversationIntent.putExtra("groupID", groupNameTextView.getText().toString());
               context.startActivity(conversationIntent);
           }
        });
    }
}
