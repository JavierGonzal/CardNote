package com.javier.cardnote.detail;

import com.javier.cardnote.utils.BasePresenter;
import com.javier.cardnote.utils.BaseView;

/**
 * Created by javiergonzalezcabezas on 13/1/18.
 */

public interface DetailContract  {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
        void showImage(String string);
    }
}

