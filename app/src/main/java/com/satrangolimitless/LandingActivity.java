package com.satrangolimitless;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.satrangolimitless.User_UI.Home_user.HomeFragment_user;
import com.satrangolimitless.User_UI.My_Job_Posts.MyJobPostsFragment;
import com.satrangolimitless.User_UI.Myaccount.MyaccountFragment;
import com.satrangolimitless.User_UI.Mybooking.MybookingFragment;
import com.satrangolimitless.User_UI.NotificationAlerts.User_Notifications;
import com.satrangolimitless.User_UI.Offer.OfferFragment;
import com.satrangolimitless.User_UI.POSTAJOB.PostAjobFragment;
import com.satrangolimitless.User_UI.Settings.SettingsFragment;
import com.satrangolimitless.User_UI.cahtwithsupport.ChatFragment;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.logout;

public class LandingActivity extends AppCompatActivity {
    public static ImageView drawerimage;
    public static LinearLayout Layout_hader;
    final Fragment fragment1 = new HomeFragment_user();
    final Fragment fragment2 = new OfferFragment();
    final Fragment fragment3 = new User_Notifications();
    final Fragment fragment4 = new ChatFragment();
    final FragmentManager fm = getSupportFragmentManager();
    LinearLayout l_provider;
    BottomNavigationView navView;
    FloatingActionButton fab;
    Fragment fragment;
    FragmentTransaction transaction;
    ImageView drawerheaderimage;
    TextView draweruname;
    Session session;
    String name, user_id, image;
    CircleImageView img_profile, imageView;

    String[] numbers = {"Home", "Post a Job", "Browse Categories", "My Account", "My Booking", "My Job Posts", "My Profile", "Settings", "Log out"};
    Integer[] images = {
            R.drawable.ic_home,
            R.drawable.ic_poasajob,
            R.drawable.ic_browsecategory,
            R.drawable.ic_myaccount,
            R.drawable.ic_mybooking,
            R.drawable.ic_myjob,
            R.drawable.ic_myprofile,
            R.drawable.ic_settings,
            R.drawable.ic_logout
    };
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragment = null;
            //String Backstack=fragment.getClass().getName();
            switch (item.getItemId()) {
                case R.id.navigation_home:

//                    transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame, fragment1);
//                    transaction.commit();


                    fragment = null;
                    fragment = new HomeFragment_user();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.frame, fragment)
                            .addToBackStack("csds").commit();
                    return true;

                case R.id.navigation_search:
//                    transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame, fragment2);
//                    transaction.commit();


                    fragment = null;
                    fragment = new OfferFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.frame, fragment)
                            .addToBackStack("csds").commit();


                    return true;

                case R.id.navigation_trial:
//                    transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame, fragment3);
//                    transaction.commit();


                    fragment = null;
                    fragment = new User_Notifications();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.frame, fragment)
                            .addToBackStack("csds").commit();

                    return true;


                case R.id.navigation_profile:

