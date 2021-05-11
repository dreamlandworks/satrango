package com.satrangolimitless;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.satrangolimitless.Utils.Utils;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.session.PrefrenceManager;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Signup_Api;

public class SignUpActivity extends AppCompatActivity {

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String mobilePattern = "[a-zA-Z]+";
    TextView btn_login;
    TextView signIn_DOB, sign_ln;
    Button selectDate, signIn_signUpButton;
    TextView txttermsandcond;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    EditText edt_name, edt_mob, edt_mail;
    Session session;
    GPSTracker gps;
    double latitude, longitude;
    String longi, latt;
    String name, userr, MOB, Email, DOB;
    CheckBox checkbox_trmscnd;
    PrefrenceManager prefrenceManager;
    String FCM_ID;
    private int Year, Month, Day, mHour, mMinute;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txttermsandcond = findViewById(R.id.txttermsandcond);
        edt_name = findViewById(R.id.edt_name);
        edt_mob = findViewById(R.id.edt_mob);
        edt_mail = findViewById(R.id.edt_mail);
        signIn_DOB = findViewById(R.id.signIn_DOB);
        sign_ln = findViewById(R.id.sign_ln);
        checkbox_trmscnd = findViewById(R.id.checkbox_trmscnd);
        signIn_signUpButton = findViewById(R.id.signIn_signUpButton);
        session = new Session(getApplicationContext());

        edt_mob.setText(session.getMobile());
        edt_name.setText(session.getUser_name());

        prefrenceManager = new PrefrenceManager(SignUpActivity.this);
        FCM_ID = PrefrenceManager.getTokenId(SignUpActivity.this);
        System.out.println("fcm id  " + FCM_ID);

        //      method for lat long
        getlatlang();

        txttermsandcond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Termsandcond.class);
                startActivity(intent);

            }
        });


        findViewById(R.id.sign_ln).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
        requestPermissions();

        edt_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                edt_mail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        findViewById(R.id.signIn_signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userr = edt_name.getText().toString();
                MOB = edt_mob.getText().toString();
                Email = edt_mail.getText().toString();
                DOB = signIn_DOB.getText().toString();
                if (userr.isEmpty()) {
                    edt_name.setError("Enter your name");
                    edt_name.requestFocus();
                    return;
                }

                if (!Pattern.matches("[a-zA-Z]+", MOB)) {
                    if (MOB.length() < 9 || MOB.length() > 13) {
                        edt_mob.setError("Please enter valid 10 digit mobile number");
                        edt_mob.requestFocus();
                        return;
                    }
                }


                if (Email.isEmpty()) {
                    edt_mail.setError("Please enter email");
                    edt_mail.requestFocus();
                    return;
                } else if (!edt_mail.getText().toString().trim().matches(emailPattern)) {
                    edt_mail.setError("Invalid email address");
                    edt_mail.requestFocus();
                    return;
                }

                if (DOB.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter Date of birth", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkbox_trmscnd.isChecked()) {
                    Toast.makeText(SignUpActivity.this, "Please check terms and conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Utils.isInternetConnected(SignUpActivity.this)) {
                    CallSignUpApi();
                } else {
                    Toast.makeText(SignUpActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }


            }
        });


        signIn_DOB.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                final java.util.Calendar c = java.util.Calendar.getInstance();

                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(SignUpActivity.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                signIn_DOB.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                signIn_DOB.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);
                                getAge(year, (monthOfYear + 1), dayOfMonth);

                                System.out.println("age--------       " + getAge(year, (monthOfYear + 1), dayOfMonth));

                            }

                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

                datePickerDialog.show();


            }
        });

    }

//Signup Api call====================================================


    private void CallSignUpApi() {
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Signup_Api,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("signup data", response);
                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("signup data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                if (msg.equals("Email Already exists")) {

                                } else {
                                    JSONObject obj1 = obj.getJSONObject("data");

                                    String id = obj1.getString("id");
                                    String name = obj1.getString("fname");
                                    String mobile = obj1.getString("phone");
                                    String fcm_id = obj1.getString("fcm_id");
                                    String email = obj1.getString("email");
                                    String image = obj1.getString("verify_otp");
                                    // String role = obj1.getString("role");


                                    session.setUserId(id);
                                    session.setEmail(email);
                                    session.setUser_name(name);
                                    session.setMobile(mobile);


                                    // Shared_Pref.setUser_Id(ActivitySignUp.this, id);
                                    Intent intent = new Intent(SignUpActivity.this, OtpverificationActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("phone", mobile);
                                    intent.putExtra("email", email);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                    finish();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(getApplicationContext(), "Hello" + e.toString(), Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();

                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", userr);
                params.put("mobile", MOB);
                params.put("email", Email);
                params.put("dob", DOB);
                params.put("lat", latt);
                params.put("lang", longi);
                params.put("fcm_id", FCM_ID);
                params.put("address", session.getAddress());
                System.out.println("signup-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


//get lat lang=---------------------------------------------------------------

    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SignUpActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //    Toast.makeText(LoginActivity.this,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(getApplicationContext());
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                latt = String.valueOf(latitude);
                longi = String.valueOf(longitude);

            }
        }
    }


    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    private void requestPermissions() {

        Dexter.withActivity(this)
                // below line is use to request the number of
                // permissions which are required in our app.
                .withPermissions(
                        // below is the list of permissions
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET)
                // after adding permissions we are
                // calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now


                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently,
                            // we will show user a dialog message.
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some
                        // permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            // this method is use to handle error
            // in runtime permissions
            @Override
            public void onError(DexterError error) {
                // we are displaying a toast message for error message.
                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                // below line is use to run the permissions
                // on same thread and to check the permissions
                .onSameThread().check();
    }

    // below is the shoe setting dialog
    // method which is use to display a
    // dialogue message.
    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

        // below line is the title
        // for our alert dialog.
        builder.setTitle("Need Permissions");

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // this method is called on click on positive
                // button and on clicking shit button we
                // are redirecting our user from our app to the
                // settings page of our app.
                dialog.cancel();
                // below is the intent from which we
                // are redirecting our user.
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // this method is called when
                // user click on negative button.
                dialog.cancel();
            }
        });
        // below line is used
        // to display our dialog
        builder.show();
    }

}


