package com.javier.cardnote.data;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class RemoteDataSource implements Service {

    private Service api;

    public RemoteDataSource(Retrofit retrofit) {


        this.api = retrofit.create(Service.class);
    }

    @Override
    public Observable<List<Example>> getCardNoteRx() {
        return api.getCardNoteRx();
    }
}
