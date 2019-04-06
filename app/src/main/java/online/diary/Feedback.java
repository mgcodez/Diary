package online.diary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



/* ------------Created by Manigandan S. on February 25,2018----------------*/


public class Feedback extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        final String toolt=this.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("parsetext","userFeedback");
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        final EditText addname= (EditText) findViewById(R.id.addName);
        final EditText addfeed= (EditText) findViewById(R.id.addFeed);
        FloatingActionButton send= (FloatingActionButton) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=addname.getText().toString();
                String feed=addfeed.getText().toString();
                String combo=name+","+feed+","+toolt;
                if ( name.equals("")|| feed.equals("")) {
                    Toast.makeText(Feedback.this, "Please fill all the details.", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseDatabase.child("feedback").push().setValue(combo);
                    Toast.makeText(Feedback.this, "Thank you for your valuable feedback.", Toast.LENGTH_SHORT).show();
                    Intent home=new Intent(Feedback.this,MainActivity.class);
                    startActivity(home);
                    finish();
                }
            }
        });
    }
}
