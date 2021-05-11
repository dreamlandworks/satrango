package com.satrangolimitless;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.satrangolimitless.User_UI.Myaccount.MyaccountFragment;
import com.satrangolimitless.User_UI.POSTAJOB.PostAjobFragment;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.Vendor_UI.Activity_VendorCalender;
import com.satrangolimitless.Vendor_UI.Alertfragment_service_provider;
import com.satrangolimitless.Vendor_UI.Chatfragment_service_provider;
import com.satrangolimitless.Vendor_UI.Homefragment_service_provider;
import com.satrangolimitless.Vendor_UI.MyAccount.MyaccountFragmentVendor;
import com.satrangolimitless.Vendor_UI.My_Bids.Fragment_MyBids_Vendor;
import com.satrangolimitless.Vendor_UI.My_profile_vendor;
import com.satrangolimitless.Vendor_UI.Offersfragment_service_provider;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_MyBooking;
import com.satrangolimitless.Vendor_UI.Vendor_Settings.Vendor_SettingsFragment;
import com.satrangolimitless.Vendor_UI.vendor_profile.VendorProfileActivity;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.logout;

public class LandingActivity_Service_provider extends AppCompatActivity {
    public static LinearLayout Layout_hader;
    final Fragment fragment1 = new Homefragment_service_provider();
    final Fragment fragment2 = new Offersfragment_service_provider();
    final Fragment fragment3 = new Alertfragment_service_provider();
    final Fragment fragment4 = new Chatfragment_service_provider();
    final FragmentManager fm = getSupportFragmentManager();
    TextView drtxtvname;
    LinearLayout myprofile;
    ImageView Notification;
    BottomNavigationView navView;
    FloatingActionButton fab;
    Fragment fragment;
    Session session;
    String vname, user_id;
    FragmentTransaction transaction;
    ImageView drawerheaderimage;
    RelativeLayout rl_user;
    String service_provider_status;
    CircleImageView imgprofilev;

    String[] numbers = {"Home", "My Account", "My Bookings", "My Bids", "My Profile", "Settings", "Log out"};
    Integer[] images = {
            R.drawable.ic_home,
            R.drawable.ic_myaccount,
            R.drawable.ic_mybooking,
            R.drawable.ic_myjob,
            R.drawable.ic_myprofile,
            R.drawable.ic_settings,
            R.drawable.ic_logout
    };
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragment = null;
            //String Backstack=fragment.getClass().getName();
            switch (item.getItemId()) {
                case R.id.navigationhomes:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment1);
                    transaction.commit();
                    return true;

