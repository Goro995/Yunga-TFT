package tft.yunga.yungatft;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddEventFragment extends Fragment{

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    WheelDatePicker wheelDatePicker;

    EditText eventNameText;
    EditText itemsText;
    TextView headerText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_event_layout, container, false);

        Toolbar toolbar = view.findViewById(R.id.add_event_screen_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");


        wheelDatePicker = view.findViewById(R.id.wheel_date_picker);
        headerText = view.findViewById(R.id.toolbar_task_name_text);
        eventNameText = view.findViewById(R.id.name_of_task_text);
        itemsText = view.findViewById(R.id.description_text);

        wheelDatePickerSetUp();
        fieldsSetUp("", "", "2017-11-11");

        return view;
    }

    private void wheelDatePickerSetUp() {
        wheelDatePicker.setVisibleItemCount(5);
        wheelDatePicker.setSelectedItemTextColor(R.color.text_color);
        wheelDatePicker.setCurved(true);
        wheelDatePicker.setItemTextSize(120);
    }

    private void fieldsSetUp(String name, String description, String dueDate) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(dueDate));
        } catch (ParseException pe) {
            Log.d("TAG", "ParseException: " + pe);
        }
        eventNameText.setText(name);
        headerText.setText(eventNameText.getText());
        itemsText.setText(description);
        wheelDatePicker.setSelectedDay(calendar.get(Calendar.DAY_OF_MONTH));
        // Incrementing by one is because of month count starts from 0
        wheelDatePicker.setSelectedMonth(calendar.get(Calendar.MONTH) + 1);
        wheelDatePicker.setSelectedYear(calendar.get(Calendar.YEAR));
    }
}
