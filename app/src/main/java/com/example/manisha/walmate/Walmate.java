package com.example.manisha.walmate;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Manisha on 01-04-2018.
 */

public class Walmate extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