//                    transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame, fragment4);
//                    transaction.commit();


                    fragment = null;
                    fragment = new ChatFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.frame, fragment)
                            .addToBackStack("csds").commit();


                    return true;

            }
            return false;
        }
    };
    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        session = new Session(getApplicationContext());
        name = session.getUser_name();
        image = session.getProfileimage();
        Layout_hader = findViewById(R.id.Layout_hader);
        navView = findViewById(R.id.customBottomBar);
        img_profile = findViewById(R.id.img_profile);
        imageView = findViewById(R.id.imageView);
        l_provider = findViewById(R.id.l_provider);
        drawerimage = findViewById(R.id.drawerimage);
        fab = findViewById(R.id.fab);
        fab.setSize(FloatingActionButton.SIZE_NORMAL);
        draweruname = findViewById(R.id.draweruname);
        draweruname.setText(name);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.frame, fragment1, "1").commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new PostAjobFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();

            }
        });
        l_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, LandingActivity_Service_provider.class);
                startActivity(intent);
            }
        });

        try {
            Glide.with(getApplicationContext())
                    .load(Image_url + image)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .centerCrop()
                            .dontTransform())
                    .skipMemoryCache(true)
                    .into(img_profile);
            Glide.with(getApplicationContext())
                    .load(Image_url + image)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .centerCrop()
                            .dontTransform())
                    .skipMemoryCache(true)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(LandingActivity.this, MyprofileActivity_User.class);
                startActivity(intent);

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingActivity.this, MyprofileActivity_User.class);
                startActivity(intent);

            }
        });
    }


    private Bitmap textAsBitmap(String Post, int i, int white) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(i);
        paint.setColor(white);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(Post) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(Post, 0, baseline, paint);
        return image;

    }


    public void setHomeItem(Activity activity) {
        navView.setSelectedItemId(R.id.navigation_home);
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

                // drawerLayout.closeDrawer(Gravity.LEFT);

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
        // drtawernametv.setText(.getSavedUserName());


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

    @Override
    public void onBackPressed() {
        int seletedItemId = navView.getSelectedItemId();

        System.out.println("seletedItemId=======     " + seletedItemId);
        if (R.id.navigation_home != seletedItemId) {
            setHomeItem(LandingActivity.this);
        } else {

            System.out.println(R.id.navigation_home);
            System.out.println(seletedItemId);
            new AlertDialog.Builder(LandingActivity.this)
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

    private void Logout() {
        final ProgressDialog progressDialog = new ProgressDialog(LandingActivity.this);
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

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final String item = getItem(pos);
            //  Integer itemimage = convertView.getId();


            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(listItemLayout, parent, false);
                viewHolder.item = (TextView) convertView.findViewById(R.id.tv_text);
                viewHolder.imageView = convertView.findViewById(R.id.imageview);
                viewHolder.draw_layout = convertView.findViewById(R.id.draw_layout);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.imageView.setImageResource(images[position]);
            viewHolder.item.setText("" + item);

            if (position == selectedPosition) {
                viewHolder.draw_layout.setBackgroundColor(LandingActivity.this.getResources().getColor(R.color.blue));
                viewHolder.item.setTextColor(Color.parseColor("#fdc400"));
                viewHolder.imageView.setColorFilter(viewHolder.imageView.getContext().getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
            } else {
                viewHolder.draw_layout.setBackgroundColor(LandingActivity.this.getResources().getColor(R.color.white));
                viewHolder.item.setTextColor(Color.parseColor("#808080"));
                viewHolder.imageView.setColorFilter(viewHolder.imageView.getContext().getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);

            }

            viewHolder.draw_layout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                @Override
                public void onClick(View view) {
                    selectedPosition = position;
                    notifyDataSetChanged();
                    switch (pos) {
                        case 0:
                            fragment = null;
                            fragment = new HomeFragment_user();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csds").commit();

                            break;
                        case 1:
                            fragment = null;
                            fragment = new PostAjobFragment();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csds").commit();
                            break;
                        case 2:

                            Intent intent = new Intent(getApplicationContext(), PopularcategoriesActivity.class);
                            startActivity(intent);


                            break;
                        case 3:

                            fragment = null;
                            fragment = new MyaccountFragment();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csds").commit();

                            break;
                        case 4:

                            fragment = null;
                            fragment = new MybookingFragment();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csds").commit();
                            break;
                        case 5:

                            fragment = null;
                            fragment = new MyJobPostsFragment();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csds").commit();
                            break;

                        case 6:
                            Intent intent1 = new Intent(LandingActivity.this, MyprofileActivity_User.class);
                            startActivity(intent1);

                            break;
                        case 7:
                            fragment = null;
                            fragment = new SettingsFragment();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.frame, fragment)
                                    .addToBackStack("csds").commit();
                            break;
                        case 8:
                            Logout();

                            break;
                    }

                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            });


            return convertView;
        }


        public void setSelectedPosition(int position) {
            this.selectedPosition = position;
        }

        class ViewHolder {
            TextView item;
            ImageView imageView;
            LinearLayout draw_layout;
        }
    }


}