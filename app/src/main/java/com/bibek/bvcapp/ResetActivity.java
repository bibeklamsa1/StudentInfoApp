package com.bibek.bvcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity {
    EditText resetusername,firstpassclue;
    Button resetbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
       resetbutton = (Button)findViewById(R.id.resetbutton);
       resetbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               resetusername=(EditText)findViewById(R.id.resetusername);
               firstpassclue=(EditText)findViewById(R.id.firstpassclue);
               try{
               //MainActivity.sqLiteDatabase
               Cursor resetusercursor = MainActivity.sqLiteDatabase.rawQuery("SELECT * FROM  userdata where username="+"'"+resetusername.getText().toString()+"'", null);
               if (resetusercursor.moveToFirst()) {
                   int user_index = resetusercursor.getColumnIndex("username");
                   int hint_index = resetusercursor.getColumnIndex("hint");
                   if (firstpassclue.getText().toString().equals(resetusercursor.getString(hint_index))) {
                       MainActivity.sqLiteDatabase.execSQL("DELETE FROM userdata WHERE username="+"'"+resetusername.getText().toString()+"'");
                       Intent intentforlogin = new Intent(ResetActivity.this, MainActivity.class);
                       startActivity(intentforlogin);
                   }
                   else{
                       Toast.makeText(ResetActivity.this, "UserName and Password incorrect !!!", Toast.LENGTH_SHORT).show();
                   }
               }
           } catch (Exception e) {
               e.printStackTrace();
           }

           }
       });

    }
}
