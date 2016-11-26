package com.example.hiral.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Hiral on 7/28/2016.
 */
public class SignUPActivity extends LoginActivity
{
    EditText editTextUserName,editTextPassword,editTextConfirmPassword,latitude,longitude;
    Button btnCreateAccount,btnHomeLocation;
    LoginDataBaseAdapter loginDataBaseAdapter;
    public GoogleMap mMap;
    Location mprovider;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // Creates object for LoginDataBaseAdapter to gain access to database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        /**
         * TODO: Define EditText attributes for editTextUserName,editTextPassword,editTextConfirmPassword
         */

        // Definition of editTextUserName attribute
        editTextUserName = (EditText) findViewById(R.id.user_name);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirm_password);


        /**
         * TODO: Define EditText Attribute for latitude,longitude
         */
        // Definition of latitude attribute
        latitude = (EditText) findViewById(R.id.latitude);

        // Definition of longitude attribute
        longitude = (EditText) findViewById(R.id.longitude);

        /**
         * TODO: Define Button attribute for btnCreateAccount
         */
        // Definition of btnCreateAccount attribute
        btnCreateAccount = (Button) findViewById(R.id.create_account);


        //int percode=1;
        ActivityCompat.requestPermissions(SignUPActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        /**
         * TODO: Define Button attribute for btnHomeLocation
         */
        // Definition of btnHomeLocation attribute
        btnHomeLocation = (Button) findViewById(R.id.set_home_location);

        btnHomeLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {

                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        String lat = String.valueOf(location.getLatitude());
                    }
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    public void onProviderEnabled(String provider) {}
                    public void onProviderDisabled(String provider) {}

                };
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                String locationProvider = LocationManager.GPS_PROVIDER;
                mprovider = locationManager.getLastKnownLocation(locationProvider);
                if (mprovider != null && !mprovider.equals("")) {
                    if (ActivityCompat.checkSelfPermission(SignUPActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SignUPActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Location location = mprovider;
                    if (location != null) {
                        //onLocationChanged(location);
                        latitude.setText(String.valueOf(location.getLatitude()));
                        longitude.setText( String.valueOf(location.getLongitude()));
                    }
                    else
                        Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();
                String lat=latitude.getText().toString();
                String lng=longitude.getText().toString();

                // check if any of the fields are vacant
                if(userName.equals("")||password.equals("")||confirmPassword.equals("")||lat.equals("")||lng.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(userName, password,lat,lng);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                }

                editTextUserName.setEnabled(false);
                editTextPassword.setEnabled(false);
                editTextConfirmPassword.setEnabled(false);
                latitude.setEnabled(false);
                longitude.setEnabled(false);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}
