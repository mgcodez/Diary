package online.diary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



/* ------------Created by Manigandan S. on February 25,2018----------------*/


public class CalEntry extends AppCompatActivity {
    private ArrayList<String> result=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        Intent i=this.getIntent();
        final String mo=i.getExtras().getString("month");
        final String yer=i.getExtras().getString("year");
        final String vl[]=mo.split(",");
        final String toolt=this.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("parsetext","");
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("messages/");
        mRef.keepSynced(true);
        final ListView l= (ListView) findViewById(R.id.listView);
        l.post(new Runnable() {
            @Override
            public void run() {
                l.setSelection(l.getCount()+1);
            }
        });
        ImageView im= (ImageView) findViewById(R.id.offline);
        if(toolt.equals(""))
        {
            im.setVisibility(View.VISIBLE);
            l.setVisibility(View.GONE);
        }
        adapter=new ArrayAdapter<String>(this, R.layout.diarymodel, R.id.mtext,result);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getPositionForView(view);
                String na=result.get(pos).toString();
                Intent i=new Intent(CalEntry.this,ViewEntries.class);
                i.putExtra("res",na);
                startActivity(i);
            }
        });
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                String[] val = value.split(",");
                String[] al=value.split("/");
                String[] ll=al[2].split(",");
                if(vl[0].equals(al[1])&&yer.equals(ll[0])&&val[4].equals(toolt)) {
                    result.add(val[6]);
                }
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
