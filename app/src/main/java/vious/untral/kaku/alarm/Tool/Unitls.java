package vious.untral.kaku.alarm.Tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.Timer;
import java.util.TimerTask;

import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.UI.AlarmDetailActivity;

import static android.content.Context.ALARM_SERVICE;

public class Unitls {
    public static final int PICKER_RINGTONE = 1;
    public static final int MISSION_DEFAULT = 0;
    public static final int MISSION_CAMERA = 1;
    public static final int MISSION_SHAKE = 2;
    public static final int MISSION_CAL = 3;
    public static final int MISSION_QR = 4;
    public static boolean[] weekkenddays = new boolean[]{false, false, false, false, false, true, true};
    public static boolean[] weekdays = new boolean[]{true, true, true, true, true, false, false};
    public static boolean[] everyday = new boolean[]{true, true, true, true, true, true, true};
    private static final String TAG = "HIEUHK";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.color.witching_hour1);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    private static String INTENT_ACTION = "vious.untral.kaku.alarm.action";
    private static String INTENT_CATELOGY = "vious.untral.kaku.alarm.action.category";

    private static String createFakeType() {

        return "vious.untral.kaku.alarm.dataType";

    }

    private static Uri createFakeURI(int senderRequestCode) {

        return Uri.parse("content://vious.untral.kaku/Alarms/" + senderRequestCode);

    }

    public static void cancelAlarm(Context activity, Alarm alarm) {
        Context context = activity;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcast.class);

        intent.setAction(INTENT_ACTION);

        intent.addCategory(INTENT_CATELOGY);

        intent.setDataAndType(createFakeURI(alarm.getId()), createFakeType());//we created a fake uri

        Log.d(TAG, "cancelAlarm: cancel request " + alarm.getId());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.cancel(alarmIntent);
    }

    public static void setAlarmFromNow(Activity activity, Alarm alarm) {
        int hour = 5;

        Intent i = new Intent(activity, AlarmBroadcast.class);

        i.setAction(INTENT_ACTION);

        i.addCategory("vious.untral.kaku.alarm.action.category");

        i.setDataAndType(createFakeURI(alarm.getId()), createFakeType());//we created a fake uri

        i.putExtra("alarm", ParcelableUtil.marshall(alarm));

        Log.d(TAG, "setAlarmFromNow: set " + alarm.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity.getApplicationContext(), alarm.getId(), i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + hour * 1000, pendingIntent);
    }

    public static String calTimeRemain(Context context, final int hour, final int minute, final boolean[] repeat) {

        DateTime now = DateTime.now();

        DateTime alarmDate = new DateTime(now.year().get(), now.monthOfYear().get(), now.dayOfMonth().get(), hour, minute);

        boolean flagStop = true;

        String update = "";

        while (flagStop) {
            for (int i = 0; i < repeat.length; i++) {
                if (repeat[i] == true) {
                    DateTime nextDay = null;
                    switch (i) {
                        case 0:
                            nextDay = alarmDate.withDayOfWeek(DateTimeConstants.MONDAY);
                            break;
                        case 1:
                            nextDay = alarmDate.withDayOfWeek(DateTimeConstants.TUESDAY);
                            break;
                        case 2:
                            nextDay = alarmDate.withDayOfWeek(DateTimeConstants.WEDNESDAY);
                            break;
                        case 3:
                            nextDay = alarmDate.withDayOfWeek(DateTimeConstants.THURSDAY);
                            break;
                        case 4:
                            nextDay = alarmDate.withDayOfWeek(DateTimeConstants.FRIDAY);
                            break;
                        case 5:
                            nextDay = alarmDate.withDayOfWeek(DateTimeConstants.SATURDAY);
                            break;
                        case 6:
                            nextDay = alarmDate.withDayOfWeek(DateTimeConstants.SUNDAY);
                            break;
                    }

                    if (nextDay.getMillis() > now.getMillis()) {
                        long durationBetweenDate = nextDay.getMillis() - now.getMillis();
                        long secondsCAL = durationBetweenDate / 1000;
                        long minutesCAL = secondsCAL / 60;
                        long hoursCAL = minutesCAL / 60;
                        long daysCAL = hoursCAL / 24;

                        update = context.getString(R.string.remain_part1);

                        if (daysCAL != 0)
                            update += " " + daysCAL + " " + context.getString(R.string.days);
                        if ((hoursCAL - daysCAL * 24) != 0)
                            update += " " + (hoursCAL - daysCAL * 24) + " " + context.getString(R.string.hours);
                        if ((minutesCAL - (hoursCAL - daysCAL * 24) * 60 - daysCAL * 60 * 24) != 0)
                            update += " " + (minutesCAL - (hoursCAL - daysCAL * 24) * 60 - daysCAL * 60 * 24) + " " + context.getString(R.string.minutes);
                        flagStop = false;
                        break;
                    }
                }
            }
            if (flagStop) alarmDate = alarmDate.plusWeeks(1);
        }
        return update;
    }
}
