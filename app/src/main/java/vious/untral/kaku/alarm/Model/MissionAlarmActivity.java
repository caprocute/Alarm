package vious.untral.kaku.alarm.Model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ryan.rv_gallery.GalleryRecyclerView;

import java.util.ArrayList;

import vious.untral.kaku.alarm.R;

public class MissionAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_alarm);
        GalleryRecyclerView mRecyclerView = findViewById(R.id.rv_list);
        ArrayList<String> data = new ArrayList<>();

        data.add(getString(R.string.Default));
        data.add(getString(R.string.Math));
        data.add(getString(R.string.Picture));
        data.add(getString(R.string.Scan));
        data.add(getString(R.string.Shake));

       /* MissionAdapter adapter = new MissionAdapter(getApplicationContext(), data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);*/
    }
}
