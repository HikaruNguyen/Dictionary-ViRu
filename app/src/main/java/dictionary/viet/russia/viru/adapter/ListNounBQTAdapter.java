package dictionary.viet.russia.viru.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dictionary.viet.russia.viru.R;
import dictionary.viet.russia.viru.activity.MeaningWordActivity;
import dictionary.viet.russia.viru.model.db.IrregularPluralNounObject;

/**
 * Created by manhi on 20/1/2016.
 */
public class ListNounBQTAdapter extends BaseRecyclerAdapter<IrregularPluralNounObject, ListNounBQTAdapter.ViewHolder> implements Filterable {
    private ValueFilter valueFilter;
    private List<IrregularPluralNounObject> mStringFilterList;

    public ListNounBQTAdapter(Context context, List<IrregularPluralNounObject> list) {
        super(context, list);
        mStringFilterList = list;
        getFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == IrregularPluralNounObject.TYPE_NORMAL) {
            View view = layoutInflater.inflate(R.layout.item_noun_bqt, parent, false);
            return new ViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.item_noun_header, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(list.get(position));
        if (list.get(position).type == IrregularPluralNounObject.TYPE_NORMAL) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MeaningWordActivity.class);
                    intent.putExtra("word", list.get(position).singular.toLowerCase());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSingular, tvPluar;
        private TextView tvGroup;

        public ViewHolder(View view) {
            super(view);
            tvSingular = (TextView) itemView.findViewById(R.id.tvSingular);
            tvPluar = (TextView) itemView.findViewById(R.id.tvPluar);
            tvGroup = (TextView) itemView.findViewById(R.id.tvGroup);
            itemView.setClickable(true);
        }

        public void bindData(IrregularPluralNounObject verbObject) {
            if (verbObject.type == IrregularPluralNounObject.TYPE_NORMAL) {
                tvSingular.setText(verbObject.singular);
                tvPluar.setText(verbObject.plural);
            } else {
                tvGroup.setText(verbObject.group);
            }

        }
    }

    private class ValueFilter extends Filter {


        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                List<IrregularPluralNounObject> filterList = new ArrayList<>();

                for (int i = 0; i < mStringFilterList.size(); i++) {

                    if ((mStringFilterList.get(i).singular != null && mStringFilterList.get(i).singular.toLowerCase().contains(constraint.toString().toLowerCase()))
                            || (mStringFilterList.get(i).plural != null && mStringFilterList.get(i).plural.toLowerCase().contains(constraint.toString().toLowerCase()))) {

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
            list = (ArrayList<IrregularPluralNounObject>) results.values;

            notifyDataSetChanged();

        }


    }
}