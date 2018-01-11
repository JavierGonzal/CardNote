package com.javier.cardnote.utils;

import com.javier.cardnote.CardNoteApplication;
import com.javier.cardnote.data.RemoteDataSource;
import com.javier.cardnote.utils.scheduler.BaseSchedulerProvider;
import com.javier.cardnote.utils.scheduler.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.javier.cardnote.utils.Constants.URL_BASE;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */
@Module
public class AppModule {

    CardNoteApplication cardNoteApplication;

    public AppModule(CardNoteApplication weatherApplication) {
        cardNoteApplication = weatherApplication;
    }

    @Singleton
    @Provides
    RemoteDataSource provideRemoteDataSource() {
        return new RemoteDataSource(new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build());
    }

    @Singleton
    @Provides
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }
}
