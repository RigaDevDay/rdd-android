package lv.rigadevday.android.repository.storage;

import android.content.Context;
import android.support.annotation.NonNull;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import lv.rigadevday.android.utils.BaseApplication;
import rx.Observable;

/**
 */
public class Storage {

    public static final String FAVORITES = "favorites";

    @Inject
    Context context;

    private DB snappy;

    public Storage() {
        BaseApplication.Companion.inject(this);

        try {
            snappy = DBFactory.open(context);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public Observable<Boolean> saveOrDeleteFavoredTimeSlot(String day, String time, int index) {
        Boolean result;
        FavoredTimeSlot fts = new FavoredTimeSlot(day, time, index);

        List<FavoredTimeSlot> list = getAllFavoredItems();
        if (list.contains(fts)) {
            list.remove(fts);
            result = Boolean.FALSE;
        } else {
            list.add(fts);
            result = Boolean.TRUE;
        }
        saveFavoritesList(list);

        return Observable.just(result);
    }

    public Observable<Boolean> hasFavoredTimeSlot(String day, String time, int index) {
        FavoredTimeSlot fts = new FavoredTimeSlot(day, time, index);

        return Observable.just(getAllFavoredItems().contains(fts));
    }


    private void saveFavoritesList(List<FavoredTimeSlot> list) {
        try {
            if (list.isEmpty()) {
                snappy.del(FAVORITES);
            } else {
                FavoredTimeSlot[] arr = new FavoredTimeSlot[list.size()];
                list.toArray(arr);
                snappy.put(FAVORITES, arr);
            }
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private List<FavoredTimeSlot> getAllFavoredItems() {
        try {
            FavoredTimeSlot[] arr = snappy.getObjectArray(FAVORITES, FavoredTimeSlot.class);
            return new ArrayList<>(Arrays.asList(arr));
        } catch (SnappydbException e) {
            return new ArrayList<>();
        }
    }

}
