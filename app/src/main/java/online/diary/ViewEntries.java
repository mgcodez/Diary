package online.diary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.R.attr.width;



/* ------------Created by Manigandan S. on February 25,2018----------------*/


public class ViewEntries extends AppCompatActivity {
    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);
        t1= (TextView) findViewById(R.id.tit);
        t2= (TextView) findViewById(R.id.dat);
        t3=(TextView)findViewById(R.id.tg);
        t4= (TextView) findViewById(R.id.ent);
        Intent i=this.getIntent();
        final String title=i.getExtras().getString("TITLE");
        final String date=i.getExtras().getString("DATE");
        final String desc=i.getExtras().getString("DESCRIPTION");
        final String tag=i.getExtras().getString("TAG");

        t2.setText(date);
        t3.setText("#"+tag);
        t4.setText(desc);

    }
}
