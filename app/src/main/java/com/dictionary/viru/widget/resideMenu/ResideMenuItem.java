package com.dictionary.viru.widget.resideMenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dictionary.viru.NextDictUtils.Utils;
import com.dictionary.viru.R;
import com.dictionary.viru.configuration.Configruation;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;



/**
 * Created by Nguyen Duc Manh on 8/12/2015.
 */
public class ResideMenuItem extends LinearLayout {
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_HEADER = 2;
    /**
     * menu item  icon
     */
    private ImageView iv_icon;
    /**
     * menu item  title
     */
    private TextView tv_title;
    private CircularImageView iv_Avatar;
    private LinearLayout lnMenu;

    public ResideMenuItem(Context context, int TYPE) {
        super(context);
        initViews(context, TYPE);
    }

    public ResideMenuItem(Context context, int icon, int title, int TYPE) {
        super(context);
        initViews(context, TYPE);
        if (TYPE == TYPE_ITEM)
            iv_icon.setImageResource(icon);
        tv_title.setText(title);
    }

    public ResideMenuItem(Context context, int icon, String title, int TYPE) {
        super(context);
        initViews(context, TYPE);
        if (TYPE == TYPE_ITEM)
            iv_icon.setImageResource(icon);

        tv_title.setText(title);
    }

    private void initViews(Context context, int TYPE) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (TYPE == TYPE_ITEM) {
            inflater.inflate(R.layout.residemenu_item, this);
            iv_icon = (ImageView) findViewById(R.id.iv_icon);
            lnMenu = (LinearLayout) findViewById(R.id.lnMenu);
//            lnMenu.setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            lnMenu.setBackgroundColor(getResources().getColor(R.color.item_click));
//                            break;
//                        case MotionEvent.ACTION_CANCEL:
//                            lnMenu.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            lnMenu.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                            break;
//                    }
//                    return true;
//                }
//            });
        } else if (TYPE == TYPE_HEADER) {
            inflater.inflate(R.layout.avatarmenu_item, this);
            iv_Avatar = (CircularImageView) findViewById(R.id.iv_icon);
        }

        tv_title = (TextView) findViewById(R.id.tv_title);
    }


    public void setAvatar(String id) {
//        Picasso.with(getContext()).load(Utils.getUrlFacebookUserAvatar(id)).placeholder(R.mipmap.avt_default).error(R.mipmap.avt_default).into(iv_Avatar);
        GetAvatar getAvatar = new GetAvatar(id);
        getAvatar.execute();
    }

    public void setAvatar(int image) {
        iv_Avatar.setImageResource(image);
    }

    public void setAvatarUrl(String url) {


    }

    public void setName(String name) {
        tv_title.setText(name);
    }

    private class GetAvatar extends AsyncTask<String, String, String> {
        private String id;

        public GetAvatar(String id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return Utils.getUrlFacebookUserAvatar(id);
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);
            Picasso.with(getContext()).load(url).placeholder(R.mipmap.avt_default).error(R.mipmap.avt_default).into(iv_Avatar);
            SharedPreferences.Editor editor = getContext().getSharedPreferences(Configruation.Pref, Context.MODE_PRIVATE).edit();
            editor.putString(Configruation.KEY_AVATAR, url).apply();

        }
    }

    /**
     * set the icon color;
     *
     * @param icon
     */
    public void setIcon(int icon) {
        iv_icon.setImageResource(icon);
    }

    /**
     * set the title with resource
     * ;
     *
     * @param title
     */
    public void setTitle(int title) {
        tv_title.setText(title);
    }

    /**
     * set the title with string;
     *
     * @param title
     */
    public void setTitle(String title) {
        tv_title.setText(title);
    }
}
