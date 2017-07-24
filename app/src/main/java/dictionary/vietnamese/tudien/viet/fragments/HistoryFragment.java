package dictionary.vietnamese.tudien.viet.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import dictionary.vietnamese.tudien.viet.R;
import dictionary.vietnamese.tudien.viet.adapter.HistoryAdapter;
import dictionary.vietnamese.tudien.viet.configuration.Configruation;
import dictionary.vietnamese.tudien.viet.database.HistoryDatabase;
import dictionary.vietnamese.tudien.viet.event.clickEvent.ClickClearMenuEvent;
import dictionary.vietnamese.tudien.viet.model.db.DictWord;
import dictionary.vietnamese.tudien.viet.widget.DividerItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BaseFragment {
    private RecyclerView rvHistory;
    private ProgressWheel prbLoading;
    private HistoryAdapter adapter;
    private HistoryDatabase historyDatabase;
//    private FloatingActionButton fab;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        InitUI(view);
        InitData();
        return view;
    }

    private void InitData() {
        historyDatabase = new HistoryDatabase(activity);
        historyDatabase.open();
        List<DictWord> arr = historyDatabase.getAll();
        adapter.addAll(arr);
        prbLoading.setVisibility(View.GONE);
        historyDatabase.close();
        checkErrorView();
    }

    private void checkErrorView() {
        if (adapter.getItemCount() <= 0) {

        }
    }

    private void InitUI(View view) {
        getFap(view);
        rvHistory = (RecyclerView) view.findViewById(R.id.rvHistory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setHasFixedSize(true);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        adapter = new HistoryAdapter(activity, new ArrayList<DictWord>(), Configruation.HOME_HISTORY);
        rvHistory.setAdapter(adapter);

        prbLoading = (ProgressWheel) view.findViewById(R.id.prbLoading);
        prbLoading.setVisibility(View.VISIBLE);
//        fab = (FloatingActionButton) view.findViewById(R.id.fab);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEvent(ClickClearMenuEvent event) {
        if (event.type == Configruation.HOME_HISTORY) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(getString(R.string.confirmDelHistory));
            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.clear();
                    historyDatabase.open();
                    historyDatabase.deleteAll();
                    historyDatabase.close();
                }
            });
            builder.show();
        }
    }
}
