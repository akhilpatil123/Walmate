package com.example.manisha.walmate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class SearchMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    AutoCompleteTextView autoCompleteTextView;
    private DatabaseReference databaseReference;
    String loc;
    Double lat=0.0;
    Double lang=0.0;
    String address;
    String downloadUrl;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String [] places={"Vartak Hall","Admin Building","Cafeteria","Canteen","CCF"};

        autoCompleteTextView =(AutoCompleteTextView)findViewById(R.id.searchText);

        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        autoCompleteTextView.setAdapter(adapter);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Places");
        databaseReference.keepSynced(true);

        myDialog=new Dialog(this);
    }

    public void search(View view)
    {
        loc=autoCompleteTextView.getText().toString();
        databaseReference.child(loc).child("Lat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lat= (Double) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child(loc).child("Long").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lang= (Double) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child(loc).child("Address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                address= dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child(loc).child("Url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downloadUrl= dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Searching...");
        progressDialog.show();
        */
        Toast.makeText(this,"Source:"+loc+"Lat:"+lat+"Long:"+lang+"Address:"+address+"Url"+downloadUrl,Toast.LENGTH_LONG).show();
        LatLng place=new LatLng(lat,lang);
        mMap.addMarker(new MarkerOptions().position(place).title(loc));
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wce,18),3000,null);
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
        mMap=googleMap;
        LatLng wce=new LatLng(16.845794,74.601327);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wce,18),3000,null);
    }

    public void showPopUp(View view)
    {
        TextView txtclose;
        TextView name;
        TextView txtaddress;
        TextView txtlat;
        TextView txtlang;
        ImageView img;
        myDialog.setContentView(R.layout.custompopup);
        txtclose=(TextView)myDialog.findViewById(R.id.txtclose);
        name=(TextView)myDialog.findViewById(R.id.txtname);
        txtaddress=(TextView)myDialog.findViewById(R.id.addresstxt);
        txtlat=(TextView)myDialog.findViewById(R.id.lattxt);
        txtlang=(TextView)myDialog.findViewById(R.id.txtlang);
        img=(ImageView)myDialog.findViewById(R.id.imageview);
        name.setText(loc);
        txtaddress.setText(address);
        txtlat.setText(""+lat);
        txtlang.setText(""+lang);
        Picasso.get().load(downloadUrl).into(img);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
