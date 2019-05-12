package foodget.ihm.foodget.services;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.activities.LoginActivity;

public class NotificationService extends Service {
    private static NotificationManagerCompat mNotificationManager;
    private static NotificationCompat.Builder notifDepenses = null;
    private WeakReference<Context> context;
    private Date next = new Date();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = new WeakReference<>(getBaseContext());
        //currentUser = (Person) intent.getExtras().get("ACTIVE_PROFILE");
        mNotificationManager = NotificationManagerCompat.from(context.get());
        createNotificationChannel();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new NotificationTask(), 0, 500);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceTask = new Intent(getApplicationContext(), this.getClass());
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (myAlarmService != null)
            myAlarmService.set(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + 500,
                    restartPendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    private void createNotificationDepenses() {

        Intent dismissIntent = new Intent(this, BroadcastCloseNotification.class);
        dismissIntent.setAction("fermer");
        dismissIntent.putExtra("ID", 1);
        PendingIntent dismissPendingIntent =
                PendingIntent.getBroadcast(this, 1, dismissIntent, 0);


        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, LoginActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);


        notifDepenses = new NotificationCompat.Builder(context.get(), "channel_notif_id_meal")
                .setContentTitle("FOODGET: Dépenses quotidiennes")
                .setContentText("Avez-vous pensé à saisir vos dépenses d'aujourd'hui?")
                .setSmallIcon(R.drawable.foodget)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDeleteIntent(getDeleteIntent())
                .addAction(R.drawable.account_icon, "FERMER", dismissPendingIntent)
                .setContentIntent(resultPendingIntent);

        Log.d("APP LAUNCHER", "je suis là");
        mNotificationManager.notify(1, notifDepenses.build());
    }


    protected PendingIntent getDeleteIntent() {
        Intent intent = new Intent(this, BroadcastCloseNotification.class);
        intent.setAction("notification_cancelled");
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_notif_depenses";
            String description = "channel_notif_depenses";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channel_notif_depenses", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.get().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private class NotificationTask extends TimerTask {
        public void run() {
            Date now = new Date();
            long tmp = next.getTime() + 60000;
            if (now.getTime() > tmp - 500 && now.getTime() < tmp + 500) {
                createNotificationDepenses();
            }
        }
    }
}
