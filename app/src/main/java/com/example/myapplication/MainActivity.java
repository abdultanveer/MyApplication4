package com.example.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.data.DAO;

public class MainActivity extends AppCompatActivity {
    EditText titleEditText, subTitleEditText;
    static String FILE_NAME = "myfile";
    public  static  String KEY_TITLE = "title";
    public  static  String KEY_SUBTITLE = "subtitle";
    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //4
        dao = new DAO(this);
        dao.openDb();

        titleEditText = findViewById(R.id.title_et);
        subTitleEditText = findViewById(R.id.subtitle_et);
        if(savedInstanceState !=null){
            titleEditText.setText(savedInstanceState.getString(KEY_TITLE));
            subTitleEditText.setText(savedInstanceState.getString(KEY_SUBTITLE));

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
       // saveData();
    }

    private void saveData() {
        String title = titleEditText.getText().toString();
        String subTitle = subTitleEditText.getText().toString();

        //create a file
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        //open the file
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //write to the file
        editor.putString(KEY_TITLE,title);
        editor.putString(KEY_SUBTITLE,subTitle);
        //save the file
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // restoreData();
    }

    private void restoreData() {
        //open file
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        //read from the file
        String title = sharedPreferences.getString(KEY_TITLE,"");
        String subtitle = sharedPreferences.getString(KEY_SUBTITLE,"");
        //put the data back into edittext
        titleEditText.setText(title);
        subTitleEditText.setText(subtitle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(KEY_TITLE,"my title");
        outState.putString(KEY_SUBTITLE,"my subtitle");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void clickListener(View view) {
        switch (view.getId()){
            case R.id.buttonget://7
              String result =  dao.readRow();
                TextView resultTextView = findViewById(R.id.textViewresult);
                resultTextView.setText(result);
                break;
            case R.id.buttonsave://4a
                String title = titleEditText.getText().toString();
                String subTitle = subTitleEditText.getText().toString();

                dao.createRow(title,subTitle);
                break;
        }
    }
}
