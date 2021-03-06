package work.beltran.discogsbrowser.business;

import java.util.List;

import rx.Observable;
import work.beltran.discogsbrowser.api.model.record.Record;

/**
 * Created by Miquel Beltran on 8/27/16
 * More on http://beltran.work
 */
public interface SearchInteractor {
    Observable<List<Record>> search(final String query);
}
