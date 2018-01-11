package com.javier.cardnote.cardnote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.javier.cardnote.CardNoteApplication;
import com.javier.cardnote.R;
import com.javier.cardnote.data.RemoteDataSource;
import com.javier.cardnote.utils.scheduler.BaseSchedulerProvider;

import javax.inject.Inject;

import static com.javier.cardnote.utils.Utils.addFragmentToActivity;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class CardNoteActivity extends AppCompatActivity{
    @Inject
    RemoteDataSource remoteDataSource;

    @Inject
    BaseSchedulerProvider schedulerProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_note_activity);
        initializeDagger();
        initFragment();
    }

    private void initializeDagger() {
        CardNoteApplication app = (CardNoteApplication) getApplication();
        app.getAppComponent().inject(this);
    }

    private void initFragment () {
        CardNoteFragment cardNoteFragment = (CardNoteFragment) getSupportFragmentManager().
                findFragmentById(R.id.card_note_activity_contentFrame);

        if (cardNoteFragment == null) {
            cardNoteFragment = cardNoteFragment.newInstance();
            addFragmentToActivity(getSupportFragmentManager(),
                    cardNoteFragment, R.id.card_note_activity_contentFrame);
        }

        new CardNotePresenter(remoteDataSource, cardNoteFragment, schedulerProvider);

    }
}
