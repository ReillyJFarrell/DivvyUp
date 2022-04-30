package neu.madcourse.divvyup.chores_list_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

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
        holder.assignedUser.setText(currentChore.getAssignedUserId());
        String date = currentChore.getRepeatedDay().name();
        holder.dueDate.setText(date);
        holder.task.setText(currentChore.getTitle());
    }

    @Override
    public int getItemCount() {
        return listOfChores.size();
    }
}
