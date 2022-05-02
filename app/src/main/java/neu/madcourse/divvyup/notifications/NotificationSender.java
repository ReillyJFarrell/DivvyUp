package neu.madcourse.divvyup.notifications;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationSender {

    private final String CHANNEL_ID = "group_notif_channel";
    private Activity activity;
    public NotificationSender(Activity activity) {
        this.activity = activity;

    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Notification Name",
                            NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("group channel description");
            NotificationManager notificationManager = this.activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String groupName, String choreName, Integer icon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID);
        String contentTitle = groupName;
        builder.setContentTitle(contentTitle);
        builder.setContentText("Progress for " + choreName + " updated!");
        builder.setSmallIcon(icon);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this.activity);
        notificationManagerCompat.notify(7, builder.build());
    }
}
