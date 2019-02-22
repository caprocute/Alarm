package vious.untral.kaku.alarm.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vious.untral.kaku.alarm.Model.Alarm;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Alarm> ITEMS = new ArrayList<Alarm>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Alarm> ITEM_MAP = new HashMap<String, Alarm>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createAlarm(i));
        }
    }

    private static void addItem(Alarm item) {
        ITEMS.add(item);
        ITEM_MAP.put("data", item);
    }

    private static Alarm createAlarm(int position) {
        return new Alarm();
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */

}
