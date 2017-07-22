package dictionary.viet.russia.viru.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.ArrayList;

import dictionary.viet.russia.viru.R;
import dictionary.viet.russia.viru.adapter.ListSentencesAdapter;
import dictionary.viet.russia.viru.configuration.Configruation;
import dictionary.viet.russia.viru.database.SentencesDBHelper;
import dictionary.viet.russia.viru.model.db.SentencesObject;
import dictionary.viet.russia.viru.widget.customeControl.CustomEditText;

public class CumDongTuActivity extends BaseActivity {
    private RecyclerView rvListVerb;
    private ListSentencesAdapter adapter;
    private CustomEditText edSearch;
    private int type;
    private DragScrollBar dragScrollBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cum_dong_tu);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", Configruation.TYPE_CUM_DT);
        InitUI();
        InitData();
        InitEvent();
    }

    private void InitEvent() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString().trim());
            }
        });
    }

    private void InitData() {
        adapter.addAll(SentencesDBHelper.getListVerb(CumDongTuActivity.this, type));
    }

    private void InitUI() {
        setBackButtonToolbar();
        switch (type) {
            case Configruation.TYPE_CAC_TU_VIET_TAT:
                setTitleToolbar(getString(R.string.cacTuVietTat));
                break;
            case Configruation.TYPE_THANH_NGU:
                setTitleToolbar(getString(R.string.thanhNgu));
                break;
            case Configruation.TYPE_TUC_NGU:
                setTitleToolbar(getString(R.string.tucNgu));
                break;
            case Configruation.TYPE_CUM_DT:
                setTitleToolbar(getString(R.string.cumDongTu));
                break;
            default:
                setTitleToolbar(getString(R.string.cumDongTu));
                break;

        }
        rvListVerb = (RecyclerView) findViewById(R.id.rvListVerb);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        rvListVerb.setLayoutManager(layoutManager1);
        rvListVerb.setHasFixedSize(true);
        rvListVerb.setItemAnimator(new DefaultItemAnimator());
//        rvListVerb.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter = new ListSentencesAdapter(this, new ArrayList<SentencesObject>(), type);
        rvListVerb.setAdapter(adapter);
        edSearch = (CustomEditText) findViewById(R.id.edSearch);
        dragScrollBar = (DragScrollBar) findViewById(R.id.dragScrollBar);
        dragScrollBar.addIndicator(new AlphabetIndicator(this), true);
        dragScrollBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
