package foodget.ihm.foodget.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BroadcastCloseNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("APP RECEIVER", "I am here");
        if (action != null && action.equals("fermer")) {
            int notificationId = intent.getIntExtra("ID", -1);
            if (notificationId > 0) {
                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.cancel(notificationId);
            }

        }

    }

}
