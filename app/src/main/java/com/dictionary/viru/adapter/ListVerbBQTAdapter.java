package com.dictionary.viru.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dictionary.viru.R;
import com.dictionary.viru.activity.DetailDongTuBQTActivity;
import com.dictionary.viru.model.db.IrregularVerbObject;
import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manhi on 20/1/2016.
 */
public class ListVerbBQTAdapter extends BaseRecyclerAdapter<IrregularVerbObject, ListVerbBQTAdapter.ViewHolder> implements Filterable, INameableAdapter {
    private ValueFilter valueFilter;
    private List<IrregularVerbObject> mStringFilterList;

    public ListVerbBQTAdapter(Context context, List<IrregularVerbObject> list) {
        super(context, list);
        mStringFilterList = list;
        getFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_verb_bqt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailDongTuBQTActivity.class);
                intent.putExtra("verb", list.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public Character getCharacterForElement(int element) {
        if (element > 0) {
            String s = list.get(element).infinitive;
            if (s != null && !s.isEmpty()) {
                return s.charAt(0);
            } else {
                return ' ';
            }
        } else {
            return ' ';
        }

    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvInFinitive, tvSimplePast, tvPastParticiple;

        public ViewHolder(View view) {
            super(view);
            tvInFinitive = (TextView) itemView.findViewById(R.id.tvInFinitive);
            tvSimplePast = (TextView) itemView.findViewById(R.id.tvSimplePast);
            tvPastParticiple = (TextView) itemView.findViewById(R.id.tvPastParticiple);
            itemView.setClickable(true);
        }

        public void bindData(IrregularVerbObject verbObject) {
            tvInFinitive.setText(verbObject.infinitive);
            tvSimplePast.setText(verbObject.past_simple);
            tvPastParticiple.setText(verbObject.past_participle);
        }
    }

    private class ValueFilter extends Filter {


        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                List<IrregularVerbObject> filterList = new ArrayList<>();

                for (int i = 0; i < mStringFilterList.size(); i++) {

                    if (mStringFilterList.get(i).infinitive.toLowerCase().contains(constraint.toString().toLowerCase())
                            || mStringFilterList.get(i).past_simple.toLowerCase().contains(constraint.toString().toLowerCase())
                            || mStringFilterList.get(i).past_participle.toLowerCase().contains(constraint.toString().toLowerCase())) {

                        filterList.add(mStringFilterList.get(i));

                    }
                }


                results.count = filterList.size();

                results.values = filterList;

            } else {

                results.count = mStringFilterList.size();

                results.values = mStringFilterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<IrregularVerbObject>) results.values;

            notifyDataSetChanged();

        }


    }
}