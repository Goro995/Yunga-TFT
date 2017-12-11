//package tft.yunga.yungatft.DataBase;
//
//import android.app.Application;
//import android.os.Bundle;
//
//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//
//
//
//public class DBSetUp extends Application {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate();
//
//        Realm.init(this);
//        val realmConfiguration = RealmConfiguration.Builder()
//                .name(Realm.DEFAULT_REALM_NAME)
//                .schemaVersion(0)
//                .deleteRealmIfMigrationNeeded()
//                .build()
//        Realm.setDefaultConfiguration(realmConfiguration)
//    }
//}
