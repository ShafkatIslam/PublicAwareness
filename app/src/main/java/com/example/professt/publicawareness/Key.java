package com.example.professt.publicawareness;

public class Key {
    //main url or address
    //public static final String MAIN_URL = "http://192.168.43.231";
    public static final String MAIN_URL = "https://www.tikabarta.com";

    //access db from device for mother
    public static  final String SIGNUP_URL = MAIN_URL+"/alert/signup.php";
    public static  final String LOGIN_URL = MAIN_URL+"/alert/login.php";
    public static  final String EMERGENCY_SMS_URL = MAIN_URL+"/alert/emergency_sms.php";
    public static  final String SHOW_EMERGENCY = MAIN_URL+"/alert/show_emergency.php";
    public static  final String SHOW_DETAILS = MAIN_URL+"/alert/show_details.php?";
    public static  final String OTP_URL = MAIN_URL+"/alert/otp.php";
    public static  final String VERIFY_URL = MAIN_URL+"/alert/verify.php";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "Name";
    public static final String KEY_PASSWORD = "Pass";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_ADD = "Address";
    public static final String KEY_CELL = "Cell";
    public static final String KEY_GNDER = "Gender";
    public static final String KEY_OTP = "Otp";
    public static final String KEY_VERIFY = "Verify";

    public static final String KEY_PEOPLE = "People";
    public static final String KEY_LOCATION = "Location";
    public static final String KEY_DETAILS = "Details";
    public static final String KEY_TIME = "Time";
    public static final String KEY_DATE = "Date";
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_ENCODE = "Encoded_String";
    public static final String KEY_IMAGE = "Path";


    public static final String SHARED_PREF_NAME = "com.example.professt.alertservice.userlogin"; //pcakage name+ id(any name)

    //This would be used to store the cell of current logged in user
    public static final String CELL_SHARED_PREF = "cell";
    public static final String USER_SHARED_PREF = "Username";

    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";
}
