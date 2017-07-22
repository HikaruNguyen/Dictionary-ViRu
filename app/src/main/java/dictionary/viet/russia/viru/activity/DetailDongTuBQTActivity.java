package dictionary.viet.russia.viru.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import dictionary.viet.russia.viru.R;
import dictionary.viet.russia.viru.adapter.ListVerbBQTBaseAdapter;
import dictionary.viet.russia.viru.database.IrregularDBHelper;
import dictionary.viet.russia.viru.model.db.IrregularVerbObject;
import dictionary.viet.russia.viru.widget.customeControl.ExpandableHeightListView;

public class DetailDongTuBQTActivity extends BaseActivity {
    private IrregularVerbObject verbObject;
    private TextView tvVerb, tvMeaning, txtConjuagation;
    private TextView tvPersonSingular, tvPastParticiple, tvSimplePast, tvBaseForm;
    //    private RecyclerView rvListVerb;
//    private ListVerbBQTAdapter adapter;
    private ExpandableHeightListView rvListVerb;
    private ListVerbBQTBaseAdapter adapter;
    private List<IrregularVerbObject> list;
    private ScrollView nScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dong_tu_bqt);
        Intent intent = getIntent();
        verbObject = (IrregularVerbObject) intent.getSerializableExtra("verb");
        InitUI();
        InitData();
        InitEvent();
    }

    private void InitEvent() {
        tvVerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailDongTuBQTActivity.this, MeaningWordActivity.class);
                intent.putExtra("word", verbObject.infinitive.toLowerCase());
                startActivity(intent);
            }
        });
        rvListVerb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DetailDongTuBQTActivity.this, DetailDongTuBQTActivity.class);
                intent.putExtra("verb", list.get(position));
                startActivity(intent);
            }
        });
    }

    private void InitData() {
        tvVerb.setText(verbObject.infinitive);
        tvMeaning.setText(Html.fromHtml(verbObject.definition));
        txtConjuagation.append(" " + verbObject.infinitive);
        tvBaseForm.setText(verbObject.infinitive);
        tvSimplePast.setText(verbObject.past_simple);
        tvPastParticiple.setText(verbObject.past_participle);
        tvPersonSingular.setText(verbObject.third_person_singular);

//        adapter.addAll(IrregularDBHelper.getListIrregualrVerbk(DetailDongTuBQTActivity.this, IrregularDBHelper.tableNameDongTu, verbObject.group));
        list = IrregularDBHelper.getListIrregualrVerbk(DetailDongTuBQTActivity.this, IrregularDBHelper.tableNameDongTu, verbObject.group);
        adapter = new ListVerbBQTBaseAdapter(this, list);
        rvListVerb.setAdapter(adapter);
//        nScrollView.scrollTo(0, 0);
        findViewById(R.id.lnTop).requestFocus();
        nScrollView.smoothScrollTo(0, 0);
    }

    private void InitUI() {
        setBackButtonToolbar();
        setTitleToolbar(verbObject.infinitive);
        tvVerb = (TextView) findViewById(R.id.tvVerb);
        tvMeaning = (TextView) findViewById(R.id.tvMeaning);
        txtConjuagation = (TextView) findViewById(R.id.txtConjuagation);
        tvBaseForm = (TextView) findViewById(R.id.tvBaseForm);
        tvSimplePast = (TextView) findViewById(R.id.tvSimplePast);
        tvPastParticiple = (TextView) findViewById(R.id.tvPastParticiple);
        tvPersonSingular = (TextView) findViewById(R.id.tvPersonSingular);

//        rvListVerb = (RecyclerView) findViewById(R.id.rvListVerb);
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
//        rvListVerb.setLayoutManager(layoutManager1);
//        rvListVerb.setHasFixedSize(true);
//        rvListVerb.setItemAnimator(new DefaultItemAnimator());
//        rvListVerb.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        adapter = new ListVerbBQTAdapter(this, new ArrayList<IrregularVerbObject>());
//        rvListVerb.setAdapter(adapter);
        nScrollView = (ScrollView) findViewById(R.id.nScrollView);

        rvListVerb = (ExpandableHeightListView) findViewById(R.id.rvListVerb);
        rvListVerb.setExpanded(true);
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
