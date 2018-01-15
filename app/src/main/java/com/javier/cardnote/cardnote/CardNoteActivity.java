package com.javier.cardnote.cardnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.javier.cardnote.CardNoteApplication;
import com.javier.cardnote.R;
import com.javier.cardnote.data.Event;
import com.javier.cardnote.data.RemoteDataSource;
import com.javier.cardnote.detail.DetailActivity;
import com.javier.cardnote.utils.GetListListener;
import com.javier.cardnote.utils.InteractionListener;
import com.javier.cardnote.utils.scheduler.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.javier.cardnote.utils.Constants.IMAGE_ITEM;
import static com.javier.cardnote.utils.Constants.RESTORE_LIST;
import static com.javier.cardnote.utils.Utils.addFragmentToActivity;
import static com.javier.cardnote.utils.Utils.replaceFragmentToActivity;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class CardNoteActivity extends AppCompatActivity implements InteractionListener, GetListListener {
    @Inject
    RemoteDataSource remoteDataSource;

    @Inject
    BaseSchedulerProvider schedulerProvider;

    CardNoteFragment cardNoteFragment;
    CardNoteGridFragment gridFragment;
    Fragment fragment;
    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_note_activity);
        initializeDagger();
        if (savedInstanceState != null) {
            events = savedInstanceState.getParcelableArrayList(RESTORE_LIST);
        }
        initFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RESTORE_LIST, events);
    }

    private void initializeDagger() {
        CardNoteApplication app = (CardNoteApplication) getApplication();
        app.getAppComponent().inject(this);
    }

    private void initFragment() {
        if (events == null) {
            cardNoteFragment = (CardNoteFragment) getSupportFragmentManager().
                    findFragmentById(R.id.card_note_activity_contentFrame);
            if (cardNoteFragment == null) {
                cardNoteFragment = cardNoteFragment.newInstance();
                addFragmentToActivity(getSupportFragmentManager(),
                        cardNoteFragment, R.id.card_note_activity_contentFrame);
            }
            fragment = cardNoteFragment;

            new CardNotePresenter(remoteDataSource, cardNoteFragment, schedulerProvider);
        }
    }

    private void replaceCardNoteFragment() {

        if (cardNoteFragment == null) {
            cardNoteFragment = cardNoteFragment.newInstance();
        }
        replaceFragmentToActivity(getSupportFragmentManager(),
                cardNoteFragment, R.id.card_note_activity_contentFrame);
        fragment = cardNoteFragment;
    }

    private void replaceGridFragment() {

        if (gridFragment == null) {
            gridFragment = gridFragment.newInstance();
        }
        replaceFragmentToActivity(getSupportFragmentManager(),
                gridFragment, R.id.card_note_activity_contentFrame);
        fragment = gridFragment;
    }


    @Override
    public void onFragmentInteraction(String string) {

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(IMAGE_ITEM, string);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    public void getList(List<Event> events) {
        this.events = new ArrayList<>(events);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.card_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        invalidateOptionsMenu();
        if (item.getItemId() == R.id.card_menu_change) {
            chooseFragment();
        }
        return true;
    }

    public void chooseFragment() {
        if (fragment == null) {
            fragment = getSupportFragmentManager().findFragmentById(R.id.card_note_activity_contentFrame);
        }
        if (fragment instanceof CardNoteFragment) {
            replaceGridFragment();
            ((CardNoteGridFragment) fragment).initList(events);
        } else {
            replaceCardNoteFragment();
            ((CardNoteFragment) fragment).initList(events);
        }
    }
}
