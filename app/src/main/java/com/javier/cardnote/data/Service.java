package com.javier.cardnote.data;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public interface Service {

    @GET("57ee2ca8260000f80e1110fa")
    Observable<List<Example>> getCardNoteRx();
}
