package com.beltranfebrer.discogsbrowser;

import com.beltranfebrer.discogsbrowser.model.MockRecordCollection;
import com.beltranfebrer.discogsbrowser.model.Record;
import com.beltranfebrer.discogsbrowser.model.RecordCollection;
import com.beltranfebrer.discogsbrowser.network.DiscogsService;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Miquel Beltran on 23.04.16.
 * More on http://beltranfebrer.com
 */
public class UserCollectionTest {
    private UserCollection userCollection;
    private DiscogsService service;

    @Before
    public void setUp() throws Exception {
        service = mock(DiscogsService.class);
        Observable<RecordCollection> mockObservable = Observable.just(new MockRecordCollection().recordCollection);
        when(service.listRecords("test")).thenReturn(mockObservable);
        userCollection = new UserCollection(service, "test", Schedulers.immediate());
    }

    @Test
    public void testSubscribeAndNext() throws Exception {
        verify(service).listRecords("test");
        TestSubscriber<Record> subscriber = new TestSubscriber<>();
        userCollection.subject.subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        Record record = subscriber.getOnNextEvents().get(0);
        assertThat(record.getInstance_id()).matches("1234");
    }
}