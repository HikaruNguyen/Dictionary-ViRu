package com.dictionary.viru.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dictionary.viru.R;


public class LikeMeFragment extends BaseFragment {


    public LikeMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like_me, container, false);
        getFap(view);
        return view;
    }


}
