package com.example.professt.publicawareness.Nearest_Fire_Station;

import java.util.Arrays;

public class NearbyFireStationsDetail {

    /** hospitalName variable */
    private String fireStationsName;
    /** ratting variable */
    private String rating;
    /** openingHours variable */
    private String openingHours;
    /** address variable */
    private String address;
    /** geometry - latitude and longitude array */
    private double[] geometry;

    public String getFireStationsName() {
        return fireStationsName;
    }

    public void setFireStationsName(String fireStationsName) {
        this.fireStationsName = fireStationsName;
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
        return "NearbyFireStationsDetail{" +
                ", hospitalName='" + fireStationsName + '\'' +
                ", rating=" + rating +
                ", openingHours='" + openingHours + '\'' +
                ", address='" + address + '\'' +
                ", geometry=" + Arrays.toString(geometry) +
                '}';
    }
}
