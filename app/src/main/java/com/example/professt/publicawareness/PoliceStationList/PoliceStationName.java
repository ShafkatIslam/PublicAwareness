package com.example.professt.publicawareness.PoliceStationList;

public class PoliceStationName {

    private String id;

    private static PoliceStationName policeStationName= new PoliceStationName();

    public PoliceStationName() {
    }

    public static PoliceStationName getInstance( ) {
        return policeStationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