                case R.id.navigationoffers:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment2);
                    transaction.commit();
                    return true;

                case R.id.navigation_alerts:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment3);
                    transaction.commit();
                    return true;


                case R.id.navigation_chats:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment4);
                    transaction.commit();
                    return true;

            }
            return false;
        }
    };
    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    CircleImageView imageprofiledrwer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_service_provider);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new Session(getApplicationContext());
        vname = session.getUser_name();
        user_id = session.getUserId();
        service_provider_status = session.getService_providr_status();


        fab = findViewById(R.id.fab);
        fab.setSize(FloatingActionButton.SIZE_NORMAL);
        navView = findViewById(R.id.customBottomBars);
        Layout_hader = findViewById(R.id.Layout_hader);
        Layout_hader.setVisibility(View.VISIBLE);
        drtxtvname = findViewById(R.id.drtxtvname);
        imageprofiledrwer = findViewById(R.id.imageprofiledrwer);
        rl_user = findViewById(R.id.rl_user);
        imgprofilev = findViewById(R.id.imgprofilev);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.frame, fragment1, "1").commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity_Service_provider.this, Activity_VendorCalender.class);
                startActivity(intent);
            }
        });


        drtxtvname.setText(vname);
        rl_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingActivity_Service_provider.this, LandingActivity.class);
                startActivity(intent);

            }
        });

        if (service_provider_status.contains("1")) {


        } else {
            alertdialogbox();
        }


        imgprofilev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity_Service_provider.this, VendorProfileActivity.class);
                startActivity(intent);
            }
        });

        Glide.with(getApplicationContext())
                .load(Image_url + session.getProfileimage())
                .into(imgprofilev);
        Glide.with(getApplicationContext())
                .load(Image_url + session.getProfileimage())
                .into(imageprofiledrwer);


        imageprofiledrwer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity_Service_provider.this, VendorProfileActivity.class);
                startActivity(intent);
            }
        });

    }


    public void setHomeItem(Activity activity) {
        navView.setSelectedItemId(R.id.navigationhomes);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerheaderimage = findViewById(R.id.drawerimage);
        drawerheaderimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        if (drawerToggle == null) {
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.app_name, R.string.app_name) {
                public void onDrawerClosed(View view) {

                }

                public void onDrawerOpened(View drawerView) {

                }

                public void onDrawerSlide(View drawerView, float slideOffset) {
                }

                public void onDrawerStateChanged(int newState) {

                }

            };
            drawerLayout.setDrawerListener(drawerToggle);
        }

        drawerToggle.syncState();

        ListView rvNumbers = (ListView) findViewById(R.id.list);
        TextView drtawernametv = (TextView) findViewById(R.id.textView);


        ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(this, R.layout.layout_drawer, numbers, images);
        rvNumbers.setAdapter(itemArrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void alertdialogbox() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(LandingActivity_Service_provider.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.vandor_activation_dialogue, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        ImageView img_close = (ImageView) dialogView.findViewById(R.id.img_close);
        Button btn_yes = (Button) dialogView.findViewById(R.id.btn_yes);
        Button btn_no = (Button) dialogView.findViewById(R.id.btn_no);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LandingActivity_Service_provider.this, VendorProfileActivity.class);
                startActivity(in);
                finish();
//VendorProfileActivity
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent in=new Intent(LandingActivity_Service_provider.this,Activity_login_type.class);
                startActivity(in);
                finish();
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent in=new Intent(LandingActivity_Service_provider.this,Activity_login_type.class);
                startActivity(in);
                finish();
            }
        });
        dialog.show();
    }

    /* Service provider activation pop up */

    @Override
    public void onBackPressed() {
        int seletedItemId = navView.getSelectedItemId();

        System.out.println("seletedItemId=======     " + seletedItemId);
        if (R.id.navigationhomes != seletedItemId) {
            setHomeItem(LandingActivity_Service_provider.this);
        } else {


            new androidx.appcompat.app.AlertDialog.Builder(LandingActivity_Service_provider.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Application")
                    .setMessage("Are you sure you want to close this App?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 21) {
                                // getActivity().finishAffinity();

                                Intent i = new Intent(Intent.ACTION_MAIN);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();


                            } else if (Build.VERSION.SDK_INT >= 21) {


                                Intent i = new Intent(Intent.ACTION_MAIN);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();

                                //  getActivity().finishAndRemoveTask();
                            }
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    }

    public class ItemArrayAdapter extends ArrayAdapter<String> {
        private final int listItemLayout;
        String[] itemList;
        Integer[] itemimage;
        private int selectedPosition = 0;

        public ItemArrayAdapter(Context context, int layoutId, String[] itemList, Integer[] itemimage) {
            super(context, layoutId, itemList);
            listItemLayout = layoutId;
            this.itemList = itemList;
            this.itemimage = itemimage;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final String item = getItem(pos);
            ItemArrayAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ItemArrayAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(listItemLayout, parent, false);
                viewHolder.item = (TextView) convertView.findViewById(R.id.tv_text);
                viewHolder.imageView = convertView.findViewById(R.id.imageview);
                viewHolder.draw_layout = convertView.findViewById(R.id.draw_layout);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ItemArrayAdapter.ViewHolder) convertView.getTag();
            }

            viewHolder.imageView.setImageResource(images[position]);
            viewHolder.item.setText("" + item);

            if (position == selectedPosition) {
                viewHolder.draw_layout.setBackgroundColor(LandingActivity_Service_provider.this.getResources().getColor(R.color.green));
                viewHolder.item.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.imageView.setColorFilter(viewHolder.imageView.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            } else {
                viewHolder.draw_layout.setBackgroundColor(LandingActivity_Service_provider.this.getResources().getColor(R.color.white));
                viewHolder.item.setTextColor(Color.parseColor("#1c8c22"));
                viewHolder.imageView.setColorFilter(viewHolder.imageView.getContext().getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
            }


            viewHolder.draw_layout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                @Override
                public void onClick(View view) {
                    selectedPosition = position;
                    notifyDataSetChanged();

                    // Toast.makeText(LandingActivity_Service_provider.this, ""+position, Toast.LENGTH_SHORT).show();
                    switch (pos) {
                        case 0:
                            fragment = null;
                            fragment = new Homefragment_service_provider();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csgds").commit();
                            break;
                        case 1:
                            fragment = null;
                            fragment = new MyaccountFragmentVendor();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csdgs").commit();
                            break;
                        case 2:

                            //MybookingFragment
                            fragment = null;
                            fragment = new Vendor_MyBooking();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csdds").commit();
                            break;
                        case 3:


                            fragment = null;
                            fragment = new Fragment_MyBids_Vendor();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csgdgds").commit();
                            break;
                        case 4:

                            Intent intent = new Intent(LandingActivity_Service_provider.this, My_profile_vendor.class);
                            startActivity(intent);
                            break;

                        case 5:

                            fragment = null;
                            fragment = new Vendor_SettingsFragment();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csds").commit();

                            break;

                        case 6:


                            Logout();

                            break;

                        case 7:


                            break;

                    }

                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView item;
            ImageView imageView;
            LinearLayout draw_layout;
        }
    }





    private void Logout() {
        final ProgressDialog progressDialog = new ProgressDialog(LandingActivity_Service_provider.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + logout
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("logout data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                session.logout();

                            } else {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

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
                params.put("user_id", session.getUserId());


                System.out.println(" logut param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}