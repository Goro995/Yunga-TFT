package tft.yunga.yungatft.adapters;


import android.content.Context;

import io.realm.RealmResults;
import tft.yunga.yungatft.Event;

public class RealmEventsAdaper extends RealmModelAdapter<Event> {

    public RealmEventsAdaper(Context context, RealmResults<Event> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}
