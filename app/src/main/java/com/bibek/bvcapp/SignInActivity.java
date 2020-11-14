package com.bibek.bvcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;

public class SignInActivity extends AppCompatActivity {

    static EditText signinpassword1;
    static EditText signinpassword2,hint;
    static EditText signinusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button signinok = (Button)findViewById(R.id.singinok);

        signinok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinusername=(EditText) findViewById(R.id.signinusername);
                signinpassword1=(EditText)findViewById(R.id.signinpassword1);
                signinpassword2=(EditText)findViewById(R.id.signinpassword2);
                hint=(EditText)findViewById(R.id.firstpassclue);
                String username=signinusername.getText().toString();
                String password1click=signinpassword1.getText().toString();
                String password2res = signinpassword2.getText().toString();
                String hints = hint.getText().toString();
                if(signinpassword1.getText().toString().equals(signinpassword2.getText().toString()) && (username.length()>=5 && password1click.length()>=5 && hints.length()>=5 )) {

                    {
                        try {

                            MainActivity.sqLiteDatabase.execSQL("INSERT INTO userdata (username,password,hint) VALUES (+" + "'" + username + "'" + "," + "'" + password1click + "'" + "," + "'" + hints + "'" + ")");
                             MainActivity.signinbutton.animate().alpha(0).setDuration(0);
                             MainActivity.forgetpasswordbutton.animate().alpha(0).setDuration(0);
                            Intent signinintent=new Intent(getApplicationContext(),MainActivity.class);
                             startActivity(signinintent);

//                            Cursor c = MainActivity.sqLiteDatabase.rawQuery("SELECT * FROM  userdata", null);
//
//                            c.moveToFirst();
//                            int user_index = c.getColumnIndex("username");
//                            int password_index = c.getColumnIndex("password");
//                            int hint_index = c.getColumnIndex("hint");
//                            while (c != null) {
//                                Log.i("The event ", c.getString(user_index));
//                                Log.i("The event is happed in ", c.getString(password_index));
//                                Log.i("hint", c.getString(hint_index));
//                                c.moveToNext();
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                else {
                    if(signinusername.length()<5){
                        makeText(SignInActivity.this, "Please enter AtLeast 5 character in username field !", LENGTH_LONG).show();
                    }
                    else if(password1click.isEmpty() && password2res.isEmpty()){
                        makeText(SignInActivity.this, "Enter Password in password field", LENGTH_SHORT).show();
                    }
                    else if(password1click==password2res){
                        signinpassword1.setText("");
                        signinpassword2.setText("");
                        makeText(SignInActivity.this, "Password atmost 5 character !", LENGTH_SHORT).show();

                    }
                    if (!password1click.equals(password2res)) {
                        signinpassword1.setText("");
                        signinpassword2.setText("");
                        makeText(SignInActivity.this, "Password Incorrect", LENGTH_SHORT).show();
                    }
                    else {
                        makeText(SignInActivity.this, "Please enter AtLeast 5 character in hint field", LENGTH_SHORT).show();
                    }
                }
            }


        });


    }
}
