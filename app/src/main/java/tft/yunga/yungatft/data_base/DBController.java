package tft.yunga.yungatft.data_base;


import android.app.Application;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;
import tft.yunga.yungatft.Event;

public class DBController {

    private static DBController instance;
    private final Realm realm;

    public DBController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static DBController with(Fragment fragment) {

        if (instance == null) {
            instance = new DBController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static DBController getInstance() {
        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //clear all objects
    public void clearAll() {
        realm.beginTransaction();
        realm.clear(Event.class);
        realm.commitTransaction();
    }

    //Find all objects
    public RealmResults<Event> getBooks() {
        return realm.where(Event.class).findAll();
    }

    //Query a single item with the given id
    public Event getEvent(String id) {
        return realm.where(Event.class).equalTo("id", id).findFirst();
    }

    //Check if is empty
    public boolean hasEvents() {
        return !realm.allObjects(Event.class).isEmpty();
    }

    public RealmResults<Event> queryedBooks() {
        return realm.where(Event.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}