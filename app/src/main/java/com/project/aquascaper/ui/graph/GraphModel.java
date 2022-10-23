package com.project.aquascaper.ui.graph;

import android.os.Parcel;
import android.os.Parcelable;

public class GraphModel implements Parcelable {

    private String notification;
    private String hour;
    private String dayOfWeek;
    private int week;
    private String parameter;
    private Long timeInMillis;

    public GraphModel(){}

    protected GraphModel(Parcel in) {
        notification = in.readString();
        hour = in.readString();
        dayOfWeek = in.readString();
        week = in.readInt();
        parameter = in.readString();
        if (in.readByte() == 0) {
            timeInMillis = null;
        } else {
            timeInMillis = in.readLong();
        }
    }

    public static final Creator<GraphModel> CREATOR = new Creator<GraphModel>() {
        @Override
        public GraphModel createFromParcel(Parcel in) {
            return new GraphModel(in);
        }

        @Override
        public GraphModel[] newArray(int size) {
            return new GraphModel[size];
        }
    };

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(Long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(notification);
        parcel.writeString(hour);
        parcel.writeString(dayOfWeek);
        parcel.writeInt(week);
        parcel.writeString(parameter);
        if (timeInMillis == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(timeInMillis);
        }
    }
}
