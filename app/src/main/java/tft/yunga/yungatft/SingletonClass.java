package tft.yunga.yungatft;

import android.widget.ListAdapter;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class SingletonClass {

    private static final SingletonClass instance = new SingletonClass();
    private Locale locale = new Locale("ru", "RU");
    public SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM, yy", locale);
    public SimpleDateFormat datePassFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
    public ListAdapter adapter;

    public static SingletonClass getInstance() {
        return instance;
    }

    private SingletonClass() {
    }

    public void setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
    }

    public ListAdapter getAdapter() {
        return this.adapter;
    }
}
