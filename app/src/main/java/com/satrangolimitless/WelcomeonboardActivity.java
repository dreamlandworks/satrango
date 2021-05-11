package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WelcomeonboardActivity extends AppCompatActivity {
    Button signUp_WElcomeboard;
TextView txt_name,txt_adres;
    GPSTracker gps;
    double latitude, longitude;
    Session session;
    String name;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeonboard);

        signUp_WElcomeboard=findViewById(R.id.signUp_WElcomeboard);
        txt_name=findViewById(R.id.txt_name);
        txt_adres=findViewById(R.id.txt_adres);
        session=new Session(getApplicationContext());

try {

    name = getIntent().getStringExtra("name");
    txt_name.setText(name);
    getlatlong();
}catch (Exception e){e.printStackTrace();}










        findViewById(R.id.signUp_WElcomeboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermissions()){

                    Intent intent = new Intent(WelcomeonboardActivity.this, SignUpActivity.class);
                    session.setUser_name(name);
                    startActivity(intent);
                    finish();
                }


            }
        });


    }



    private  boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int aud = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int vid = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (aud != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (vid != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }




 public  void getlatlong(){

try{
    //    Toast.makeText(LoginActivity.this,"You need have granted permission",Toast.LENGTH_SHORT).show();
    gps = new GPSTracker(getApplicationContext());
    if (gps.canGetLocation()) {
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        // latt= String.valueOf(latitude);
        //longi= String.valueOf(longitude);



        try {
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            System.out.println("addressssssssssssss --------    "+address);
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            txt_adres.setText(address);
            session.setAddress(address);
        } catch (IOException e) {
            e.printStackTrace();
        }


    } else {
        gps.showSettingsAlert();
    }



}
catch (Exception e){e.printStackTrace();}

}


}