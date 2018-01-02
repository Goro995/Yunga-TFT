package tft.yunga.yungatft.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import tft.yunga.yungatft.Event;
import tft.yunga.yungatft.R;
import tft.yunga.yungatft.SingletonClass;

public class EventsAdapter extends ArrayAdapter<Event> {

    private Context context;
    private Realm realm;
    private LayoutInflater inflater;
    private ArrayList<Event> eventsList;

    private static class ViewHolder {
        TextView textTitle;
        TextView textEquipment;
        TextView textDate;
    }

    public EventsAdapter(ArrayList<Event> data, Context context) {
        super(context, R.layout.event_item, data);
        this.eventsList = data;
        this.context = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.event_item, parent, false);
            viewHolder.textTitle = (TextView) convertView.findViewById(R.id.title_text);
            viewHolder.textEquipment = (TextView) convertView.findViewById(R.id.equipment_text);
            viewHolder.textDate = (TextView) convertView.findViewById(R.id.date_text);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        if (event.getTitle() != null) {
            viewHolder.textTitle.setText(event.getTitle());
        } else {
            viewHolder.textTitle.setText("");
        }
        if (event.getEquipment() != null) {
            viewHolder.textEquipment.setText(event.getEquipment());
        } else {
            viewHolder.textEquipment.setText("");
        }
        if (event.getEventDate() != null) {
            String formattedDate = SingletonClass.getInstance().dateFormat.format(event.getEventDate());
            viewHolder.textDate.setText(formattedDate);
        } else {
            viewHolder.textDate.setText("Без даты");
        }

        return convertView;
    }
}
