package com.javier.cardnote;

import com.google.gson.Gson;
import com.javier.cardnote.data.Example;
import com.javier.cardnote.data.RemoteDataSource;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

import static com.javier.cardnote.utils.Constants.URL_BASE;

/**
 * Created by javiergonzalezcabezas on 11/1/18.
 */

public class RemoteDataSourceTest {
    List<Example> result;
    MockWebServer mockWebServer;
    TestSubscriber<List<Example>> subscriber;

    @Before
    public void setUp() {

        List<String> tags = new ArrayList<>();
        tags.add("tagOne");
        tags.add("tagTwo");

        Example exampleOne = new Example("id","description","date", tags, "title", "image");
        result = new ArrayList<>();
        result.add(exampleOne);
        mockWebServer = new MockWebServer();
        subscriber = new TestSubscriber<>();
    }

    @Test
    public void serverCallWithError() {
        //Given
        String url = "dfdf/";
        mockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(result)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockWebServer.url(url))
                .build();
        RemoteDataSource remoteDataSource = new RemoteDataSource(retrofit);

        //When
        remoteDataSource.getCardNoteRx().subscribe(subscriber);

        //Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

    @Test
    public void severCallWithSuccessful() {
        //Given
        mockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(result)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockWebServer.url(URL_BASE))
                .build();
        RemoteDataSource remoteDataSource = new RemoteDataSource(retrofit);

        //When
        remoteDataSource.getCardNoteRx().subscribe(subscriber);

        //Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }
}

