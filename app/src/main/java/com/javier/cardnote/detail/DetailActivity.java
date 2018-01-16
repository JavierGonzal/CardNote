package com.javier.cardnote.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.javier.cardnote.R;

import static com.javier.cardnote.utils.Constants.IMAGE_ITEM;
import static com.javier.cardnote.utils.Utils.addFragmentToActivity;

/**
 * Created by javiergonzalezcabezas on 13/1/18.
 */

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initFragment(getIntent().getStringExtra(IMAGE_ITEM));
    }

    private void initFragment(String string) {
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_activity_contentFrame);
        if (detailFragment == null) {
            detailFragment = detailFragment.newInstance();
            addFragmentToActivity(getSupportFragmentManager(), detailFragment, R.id.detail_activity_contentFrame);
        }

        new DetailPresenter(string, detailFragment);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
