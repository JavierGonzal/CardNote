package com.javier.cardnote.cardnote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.javier.cardnote.CardNoteApplication;
import com.javier.cardnote.R;
import com.javier.cardnote.data.RemoteDataSource;
import com.javier.cardnote.utils.scheduler.BaseSchedulerProvider;

import javax.inject.Inject;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class CardNoteActivity extends AppCompatActivity{
    @Inject
    RemoteDataSource mRemoteDataSource;

    @Inject
    BaseSchedulerProvider mSchedulerProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_note_activity);
        initializeDagger();
    }

    private void initializeDagger() {
        CardNoteApplication app = (CardNoteApplication) getApplication();
        app.getAppComponent().inject(this);
    }
}
