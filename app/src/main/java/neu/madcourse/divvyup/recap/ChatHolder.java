package neu.madcourse.divvyup.recap;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ChatHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public ImageView sticker;

    public ChatHolder(View itemView, final chatMessageClickListener listener) {
        super(itemView);
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
