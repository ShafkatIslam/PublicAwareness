package v.fsa.com.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import v.fsa.com.startup.fsa.LoginActivity;
import v.fsa.com.startup.fsa.R;
import v.fsa.com.startup.fsa.v.fsa.com.config.Config.Config;

public class UserRegistration extends AppCompatActivity {

    EditText txtUserName,txtPassword,txtConfirmPassword;
    Button btnRegistration;

    private ProgressDialog progressDialog;
    private String URL_USER_REGISTRATION= Config.getServerLink()+"user_registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        txtUserName=(EditText)findViewById(R.id.txt_userName);
        txtPassword=(EditText)findViewById(R.id.txt_userPass);
        txtConfirmPassword=(EditText)findViewById(R.id.txt_userConPass);
        btnRegistration=(Button)findViewById(R.id.btnUserRegistration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userRegistration();
            }
        });

    }

    private void userRegistration(){

        final String UserName=txtUserName.getText().toString();
        final String UserPass=txtPassword.getText().toString();
        String ConPass=txtConfirmPassword.getText().toString();

        if(UserName.isEmpty())
            Toast.makeText(getApplicationContext(),"User name is empty.....",Toast.LENGTH_LONG).show();
        else if(UserPass.isEmpty())
            Toast.makeText(getApplicationContext(),"User Password is empty....",Toast.LENGTH_LONG).show();
        else if(!UserPass.equalsIgnoreCase(ConPass))
            Toast.makeText(getApplicationContext(),"Password not match with confirm password....",Toast.LENGTH_LONG).show();
        else{

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

                                Toast.makeText(getApplicationContext(),obj.getString("msg").toString(),Toast.LENGTH_LONG).show();

                                if (obj.getInt("status") == 0) {

                                    Intent i=new Intent(UserRegistration.this,LoginActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                }
                            }
                            catch (JSONException e){
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
                    params.put("type","3");
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        }

    }
}
