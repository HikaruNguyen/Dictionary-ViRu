package dictionary.vietnamese.tudien.viet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import dictionary.vietnamese.tudien.viet.NextDictUtils.CLog;
import dictionary.vietnamese.tudien.viet.R;
import dictionary.vietnamese.tudien.viet.configuration.Configruation;
import dictionary.vietnamese.tudien.viet.event.ChangeMenuEvent;


/**
 * Created by Nguyen Duc Manh on 17/8/2015.
 */
public class BaseFragment extends Fragment {
    protected AppCompatActivity activity;
    protected Context mcontext;
    public FloatingActionButton fab;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (AppCompatActivity) activity;
        this.mcontext = activity;
//        getFap();
    }

    public void getFap(View view) {
        try {
            fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CLog.d("", "click fap");
                    EventBus.getDefault().post(new ChangeMenuEvent(Configruation.HOME_SEARCH_WORD,true));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        activity = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void ToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void ToastErrorConnect(Context context) {
        Toast.makeText(context, context.getString(R.string.error_connect), Toast.LENGTH_SHORT).show();
    }


}
