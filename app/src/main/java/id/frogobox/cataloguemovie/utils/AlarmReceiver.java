package id.frogobox.cataloguemovie.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * CatalogueMovie
 * Copyright (C) 22/01/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Line     : bullbee117
 * Phone    : 081357108568
 * Majors   : D3 Teknik Informatika 2016
 * Campus   : Telkom University
 * -----------------------------------------
 * id.amirisback.frogobox
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String EXTRA_TITLE = "extra_content_title";
    public static final String EXTRA_TEXT = "extra_content_text";
    public static final String EXTRA_NOTIFICATION_ID = "extra_notification_id";
    public static final String EXTRA_CHANNEL_ID = "extra_channel_id";
    public static final String EXTRA_CHANNEL_NAME = "extra_channel_name";

    private NotificationCall notif = new NotificationCall();

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showAlarmNotification(context, intent);
    }

    private void showAlarmNotification(Context context, Intent intent) {

        String extraTitle = intent.getStringExtra(EXTRA_TITLE);
        String extraText = intent.getStringExtra(EXTRA_TEXT);
        int extraNotificationID = intent.getIntExtra(EXTRA_NOTIFICATION_ID,0);
        String extraChannelID = intent.getStringExtra(EXTRA_CHANNEL_ID);
        CharSequence extraChannelName = intent.getStringExtra(EXTRA_CHANNEL_NAME);

        notif.setID(extraNotificationID, extraChannelID, extraChannelName);
        notif.sendNotification(context, extraTitle, extraText);
    }

    public void setRepeatingAlarm(Context context,
                                  String content_title,
                                  String content_text,
                                  String time,
                                  int notification_id,
                                  String channel_id,
                                  CharSequence channel_name,
                                  int id_repeating) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // -----------------------------------------------------------------------------------------
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TITLE, content_title);
        intent.putExtra(EXTRA_TEXT, content_text);
        intent.putExtra(EXTRA_NOTIFICATION_ID, notification_id);
        intent.putExtra(EXTRA_CHANNEL_ID, channel_id);
        intent.putExtra(EXTRA_CHANNEL_NAME, channel_name);
        // -----------------------------------------------------------------------------------------
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        // -----------------------------------------------------------------------------------------
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id_repeating, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context, int id_repeating) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id_repeating, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

}