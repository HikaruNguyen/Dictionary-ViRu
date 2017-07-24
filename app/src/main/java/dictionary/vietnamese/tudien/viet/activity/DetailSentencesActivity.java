package dictionary.vietnamese.tudien.viet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import dictionary.vietnamese.tudien.viet.R;
import dictionary.vietnamese.tudien.viet.configuration.Configruation;
import dictionary.vietnamese.tudien.viet.model.db.SentencesObject;

public class DetailSentencesActivity extends BaseActivity {
    private SentencesObject verbObject;
    private TextView tvVerb, tvMeaning, tvExample, tvCategory, tvCountry;
    private TextView txtMeaning, txtExample, txtCategory, txtCountry;
    private int type;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cum_dtdetail);
        Intent intent = getIntent();
        verbObject = (SentencesObject) intent.getSerializableExtra("cumDT");
        type = intent.getIntExtra("type", Configruation.TYPE_CUM_DT);
        InitUI();
        InitData();
        InitEvent();
    }

    private void InitEvent() {
        txtMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trans(verbObject.definition);
            }
        });
        txtExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trans(verbObject.example);
            }
        });
        tvVerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == Configruation.TYPE_CUM_DT) {
                    Meaning(title.toLowerCase());
                } else {
                    Trans(title);
                }

            }
        });
    }

    private void Trans(String s) {
//        Intent intent = new Intent(DetailSentencesActivity.this, TranslateActivity.class);
//        intent.putExtra("trans", s);
//        startActivity(intent);
    }

    private void Meaning(String s) {
        Intent intent = new Intent(DetailSentencesActivity.this, MeaningWordActivity.class);
        intent.putExtra("word", s);
        startActivity(intent);
    }

    private void InitData() {
        tvVerb.setText(title);
        tvMeaning.setText(verbObject.definition);
        tvExample.setText(verbObject.example);
        tvCategory.setText(verbObject.category);
        tvCountry.setText(verbObject.country);
        switch (type) {
            case Configruation.TYPE_CAC_TU_VIET_TAT:
                break;
            case Configruation.TYPE_THANH_NGU:
                txtExample.setVisibility(View.GONE);
                tvExample.setVisibility(View.GONE);
                if (verbObject.category != null && !verbObject.category.isEmpty()) {
                    txtCategory.setVisibility(View.VISIBLE);
                    tvCategory.setVisibility(View.VISIBLE);
                }
                if (verbObject.country != null && !verbObject.country.isEmpty()) {
                    txtCountry.setVisibility(View.VISIBLE);
                    tvCountry.setVisibility(View.VISIBLE);
                }

                break;
            case Configruation.TYPE_TUC_NGU:
                txtExample.setVisibility(View.GONE);
                tvExample.setVisibility(View.GONE);
                break;
            case Configruation.TYPE_CUM_DT:

                break;
            default:

                break;

        }
    }

    private void InitUI() {
        setBackButtonToolbar();
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
        setTitleToolbar(title);
        tvVerb = (TextView) findViewById(R.id.tvVerb);
        tvMeaning = (TextView) findViewById(R.id.tvMeaning);
        tvExample = (TextView) findViewById(R.id.tvExample);
        txtExample = (TextView) findViewById(R.id.txtExample);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        txtCountry = (TextView) findViewById(R.id.txtCountry);
        tvCountry = (TextView) findViewById(R.id.tvCountry);
        txtMeaning = (TextView) findViewById(R.id.txtMeaning);
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
