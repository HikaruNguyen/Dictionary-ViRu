package com.dictionary.viru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dictionary.viru.R;

import java.util.List;

/**
 * Created by manhi on 21/1/2016.
 */
public class PopupWordAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> dictWords;

    public PopupWordAdapter(Context context, List<String> Strings) {
        super(context, 0, Strings);
        this.context = context;
        this.dictWords = Strings;
    }

    public String getWordbyPosition(int position) {
//        if (position < dictWords.size()) {
            return getItem(position);
//        } else {
//            return "";
//        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String dictWord = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_word, parent, false);
        }
        // Lookup view for data population
        TextView tvWord = (TextView) convertView.findViewById(R.id.tvWord);
        // Populate the data into the template view using the data object
        tvWord.setText(dictWord);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        String dictWord = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_word, parent, false);
        }
        // Lookup view for data population
        TextView tvWord = (TextView) convertView.findViewById(R.id.tvWord);
        // Populate the data into the template view using the data object
        tvWord.setText(dictWord);

        return convertView;
    }
}