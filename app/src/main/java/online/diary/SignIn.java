package online.diary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users/");
        final String toolt=this.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("parsetext","");
        final EditText regNo= (EditText) findViewById(R.id.regNo);
        final EditText psd= (EditText) findViewById(R.id.psd);
        final TextView t= (TextView) findViewById(R.id.textViewEmail);
        Button sin= (Button) findViewById(R.id.sin);
        LinearLayout ll= (LinearLayout) findViewById(R.id.ll);
        RelativeLayout rl= (RelativeLayout) findViewById(R.id.rl);
        Button sout= (Button) findViewById(R.id.sign_out);
        final TextView wrong= (TextView) findViewById(R.id.wrong);
        t.setText(toolt);
        sout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignIn.this, "Signed Out Successfully.", Toast.LENGTH_SHORT).show();
                getSharedPreferences("MyPref", Context.MODE_PRIVATE).edit().putString("parsetext","").apply();
                Intent a=new Intent(SignIn.this,MainActivity.class);
                startActivity(a);
            }
        });
        if(toolt.equals(""))
        {
            ;
        }
        else
            ll.setVisibility(View.GONE);

        TextView sup= (TextView) findViewById(R.id.sup);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignIn.this,SignUp.class);
                startActivity(i);
            }
        });

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String reg=regNo.getText().toString();
                final String pd=psd.getText().toString();
                if ( reg.equals("")||pd.equals("")) {
                    Toast.makeText(SignIn.this, "Please fill all the details.", Toast.LENGTH_SHORT).show();
                }else{
                    mRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String value = dataSnapshot.getValue(String.class);
                            String[] val = value.split(",");
                            if(val[0].equals(reg)&&val[2].equals(pd))
                            {
                                Toast.makeText(SignIn.this, "Signed In Successfully.", Toast.LENGTH_SHORT).show();
                                getSharedPreferences("MyPref", Context.MODE_PRIVATE).edit().putString("parsetext",reg).apply();
                                Intent a=new Intent(SignIn.this,MainActivity.class);
                                startActivity(a);

                            }
                            else
                            {
                                wrong.setText("Email and Password Combination is wrong!.");
                                wrong.setTextColor(Color.parseColor("#ed0d11"));
                            }


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
        });
    }
}
