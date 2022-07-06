package me.splerix.bowbattle.Objects;

public class TimeFormat {

    public String getFormatedTime(int seconds) {

        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds -= minutes * 60;
        minutes -= hours * 60;
        String s = "";
        if (minutes > 0)
            s += "" + minutes + ":";
        if (seconds > 0) {
            if (minutes <= 0) {
                if (seconds < 10)
                    s += "0:0" + seconds;
                else
                    s += "0:" + seconds;
            } else {
                if (seconds < 10)
                    s += "0" + seconds;
                else
                    s += seconds;
            }
        } else {
            s += "00";
        }
        return s;
    }
}
