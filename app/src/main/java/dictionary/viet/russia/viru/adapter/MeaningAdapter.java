package dictionary.viet.russia.viru.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import dictionary.viet.russia.viru.NextDictUtils.AESUtils;
import dictionary.viet.russia.viru.NextDictUtils.CLog;
import dictionary.viet.russia.viru.R;
import dictionary.viet.russia.viru.database.DictInfoDBHelper;
import dictionary.viet.russia.viru.event.clickEvent.ClickVoiceEvent;
import dictionary.viet.russia.viru.model.db.DictInfo;
import dictionary.viet.russia.viru.model.db.DictWordObject;
import dictionary.viet.russia.viru.widget.customeControl.CustomeWebView;

/**
 * Created by manhi on 31/1/2016.
 */
public class MeaningAdapter extends BaseRecyclerAdapter<DictWordObject, MeaningAdapter.ViewHolder> {
    public static String TAG = MeaningAdapter.class.getSimpleName();
    public static int type;
    private Context context;

    public MeaningAdapter(Context context, List<DictWordObject> list, int type) {
        super(context, list);
        this.type = type;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_meaning_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(list.get(position));
        holder.lnUK.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        holder.img_speechUK.setColorFilter(ContextCompat.getColor(context, R.color.dark_alpha));
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        holder.img_speechUK.setColorFilter(Color.TRANSPARENT);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        holder.img_speechUK.setColorFilter(Color.TRANSPARENT);
                        EventBus.getDefault().post(new ClickVoiceEvent(Locale.UK, list.get(position).dictWord.getWord(),type));
                        break;
                }
                return true;
            }
        });
        holder.lnUS.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        holder.img_speechUS.setColorFilter(ContextCompat.getColor(context, R.color.dark_alpha));
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        holder.img_speechUS.setColorFilter(Color.TRANSPARENT);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        holder.img_speechUS.setColorFilter(Color.TRANSPARENT);
                        EventBus.getDefault().post(new ClickVoiceEvent(Locale.US, list.get(position).dictWord.getWord(),type));
                        break;
                }
                return true;
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CustomeWebView webView;
        private ImageButton img_speechUK, img_speechUS;
        private TextView tvWord;
        private LinearLayout lnUK, lnUS;

        public ViewHolder(View view) {
            super(view);
            webView = (CustomeWebView) itemView.findViewById(R.id.webView);
            webView.setType(type);
            img_speechUK = (ImageButton) itemView.findViewById(R.id.img_speechUK);
            img_speechUS = (ImageButton) itemView.findViewById(R.id.img_speechUS);
            lnUK = (LinearLayout) itemView.findViewById(R.id.lnVoiceUK);
            lnUS = (LinearLayout) itemView.findViewById(R.id.lnVoiceUS);
            tvWord = (TextView) itemView.findViewById(R.id.tvWord);
            itemView.setClickable(true);
        }

        public void bindData(DictWordObject dictWord) {
            if (dictWord != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("<HTML><HEAD><LINK href=\"css/result.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
                String info = "";
                info = DictInfoDBHelper.getInfoDict(dictWord.dictId);
                CLog.d(TAG, "dictinfo: " + info.split("\n").length);
                DictInfo dictInfo = new DictInfo();
                int isEncrypted = 0;
                int format_version = 1;
                for (int i = 0; i < info.split("\n").length; i++) {
                    String s = info.split("\n")[i];
                    if (s.trim() != null && !s.isEmpty()) {
                        String s1[] = s.split("=");
                        if (s1[0].trim().equals("format_version")) {
                            dictInfo.format_version = s1[1];
                            try {
                                format_version = Integer.parseInt(dictInfo.format_version);
                            } catch (Exception e) {
                                format_version = 1;
                            }
                        } else if (s1[0].trim().equals("encrypted")) {
                            dictInfo.encrypted = s1[1];
                            try {
                                isEncrypted = Integer.parseInt(dictInfo.encrypted);
                            } catch (Exception e) {
                                isEncrypted = 0;
                            }
                        } else if (s1[0].trim().equals("encryptKey")) {
                            dictInfo.encryptKey = s1[1];
                        }
                    }
                }
                String s = "";
                if (isEncrypted == 1) {
                    if (format_version == 1) {
                        s = dictWord.dictWord.getDefinition().toString();
                        AESUtils aesUtils = new AESUtils();
                        aesUtils.setKey(dictInfo.encryptKey);
                        try {
                            s = aesUtils.decrypt(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        byte[] ss = dictWord.dictWord.getDefinition2();
                        AESUtils aesUtils = new AESUtils();
                        aesUtils.setKey(dictInfo.encryptKey);
                        try {
                            s = aesUtils.decrypt2(ss);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                sb.append(s);
                sb.append("</body></HTML>");
                webView.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "UTF-8", null);
                tvWord.setText(dictWord.dictWord.getWord());
            }
        }


    }


}
