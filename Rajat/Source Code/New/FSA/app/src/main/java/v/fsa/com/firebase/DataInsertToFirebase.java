package v.fsa.com.firebase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import v.fsa.com.startup.fsa.R;

public class DataInsertToFirebase extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_insert_to_firebase);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTitleField =(EditText) findViewById(R.id.unique_id);
        loginToFirebase();
        btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postData();


            }
        });

    }
    private void loginToFirebase() {
        String email = getString(R.string.firebase_email);
        String password = getString(R.string.firebase_password);
        // Authenticate with Firebase and subscribe to updates
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    subscribeToUpdates();
                    Log.d(TAG, "firebase auth success");
                } else {
                    Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }
    private void postData(){


        final String title = mTitleField.getText().toString();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            Toast.makeText(getApplicationContext(),"User Null",Toast.LENGTH_LONG).show();
        }
        else{
           final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();



            mDatabase.child("tracking-ad9cb").child(userId).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get user value
                            User user = dataSnapshot.getValue(User.class);

                            // [START_EXCLUDE]
                            if (user == null) {
                                // User is null, error out
                               // Log.e(TAG, "User " + userId + " is unexpectedly null");

                                writeNewPost(userId, "amin", title, "head");
                              //  Toast.makeText(DataInsertToFirebase.this,"User " + userId + " is unexpectedly null", Toast.LENGTH_SHORT).show();
                            } else {
                                // Write new post
                                writeNewPost(userId, "amin", title, "bead");
                            }

                            // Finish this Activity, back to the stream

                            finish();
                            // [END_EXCLUDE]
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                            // [START_EXCLUDE]
                            setEditingEnabled(true);
                            // [END_EXCLUDE]
                        }

        });



    }
}
    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("tracking-ad9cb/fire_app").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("/tracking-ad9cb/" + key, postValues);
        //childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
        childUpdates.put("/fire_app/"+title+"/", postValues);

        mDatabase.updateChildren(childUpdates);
    }

    private void setMarker(DataSnapshot dataSnapshot) {

        String key = dataSnapshot.getKey();
        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
       // map.addMarker(new MarkerOptions().position(location).title(firstname,lastname).snippet(dob,dod));
        System.out.println("Data List"+value);

    }
    private void subscribeToUpdates() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("fire_app");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                setMarker(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                setMarker(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}