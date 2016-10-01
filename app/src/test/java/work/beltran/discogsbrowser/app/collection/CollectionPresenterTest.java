package work.beltran.discogsbrowser.app.collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import work.beltran.discogsbrowser.api.model.UserCollection;
import work.beltran.discogsbrowser.api.model.UserProfile;
import work.beltran.discogsbrowser.api.model.pagination.Pagination;
import work.beltran.discogsbrowser.api.model.record.Record;
import work.beltran.discogsbrowser.business.CollectionInteractor;
import work.beltran.discogsbrowser.business.ProfileInteractor;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static work.beltran.discogsbrowser.api.model.mocks.DiscogsModelMocks.getUserProfile;

/**
 * Created by Miquel Beltran on 8/28/16
 * More on http://beltran.work
 */
public class CollectionPresenterTest {
    private CollectionPresenter presenter;
    private UserProfile userProfile;
    private UserCollection userCollection;
    private List<Record> recordList;

    @Mock
    private CollectionInteractor interactor;
    @Mock
    private ProfileInteractor profileInteractor;
    @Mock
    private CollectionView view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new CollectionPresenter(interactor, profileInteractor);
        userProfile = getUserProfile();
        userCollection = new UserCollection();
        recordList = new ArrayList<>();
        userCollection.setRecords(recordList);
        when(interactor.getCollection(anyInt())).thenReturn(Observable.just(userCollection));
        when(profileInteractor.getProfile()).thenReturn(Observable.just(userProfile));
    }


    @Test
    public void attachView() throws Exception {
        presenter.attachView(view);
        verify(view).display(userProfile);
        verify(view).addRecords(recordList);
    }

    @Test
    public void loadMore() throws Exception {
        Pagination pagination = Pagination.builder()
                .page(0)
                .pages(2)
                .build();
        userCollection.setPagination(pagination);
        presenter.attachView(view);
        presenter.loadMore();
        verify(interactor).getCollection(1);
    }
}