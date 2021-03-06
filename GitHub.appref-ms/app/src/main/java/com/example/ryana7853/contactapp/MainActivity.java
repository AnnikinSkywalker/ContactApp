package com.example.ryana7853.contactapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    DatabaseHelper myDb;
    EditText editName;
    EditText editNumber;
    EditText editEmail;
    Button btnAddData;
    public static final String EXTRA_MESSAGE = "hello world";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editText_name);
        editNumber = (EditText) findViewById(R.id.editText_number);
        editEmail = (EditText) findViewById(R.id.editText_email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addData (View v){
        boolean isInserted = myDb.insertData(editName.getText().toString(), editNumber.getText().toString(), editEmail.getText().toString());
        if(isInserted){
            Log.d("MyContact", "Data insertion successful!");
            Toast.makeText(getApplicationContext(), "SUCCESS!",  Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("MyContact", "Data insertion unsuccessful!");
            Toast.makeText(getApplicationContext(), "FAILURE!",  Toast.LENGTH_SHORT).show();
        }
    }

    public void viewData(View v){
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            showMessage("Error", "No data found in database");
            Log.d("MyContact", "No data found in database!");
            Toast.makeText(getApplicationContext(), "No data found in database!!", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID " + res.getString(0) + "\n");
            buffer.append("NAME " + res.getString(1) + "\n");
            buffer.append("NUMBER " + res.getString(2) + "\n");
            buffer.append("EMAIL " + res.getString(3) + "\n");
        }

        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void findData(View view){

        Log.d("MyContact", "Trying...");
        Cursor res = myDb.getAllData();
        String name = editName.getText().toString();
        StringBuffer buffer = new StringBuffer();

        boolean isPresent = false;
        while(res.moveToNext()){
            if(name.equalsIgnoreCase(res.getString(1))){
                Log.d("MyContact", "Contact found!");
                Toast.makeText(getApplicationContext(), "Successfully found contact!", Toast.LENGTH_SHORT).show();
                buffer.append("ID " + res.getString(0) + "\n");
                buffer.append("NAME " + res.getString(1) + "\n");
                buffer.append("NUMBER " + res.getString(2) + "\n");
                buffer.append("EMAIL " + res.getString(3) + "\n");
               isPresent = true;
            }
        }
        if(!isPresent) {
            Log.d("MyContact", "Not found");
            Toast.makeText(getApplicationContext(), "Data not found.", Toast.LENGTH_SHORT).show();
        }
        if(isPresent){
            showMessage("Data found: ", buffer.toString());
        }

    }

    public void searchTool(View view){

        Cursor res = myDb.getAllData();
        String name = editName.getText().toString();
        StringBuffer buffer = new StringBuffer();

        boolean isPresent = false;
        while(res.moveToNext()){
            if(name.equalsIgnoreCase(res.getString(1))){
                Log.d("MyContact", "Contact found!");
                Toast.makeText(getApplicationContext(), "Successfully found contact!", Toast.LENGTH_SHORT).show();
                buffer.append("ID " + res.getString(0) + "\n");
                buffer.append("NAME " + res.getString(1) + "\n");
                buffer.append("NUMBER " + res.getString(2) + "\n");
                buffer.append("EMAIL " + res.getString(3) + "\n");
                isPresent = true;
            }
        }
        if(!isPresent) {
            Log.d("MyContact", "Not found");
            Toast.makeText(getApplicationContext(), "Data not found.", Toast.LENGTH_SHORT).show();
        }
        if(isPresent) {
            String message = buffer.toString();
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

}
