package online.diary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Audio.AudioColumns.ARTIST_ID;
import static online.diary.R.id.wrong;



/* ------------Created by Manigandan S. on February 25,2018----------------*/


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //String[] result;
    private ArrayList<String> result = new ArrayList<>();
    private ArrayList<String> mylist = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> dapter;
    private ListView resultview;
    List<AddEntry> artists;
    DatabaseReference databaseArtists;
    String toolt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseArtists = FirebaseDatabase.getInstance().getReference("messages");
        artists = new ArrayList<>();
        databaseArtists.keepSynced(true);
        ImageView im = (ImageView) findViewById(R.id.offline);
        toolt = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("parsetext", "");
        resultview = (ListView) findViewById(R.id.listView);
        //ListView resultvi= (ListView) findViewById(R.id.listView);


        if (toolt.equals("")) {
            im.setVisibility(View.VISIBLE);
            resultview.setVisibility(View.GONE);
        }

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AddNew.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent a=new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
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
                 if(artist.getmDUser().equals(toolt))
                artists.add(artist);
            }

            //creating adapter
            DiaryModel artistAdapter = new DiaryModel(MainActivity.this, artists);
            //attaching adapter to the listview
            resultview.setAdapter(artistAdapter);
            resultview.post(new Runnable() {
                @Override
                public void run() {
                    resultview.setSelection(resultview.getCount() + 1);
                }
            });
         //  resultview.setStackFromBottom(true);
           // resultview.smoothScrollToPosition(artistAdapter.getCount()-1);
            resultview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //getting the selected artist
                    AddEntry artist = artists.get(i);

                    //creating an intent
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

        dialogBuilder.setTitle(mBDescription);
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_prof:
                Intent register=new Intent(MainActivity.this,SignIn.class);
                startActivity(register);
                break;
            case R.id.nav_photos:
                Intent pt=new Intent(MainActivity.this,CalendarAc.class);
                startActivity(pt);
                break;
            case R.id.nav_general:
                Intent gen=new Intent(MainActivity.this,Tags.class);
                gen.putExtra("tag","general");
                startActivity(gen);
                break;
            case R.id.nav_functions:
                Intent fun=new Intent(MainActivity.this,Tags.class);
                fun.putExtra("tag","functions");
                startActivity(fun);
                break;
            case R.id.nav_travel:
                Intent dress=new Intent(MainActivity.this,Tags.class);
                dress.putExtra("tag","travel");
                startActivity(dress);
                break;
            case R.id.nav_love:
                Intent furniture=new Intent(MainActivity.this,Tags.class);
                furniture.putExtra("tag","love");
                startActivity(furniture);
                break;
            case R.id.nav_sweet:
                Intent place=new Intent(MainActivity.this,Tags.class);
                place.putExtra("tag","sweet_moments");
                startActivity(place);
                break;
            case R.id.nav_sad:
                Intent con=new Intent(MainActivity.this,Tags.class);
                con.putExtra("tag","sads_angry");
                startActivity(con);
                break;
            case R.id.nav_feedback:
                Intent feed=new Intent(MainActivity.this,Feedback.class);
                startActivity(feed);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}