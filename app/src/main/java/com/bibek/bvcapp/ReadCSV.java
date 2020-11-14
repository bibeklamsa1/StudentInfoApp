package com.bibek.bvcapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV extends AppCompatActivity {

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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_csv);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lvInternalStorage = (ListView) findViewById(R.id.lvInternalStorage);
        btnUpDirectory = (Button) findViewById(R.id.btnUpDirectory);
        btnSDCard = (Button) findViewById(R.id.btnViewSDCard);

//         declearing the student database
        studentinfo=this.openOrCreateDatabase("StudentInfo",MODE_PRIVATE,null);
        studentinfo.execSQL("CREATE TABLE IF NOT EXISTS studentinfos(regno VARCHAR unique,studentname VARCHAR,tenthgpa NUMBER(1,2),twelper NUMBER(2,2),btechper NUMBER(2,2),parentno NUMBER(10),email VARCHAR)");
        studentinfo.execSQL("DELETE FROM studentinfos;");
        //need to check the permissions
        checkFilePermissions();

        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);

                    //Execute method for reading the excel data.

                    try {
                        readA();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else
                {
                    count++;
                    pathHistory.add(count,(String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });

        //Goes up one directory level
        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0){
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                }else{
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });

        //Opens the SDCard or phone memory
        btnSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });

    }

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++)
            {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    List<String> xyvalues=new ArrayList<String>();
    public void readA() throws IOException {
        File inputFile = new File(lastDirectory);
        InputStream inputStream=new FileInputStream(inputFile);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line;

        try {
            line =reader.readLine();
            while ((line = reader.readLine()) != null) {
                //split by ','
                String[] tokens=line.split(",");
                studentinfo.execSQL("INSERT INTO studentinfos VALUES (+" + "'" + tokens[1] + "'" + "," + "'" + tokens[2]+ "'"  +  "," + "'" + tokens[3]+ "'"  + "," + "'" + tokens[4]+ "'"  + "," + "'" + tokens[5]+ "'"  +  "," + "'" + tokens[6]+ "'"   + "," + "'" + tokens[7]+ "'" +");");

            }

//                String str="INSERT INTO studentinfo (student,regno) VALUES("+"'"+tokens[0]+"'"+","+"'"+tokens[1]+"'"+")";
//                studentinfo.execSQL(str);

            Intent studentinfoIntent = new Intent(this.getApplicationContext(),SearchStudentInfo.class);
            startActivity(studentinfoIntent);


        } catch (Exception e) {
            Log.wtf("MyActivity","Error reaading data file on line",e);
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}

