package tft.yunga.yungatft;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import tft.yunga.yungatft.adapters.EventsAdapter;


public class AddEventFragment extends Fragment{

    WheelDatePicker wheelDatePicker;

    EditText eventNameText;
    EditText itemsText;
    TextView headerText;
    TextView saveText;
    Boolean isNewEvent;
    Event eventToChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_layout, container, false);

        Toolbar toolbar = view.findViewById(R.id.add_event_screen_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");

        wheelDatePicker = view.findViewById(R.id.wheel_date_picker);
        headerText = view.findViewById(R.id.toolbar_task_name_text);
        eventNameText = view.findViewById(R.id.name_of_task_text);
        itemsText = view.findViewById(R.id.description_text);
        saveText = view.findViewById(R.id.toolbar_save_text);

        wheelDatePickerSetUp();
        handlePassedData();

        eventNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                headerText.setText(eventNameText.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventNameText.getText().length() > 0) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    Event event = new Event();
                    if (!isNewEvent) {
                        event = eventToChange;
                        event.setTitle(eventNameText.getText().toString());
                        event.setEquipment(itemsText.getText().toString());
                        event.setEventDate(wheelDatePicker.getCurrentDate());
                    } else {
                        Number maxID = realm.where(Event.class).max("id");
                        event.setTitle(eventNameText.getText().toString());
                        event.setEquipment(itemsText.getText().toString());
                        event.setEventDate(wheelDatePicker.getCurrentDate());
                        if (maxID != null) {
                            event.setId(maxID.intValue() + 1);
                        } else {
                            event.setId(1);
                        }
                    }
                    realm.copyToRealmOrUpdate(event);
                    realm.commitTransaction();
                    getActivity().onBackPressed();
                }
            }
        });
        return view;
    }

    private void handlePassedData() {
        int id = 0;
        Realm realm = Realm.getDefaultInstance();
        try {
            id = getArguments().getInt("id");
            eventToChange = realm.where(Event.class).equalTo("id", id).findFirst();
            fieldsSetUp(eventToChange.getTitle(), eventToChange.getEquipment(), eventToChange.getEventDate());
            isNewEvent = false;
        } catch (NullPointerException exсeption) {
            isNewEvent = true;
            fieldsSetUp("", "", Calendar.getInstance().getTime());
        }
    }

    private void wheelDatePickerSetUp() {
        wheelDatePicker.setVisibleItemCount(5);
        wheelDatePicker.setSelectedItemTextColor(R.color.text_color);
        wheelDatePicker.setCurved(true);
        wheelDatePicker.setItemTextSize(120);
    }

    private void fieldsSetUp(String name, String equipment, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (name != null) {
            eventNameText.setText(name);
            headerText.setText(name);
        }
        if(equipment != null) {
            itemsText.setText(equipment);
        }
        wheelDatePicker.setSelectedDay(calendar.get(Calendar.DAY_OF_MONTH));
        // Incrementing by one is because of month count starts from 0
        wheelDatePicker.setSelectedMonth(calendar.get(Calendar.MONTH) + 1);
        wheelDatePicker.setSelectedYear(calendar.get(Calendar.YEAR));
    }
}
