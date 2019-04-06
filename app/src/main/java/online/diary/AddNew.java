package online.diary;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;



/* ------------Created by Manigandan S. on February 25,2018----------------*/


public class AddNew extends AppCompatActivity {
    /*----------------------------------------------*/
    String date;
    String title;
    String tags;
    String msg;
    String tandd;
    String toolt;
    String id;
    /*---------------------------------------------*/
    private DatabaseReference mFirebaseDatabase;
    private FirebaseStorage storage;
    Calendar calendar;
    String dat;
    ImageView addImage;
    private Uri filePath;
    String url;
    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    private final int PICK_IMAGE_REQUEST=71;
    StorageReference storageReference;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    int day,month,year;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        storage=FirebaseStorage.getInstance();
        storageReference= storage.getReference();
        final Dialog dialog = new Dialog(AddNew.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_profile);
        dialog.setTitle("Oops!");
        Button b= (Button) dialog.findViewById(R.id.gs);
        Button c= (Button) dialog.findViewById(R.id.can);
        Button button= (Button)findViewById(R.id.button);
        addImage= (ImageView) findViewById(R.id.addImage);
        LinearLayout addImLL= (LinearLayout) findViewById(R.id.addIm);
       toolt=this.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("parsetext","");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("messages");
        if(toolt.equals(""))
        {
            dialog.show();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),SignIn.class);
                    startActivity(i);
                }
            });
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }
            });
        }
        addImLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        final TextView addDate= (TextView) findViewById(R.id.addDate);
        final EditText addTitle= (EditText) findViewById(R.id.addTitle);
        final EditText addMessage= (EditText) findViewById(R.id.addMessage);
        final Spinner addTags= (Spinner) findViewById(R.id.addTags);
        FloatingActionButton save= (FloatingActionButton) findViewById(R.id.save);
        calendar=Calendar.getInstance();
        day=calendar.get(Calendar.DAY_OF_MONTH);
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);
        month=month+1;
        if(month==1)
            dat="January "+day+","+year;
        else if(month==2)
            dat="February "+day+","+year;
        else if(month==3)
            dat="March "+day+","+year;
        else if(month==4)
            dat="April "+day+","+year;
        else if(month==5)
            dat="May "+day+","+year;
        else if(month==6)
            dat="June "+day+","+year;
        else if(month==7)
            dat="July "+day+","+year;
        else if(month==8)
            dat="August "+day+","+year;
        else if(month==9)
            dat="September "+day+","+year;
        else if(month==10)
            dat="October "+day+","+year;
        else if(month==11)
            dat="November "+day+","+year;
        else if(month==12)
            dat="December "+day+","+year;
        addDate.setText(dat);
        addDate.setText(dat);
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   DatePickerDialog datePickerDialog=new DatePickerDialog(AddNew.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        addDate.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },day,month,year);
                datePickerDialog.show();*/
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(
                        AddNew.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,year,month,day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                if(month==1)
                    dat="January "+dayOfMonth+","+year;
                else if(month==2)
                    dat="February "+dayOfMonth+","+year;
                else if(month==3)
                    dat="March "+dayOfMonth+","+year;
                else if(month==4)
                    dat="April "+dayOfMonth+","+year;
                else if(month==5)
                    dat="May "+dayOfMonth+","+year;
                else if(month==6)
                    dat="June "+dayOfMonth+","+year;
                else if(month==7)
                    dat="July "+dayOfMonth+","+year;
                else if(month==8)
                    dat="August "+dayOfMonth+","+year;
                else if(month==9)
                    dat="September "+dayOfMonth+","+year;
                else if(month==10)
                    dat="October "+dayOfMonth+","+year;
                else if(month==11)
                    dat="November "+dayOfMonth+","+year;
                else if(month==12)
                    dat="December "+dayOfMonth+","+year;
                addDate.setText(dat);
            }
        };
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    String date= addDate.getText().toString();
                String title=addTitle.getText().toString();
                String tags=addTags.getSelectedItem().toString();
                String msg=addMessage.getText().toString();
                String tandd=date;*/
                date= addDate.getText().toString();
                title=addTitle.getText().toString();
                tags=addTags.getSelectedItem().toString();
                msg=addMessage.getText().toString();
                tandd=date;
                   id = mFirebaseDatabase.push().getKey();
                    uploadFile();
                    AddEntry artist = new AddEntry( title, msg,tandd,toolt,tags,id);
                    mFirebaseDatabase.child(id).setValue(artist);
                    Intent home=new Intent(AddNew.this,MainActivity.class);
                    startActivity(home);
                    finish();
            }
        });

    }
    private void chooseImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK
                && data!=null&&data.getData()!=null){
            filePath=data.getData();
            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                addImage.setImageBitmap(bitmap);
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child("images/"+ UUID.randomUUID().toString());

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "Image Uploaded ", Toast.LENGTH_LONG).show();

                            AddEntry artist = new AddEntry( taskSnapshot.getDownloadUrl().toString(), msg,tandd,toolt,tags,id);
                            mFirebaseDatabase.child(String.valueOf(id)).setValue(artist);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }
}
