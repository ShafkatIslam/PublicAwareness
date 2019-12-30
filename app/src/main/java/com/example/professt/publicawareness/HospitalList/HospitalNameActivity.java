package com.example.professt.publicawareness.HospitalList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.professt.publicawareness.LoginActivity2;
import com.example.professt.publicawareness.R;

public class HospitalNameActivity extends AppCompatActivity {

    private ListView listView1;
    ArrayAdapter<String> adapter1;
    String[] hospitalNames;
    private SearchView searchView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_name);

        listView1 = (ListView) findViewById(R.id.listViewId1);
        searchView1 = (SearchView) findViewById(R.id.searchViewId1);

        hospitalNames = getResources().getStringArray(R.array.hospital_list_header);   //fetch the string array from resourse file's string folder and keep them in the String array

        adapter1 = new ArrayAdapter<String>(HospitalNameActivity.this,R.layout.sample_view,R.id.textViewId,hospitalNames); //creating object of ArrayAdapter....thre are 4 parameters in ArrayAdapter
        listView1.setAdapter(adapter1);   //setting the adapter in listView

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HospitalNameActivity.this,HospitalDetailsActivity.class);
                String value = adapter1.getItem(position);
                HospitalName hospitalName = HospitalName.getInstance();
                hospitalName.setId(value);

                startActivity(intent);
            }
        });


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {  //Listener add with searchView
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {        //When we search a text or String in the serachView the onQueryTextChange method will help to find

                adapter1.getFilter().filter(newText);              //we will find the String by the getFilter method and it's filter method from the adapter which makes the sampleView in ListView.
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(HospitalNameActivity.this,LoginActivity2.class);
        startActivity(intent);

    }
}
