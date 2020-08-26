package com.example.razorpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    private Button buttonConfirmOrder;
    private EditText editTextPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());
        findViews();
        listeners();


    }
    public void findViews() {
        buttonConfirmOrder = (Button) findViewById(R.id.buttonConfirmOrder);
        editTextPayment = (EditText) findViewById(R.id.editTextPayment);
    }

    public void listeners() {


        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextPayment.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please fill payment", Toast.LENGTH_LONG).show();
                    return;
                }
                startPayment();
            }
        });
    }


    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "OkZio Pvt. Ltd");
            options.put("description", "Delivery Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSX-HXB4YTEstkMafwkvBn0RWD80rQAxrrp4Qy_82LhrYKGaHZ7cg");
            options.put("currency", "INR");

            String payment = editTextPayment.getText().toString();

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", "rajeev1995rajan@gmail.com");
            preFill.put("contact", "9781767938");
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        Toast.makeText(this, "oid"+paymentData.getOrderId()+"pid"+paymentData.getPaymentId()+"user contact" +
                paymentData.getUserContact()+"user email"+paymentData.getUserEmail()  , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }
}