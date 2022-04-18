package neu.madcourse.divvyup.recap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import neu.madcourse.divvyup.R;


public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {

    private final ArrayList<ChatCard> listOfChats;
    private chatMessageClickListener listener;

    public ChatAdapter(ArrayList<ChatCard> listOfChats) {
        this.listOfChats = listOfChats;
    }

    public void setOnLinkClickListener(chatMessageClickListener listener) {
        this.listener = listener;
    }


    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_card_item, parent, false);
        return new ChatHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        ChatCard currentChatMessage = listOfChats.get(position);
        holder.userName.setText(currentChatMessage.getUserName());
        holder.sticker.setImageResource(currentChatMessage.getSticker());
    }

    @Override
    public int getItemCount() {
        return listOfChats.size();
    }
}
