package online.diary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



/* ------------Created by Manigandan S. on February 25,2018----------------*/


public class Tags extends AppCompatActivity {
    private ArrayList<String> result=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView resultview;
    List<AddEntry> artists;
    DatabaseReference databaseArtists;
    String toolt;
    String name;
    ImageView im;

    public Tags() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        Intent i=this.getIntent();
        //-------------------add mob-------------------//

        //---------------------------------------------//
        name=i.getExtras().getString("tag");
        databaseArtists = FirebaseDatabase.getInstance().getReference("messages");
        toolt = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("parsetext", "");
        artists = new ArrayList<>();
        databaseArtists.keepSynced(true);
        resultview= (ListView) findViewById(R.id.listView);

        im= (ImageView) findViewById(R.id.offline);
        if(toolt.equals(""))
        {
            im.setVisibility(View.VISIBLE);
            resultview.setVisibility(View.GONE);
        }
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
                    AddEntry artist = postSnapshot.getValue(AddEntry.class);
                    //adding artist to the list
                    if(artist.getmDUser().equals(toolt)&&artist.getmETags().equals(name))
                        artists.add(artist);
                }

                //creating adapter
                DiaryModel artistAdapter = new DiaryModel(Tags.this, artists);
                //attaching adapter to the listview
                resultview.setAdapter(artistAdapter);
                resultview.post(new Runnable() {
                    @Override
                    public void run() {
                        resultview.setSelection(resultview.getCount()+1);
                    }
                });
                resultview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //getting the selected artist
                        AddEntry artist = artists.get(i);

                        Intent intent = new Intent(getApplicationContext(), ViewEntries.class);

                        //putting artist name and id to intent
                        intent.putExtra("TITLE", artist.getmATitle());
                        intent.putExtra("DATE", artist.getmCDate());
                        intent.putExtra("DESCRIPTION", artist.getmBDescription());
                        intent.putExtra("TAG", artist.getmETags());

                        //starting the activity with intent
                        startActivity(intent);
                    }
                });
                resultview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AddEntry artist = artists.get(i);
                        showUpdateDeleteDialog(artist.getmATitle(),artist.getmBDescription(),artist.getmCDate(),artist.getmDUser(),artist.getmETags(),artist.getmFId());
                        return true;
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void showUpdateDeleteDialog(String mATitle, String mBDescription, String mCDate, String mDUser, String mETags, final String mFId) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(mATitle);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteArtist(mFId);
                b.dismiss();

            }
        });
    }
    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("messages").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
}
