package com.dictionary.viru.service.standOut;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dictionary.viru.R;
import com.dictionary.viru.activity.MainActivity;
import com.dictionary.viru.configuration.StandOutFlags;
import com.dictionary.viru.NextDictUtils.CLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by manhi on 30/1/2016.
 */
public class MultiWindow extends StandOutWindow {

    @Override
    public String getAppName() {
        return "MultiWindow";
    }

    @Override
    public int getAppIcon() {
        return android.R.drawable.ic_menu_add;
    }

    @Override
    public String getTitle(int id) {
        return getAppName();
    }

    @Override
    public void createAndAttachView(int id, FrameLayout frame) {
        // create a new layout from body.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.body, frame, true);

        TextView idText = (TextView) view.findViewById(R.id.id);
        idText.setText(String.valueOf(id));
    }

    // every window is initially same size
    @Override
    public StandOutLayoutParams getParams(int id, Window window) {
        return new StandOutLayoutParams(id, 400, 300,
                StandOutLayoutParams.AUTO_POSITION,
                StandOutLayoutParams.AUTO_POSITION, 100, 100);
    }

    // we want the system window decorations, we want to drag the body, we want
    // the ability to hide windows, and we want to tap the window to bring to
    // front
    @Override
    public int getFlags(int id) {
        return StandOutFlags.FLAG_DECORATION_SYSTEM
                | StandOutFlags.FLAG_BODY_MOVE_ENABLE
                | StandOutFlags.FLAG_WINDOW_HIDE_ENABLE
                | StandOutFlags.FLAG_WINDOW_BRING_TO_FRONT_ON_TAP
                | StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE
                | StandOutFlags.FLAG_WINDOW_PINCH_RESIZE_ENABLE;
    }

//    @Override
//    public String getPersistentNotificationTitle(int id) {
//        return getAppName() + " Running";
//    }

//    @Override
//    public String getPersistentNotificationMessage(int id) {
//        return getString(R.string.ClickToAddAnew) + getAppName();
//    }

    // return an Intent that creates a new MultiWindow
    @Override
    public Intent getPersistentNotificationIntent(int id) {
        return StandOutWindow.getShowIntent(this, getClass(), getUniqueId());
    }

    @Override
    public int getHiddenIcon() {
        return android.R.drawable.ic_menu_info_details;
    }

//    @Override
//    public String getHiddenNotificationTitle(int id) {
//        return getAppName() + getString(R.string.Hidden);
//    }

//    @Override
//    public String getHiddenNotificationMessage(int id) {
//        return getResources().getString(R.string.clickToRestore) + "#" + id;
//    }

    // return an Intent that restores the MultiWindow
    @Override
    public Intent getHiddenNotificationIntent(int id) {
        return StandOutWindow.getShowIntent(this, getClass(), id);
    }

    @Override
    public Animation getShowAnimation(int id) {
        if (isExistingId(id)) {
            // restore
            return AnimationUtils.loadAnimation(this,
                    android.R.anim.slide_in_left);
        } else {
            // show
            return super.getShowAnimation(id);
        }
    }

    @Override
    public Animation getHideAnimation(int id) {
        return AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
    }

    @Override
    public List<DropDownListItem> getDropDownItems(int id) {
        List<DropDownListItem> items = new ArrayList<DropDownListItem>();
        items.add(new DropDownListItem(R.mipmap.ic_launcher,
                getString(R.string.openNextDict)+" "+getString(R.string.app_name), new Runnable() {

            @Override
            public void run() {
//                Toast.makeText(
//                        MultiWindow.this,
//                        getAppName()
//                                + " is a demonstration of StandOut.",
//                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MultiWindow.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }));
//        items.add(new DropDownListItem(R.mipmap.ic_zoom,
//                getString(R.string.zoomout), new Runnable() {
//
//            @Override
//            public void run() {
//                EventBus.getDefault().post(new ClickMenuPopupEvent(1));
//            }
//        }));
//        items.add(new DropDownListItem(android.R.drawable.ic_menu_preferences,
//                "Settings", new Runnable() {
//
//            @Override
//            public void run() {
//                Toast.makeText(MultiWindow.this,
//                        "There are no settings.", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }));
        return items;
    }

    @Override
    public void onReceiveData(int id, int requestCode, Bundle data,
                              Class<? extends StandOutWindow> fromCls, int fromId) {
        // receive data from WidgetsWindow's button press
        // to show off the data sending framework
        switch (requestCode) {
            case WidgetsWindow.DATA_CHANGED_TEXT:
                Window window = getWindow(id);
                if (window == null) {
                    String errorText = String.format(Locale.US,
                            "%s received data but Window id: %d is not open.",
                            getAppName(), id);
                    Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show();
                    return;
                }
                String changedText = data.getString("changedText");
                TextView status = (TextView) window.findViewById(R.id.id);
                status.setTextSize(20);
                status.setText("Received data from WidgetsWindow: "
                        + changedText);
                break;
            default:
                CLog.d("MultiWindow", "Unexpected data received.");
                break;
        }
    }
}
