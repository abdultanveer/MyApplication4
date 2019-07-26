package com.example.myapplication;

import android.os.AsyncTask;
import android.widget.EditText;

import com.example.myapplication.data.TodoNote;
import com.example.myapplication.data.source.local.DAO;


public class InsertAsyncTask extends AsyncTask<TodoNote,Void,Void> {

    DAO dao;
    EditText titleEditText, subtitleEditText;
    public InsertAsyncTask(DAO dao, EditText titleEditText, EditText subTitleEditText) {
        this.dao = dao;
        this.titleEditText = titleEditText;
        this.subtitleEditText = subTitleEditText;


    }

    @Override
    protected Void doInBackground(TodoNote... todoNotes) {
         dao.createRow(todoNotes[0]);
         return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        titleEditText.setText("");
        subtitleEditText.setText("");
    }
}
