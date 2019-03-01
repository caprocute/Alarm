package vious.untral.kaku.alarm.Tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import vious.untral.kaku.alarm.R;

public class Unitls {
    public static final int PICKER_RINGTONE = 1;
    public static boolean[] weekkenddays = new boolean[]{false, false, false, false, false, true, true};
    public static boolean[] weekdays = new boolean[]{true, true, true, true, true, false, false};
    public static boolean[] everyday = new boolean[]{true, true, true, true, true, true, true};

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
}
