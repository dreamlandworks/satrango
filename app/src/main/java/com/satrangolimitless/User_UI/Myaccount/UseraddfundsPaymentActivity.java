package com.satrangolimitless.User_UI.Myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

import org.json.JSONObject;

public class UseraddfundsPaymentActivity extends AppCompatActivity implements PaymentResultListener  {
CardView paymentmethod;
    Session session;
    private Checkout chackout;
    private String razorpayKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        session = new Session(getApplicationContext());


        rezorpayCall();
        findViewById(R.id.paymentmethod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling this method to show our android custom alert dialog
                showCustomDialog();
            }
        });

    }
        private void showCustomDialog() {
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_sucessbooking_diglog, viewGroup, false);

            TextView SucessClose=dialogView.findViewById(R.id.SucessClose);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            SucessClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UseraddfundsPaymentActivity.this, LandingActivity.class);
                    startActivity(intent);
                }
            });
            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

    }


    private void rezorpayCall() {

        razorpayKey = "rzp_live_VIyMilt2ymDzxD"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id

        // razorpayKey="rzp_test_Hr1m6CAZ27tNGD"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id

        chackout = new Checkout();
        chackout.setKeyID(razorpayKey);
        try {
            JSONObject options = new JSONObject();
            options.put("name", session.getUser_name());
            options.put("description", "Razorpay Payment ");
            options.put("currency", "INR");
            double total = Double.parseDouble("1");
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", session.getEmail());
            preFill.put("contact", session.getMobile());
            options.put("prefill", preFill);

            chackout.open(UseraddfundsPaymentActivity.this, options);


        } catch (Exception e) {
            Toast.makeText(UseraddfundsPaymentActivity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}