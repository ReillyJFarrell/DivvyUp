package neu.madcourse.divvyup.recap;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import neu.madcourse.divvyup.R;

public class RecapHolder extends RecyclerView.ViewHolder {
    public TextView choreName;
    public TextView Mon;
    public TextView Tues;
    public TextView Wed;
    public TextView Thurs;
    public TextView Fri;
    public TextView Sat;
    public TextView Sun;
    public ImageView statImage;
    public TextView user;

    public RecapHolder(View itemView, final recapClickListener listener) {
        super(itemView);
        choreName = itemView.findViewById(R.id.choreNameRecap);
        Mon = itemView.findViewById(R.id.monday);
        Tues = itemView.findViewById(R.id.tuesday);
        Wed = itemView.findViewById(R.id.wednesday);
        Thurs = itemView.findViewById(R.id.thursday);
        Fri = itemView.findViewById(R.id.friday);
        Sat = itemView.findViewById(R.id.saturday);
        Sun = itemView.findViewById(R.id.sunday);
        statImage = itemView.findViewById(R.id.progImage);
        user = itemView.findViewById(R.id.assignedUserRecap);



//        userName = itemView.findViewById(R.id.chatMessageUser);
//        sticker = itemView.findViewById(R.id.stickerImage);

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
