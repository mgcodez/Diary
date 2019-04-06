package online.diary;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



/* ------------Created by Manigandan S. on February 25,2018----------------*/


public class CalendarAc extends AppCompatActivity {
    GridView resultview;
    List<CalendarNav> artists;
    DatabaseReference databaseArtists;
    String toolt;
    String name;
    ImageView im;

    public CalendarAc() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        resultview = (GridView) findViewById(R.id.gd);
        databaseArtists = FirebaseDatabase.getInstance().getReference("messages");
        toolt = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("parsetext", "");
        artists = new ArrayList<>();
        databaseArtists.keepSynced(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artists.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    CalendarNav artist = postSnapshot.getValue(CalendarNav.class);
                    //adding artist to the list
                    if (artist.getmDUser().equals(toolt))
                        artists.add(artist);
                }

                //creating adapter
                Photos artistAdapter = new Photos(CalendarAc.this, artists);
                //attaching adapter to the listview
                resultview.setAdapter(artistAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}