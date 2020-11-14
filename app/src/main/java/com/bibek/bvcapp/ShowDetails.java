package com.bibek.bvcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowDetails extends AppCompatActivity {

    TextView Regno,name,email,phno,tengpa,twelper,btechper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        Regno = (TextView) findViewById(R.id.Regno);
        name=(TextView) findViewById(R.id.name);
        email=(TextView)findViewById(R.id.emailid);
        phno = (TextView)findViewById(R.id.phno);
        tengpa=(TextView)findViewById(R.id.tenper);
        twelper=(TextView)findViewById(R.id.twelper);
        btechper = (TextView)findViewById(R.id.btechper);
        Intent intent=getIntent();
        String nameofstudent = intent.getStringExtra("studentname");
        String regno=intent.getStringExtra("regno");
        String emailid = intent.getStringExtra("email");
        String ph_no = intent.getStringExtra("parentno");
        String tengpano = intent.getStringExtra("tengpa");
        String twelperno = intent.getStringExtra("twelper");
        String btech = intent.getStringExtra("btech");
        Regno.setText(regno);
        name.setText(nameofstudent);
        email.setText(emailid);
        phno.setText(ph_no);
        tengpa.setText(tengpano);
        twelper.setText(twelperno);
        btechper.setText(btech);
    }
}
