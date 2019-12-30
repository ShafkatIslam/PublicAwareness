package v.fsa.com.startup.fsa.v.fsa.com.config.Config;

/**
 * Created by cursor on 10/16/2017.
 */

public class MapLocationHistory {

    String longnitute;
    String countryName;
    String cityName;
    String postalCode;

    String total_requsition;
    String number_of_slot;

    public String getTotal_requsition() {
        return total_requsition;
    }

    public void setTotal_requsition(String total_requsition) {
        this.total_requsition = total_requsition;
    }

    public String getNumber_of_slot() {
        return number_of_slot;
    }

    public void setNumber_of_slot(String number_of_slot) {
        this.number_of_slot = number_of_slot;
    }



    public MapLocationHistory(
            String address, String latitude,
            String longnitute, String countryName, String cityName,
            String postalCode, String knownName, String stateName,
            String total_requsition,String number_of_slot

    ) {
        this.longnitute = longnitute;
        this.countryName = countryName;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.knownName = knownName;
        this.stateName = stateName;
        this.address = address;
        this.latitude = latitude;

        this.total_requsition=total_requsition;
        this.number_of_slot=number_of_slot;

    }

    String knownName;
    String stateName;
    String address;
    String latitude;



    public String getLongnitute() {
        return longnitute;
    }

    public void setLongnitute(String longnitute) {
        this.longnitute = longnitute;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getKnownName() {
        return knownName;
    }

    public void setKnownName(String knownName) {
        this.knownName = knownName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


}
