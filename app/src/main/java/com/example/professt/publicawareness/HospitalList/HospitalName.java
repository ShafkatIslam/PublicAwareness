package com.example.professt.publicawareness.HospitalList;

public class HospitalName {

    private String id;

    private static HospitalName hospitalName= new HospitalName();

    public HospitalName() {
    }

    public static HospitalName getInstance( ) {
        return hospitalName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
