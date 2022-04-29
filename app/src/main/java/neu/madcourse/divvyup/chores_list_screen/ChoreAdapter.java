package neu.madcourse.divvyup.chores_list_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import neu.madcourse.divvyup.R;


public class ChoreAdapter extends RecyclerView.Adapter<ChoreHolder> {

    private final List<ChoreCard> listOfChores;
    private ChoreCardClickListener listener;

    public ChoreAdapter(List<ChoreCard> listOfChores) {
        this.listOfChores = listOfChores;
    }

    public void setOnLinkClickListener(ChoreCardClickListener listener) {
        this.listener = listener;
    }


    @Override
    public ChoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chore_card, parent, false);
        return new ChoreHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ChoreHolder holder, int position) {
        ChoreCard currentChore = listOfChores.get(position);
        holder.assignedUser.setText(Integer.toString(currentChore.getAssignedUserId()));
        holder.dueDate.setText(currentChore.getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        holder.task.setText(formatter.format(currentChore.getDeadline()));
    }

    @Override
    public int getItemCount() {
        return listOfChores.size();
    }
}