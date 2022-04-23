package com.example.actmysql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.actmysql.adapter.TemanAdapter;
import com.example.actmysql.database.DBController;
import com.example.actmysql.database.Teman;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private TemanAdapter adapter;
    private ArrayList<Teman> temanArrayList;
    private FloatingActionButton fab;
    DBController dbc = new DBController(this);

    private static final String TAG = MainActivity.class.getSimpleName();
    private static String url_select = "http://127.0.0.1/umyTI/bacateman.php";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_TELPON = "telpon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingBtn);
        readData();
        adapter = new TemanAdapter(temanArrayList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,TemanBaru.class);
                startActivity(i);
            }
        });
    }
    /*public void readData(){
        ArrayList<HashMap<String,String>> listTeman = dbc.getAllTeman();
        temanArrayList = new ArrayList<>();
//        Pindah hasil query
        for (int i = 0; i < listTeman.size(); i++){
            Teman t = new Teman();
            t.setId(listTeman.get(i).get("id").toString());
            t.setNama(listTeman.get(i).get("nama").toString());
            t.setTelp(listTeman.get(i).get("telp").toString());
//            Masukkan ke arrayList
            temanArrayList.add(t);
        }
    }*/

    public void readData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jArr = new JsonArrayRequest(url_select,new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response){
                Log.d(TAG, response.toString());
                //Parsing json
                for (int i=0; i<response.length();i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Teman item = new Teman();
                        item.setId(obj.getString(TAG_ID));
                        item.setNama(obj.getString(TAG_NAMA));
                        item.setTelp(obj.getString(TAG_TELPON));
                        temanArrayList.add(item);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(MainActivity.this,"gagal",Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jArr);
    }
}