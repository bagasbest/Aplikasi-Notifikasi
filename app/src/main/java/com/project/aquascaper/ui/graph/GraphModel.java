package com.project.aquascaper.ui.graph;

import android.os.Parcel;
import android.os.Parcelable;

public class GraphModel implements Parcelable {


    private String dayOfWeek;
    private int week;
    private String parameter;
    private double value;

    public GraphModel(){}

    protected GraphModel(Parcel in) {
        dayOfWeek = in.readString();
        week = in.readInt();
        parameter = in.readString();
        value = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dayOfWeek);
        dest.writeInt(week);
        dest.writeString(parameter);
        dest.writeDouble(value);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
