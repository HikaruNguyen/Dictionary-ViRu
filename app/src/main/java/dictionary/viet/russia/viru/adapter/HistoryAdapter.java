package dictionary.viet.russia.viru.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dictionary.viet.russia.viru.NextDictUtils.CLog;
import dictionary.viet.russia.viru.R;
import dictionary.viet.russia.viru.activity.MeaningWordActivity;
import dictionary.viet.russia.viru.configuration.Configruation;
import dictionary.viet.russia.viru.database.FavoriteDatabase;
import dictionary.viet.russia.viru.database.HistoryDatabase;
import dictionary.viet.russia.viru.model.db.DictWord;

/**
 * Created by manhi on 20/1/2016.
 */
public class HistoryAdapter extends BaseRecyclerAdapter<DictWord, HistoryAdapter.ViewHolder> {
    public static final String TAG = HistoryAdapter.class.getSimpleName();
    public int type;
    public Context context;

    public HistoryAdapter(Context context, List<DictWord> data, int type) {
        super(context, data);
        this.type = type;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(list.get(position));
        if (type == Configruation.HOME_FAVORITE) {
            holder.imgFavorite.setVisibility(View.VISIBLE);
            holder.imgFavorite.setImageResource(R.mipmap.ic_favorite_active);
        } else {
            holder.imgFavorite.setVisibility(View.VISIBLE);
            holder.imgFavorite.setImageResource(R.mipmap.ic_delete_search);
        }
        final int pos = position;
        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLog.d(TAG, "position: " + pos);
                if (type == Configruation.HOME_FAVORITE) {
                    FavoriteDatabase favoriteDatabase = new FavoriteDatabase(mContext);
                    favoriteDatabase.open();
                    favoriteDatabase.delFavroritebyWord(list.get(pos));
                    favoriteDatabase.close();
                    remove(pos);
                    notifyDataSetChanged();
                } else {
                    HistoryDatabase historyDatabase = new HistoryDatabase(mContext);
                    historyDatabase.open();
                    historyDatabase.delHistorybyWord(list.get(pos));
                    historyDatabase.close();
                    remove(pos);
                    notifyDataSetChanged();
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MeaningWordActivity.class);
                intent.putExtra("word", list.get(pos).getWord());
                context.startActivity(intent);
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvWord, tvSpeek;
        private ImageView imgFavorite;

        public ViewHolder(View view) {
            super(view);
            tvWord = (TextView) itemView.findViewById(R.id.tvWord);
            tvSpeek = (TextView) itemView.findViewById(R.id.tvSpeek);
            imgFavorite = (ImageView) itemView.findViewById(R.id.imgFavorite);

        }

        public void bindData(DictWord dictWord) {
            tvWord.setText(dictWord.getWord());
        }
    }
}
