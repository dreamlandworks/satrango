package com.satrangolimitless.Setting;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Settings.SettingsFragment;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.app_feedback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuggestionAndfeedbackfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestionAndfeedbackfragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout l_submit;
    Session session;
    private RequestQueue rQueue;
    EditText et_suggestionmsg;
    RatingBar ratingBar, pro_rating, services_rating, app_interface_rating, customer_support_rating;
    TextView txtrating;
    Button btnsubmit;
    ImageView iv_back;
    float getrating, pro_getrating, services_getrating, appinterface_rating, customersupport_rating;
    Fragment fragment;

    public SuggestionAndfeedbackfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestionAndfeedbackfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestionAndfeedbackfragment newInstance(String param1, String param2) {
        SuggestionAndfeedbackfragment fragment = new SuggestionAndfeedbackfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_suggestion_andfeedbackfragment, container, false);
        ratingBar = (RatingBar) root.findViewById(R.id.rating);
        pro_rating = (RatingBar) root.findViewById(R.id.pro_rating);
        services_rating = (RatingBar) root.findViewById(R.id.services_rating);
        app_interface_rating = (RatingBar) root.findViewById(R.id.app_interface_rating);
        customer_support_rating = (RatingBar) root.findViewById(R.id.customer_support_rating);
        l_submit = root.findViewById(R.id.l_submit);
        et_suggestionmsg = root.findViewById(R.id.et_suggestionmsg);
        txtrating = root.findViewById(R.id.txtrating);
        btnsubmit = root.findViewById(R.id.btnsubmit);
        iv_back = root.findViewById(R.id.iv_back);
        session = new Session(getActivity());
        LandingActivity.Layout_hader.setVisibility(View.GONE);
        System.out.println("<><><><====" + session.getUserId());

        final String rating = String.valueOf(ratingBar.getRating());
        final String profession_rating = String.valueOf(pro_rating.getRating());
        final String Services_rating = String.valueOf(services_rating.getRating());
        final String App_interface_rating = String.valueOf(app_interface_rating.getRating());
        final String Customer_support_rating = String.valueOf(customer_support_rating.getRating());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new SettingsFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int noofstars = ratingBar.getNumStars();
                getrating = ratingBar.getRating();
                String s = String.valueOf(getrating);
                txtrating.setText("Rating: " + getrating + "/" + noofstars);

            }
        });


        pro_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int noofstars = ratingBar.getNumStars();
                pro_getrating = ratingBar.getRating();
                txtrating.setText("Rating: " + pro_getrating + "/" + noofstars);

            }
        });
        services_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int noofstars = ratingBar.getNumStars();
                services_getrating = ratingBar.getRating();
                txtrating.setText("Rating: " + services_getrating + "/" + noofstars);
            }
        });

        app_interface_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int noofstars = ratingBar.getNumStars();
                appinterface_rating = ratingBar.getRating();
                txtrating.setText("Rating: " + appinterface_rating + "/" + noofstars);
            }
        });

        customer_support_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int noofstars = ratingBar.getNumStars();
                customersupport_rating = ratingBar.getRating();
                txtrating.setText("Rating: " + customersupport_rating + "/" + noofstars);
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CallSuggestionfeedbackApi(rating, profession_rating, Services_rating, App_interface_rating, Customer_support_rating);
            }
        });

        return root;
    }

    private void CallSuggestionfeedbackApi(final String rating, final String profession_rating, final String services_rating, final String app_interface_rating, final String customer_support_rating) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + app_feedback,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");
                            Log.e("<><><>namiikdnjcdshnj", jsonObject.toString());

                            if (result.equals(true)) {
                                final Dialog dialog = new Dialog(requireContext());
                                dialog.setContentView(R.layout.dialog_feedback_success);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                Button closeBtn = dialog.findViewById(R.id.btn_yes);
                                closeBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println("gkgkgkgkgk" + error);

                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", session.getUserId());
                params.put("message", et_suggestionmsg.getText().toString());
                params.put("over_all_rating", String.valueOf(getrating));
                params.put("profession_rating", String.valueOf(pro_getrating));
                params.put("services_rating", String.valueOf(services_getrating));
                params.put("app_interface_rating", String.valueOf(appinterface_rating));
                params.put("customer_support_rating", String.valueOf(customersupport_rating));
                params.put("type", "user");
                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);

    }


}