package com.javier.cardnote.cardnote;

import android.support.annotation.NonNull;

import com.javier.cardnote.data.Example;
import com.javier.cardnote.data.RemoteDataSource;
import com.javier.cardnote.utils.scheduler.BaseSchedulerProvider;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class CardNotePresenter implements CardNoteContract.Presenter {

    @NonNull
    private CardNoteContract.View view;

    @NonNull
    private BaseSchedulerProvider schedulerProvider;

    @NonNull
    private CompositeSubscription subscription;

    @NonNull
    private RemoteDataSource remoteDataSource;

    public CardNotePresenter(@NonNull RemoteDataSource remoteDataSource, @NonNull CardNoteContract.View view, @NonNull BaseSchedulerProvider provider) {
        this.remoteDataSource = checkNotNull(remoteDataSource, "remoteDataSource");
        this.view = checkNotNull(view, "view cannot be null!");
        this.schedulerProvider = checkNotNull(provider, "schedulerProvider cannot be null");

        subscription = new CompositeSubscription();

        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        fetch();
    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }

    @Override
    public void fetch() {

        view.setLoadingIndicator(true);
        Subscription subscription = remoteDataSource.getCardNoteRx()
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe((List<Example> example) -> {
                            view.setLoadingIndicator(false);
                            view.showCardNote(example);
                        },
                        (Throwable error) -> {
                            try {
                                view.showError();
                            } catch (Throwable t) {
                                throw new IllegalThreadStateException();
                            }

                        },
                        () -> {
                        });

        this.subscription.add(subscription);
    }
}
