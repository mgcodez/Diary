package online.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText mail= (EditText) findViewById(R.id.mail);
        final EditText name= (EditText) findViewById(R.id.name);
        final EditText pass= (EditText) findViewById(R.id.pass);
        final EditText cpass= (EditText) findViewById(R.id.cpass);
        TextView sin= (TextView) findViewById(R.id.sin);
        Button sup= (Button) findViewById(R.id.sup);
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUp.this,SignIn.class);
                startActivity(i);
            }
        });
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ml=mail.getText().toString();
                String nam=name.getText().toString().toUpperCase();
                String pd=pass.getText().toString();
                String cpd=cpass.getText().toString();
                String combo=ml+","+nam+","+pd+","+ml;
                if ( ml.equals("")||nam.equals("")||pd.equals("")||cpd.equals("")) {
                    Toast.makeText(SignUp.this, "Please fill all the details.", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseDatabase.child("Users").push().setValue(combo);
                    Toast.makeText(SignUp.this,"Successfully Registered!",Toast.LENGTH_SHORT).show();
                    Intent home=new Intent(SignUp.this,SignIn.class);
                    startActivity(home);
                }
            }
        });
    }
}
