package com.javier.cardnote;

import android.app.Application;

import com.javier.cardnote.utils.AppComponent;
import com.javier.cardnote.utils.AppModule;
import com.javier.cardnote.utils.DaggerAppComponent;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class CardNoteApplication extends Application {
    AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
