package com.bibek.bvcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchStudentInfo extends AppCompatActivity {
    EditText editText;
    Button button;
    public static SQLiteDatabase studentinfo;
    ListView lvInternalStorage;

    private static final String TAG = "MainActivity";

    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;

    Button btnUpDirectory,btnSDCard;

    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(R.id.addcsvfile==item.getItemId()){
            Intent searchcsv = new Intent(getApplicationContext(),ReadCSV.class);
            startActivity(searchcsv);


        }

        return super.onOptionsItemSelected(item);
    }
    protected  void onStart() {

        super.onStart();
        editText = (EditText)findViewById(R.id.editText);
        button=(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String  full=editText.getText().toString();

                    Cursor c = ReadCSV.studentinfo.rawQuery(" SELECT * FROM studentinfos WHERE regno ="+"'"+full+"'", null);

                    if(c.moveToFirst()) {
                        int regno_column_index = c.getColumnIndex("regno");
                        int student_column_index = c.getColumnIndex("studentname");
                        int tengpa_column_index = c.getColumnIndex("tenthgpa");
                        int twelper_column_index =c.getColumnIndex("twelper");
                        int btech_column_index =c.getColumnIndex("btechper");
                        int parentno_column_index = c.getColumnIndex("parentno");
                        int email_column_index = c.getColumnIndex("email");
                        String nameofstudent = c.getString(student_column_index);
                        String regnoofstudent = c.getString(regno_column_index);
                        String tengpa  = c.getString(tengpa_column_index);
                        String twelper = c.getString(twelper_column_index);
                        String btech = c.getString(btech_column_index);
                        String parentno = c.getString(parentno_column_index);
                        String email = c.getString(email_column_index);

                        Intent intent = new Intent(getApplicationContext(), ShowDetails.class);
                        intent.putExtra("studentname",nameofstudent);
                        intent.putExtra("regno",regnoofstudent);
                        intent.putExtra("tengpa",tengpa);
                        intent.putExtra("twelper",twelper);
                        intent.putExtra("btech",btech);
                        intent.putExtra("parentno",parentno);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SearchStudentInfo.this, "Search not found !!!", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student_info);




    }
}
