package vious.untral.kaku.alarm.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import vious.untral.kaku.alarm.Adapter.MyAlarmRecyclerViewAdapter;
import vious.untral.kaku.alarm.Model.Alarm;
import vious.untral.kaku.alarm.R;
import vious.untral.kaku.alarm.Tool.MyDatabaseHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AlarmFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AlarmFragment newInstance(int columnCount) {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    private RecyclerView recyclerView;
    private MyAlarmRecyclerViewAdapter myAlarmRecyclerViewAdapter;
    private Context mContext;
    private List<Alarm> adapterData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            mContext = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));
            }

            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(mContext);
            myDatabaseHelper.createDefaultAlarmsIfNeed();
            adapterData = myDatabaseHelper.getAllAlarms();

            myAlarmRecyclerViewAdapter = new MyAlarmRecyclerViewAdapter(adapterData, mListener, getActivity());
            recyclerView.setAdapter(myAlarmRecyclerViewAdapter);
            recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }


    public boolean deleteAlarm(int postion) {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(mContext);

        Alarm alarm = adapterData.get(postion);
        myDatabaseHelper.deleteAlarm(alarm);
        adapterData.remove(postion);
        myAlarmRecyclerViewAdapter.notifyDataSetChanged();

        return true;
    }

    public boolean updateAlarm(int postion, Alarm alarm) {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(mContext);
        myDatabaseHelper.updateAlarm(alarm);

        adapterData.set(postion, alarm);
        myAlarmRecyclerViewAdapter.notifyDataSetChanged();
        return true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void addAlarm(Alarm mAlarm) {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(mContext);
        myDatabaseHelper.addAlarm(mAlarm);

        adapterData.add(mAlarm);
        myAlarmRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Alarm item);
    }
}
