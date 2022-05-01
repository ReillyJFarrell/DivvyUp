package neu.madcourse.divvyup.recap;

import static java.lang.Integer.parseInt;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neu.madcourse.divvyup.R;


public class RecapAdapter extends RecyclerView.Adapter<RecapHolder> {

    private final ArrayList<RecapCard> listOfChoreRecaps;
    private recapClickListener listener;

    public RecapAdapter(ArrayList<RecapCard> listOfChoreRecaps) {
        this.listOfChoreRecaps = listOfChoreRecaps;
    }

    public void setOnLinkClickListener(recapClickListener listener) {
        this.listener = listener;
    }


    @Override
    public RecapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recap_chore_item, parent, false);
        return new RecapHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecapHolder holder, int position) {
        RecapCard currentRecapItem = listOfChoreRecaps.get(position);
        switch (currentRecapItem.getStatus()){
            case -1: break;
            case 0: {
                holder.statImage.setImageResource(R.drawable.small_x);
                break;
            } case 1: {
                holder.statImage.setImageResource(R.drawable.small_wip);
                break;
            } case 2: {
                holder.statImage.setImageResource(R.drawable.small_done);
                break;
            }
        }

        List<String> daysText = Arrays.asList("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su");

        int ii = 0;
        List<TextView> days = Arrays.asList(holder.Mon, holder.Tues, holder.Wed, holder.Thurs, holder.Fri, holder.Sat, holder.Sun);
        for (Integer  day: listOfChoreRecaps.get(position).getDayStatus())
        {
            int compVal = day;
            days.get(ii).setText(daysText.get(ii));
//            days.get(ii).setTextColor(Color.parseColor("#8c8887"));
            switch (compVal){
                case -1: {
                    days.get(ii).setTextColor(Color.parseColor("#ddd8d8"));
                    break;
                } case 0: {
                    days.get(ii).setTextColor(Color.parseColor("#f90b08"));
                    break;
                } case 1: {
                    days.get(ii).setTextColor(Color.parseColor("#f58405"));
                    break;
                } case 2: {
                    days.get(ii).setTextColor(Color.parseColor("#30e82a"));
                    break;
                }
//                default: break;
            }
            ii ++;
        };
        holder.choreName.setText(currentRecapItem.getChoreName());
        holder.user.setText(currentRecapItem.getAssignedUser());
    }

    @Override
    public int getItemCount() {
        return listOfChoreRecaps.size();
    }
}
