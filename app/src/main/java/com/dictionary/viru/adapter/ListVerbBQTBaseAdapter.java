package com.dictionary.viru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dictionary.viru.R;
import com.dictionary.viru.model.db.IrregularVerbObject;

import java.util.List;

/**
 * Created by manhi on 20/1/2016.
 */
public class ListVerbBQTBaseAdapter extends BaseAdapter {
    public Context context;
    public List<IrregularVerbObject> list;

    public ListVerbBQTBaseAdapter(Context context, List<IrregularVerbObject> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_verb_bqt, null);
            viewHolder.tvInFinitive = (TextView) convertView.findViewById(R.id.tvInFinitive);
            viewHolder.tvSimplePast = (TextView) convertView.findViewById(R.id.tvSimplePast);
            viewHolder.tvPastParticiple = (TextView) convertView.findViewById(R.id.tvPastParticiple);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        IrregularVerbObject object = list.get(position);
        if (object != null) {
            viewHolder.tvInFinitive.setText(object.infinitive);
            viewHolder.tvSimplePast.setText(object.past_simple);
            viewHolder.tvPastParticiple.setText(object.past_participle);
        }

        return convertView;
    }

    public class ViewHolder {
        private TextView tvInFinitive, tvSimplePast, tvPastParticiple;
    }
}