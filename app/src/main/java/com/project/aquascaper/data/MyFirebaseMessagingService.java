//package com.project.aquascaper.data;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.project.aquascaper.MainActivity;
//import com.project.aquascaper.R;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Locale;
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
//    private Context context;
//
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        if(remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
//        }
//
//        if(remoteMessage.getNotification() != null) {
//            SharedPreferences prefs = getSharedPreferences("NOTIFICATION", Context.MODE_PRIVATE);
//            boolean isNotificationEnable = prefs.getBoolean("notification", false);
//            if(isNotificationEnable) {
//                sendNotification(remoteMessage.getNotification().getBody());
//            }
//
//            saveNotifToDatabase(remoteMessage.getNotification().getBody());
//        }
//    }
//
//    private void saveNotifToDatabase(String body) {
//        long timeInMillis = System.currentTimeMillis();
//        // Create a DateFormatter object for displaying date in specified format.
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        // Create a calendar object that will convert the date and time value in milliseconds to date.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(timeInMillis);
//        String hour = formatter.format(calendar.getTime());
//
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("notification/"+ timeInMillis);
//        ref.child("notification").setValue(body);
//        ref.child("hour").setValue(hour);
//    }
//
//    @Override
//    public void onNewToken(@NonNull String token) {
//        Log.e(TAG, token);
//    }
//
//
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_IMMUTABLE);
//
//        String channelId = getString(R.string.app_name);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Aquascaper")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId, "Aquascaper", NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0, notificationBuilder.build());
//    }
//
//
//}
