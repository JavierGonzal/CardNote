package com.javier.cardnote.cardnote;

import com.javier.cardnote.data.Event;
import com.javier.cardnote.data.Example;
import com.javier.cardnote.utils.BasePresenter;
import com.javier.cardnote.utils.BaseView;

import java.util.List;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public interface CardNoteContract {

    interface Presenter extends BasePresenter {

        void unSubscribe();
        void fetch();
        void convertToEvent(List<Example> examples, String noFound);
    }

    interface View extends BaseView<Presenter> {

        void convertToEvent(List<Example> examples);
        void showCardNote(List<Event> listElements);

        void showError();

        void setLoadingIndicator(boolean active);

        void takeEvent(List<Event> events);

    }
}
