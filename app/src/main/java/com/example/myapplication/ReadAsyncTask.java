package com.example.myapplication;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.myapplication.data.TodoNote;
import com.example.myapplication.data.source.local.DAO;

public class ReadAsyncTask extends AsyncTask<Void,Void, TodoNote> {

    DAO dao;
    TextView mTextView;

    public ReadAsyncTask(TextView resultTextView, DAO dao) {
        this.dao = dao;
        mTextView = resultTextView;
    }

    @Override
    protected TodoNote doInBackground(Void... voids) {
        return dao.readRow1();
    }

    @Override
    protected void onPostExecute(TodoNote todoNote) {
        super.onPostExecute(todoNote);
        mTextView.setText(todoNote.getTitle()+"\n"+todoNote.getSubTitle());
    }
}
