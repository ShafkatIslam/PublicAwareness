package com.example.professt.publicawareness.Nearest_Fire_Station;

import android.util.Log;

import com.example.professt.publicawareness.Nearest_Hospital.NearbyHospitalsDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GeometryController {

    /** boolean variable for check loading */
    public static boolean loading;
    /** initializing arrayList to to carry nearBy hospital details */
    public static ArrayList<NearbyHospitalsDetail> detailArrayList = new ArrayList();

    /**
     * manipulateData method to manipulate data through from JSON format
     * @param buffer
     * */
    public static void manipulateData(StringBuffer buffer){

        /** loading variable initializing with true  */
        loading = true;
        try {
            /** detailArrayList to clear previous data */
            detailArrayList.clear();

            /** JSON parser initializing
             * @param buffer.toString()
             * */
            JSONObject jsonpObject = new JSONObject(buffer.toString());
            /** getting results array from JSON format */
            JSONArray array = jsonpObject.getJSONArray("results");

            /** adding json array to detailedHospitalArray  */
            for(int i=0; i<array.length(); i++){
                try {
                    JSONObject jsonObject = array.getJSONObject(i);
                    NearbyHospitalsDetail hospitalDetail = new NearbyHospitalsDetail();

                    if(jsonObject.getString("name")!=null)  hospitalDetail.setHospitalName(jsonObject.getString("name"));
                    else  hospitalDetail.setHospitalName("Not Available");

                    try {
                        hospitalDetail.setRating(String.valueOf(jsonObject.getDouble("rating")));
                    }catch (Exception e){
                        hospitalDetail.setRating("Not Available");
                    }

                    try {
                        if (jsonObject.getJSONObject("opening_hours").getBoolean("open_now"))  hospitalDetail.setOpeningHours("Opened");
                        else hospitalDetail.setOpeningHours("closed");
                    } catch (Exception e) {
                        hospitalDetail.setOpeningHours("Not Available");
                    }

                    hospitalDetail.setAddress(jsonObject.getString("vicinity"));
                    hospitalDetail.setGeometry(new double[]{jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                            jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng")});

                    detailArrayList.add(hospitalDetail);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        /** initializing loading variable to false when list when data loaded to arrayList */
        loading = false;
        Log.d("Array Loaded with size ", "Size of "+detailArrayList.size());
    }
}

