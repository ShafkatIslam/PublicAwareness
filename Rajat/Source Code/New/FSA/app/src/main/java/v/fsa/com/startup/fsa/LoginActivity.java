package v.fsa.com.startup.fsa;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import v.fsa.com.firebase.DataInsertToFirebase;
import v.fsa.com.firebase.SharedPrefManager;
import v.fsa.com.share.AndroidMultiPartEntity.MapsExample;
import v.fsa.com.startup.fsa.v.fsa.com.config.Config.Config;
import v.fsa.com.startup.fsa.v.fsa.com.config.Config.RequestUserPermission;
import v.fsa.com.user.UserRegistration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends Activity {

    Button btpic, btnup,btnMap;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "http://192.168.0.105/AndroidFileUpload/image_upload.php";
    // LogCat tag
    private static final String TAG = LoginActivity.class.getSimpleName();
    

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 101;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    RequestUserPermission requestUserPermission = new RequestUserPermission(this);
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    ProgressDialog dialog;
    private Button btnCapturePicture,btnRecordVideo,btnLogin,btnCreateRegistration;

    // LogCat tag

    EditText txtUserName,txtPassword,txtConfirmPassword;
    private ProgressDialog progressDialog;
    private String URL_USER_REGISTRATION= Config.getServerLink()+"user_login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txtUserName=(EditText)findViewById(R.id.txtUserName);
        txtPassword=(EditText)findViewById(R.id.password);

        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();

                /*Intent i=new Intent(LoginActivity.this,MapsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
            }
        });

        btnCreateRegistration=(Button)findViewById(R.id.btnCreateRegistration);
        btnCreateRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(LoginActivity.this,UserRegistration.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
       /* requestUserPermission.verifyStoragePermissions();
        SharedPrefManager mm=new SharedPrefManager(getApplicationContext());
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        btpic = (Button) findViewById(R.id.cpic);

        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // record video
                recordVideo();
            }
        });
        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickpic();
            }
        });

        btnup = (Button) findViewById(R.id.up);

        btnMap=(Button) findViewById(R.id.google_map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,MapsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
*/


    }


    private void userLogin(){


        final String UserName=txtUserName.getText().toString();
        final String UserPass=txtPassword.getText().toString();

        if(UserName.isEmpty())
            Toast.makeText(getApplicationContext(),"User name is empty.....",Toast.LENGTH_LONG).show();
        else if(UserPass.isEmpty())
            Toast.makeText(getApplicationContext(),"User Password is empty....",Toast.LENGTH_LONG).show();
        else {

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_USER_REGISTRATION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), obj.getString("msg").toString(), Toast.LENGTH_LONG).show();




                              if (obj.getInt("status") == 2)
                                    {

                                        if(obj.getInt("user_type") == 3){

                                        }
                                        else{

                                            Intent i = new Intent(LoginActivity.this, MapsActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                        }



                                    }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_name", UserName);
                    params.put("user_pass", UserPass);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }


    private void recordVideo() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile = new
                File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator
                + "VID_" + timeStamp + ".mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        fileUri = Uri.fromFile(mediaFile);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);


    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }



    private void clickpic() {

        fileUri=null;

        // Check Camera
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
   /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {

            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), photo);
                File finalFile = new File(getRealPathFromURI(tempUri));
                //fileUri=finalFile.getPath();
                //fileUri = finalFile.getPath().toString()
                picturePath = finalFile.getPath();
                launchUploadActivity(true);
                //System.out.println(" Image Url "+finalFile);

            } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    launchUploadActivity(false);

                } else if (resultCode == RESULT_CANCELED) {

                    // user cancelled recording
                    Toast.makeText(getApplicationContext(),
                            "User cancelled video recording", Toast.LENGTH_SHORT)
                            .show();

                } else {
                    // failed to record video
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        }
    }*/

        private void launchUploadActivity(boolean isImage){


            //Toast.makeText(getApplicationContext(),"File =>"+fileUri,Toast.LENGTH_LONG).show();
            //System.out.println("Video Path = "+fileUri.getPath());
            Intent i = new Intent(LoginActivity.this, UploadActivity.class);

            if(!isImage)
                i.putExtra("filePath", fileUri.getPath());
            else
                i.putExtra("filePath", picturePath);

            i.putExtra("isImage", isImage);
            startActivity(i);
        }



}