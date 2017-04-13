package com.nghinv.textnote.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nghinv.textnote.R;
import com.nghinv.textnote.data.TextNote;

public class TextNoteActivity extends Activity {

    EditText editTitie, editNote;
    TextView dateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
        setTitle(R.string.app_name);
        editTitie = (EditText) findViewById(R.id.editTitie);
        editNote = (EditText) findViewById(R.id.editNote);
        dateText = (TextView) findViewById(R.id.notelist_date);


    }


    public void saveNewNote(){
        TextNote textNote = new TextNote();
        textNote.setTitle(editTitie.getText()+ "");
        textNote.setNote(editNote.getText()+ "");

        TextNote saveNote = Backendless.Persistence.save(textNote);
        Backendless.Persistence.save(textNote, new AsyncCallback<TextNote>() {
            @Override
            public void handleResponse(TextNote textNote) {

                Log.d("TAG", textNote.toString());
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

                Log.d("TAG", backendlessFault.toString());
            }
        });
    }



}
