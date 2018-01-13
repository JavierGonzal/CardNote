package com.javier.cardnote.detail;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.javier.cardnote.R;

import butterknife.BindView;

/**
 * Created by javiergonzalezcabezas on 13/1/18.
 */

public class DetailPresenter implements DetailContract.Presenter {

    @NonNull
    private DetailContract.View view;
    private String urlString;

    public DetailPresenter(String urlString, DetailContract.View view) {
        this.urlString = urlString;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        view.showImage(urlString);
    }


}
