package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    //"EEE dd/MM/yyyy HH:mm"
    //MODIFIES: this
    //EFFECTS: return the current time as a string
    public String getTime() {
        SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd HH:mm EEE");
        Date date = new Date();
        return time.format(date);
    }
}
