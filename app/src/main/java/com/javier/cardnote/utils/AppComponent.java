package com.javier.cardnote.utils;

import com.javier.cardnote.cardnote.CardNoteActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(CardNoteActivity cardNoteActivity);

}
