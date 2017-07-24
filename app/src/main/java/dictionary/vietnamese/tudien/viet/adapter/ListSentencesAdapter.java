package dictionary.vietnamese.tudien.viet.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.util.ArrayList;
import java.util.List;

import dictionary.vietnamese.tudien.viet.R;
import dictionary.vietnamese.tudien.viet.activity.DetailSentencesActivity;
import dictionary.vietnamese.tudien.viet.configuration.Configruation;
import dictionary.vietnamese.tudien.viet.model.db.SentencesObject;

/**
 * Created by manhi on 20/1/2016.
 */
public class ListSentencesAdapter extends BaseRecyclerAdapter<SentencesObject, ListSentencesAdapter.ViewHolder> implements Filterable, INameableAdapter {
    private ValueFilter valueFilter;
    private List<SentencesObject> mStringFilterList;
    private int type;

    public ListSentencesAdapter(Context context, List<SentencesObject> list, int type) {
        super(context, list);
        mStringFilterList = list;
        this.type = type;
        getFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_cumverb, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(list.get(position), type);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != Configruation.TYPE_CAC_TU_VIET_TAT) {
                    Intent intent = new Intent(mContext, DetailSentencesActivity.class);
                    intent.putExtra("cumDT", list.get(position));
                    intent.putExtra("type", type);
                    mContext.startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(list.get(position).acronym);
                    builder.setMessage(list.get(position).definition);
                    builder.setPositiveButton(mContext.getString(R.string.translate), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(mContext, TranslateActivity.class);
//                            intent.putExtra("trans", list.get(position).definition);
//                            mContext.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

            }
        });


    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    @Override
    public Character getCharacterForElement(int element) {
        String title = "";
        if (element > 0) {
            SentencesObject verbObject = mStringFilterList.get(element);
            switch (type) {
                case Configruation.TYPE_CAC_TU_VIET_TAT:
                    title = (verbObject.acronym);
                    break;
                case Configruation.TYPE_THANH_NGU:
                    title = (verbObject.idiom);
                    break;
                case Configruation.TYPE_TUC_NGU:
                    title = (verbObject.proverb);
                    break;
                case Configruation.TYPE_CUM_DT:
                    title = (verbObject.verb);
                    break;
                default:
                    title = (verbObject.verb);
                    break;

            }
            if (title != null && !title.isEmpty()) {
                return title.charAt(0);
            } else {
                return ' ';
            }

        } else {
            return ' ';
        }

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVerb, tvDescription;

        public ViewHolder(View view) {
            super(view);
            tvVerb = (TextView) itemView.findViewById(R.id.tvVerb);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            itemView.setClickable(true);

        }

        public void bindData(SentencesObject verbObject, int type) {
            switch (type) {
                case Configruation.TYPE_CAC_TU_VIET_TAT:
                    tvVerb.setText(verbObject.acronym);
                    break;
                case Configruation.TYPE_THANH_NGU:
                    tvVerb.setText(verbObject.idiom);
                    break;
                case Configruation.TYPE_TUC_NGU:
                    tvVerb.setText(verbObject.proverb);
                    break;
                case Configruation.TYPE_CUM_DT:
                    tvVerb.setText(verbObject.verb);
                    break;
                default:
                    tvVerb.setText(verbObject.verb);
                    break;

            }

            tvDescription.setText(verbObject.definition);
//            tvPastParticiple.setText(verbObject.past_participle);
        }
    }

    private class ValueFilter extends Filter {


        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                List<SentencesObject> filterList = new ArrayList<>();

                for (int i = 0; i < mStringFilterList.size(); i++) {
                    SentencesObject verbObject = mStringFilterList.get(i);
                    String title = "";
                    switch (type) {
                        case Configruation.TYPE_CAC_TU_VIET_TAT:
                            title = (verbObject.acronym);
                            break;
                        case Configruation.TYPE_THANH_NGU:
                            title = (verbObject.idiom);
                            break;
                        case Configruation.TYPE_TUC_NGU:
                            title = (verbObject.proverb);
                            break;
                        case Configruation.TYPE_CUM_DT:
                            title = (verbObject.verb);
                            break;
                        default:
                            title = (verbObject.verb);
                            break;

                    }
                    if (title.toLowerCase().contains(constraint.toString().toLowerCase())) {

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
            list = (ArrayList<SentencesObject>) results.values;

            notifyDataSetChanged();

        }


    }
}