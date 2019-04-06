package online.diary;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;



/* ------------Created by Manigandan S. on February 25,2018----------------*/


public class Diary extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
