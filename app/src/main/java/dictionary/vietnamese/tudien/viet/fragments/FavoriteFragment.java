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
import dictionary.vietnamese.tudien.viet.database.FavoriteDatabase;
import dictionary.vietnamese.tudien.viet.event.clickEvent.ClickClearMenuEvent;
import dictionary.vietnamese.tudien.viet.model.db.DictWord;
import dictionary.vietnamese.tudien.viet.widget.DividerItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends BaseFragment {
    private static final String TAG = FavoriteFragment.class.getSimpleName();
    private RecyclerView rvHistory;
    private ProgressWheel prbLoading;
    private HistoryAdapter adapter;
    private FavoriteDatabase favoriteDatabase;
    private ArrayList<String> words;

    public FavoriteFragment() {
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
        favoriteDatabase = new FavoriteDatabase(activity);
        favoriteDatabase.open();
        List<DictWord> arr = favoriteDatabase.getAll();
        adapter.addAll(arr);
        prbLoading.setVisibility(View.GONE);
        favoriteDatabase.close();
        words = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            words.add(adapter.getItembyPostion(i).getWord());
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
        adapter = new HistoryAdapter(activity, new ArrayList<DictWord>(), Configruation.HOME_FAVORITE);
        rvHistory.setAdapter(adapter);

        prbLoading = (ProgressWheel) view.findViewById(R.id.prbLoading);
        prbLoading.setVisibility(View.VISIBLE);
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
        if (event.type == Configruation.HOME_FAVORITE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(getString(R.string.confirmDelFavorite));
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
                    favoriteDatabase.open();
                    favoriteDatabase.deleteAll();
                    favoriteDatabase.close();
                }
            });
            builder.show();
        }
    }
}
