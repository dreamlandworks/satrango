package com.satrangolimitless;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.satrangolimitless.Utils.Utils;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;
import com.satrangolimitless.session.Sessiontemp;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.login_Api;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1;
    TextView sign_up;
    Button signIn_signInButton;
    TextView forgot_password;
    String phone, password, fireb_token;
    EditText edt_mobile, edt_password;
    Session session;
    Session_vendor session_vendor;
    Sessiontemp sessiontemp;
    ImageView img_google_login;
    String name, email, useremail;
    String idToken;
    CheckBox rememberMeCbx;
    ImageView signIn_facebook;
    String pswd, mobile;

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    String googleid_id;
    CallbackManager callbackManager;
    LoginButton loginButton;
    String fbid, fbname, fbemail, birthday, gender;
    private RequestQueue rQueue;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        sign_up = findViewById(R.id.sign_up);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_password = findViewById(R.id.edt_password);
        img_google_login = findViewById(R.id.img_google_login);
        signIn_signInButton = findViewById(R.id.signIn_signInButton);
        forgot_password = findViewById(R.id.forgot_password);
        signIn_facebook = findViewById(R.id.signIn_facebook);
        loginButton = findViewById(R.id.loginButton);

        rememberMeCbx = findViewById(R.id.remeber_cbx);
        session = new Session(getApplicationContext());
        sessiontemp = new Sessiontemp(getApplicationContext());
        session_vendor = new Session_vendor(getApplicationContext());


        fireb_token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("LoginActivity!@##$$%^$%%^token    " + fireb_token);
        Log.e("FIREBASE TOKEN", fireb_token);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        TwitterConfig config = new TwitterConfig.Builder(this)
//                .logger(new DefaultLogger(Log.DEBUG))
//                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY,
//                        TWITTER_SECRET))
//                .debug(true)
//                .build();
//        Twitter.initialize(config);

        System.out.println("data session mobile  ---   " + sessiontemp.getMobile() + "" + sessiontemp.getPass());
        if (sessiontemp.getMobile() != null && !sessiontemp.getMobile().isEmpty() && !sessiontemp.getMobile().equals("null") && !sessiontemp.getMobile().equals("0")) {
            edt_mobile.setText(sessiontemp.getMobile());
            edt_password.setText(sessiontemp.getPass());
            rememberMeCbx.setChecked(true);
        } else {
            edt_mobile.setText("");
            edt_password.setText("");
            rememberMeCbx.setChecked(false);
        }

        rememberMeCbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    System.out.println("check condition ><><><    ");
                    saveLoginDetails();
                } else {
                    removeLoginDetails();
                }
            }
        });


        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupDetailsActivity_one.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.signIn_signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
            }
        });


        findViewById(R.id.forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });


        img_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        signIn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.e("LOGIN RESULT", object.toString());
                                    fbid = object.getString("id");
                                    if (!fbid.equals("0")) {
                                        String profile_pic = "http://graph.facebook.com/" + fbid + "/picture?type=large";
                                        fbname = object.getString("name");
                                        Intent intent = new Intent(LoginActivity.this, WelcomeonboardActivity.class);
                                        intent.putExtra("name", fbname);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, object.get("error").toString(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.d("FB_ERROR", "onError: " + exception.toString());
            }
        });

    }


    private void loginAction() {
        mobile = edt_mobile.getText().toString();
        pswd = edt_password.getText().toString();
        if (mobile.isEmpty()) {
            edt_mobile.setError("Enter Valid Mobile Number");
            edt_mobile.requestFocus();
            return;
        }
        if (mobile.length() < 10) {
            edt_mobile.setError("Mobile Number should not be below 10 digits.");
            edt_mobile.requestFocus();
            return;
        }
        if (pswd.isEmpty()) {
            edt_password.setError("Enter Password");
            edt_password.requestFocus();
            return;
        } else {

            if (Utils.isInternetConnected(LoginActivity.this)) {
                logiapi();
            }

        }


    }


    public void saveLoginDetails() {
        sessiontemp.setMobile(edt_mobile.getText().toString());
        sessiontemp.setPass(edt_password.getText().toString());
        sessiontemp.setCheckbox("1");

    }

    public void removeLoginDetails() {

        sessiontemp.setMobile("");
        sessiontemp.setPass("");
        sessiontemp.setCheckbox("0");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        System.out.println("<><><====googlesignin" + result.toString());
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            idToken = account.getIdToken();
            name = account.getDisplayName();
            email = account.getEmail();
            googleid_id = account.getId();
            Intent intent = new Intent(LoginActivity.this, WelcomeonboardActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login Unsuccessful!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("<><><>connectionResult" + connectionResult);
    }


    private void requestPermissions() {

        Dexter.withActivity(this)
                // below line is use to request the number of
                // permissions which are required in our app.
                .withPermissions(
                        // below is the list of permissions
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET)
                // after adding permissions we are
                // calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now

                            loginAction();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

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

    void logiapi() {

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + login_Api,


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println("login activity response-----       " + jsonObject.toString());
                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("fname");
                                String email = jsonObject1.getString("email");
                                String phone = jsonObject1.getString("phone");
                                String verify_otp = jsonObject1.getString("verify_otp");
                                String online_status = jsonObject1.getString("status");
                                String image = jsonObject1.getString("image");
                                String service_provider = jsonObject1.getString("service_provider");
                                System.out.println("name id " + name + " " + id);
                                session.setLogin(true);
                                session.setUserId(id);
                                session.setService_providr_status(service_provider);
                                //  session1.setMobile(phone,email);
                                session.setUser_name(name);
                                session.setMobile(phone);
                                session.setProfileimage(image);
                                session_vendor.set_onlinestatus(online_status);


                                    /*sharedPrefrencesHelper.setFirstname(jsonObject1.getString("firstname"));
                                    sharedPrefrencesHelper.setLastname(jsonObject1.getString("lastname"));
                                    sharedPrefrencesHelper.setUsername(jsonObject1.getString("username"));
                                    sharedPrefrencesHelper.setEmail(jsonObject1.getString("email"));*/
                                progressDialog.dismiss();


                                if (verify_otp.contains("1")) {

                                    startActivity(new Intent(getBaseContext(), Activity_login_type.class));
                                    finish();

                                } else {

                                    startActivity(new Intent(getBaseContext(), Otpverification_onLoginActivity.class));
                                    finish();
                                }


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);
                params.put("password", pswd);
                params.put("fcm_id", fireb_token);

                System.out.println("params login -------        " + params);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(stringRequest);

    }
}