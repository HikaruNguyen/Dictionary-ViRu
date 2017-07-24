package dictionary.vietnamese.tudien.viet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.ArrayList;
import java.util.List;

import dictionary.vietnamese.tudien.viet.R;
import dictionary.vietnamese.tudien.viet.adapter.ListNounBQTAdapter;
import dictionary.vietnamese.tudien.viet.adapter.ListVerbBQTAdapter;
import dictionary.vietnamese.tudien.viet.configuration.Configruation;
import dictionary.vietnamese.tudien.viet.database.IrregularDBHelper;
import dictionary.vietnamese.tudien.viet.model.db.IrregularPluralNounObject;
import dictionary.vietnamese.tudien.viet.model.db.IrregularVerbObject;
import dictionary.vietnamese.tudien.viet.widget.DividerItemDecoration;
import dictionary.vietnamese.tudien.viet.widget.customeControl.CustomEditText;

public class BatQuyTacActivity extends BaseActivity {
    private RecyclerView rvListVerb;
    private ListVerbBQTAdapter adapter;
    private ListNounBQTAdapter nounBQTAdapter;
    private CustomEditText edSearch;
    private int type;
    private TextView txtInfinitive, txtSimplePast, txtPastParticiple;
    private DragScrollBar dragScrollBar;
    private List<IrregularPluralNounObject> listGroup;
    private List<IrregularPluralNounObject> listNoun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_tu_bqt);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", Configruation.TYPE_DONGTU_BQT);
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
                if (type == Configruation.TYPE_DONGTU_BQT) {
                    adapter.getFilter().filter(s.toString().trim());
                } else {
                    nounBQTAdapter.getFilter().filter(s.toString().trim());
                }

            }
        });
    }

    private void InitData() {
        if (type == Configruation.TYPE_DONGTU_BQT) {
            txtInfinitive.setText(getString(R.string.infinitive));
            txtSimplePast.setText(getString(R.string.simplePast));
            txtPastParticiple.setText(getString(R.string.PastParticiple));
        } else {
            txtInfinitive.setText(getString(R.string.singular));
            txtPastParticiple.setText(getString(R.string.plural));
//            txtPastParticiple.setText(getString(R.string.group));
        }
        if (type == Configruation.TYPE_DONGTU_BQT) {
            adapter.addAll(IrregularDBHelper.getListIrregualrVerbk(BatQuyTacActivity.this, type == Configruation.TYPE_DONGTU_BQT ? IrregularDBHelper.tableNameDongTu : IrregularDBHelper.tableNameDanhTu));
        } else {
            listGroup = IrregularDBHelper.getGroupNoun(BatQuyTacActivity.this, IrregularDBHelper.tableNameDanhTu);
            listNoun = new ArrayList<>();
            for (int i = 0; i < listGroup.size(); i++) {
                listNoun.add(listGroup.get(i));
                listNoun.addAll(IrregularDBHelper.getListIrregualrNoun(BatQuyTacActivity.this, IrregularDBHelper.tableNameDanhTu, listGroup.get(i).group));
            }
            nounBQTAdapter.addAll(listNoun);
//            nounBQTAdapter.addAll(IrregularDBHelper.getListIrregualrNoun(BatQuyTacActivity.this, IrregularDBHelper.tableNameDanhTu));
        }

    }

    private void InitUI() {
        setBackButtonToolbar();
        setTitleToolbar(type == Configruation.TYPE_DONGTU_BQT ? getString(R.string.dongTuBatQuyTac) : getString(R.string.danhTuSoNhieuBQT));
        edSearch = (CustomEditText) findViewById(R.id.edSearch);
        txtInfinitive = (TextView) findViewById(R.id.txtInfinitive);
        txtSimplePast = (TextView) findViewById(R.id.txtSimplePast);
        txtPastParticiple = (TextView) findViewById(R.id.txtPastParticiple);
        rvListVerb = (RecyclerView) findViewById(R.id.rvListVerb);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        rvListVerb.setLayoutManager(layoutManager1);
        rvListVerb.setHasFixedSize(true);
        rvListVerb.setItemAnimator(new DefaultItemAnimator());
        rvListVerb.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        if (type == Configruation.TYPE_DONGTU_BQT) {
            adapter = new ListVerbBQTAdapter(this, new ArrayList<IrregularVerbObject>());
            rvListVerb.setAdapter(adapter);
            dragScrollBar = (DragScrollBar) findViewById(R.id.dragScrollBar);
            dragScrollBar.addIndicator(new AlphabetIndicator(this), true);
            dragScrollBar.setVisibility(View.VISIBLE);
        } else {
            nounBQTAdapter = new ListNounBQTAdapter(this, new ArrayList<IrregularPluralNounObject>());
            rvListVerb.setAdapter(nounBQTAdapter);
            txtSimplePast.setVisibility(View.GONE);
        }

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
