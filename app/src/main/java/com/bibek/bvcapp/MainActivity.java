package com.bibek.bvcapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static String username,password;
    EditText usernamefield,passwordfield;
    static SQLiteDatabase sqLiteDatabase;
    public static Button signinbutton,forgetpasswordbutton, loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creating the database and table named 'userpasstable' if not exists !
        try {

            sqLiteDatabase = this.openOrCreateDatabase("userpassdatabase", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS userdata(username VARCHAR,password VARCHAR,hint VARCHAR)");

        }catch (Exception e){
            e.printStackTrace();
        }

//button decleartion part
         signinbutton  = (Button)findViewById(R.id.signinbutton);
         forgetpasswordbutton = (Button)findViewById(R.id.forgetpassword);
         loginbutton = (Button)findViewById(R.id.loginButton);

//on singinbutton click lisetner
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentforsignin = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intentforsignin);
            }
        });
//on forgetpassword click listener
        forgetpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intentforforgetpassword= new Intent(getApplicationContext(), ResetActivity.class);

                        startActivity(intentforforgetpassword);


            }
        });

//on login button click listener
          loginbutton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  try {
                      usernamefield=(EditText)findViewById(R.id.username);
                      passwordfield=(EditText)findViewById(R.id.password);
                      //sqLiteDatabase.execSQL("DELETE  FROM userdata ");
                      Cursor c = MainActivity.sqLiteDatabase.rawQuery("SELECT * FROM  userdata where username="+"'"+usernamefield.getText().toString()+"'", null);

                      if (c.moveToFirst()) {
                          int user_index = c.getColumnIndex("username");
                          int password_index = c.getColumnIndex("password");
                          int hint_index = c.getColumnIndex("hint");
                          if (passwordfield.getText().toString().equals(c.getString(password_index))) {

                              Intent intentforlogin = new Intent(getApplicationContext(), SearchStudentInfo.class);
                              startActivity(intentforlogin);
                          }

                      }
                      else{
                          Toast.makeText(MainActivity.this, "UserName and Password incorrect !!!", Toast.LENGTH_SHORT).show();
                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }


          });
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


}
