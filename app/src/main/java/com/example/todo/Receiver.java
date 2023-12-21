package com.example.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Receiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        // Create a notification builder and set the title, text, icon.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "taskade_id")
                .setSmallIcon(R.drawable.ic_menu_bell)
                .setContentTitle("Reminder")
                .setContentText("Your event is starting soon")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Log.println(Log.ASSERT, "reciver", "Receiver Received");

        // Create a notification manager and show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(10, builder.build());

        // Get the person's phone number from the intent
        String phoneNumber = intent.getStringExtra("phone_number");
        String eventTime = intent.getStringExtra("event_time");
        // Check if the phone number and event time is returned from the intent
        if(phoneNumber != null && eventTime != null) {
            // Create a SmsManager and send the SMS message
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, "Hey, just a reminder that we have an event at " + eventTime + ". See you soon!", null, null);
        }
    }

}
