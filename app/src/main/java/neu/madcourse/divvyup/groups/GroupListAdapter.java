package neu.madcourse.divvyup.groups;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import neu.madcourse.divvyup.R;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListRviewHolder> {

    private ArrayList<GroupCard> conversationCardList;
    private Activity context;
    private String currentUser;

    public GroupListAdapter(ArrayList<GroupCard> conversationCardList, Activity context, String currentUser) {
        this.conversationCardList = conversationCardList;
        this.context = context;
        this.currentUser = currentUser;
    }

    @Override
    public GroupListRviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_card, parent, false);
        return new GroupListRviewHolder(view, context, this.currentUser);
    }

    @Override
    public void onBindViewHolder(GroupListRviewHolder holder, int position) {
        GroupCard currentGroup = conversationCardList.get(position);
        holder.groupNameTextView.setText(currentGroup.getName());
        holder.groupIdView.setText(currentGroup.getGroupID());
    }

    @Override
    public int getItemCount() {
        return conversationCardList.size();
    }
}
