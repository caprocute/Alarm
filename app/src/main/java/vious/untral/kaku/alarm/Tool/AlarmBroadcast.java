package vious.untral.kaku.alarm.Tool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.util.Log;
import android.widget.Toast;

import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.UI.AlarmScreenActivity;

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        byte[] bytes = intent.getByteArrayExtra("alarm");
        Parcel alarmParcel = ParcelableUtil.unmarshall(bytes);
        Alarm alarm = Alarm.CREATOR.createFromParcel(alarmParcel);

        Toast.makeText(context, "GAU GAU GAU", Toast.LENGTH_SHORT).show();
        if (alarm != null)
            context.startActivity(new Intent(context, AlarmScreenActivity.class)
                    .putExtra("alarm", alarm)
                    .putExtra("isdemo", true));
    }
}
