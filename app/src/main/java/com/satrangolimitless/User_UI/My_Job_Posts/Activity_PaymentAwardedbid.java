package com.satrangolimitless.User_UI.My_Job_Posts;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

import org.json.JSONObject;

public class Activity_PaymentAwardedbid extends AppCompatActivity implements PaymentResultListener {
    Session session;
    private Checkout chackout;
    private String razorpayKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        session = new Session(getApplicationContext());
        rezorpayCall();
    }

    private void rezorpayCall() {

        razorpayKey = "rzp_test_I4F35g0EXHdpYt"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id

        // razorpayKey="rzp_live_VIyMilt2ymDzxD"; //Generate your razorpay key from Settings-> API Keys-> copy Key Id
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

            chackout.open(Activity_PaymentAwardedbid.this, options);


        } catch (Exception e) {
            Toast.makeText(Activity_PaymentAwardedbid.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        System.out.println("onPaymentSuccess-----------      " + s);


    }

    @Override
    public void onPaymentError(int i, String s) {

        System.out.println("onPaymentError-----------      " + s);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
