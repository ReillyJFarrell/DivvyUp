package neu.madcourse.divvyup.recap;

public class ChatCard implements chatMessageClickListener {
    private String userName;
    private Integer sticker;

    public ChatCard(String userName, Integer sticker){
        this.userName = userName;
        this.sticker = sticker;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getSticker() {
        return sticker;
    }


    @Override
    public void onItemClick(int position) {
    }
}
