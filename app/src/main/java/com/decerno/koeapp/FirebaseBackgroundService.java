package com.decerno.koeapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by jear on 2015-01-20.
 */
public class FirebaseBackgroundService extends Service{

    private Firebase firebase;
    private ValueEventListener handler;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        handler = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot == null || dataSnapshot.getValue() == null){
                    return;
                }
//                try {
//                    String value = dataSnapshot.getValue().toString();
//                    JSONObject json = new JSONObject(value);
//                    List<QueueMessage> messages = new LinkedList<>();
//                    Iterator<String> iterator = json.keys();
//                    while(iterator.hasNext()){
//                        JSONObject JSONmessage = json.getJSONObject(iterator.next());
//                        messages.add(new QueueMessage(JSONmessage.getString("name"), JSONmessage.getString("comment"), JSONmessage.getString("time")));
//                    }
//                    if(messages.isEmpty()){
//                        return;
//                    }
                    //QueueMessage lastMessage = messages.get(messages.size()-1);
                    //postNotif(lastMessage.getTitle(), lastMessage.getMessage());
                    postNotification("Nån köade just", "Tryck här för att kolla in vem");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        Firebase.setAndroidContext(getApplicationContext());
        firebase = new Firebase("https://sweltering-fire-681.firebaseio.com/koe/Jonathan");
        firebase.addValueEventListener(handler);
    }


    private void postNotification(String title, String message) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;
        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, WebActivity.class);
        notificationIntent.putExtra("notification", message);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(),0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle(title+" Köade just");
        builder.setContentText(message);
        builder.setContentIntent(pendingNotificationIntent);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        Random r = new Random();
        mNotificationManager.notify(r.nextInt(10000), notification);
    }

    private class QueueMessage {

        private final String title;
        private final String message;
        private String time;


        public QueueMessage(String title, String message, String time){
            this.title = title;
            this.message = message;
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }

        public String getTime() {
            return time;
        }
    }

}
