package com.example.professt.publicawareness.Nearest_Police_Station;

import java.util.Arrays;

public class NearbyPoliceStationDetail {

    /** hospitalName variable */
    private String policeStationName;
    /** ratting variable */
    private String rating;
    /** openingHours variable */
    private String openingHours;
    /** address variable */
    private String address;
    /** geometry - latitude and longitude array */
    private double[] geometry;

    public String getPoliceStationName() {
        return policeStationName;
    }

    public void setPoliceStationName(String policeStationName) {
        this.policeStationName = policeStationName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double[] getGeometry() {
        return geometry;
    }

    public void setGeometry(double[] geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "NearbyPoliceStationDetail{" +
                ", hospitalName='" + policeStationName + '\'' +
                ", rating=" + rating +
                ", openingHours='" + openingHours + '\'' +
                ", address='" + address + '\'' +
                ", geometry=" + Arrays.toString(geometry) +
                '}';
    }
}
