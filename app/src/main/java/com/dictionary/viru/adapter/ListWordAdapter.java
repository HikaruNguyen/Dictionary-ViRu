package com.dictionary.viru.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dictionary.viru.R;
import com.dictionary.viru.model.db.DictWord;

import java.util.List;

/**
 * Created by manhi on 20/1/2016.
 */
public class ListWordAdapter extends BaseRecyclerAdapter<DictWord, ListWordAdapter.ViewHolder> {

    public ListWordAdapter(Context context, List<DictWord> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_search_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(list.get(position));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWord;

        public ViewHolder(View view) {
            super(view);
            tvWord = (TextView) itemView.findViewById(R.id.tvWord);
            itemView.setClickable(true);
        }

        public void bindData(DictWord dictWord) {
            tvWord.setText(dictWord.getWord());
        }


    }
}
