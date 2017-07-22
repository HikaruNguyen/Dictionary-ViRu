package dictionary.viet.russia.viru.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import dictionary.viet.russia.viru.R;
import dictionary.viet.russia.viru.model.resultApi.LanguageResult;

/**
 * Created by manhi on 29/2/2016.
 */
public class ListLanguageAdapter extends ArrayAdapter<LanguageResult.LanguageInfo> {

    public ListLanguageAdapter(Context context, List<LanguageResult.LanguageInfo> Strings) {
        super(context, 0, Strings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String draw = getItem(position).name;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_language, parent, false);
        }
        // Lookup view for data population
        TextView tvlanguage = (TextView) convertView.findViewById(R.id.tvLanguageName);
        tvlanguage.setGravity(Gravity.CENTER);
        // Populate the data into the template view using the data object
        tvlanguage.setText(draw);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String draw = getItem(position).name;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_language, parent, false);
        }
        // Lookup view for data population
        TextView tvlanguage = (TextView) convertView.findViewById(R.id.tvLanguageName);
        // Populate the data into the template view using the data object
        tvlanguage.setText(draw);

        return convertView;
    }
}