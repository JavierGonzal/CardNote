package com.javier.cardnote;

import com.javier.cardnote.cardnote.CardNoteContract;
import com.javier.cardnote.cardnote.CardNotePresenter;
import com.javier.cardnote.data.Example;
import com.javier.cardnote.data.RemoteDataSource;
import com.javier.cardnote.utils.scheduler.BaseSchedulerProvider;
import com.javier.cardnote.utils.scheduler.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.when;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class CardNotePresenterTest {

    @Mock
    private CardNoteContract.View view;

    @Mock
    private BaseSchedulerProvider schedulerProvider;

    @Mock
    private RemoteDataSource remoteDataSource;

    CardNotePresenter presenter;
    List<Example> result;

    @Before
    public void setup() {

        List<String> tags = new ArrayList<>();
        tags.add("tagOne");
        tags.add("tagTwo");

        Example exampleOne = new Example("id","description","date", tags, "title", "image");
        result = new ArrayList<>();
        result.add(exampleOne);
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new ImmediateSchedulerProvider();
        presenter = new CardNotePresenter(remoteDataSource, view, schedulerProvider);
    }

    @Test
    public void fetchData() {
        when(remoteDataSource.getCardNoteRx())
                .thenReturn(rx.Observable.just(result));

        presenter.fetch();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).setLoadingIndicator(true);
        inOrder.verify(view).setLoadingIndicator(false);
        inOrder.verify(view).showCardNote(result);
    }

    @Test
    public void fetchError() {

        when(remoteDataSource.getCardNoteRx())
                .thenReturn(Observable.error(new Throwable("An error has occurred!")));

        presenter.fetch();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).setLoadingIndicator(true);
        inOrder.verify(view).showError();
    }
}
