package loginsocial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import hoanvusolution.pikapaka.R;

/**
 * Created by MrThanhPhong on 2/24/2016.
 */
public class FriendsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youractivity);

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata"); // receiving data from  MainActivity.java

        JSONArray friendslist;
        ArrayList<String> friends = new ArrayList<String>();
        try {
            friendslist = new JSONArray(jsondata);
            for (int l=0; l < friendslist.length(); l++) {
                friends.add(friendslist.getJSONObject(l).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // adapter which populate the friends in listview
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, friends);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}