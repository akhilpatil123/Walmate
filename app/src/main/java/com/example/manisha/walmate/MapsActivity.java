package com.example.manisha.walmate;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    AutoCompleteTextView autoCompleteTextViewS,autoCompleteTextViewD;
    String source,destination;
    private GoogleMap mMap;

    //Map<String,ArrayList<Double>> map;
    Double slat,slang,dlat,dlang;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        String [] places={"Vartak Hall","Admin Building","Cafeteria","Canteen","CCF","IT Department"};
        autoCompleteTextViewS=(AutoCompleteTextView)findViewById(R.id.AutoCompleteS);
        autoCompleteTextViewD=(AutoCompleteTextView)findViewById(R.id.AutoCompleteD);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        autoCompleteTextViewS.setAdapter(adapter);
        autoCompleteTextViewD.setAdapter(adapter);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Places");
        databaseReference.keepSynced(true);
    }


    public void showRoute(View view)
    {
        source=autoCompleteTextViewS.getText().toString();
        destination=autoCompleteTextViewD.getText().toString();
        if(source.isEmpty())
        {
            Toast.makeText(this, "Please enter source address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(destination.isEmpty())
        {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(source).child("Lat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                slat= (Double) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child(source).child("Long").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                slang= (Double) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child(destination).child("Lat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dlat= (Double) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child(destination).child("Long").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dlang= (Double) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Searching...");
        progressDialog.show();
        progressDialog.dismiss();

        Toast.makeText(this,"Source:"+source+"Lat:"+slat+"Long:"+slang,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"Dest:"+destination+"Lat:"+dlat+"Long:"+dlang,Toast.LENGTH_SHORT).show();

    }
    public void click(View view)
    {
        Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng wce=new LatLng(16.845794,74.601327);
        googleMap.addMarker(new MarkerOptions().position(wce).title("WCE"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wce,18),3000,null);

    }
}
