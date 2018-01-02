package tft.yunga.yungatft;

import org.joda.time.DateTime;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Event extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private String equipment;
    private Date eventDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getEquipment() {
        return this.equipment;
    }

    public void setEventDate(Date date) {
        this.eventDate = date;
    }

    public Date getEventDate() {
        return this.eventDate;
    }
}
