package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class paymentMethod extends AppCompatActivity implements PaymentResultListener {

    private TextView txtPaymentStatus;
    private Button btnPayNow;
    private EditText editAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);


        txtPaymentStatus = findViewById(R.id.paymentStatus);
        editAmount = findViewById(R.id.edit_amount);
        btnPayNow = findViewById(R.id.btn_pay);


        Checkout.preload(paymentMethod.this);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment(Integer.parseInt(editAmount.getText().toString()));
            }
        });

    }

    public void startPayment(int Amount){
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_WvXhA1WYXiWh3S");


        try {
            JSONObject options = new JSONObject();
            options.put("name","RazorPay Demo");
            options.put("description","description demo");
            options.put("theme color", "#FF4081");
            options.put("currency","IDR");
            options.put("amount",Amount*100);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count",4);

            options.put("retry",retryObj);

            checkout.open(paymentMethod.this,options);
        }catch (Exception e){
            Toast.makeText(paymentMethod.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        txtPaymentStatus.setText(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        txtPaymentStatus.setText("Error");
    }
}