package com.dictionary.viru.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dictionary.viru.R;
import com.dictionary.viru.activity.CumDongTuActivity;
import com.dictionary.viru.activity.BatQuyTacActivity;
import com.dictionary.viru.configuration.Configruation;


public class ReferencesFragment extends BaseFragment implements View.OnClickListener {
    private Button btnDongTuBatQuyTac;
    private Button btnCumDongTu;
    private Button btnDanhTuSoNhieuBQT;
    private Button btnThanhNgu;
    private Button btnTucNgu;
    private Button btnCacTuVietTat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_references, container, false);
        InitUI(view);
        InitEvent();
        return view;
    }

    private void InitEvent() {
        btnDongTuBatQuyTac.setOnClickListener(this);
        btnCumDongTu.setOnClickListener(this);
        btnDanhTuSoNhieuBQT.setOnClickListener(this);
        btnThanhNgu.setOnClickListener(this);
        btnTucNgu.setOnClickListener(this);
        btnCacTuVietTat.setOnClickListener(this);
    }

    private void InitUI(View view) {
        getFap(view);
        btnDongTuBatQuyTac = (Button) view.findViewById(R.id.btnDongTuBatQuyTac);
        btnCumDongTu = (Button) view.findViewById(R.id.btnCumDongTu);
        btnDanhTuSoNhieuBQT = (Button) view.findViewById(R.id.btnDanhTuSoNhieuBQT);
        btnThanhNgu = (Button) view.findViewById(R.id.btnThanhNgu);
        btnTucNgu = (Button) view.findViewById(R.id.btnTucNgu);
        btnCacTuVietTat = (Button) view.findViewById(R.id.btnCacTuVietTat);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnDongTuBatQuyTac:
                intent = new Intent(activity, BatQuyTacActivity.class);
                intent.putExtra("type", Configruation.TYPE_DONGTU_BQT);
                startActivity(intent);
                break;
            case R.id.btnDanhTuSoNhieuBQT:
                intent = new Intent(activity, BatQuyTacActivity.class);
                intent.putExtra("type", Configruation.TYPE_DANHTU_BQT);
                startActivity(intent);
                break;
            case R.id.btnCumDongTu:
                intent = new Intent(activity, CumDongTuActivity.class);
                intent.putExtra("type", Configruation.TYPE_CUM_DT);
                startActivity(intent);
                break;
            case R.id.btnThanhNgu:
                intent = new Intent(activity, CumDongTuActivity.class);
                intent.putExtra("type", Configruation.TYPE_THANH_NGU);
                startActivity(intent);
                break;
            case R.id.btnTucNgu:
                intent = new Intent(activity, CumDongTuActivity.class);
                intent.putExtra("type", Configruation.TYPE_TUC_NGU);
                startActivity(intent);
                break;
            case R.id.btnCacTuVietTat:
                intent = new Intent(activity, CumDongTuActivity.class);
                intent.putExtra("type", Configruation.TYPE_CAC_TU_VIET_TAT);
                startActivity(intent);
                break;
        }
    }
}
