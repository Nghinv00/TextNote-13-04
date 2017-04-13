package com.nghinv.textnote.Sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.nghinv.textnote.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends Activity {

    public static String curDate = "";
    public static String curText = "";
    TextView dateText;
    EditText editTitle;
    EditText lineEditText;
    Long rowId;
    private Cursor cursor;
    private NoteAdapter noteAdapter;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mở ứng dung xử lý dữ liệu
        noteAdapter = new NoteAdapter(this);
        noteAdapter.open();
        setContentView(R.layout.activity_note);
        setTitle(R.string.app_name);
        editTitle = (EditText) findViewById(R.id.editTitie);
        lineEditText = (EditText) findViewById(R.id.lineEditText);
        dateText = (TextView) findViewById(R.id.notelist_date);
        /**
         *
         */

        long time = System.currentTimeMillis();
        Date dateTime = new Date(time);

        SimpleDateFormat formatter = new SimpleDateFormat("d'/'M'/'y");
        curDate = formatter.format(dateTime);

        dateText.setText(""+ curText);

        rowId = (savedInstanceState == null) ? null :
                (Long) savedInstanceState.getSerializable(NoteAdapter.KEY_ROWID);

        if (rowId == null){
            Bundle bundle = getIntent().getExtras();
            rowId = bundle !=
                    null ? bundle.getLong(NoteAdapter.KEY_ROWID) : null;
        }

        populateFields();

    }

    public void populateFields() {
        if (rowId != null) {
            cursor = noteAdapter.fetchNote(rowId);
            startManagingCursor(cursor);

            editTitle.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteAdapter.KEY_TITILE)));

            lineEditText.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteAdapter.KEY_NOTE)));
            curText = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteAdapter.KEY_DATE));
        }

    }


    public void saveState() {
        String title = editTitle.getText() + "";
        String note = lineEditText.getText() + "";
        // String date = dateText.getText() + "";

        if (title.equals(null) && note.equals(null)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set title
            alertDialogBuilder.setTitle("Your Title");

            // set dialog message

            alertDialogBuilder.setMessage("Click vào yes để xóa ghi chú");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            NoteActivity.this.finish();
                        }
                    });
            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                            return;
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }

        if (rowId == null) {
            long id = noteAdapter.createNode(title, note, curDate);
                if (id != -1 ) {
                    rowId = id;
                }
                else {
                    Log.e("saveState", "Tạo note thất bại");
                    }
                }
            else {
                boolean check = noteAdapter.updateNote(rowId, title, note, curDate);
                if ( check == false)
                Log.e("saveState", "Tạo note thất bại");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(NoteAdapter.KEY_ROWID, rowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuTroGiup:
                AlertDialog.Builder dialog = new AlertDialog.Builder(NoteActivity.this);
                dialog.setTitle("Trợ Giúp");
                dialog.setMessage("Hello");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                return true;

            case R.id.menuDelete:
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
                if (rowId != null) {
                    noteAdapter.deleteNote(rowId);
                }
                finish();
                return true;
            case R.id.menuSave:
                saveState();
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
