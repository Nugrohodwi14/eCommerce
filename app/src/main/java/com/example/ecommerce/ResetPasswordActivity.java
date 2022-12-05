package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity
{

    private String check = "";
    private TextView pageTitle, titleQuestions;
    private EditText phoneNumber,questions1, questions2 ;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        check = getIntent().getStringExtra("check");

        pageTitle = findViewById(R.id.page_title);
        titleQuestions = findViewById(R.id.title_questions);
        phoneNumber = findViewById(R.id.find_phone_number);
        questions1 = findViewById(R.id.question_1);
        questions2 = findViewById(R.id.question_2);
        verifyButton = findViewById(R.id.verify_btn);

    }



    @Override
    protected void onStart()
    {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);


        if(check.equals("settings"))
        {
            pageTitle.setText("set Questions");
            titleQuestions.setText("Please set Answer for the Following Security Questions?");
            verifyButton.setText("Set");

            displayPreviousAnswer();

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    setAnswer();
                }
            });

        }
        else if(check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);

            verifyButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    verifyUser();
                }
            });
        }
    }


    private void setAnswer()
    {
        String answer1 = questions1.getText().toString().toLowerCase();
        String answer2 = questions2.getText().toString().toLowerCase();

        if(questions1.equals("") && questions2.equals(""))
        {
            Toast.makeText(ResetPasswordActivity.this, "Please answer both questions", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());

            HashMap<String,Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", answer1);
            userdataMap.put("answer2", answer2);

            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ResetPasswordActivity.this, "you have answer security questions succesfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void displayPreviousAnswer()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String ans1 = dataSnapshot.child("answer1").getValue().toString();
                    String ans2 = dataSnapshot.child("answer2").getValue().toString();

                    questions1.setText(ans1);
                    questions2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void verifyUser()
    {
        String phone = phoneNumber.getText().toString();
        String answer1 = questions1.getText().toString().toLowerCase();
        String answer2 = questions2.getText().toString().toLowerCase();
        
        if (!phone.equals("") && !answer1.equals("")  && !answer2.equals(""))
        {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(phone);



            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.exists())
                    {
                        String mPhone = dataSnapshot.child("phone").getValue().toString();

                        if(dataSnapshot.hasChild("Sequrity Questions"))
                        {
                            String ans1 = dataSnapshot.child("Sequrity Questions").child("answer1").getValue().toString();
                            String ans2 = dataSnapshot.child("Sequrity Questions").child("answer2").getValue().toString();

                            if(!ans1.equals(answer1))
                            {
                                Toast.makeText(ResetPasswordActivity.this, "your 1st answer is wrong", Toast.LENGTH_SHORT).show();
                            }
                            else if(!ans2.equals(answer2))
                            {
                                Toast.makeText(ResetPasswordActivity.this, "your 2nd answer is wrong", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("New Password");

                                final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("Write your new password");
                                builder.setView(newPassword);

                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i)
                                    {
                                        if(!newPassword.getText().toString().equals(""))
                                        {
                                            ref.child("password")
                                                    .setValue(newPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task)
                                                        {
                                                            if(task.isSuccessful())
                                                            {
                                                                Toast.makeText(ResetPasswordActivity.this, "password changed succesfull", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(ResetPasswordActivity.this, loginActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        dialogInterface.cancel();
                                    }
                                });

                                builder.show();
                            }
                        }

                        else
                        {
                            Toast.makeText(ResetPasswordActivity.this, "you have not set the sequrity questions.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ResetPasswordActivity.this, "this number does not exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {

                }
            });
        }
        else 
        {
            Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show();
        }
    }

}