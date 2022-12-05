package com.example.ecommerce.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ecommerce.HomeActivity;
import com.example.ecommerce.MainActivity;
import com.example.ecommerce.R;

public class AdminCategoryActivity extends AppCompatActivity {


    private ImageView chair, table, setChairTable, etc ;

    private ImageView tShirt, sportsTShirt, femaleDresses, sweathers;
    private ImageView glasses, hatsCaps, walletBagsPurses, shoes;
    private ImageView headPhoneHandFree, Laptops, watches, mobilePhones;

    private Button LogoutBtn, CheckOrdersBtn, maintainProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        LogoutBtn = (Button)findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button)findViewById(R.id.check_orders_btn);
        maintainProductsBtn = (Button)findViewById(R.id.maintain_btn);


        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });


        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });



        chair = (ImageView)findViewById(R.id.chair);
        table = (ImageView)findViewById(R.id.table);
        setChairTable = (ImageView)findViewById(R.id.set);
        //etc = (ImageView)findViewById(R.id.etc);

        /*
        tShirt = (ImageView)findViewById(R.id.t_shirt);
        sportsTShirt = (ImageView)findViewById(R.id.sport_t_shirt);
        femaleDresses = (ImageView)findViewById(R.id.female_dresses);
        sweathers = (ImageView)findViewById(R.id.sweathers);

        glasses = (ImageView)findViewById(R.id.glasses);
        hatsCaps = (ImageView)findViewById(R.id.hats_caps);
        walletBagsPurses = (ImageView)findViewById(R.id.purses_bags_wallets);
        shoes = (ImageView)findViewById(R.id.shoes);

        headPhoneHandFree = (ImageView)findViewById(R.id.headphones_handfree);
        Laptops = (ImageView)findViewById(R.id.laptop_pc);
        watches = (ImageView)findViewById(R.id.watches);
        mobilePhones = (ImageView)findViewById(R.id.mobilephones);

         */





        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Table");
                startActivity(intent);
            }
        });



        setChairTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Set Chair&Table");
                startActivity(intent);
            }
        });


        /*
        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Lain-lain");
                startActivity(intent);
            }
        });

        */

        chair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Chair");
                startActivity(intent);
            }
        });

    }
}