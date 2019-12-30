package com.example.professt.publicawareness;

import com.google.android.gms.maps.model.LatLng;

public class MapBean {

    String details,types,dates,times,location,people,name;
    LatLng temp;

    public MapBean(String details, String types, String dates, String times, String location, String people, String name, LatLng temp) {
        this.details = details;
        this.types = types;
        this.dates = dates;
        this.times = times;
        this.location = location;
        this.people = people;
        this.name = name;
        this.temp = temp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getTemp() {
        return temp;
    }

    public void setTemp(LatLng temp) {
        this.temp = temp;
    }
}
