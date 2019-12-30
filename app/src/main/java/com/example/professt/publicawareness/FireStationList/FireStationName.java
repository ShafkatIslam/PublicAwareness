package com.example.professt.publicawareness.FireStationList;

public class FireStationName {

    private String id;

    private static FireStationName fireStationName= new FireStationName();

    public FireStationName() {
    }

    public static FireStationName getInstance( ) {
        return fireStationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
