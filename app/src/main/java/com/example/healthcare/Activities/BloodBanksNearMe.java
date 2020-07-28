package com.example.healthcare.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.viewmodels.Banks;
import com.example.healthcare.R;
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

import java.util.ArrayList;
import java.util.List;

public class BloodBanksNearMe extends AppCompatActivity implements OnMapReadyCallback {

    String type;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    LocationManager locationManager;
    Location location;

    double lat;
    double lon;

    private ArrayList<Banks> res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_banks_near_me);

        Intent i = getIntent();
        type = i.getStringExtra("Type");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //getting the current location of the user
        location = getLastKnownLocation();
        lat = location.getLatitude();
        lon = location.getLongitude();

        //Toast.makeText(this , lat + " " + lon + "" , Toast.LENGTH_SHORT).show();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("BloodBanks");

        //creating a new thread to search the nearby blood banks
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(databaseReference);
    }

    //the Async task
    private class AsyncTaskRunner extends AsyncTask<DatabaseReference, String, ArrayList<Banks>> {



        ProgressDialog pd;

        @Override
        protected ArrayList<Banks> doInBackground(DatabaseReference... params) {
            //publishProgress("Sleeping..."); // Calls onProgressUpdate()

            res = new ArrayList<>();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Banks bank = ds.getValue(Banks.class);
                        if (distance(bank.getLat() , bank.getLon() , lat ,lon) < 1) {
                            for (String s : bank.getBloodAvail()) {
                                if (type.equals(s))
                                    res.add(bank);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return res;
        }


        @Override
        protected void onPostExecute(ArrayList<Banks> result) {
            super.onPostExecute(result);
            // execution of result of Long time consuming operation
            pd.dismiss();
            Toast.makeText(BloodBanksNearMe.this , result.size()+"" , Toast.LENGTH_SHORT).show();


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(BloodBanksNearMe.this , "Came here" , Toast.LENGTH_SHORT).show();
            pd = new ProgressDialog(BloodBanksNearMe.this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Fetching the nearby blood banks");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();


        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    /** calculates the distance between two locations in MILES */
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometers

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        for (Banks b : res){

            LatLng latLng = new LatLng(b.getLat() , b.getLon());
            googleMap.addMarker(new MarkerOptions().position(latLng)
                    .title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
