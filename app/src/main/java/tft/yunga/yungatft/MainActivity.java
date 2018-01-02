package tft.yunga.yungatft;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import tft.yunga.yungatft.adapters.EventsAdapter;

public class MainActivity extends AppCompatActivity {

    TextView previousText;
    TextView currentWeekText;
    TextView futureText;
    ImageView plusImage;
    ListView eventsListView;
    EventsAdapter adapter;
    ArrayList<Event> eventsList;
    Realm realm;
    int currentlySelectedTab = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        previousText = findViewById(R.id.prev_events_text);
        currentWeekText = findViewById(R.id.curr_events_text);
        futureText = findViewById(R.id.future_events_text);
        plusImage = findViewById(R.id.plus_button);
        eventsListView = findViewById(R.id.events_list);

        realm = Realm.getDefaultInstance();
        currentWeekEventsList();

        plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventFragment addEventFragment = new AddEventFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_screen_layout, addEventFragment)
                        .addToBackStack(MainActivity.class.getSimpleName())
                        .commit();
            }
        });

        previousText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousEventsList();
                currentlySelectedTab = 1;
            }
        });

        currentWeekText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentWeekEventsList();
                currentlySelectedTab = 2;
            }
        });

        futureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                futureEventsList();
                currentlySelectedTab = 3;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void futureEventsList() {
        eventsList = new ArrayList<Event>(realm.where(Event.class)
                .greaterThan("eventDate", Calendar.getInstance().getTime())
                .findAll());
        setUpListView();
        setUpTextViewsColor(R.color.weak_text_color, R.color.weak_text_color, R.color.text_color);
    }

    public void previousEventsList() {
        DateTime dateTime = DateTime.now().minusDays(1);
        dateTime.minusMillis(1);
        eventsList = new ArrayList<Event>(realm.where(Event.class)
                .lessThan("eventDate", dateTime.toDate())
                .findAll());
        setUpListView();
        setUpTextViewsColor(R.color.weak_text_color, R.color.text_color, R.color.weak_text_color);
    }

    public void currentWeekEventsList() {
        Calendar calendar = Calendar.getInstance();
        Date startDate = DateTime.now().minusDays(1).toDate();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DATE, 6);
        Date endOfWeek = calendar.getTime();
        eventsList = new ArrayList<Event>(realm.where(Event.class)
                .between("eventDate", startDate, endOfWeek)
                .findAll());
        setUpListView();
        setUpTextViewsColor(R.color.text_color, R.color.weak_text_color, R.color.weak_text_color);
    }

    public void setUpTextViewsColor(int currentWeekTextColor, int previousTextColor, int futureTextColor) {
        currentWeekText.setTextColor(ContextCompat.getColor(this, currentWeekTextColor));
        previousText.setTextColor(ContextCompat.getColor(this, previousTextColor));
        futureText.setTextColor(ContextCompat.getColor(this, futureTextColor));
    }

    public void setUpListView() {
        adapter = new EventsAdapter(eventsList, getApplicationContext());
        eventsListView.setAdapter(adapter);
        SingletonClass.getInstance().setAdapter(adapter);

        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = eventsList.get(position);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        MainActivity.this)
                        .setTitle(event.getTitle())
                        .setMessage(event.getEquipment())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog.show();
            }
        });

        eventsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final Event event = eventsList.get(position);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        MainActivity.this)
                        .setTitle(event.getTitle())
                        .setMessage(R.string.something_changed)
                        .setPositiveButton(R.string.change, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", event.getId());

                                Fragment argumentFragment = new AddEventFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                argumentFragment.setArguments(bundle);
                                fragmentManager.beginTransaction().replace(R.id.main_screen_layout, argumentFragment).commit();
                            }
                        })
                        .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        RealmResults<Event> result = realm.where(Event.class).equalTo("id", event.getId()).findAll();
                                        result.clear();
                                        switch (currentlySelectedTab) {
                                            case 1: previousEventsList();
                                            break;

                                            case 2: currentWeekEventsList();
                                            break;

                                            case 3: futureEventsList();
                                            break;
                                        }
                                    }
                                });
                            }
                        });
                alertDialog.show();

                return true;
            }
        });
    }
}
