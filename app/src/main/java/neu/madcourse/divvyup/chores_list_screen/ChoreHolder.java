package neu.madcourse.divvyup.chores_list_screen;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import neu.madcourse.divvyup.R;

public class ChoreHolder extends RecyclerView.ViewHolder {
    public TextView assignedUser;
    public TextView task;
    public TextView dueDate;

    public ChoreHolder(View itemView, final ChoreCardClickListener listener) {
        super(itemView);
        assignedUser = itemView.findViewById(R.id.idAssignedUser);
        task = itemView.findViewById(R.id.idTask);
        dueDate = itemView.findViewById(R.id.idDueDate);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }


}
