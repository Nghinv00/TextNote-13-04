package com.nghinv.textnote;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    // public class MainActivity extends ListActivity {
    static final int TAO_NOTEACTIVITY_THANHCONG = 0;
    static final int MO_NOTEACTIVITY_THANHCONG = 1;
    static final int TRA_VE_KQ_NOTEACTIVITY_THANHCONG = 2;
    private static final int DELETE_ID = Menu.FIRST;
    private  static final  int NgayTao = Menu.CATEGORY_SECONDARY;
    private NoteAdapter noteAdapter;
    Button btnAdd;
    Cursor cursor;
    String time;
    Cursor cursor1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteAdapter = new NoteAdapter(this);
        noteAdapter.open();
        fillData();
        registerForContextMenu(getListView());
        getFormWidget();
        addEvent();
    }

    public void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, TAO_NOTEACTIVITY_THANHCONG);
            }
        });
    }

    public void getFormWidget() {
        btnAdd = (Button) findViewById(R.id.btnAdd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                return true;
            case R.id.menuSapXep:
                return true;
            case  R.id.MenuLocDS:
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(NoteAdapter.KEY_ROWID, id);
        startActivityForResult(intent, MO_NOTEACTIVITY_THANHCONG);
    }

    public void fillData() {
        // Lấy tất cả các bản ghi trong databasse
        cursor = noteAdapter.fetchAllNotes();
        startManagingCursor(cursor);

        String[] from = new String[]{ NoteAdapter.KEY_TITILE, NoteAdapter.KEY_NOTE, NoteAdapter.KEY_DATE};
        int[] values = new int[]{R.id.txtTitle, R.id.txtNote, R.id.txtDate};

        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.row_note,
                cursor, from, values);
        setListAdapter(notes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        menu.add(1, NgayTao, 0, R.string.menu_ngaytao);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                noteAdapter.deleteNote(info.id);
                fillData();
                return true;
            case NgayTao:   {
                /**
                 * Lấy ra thông tin ngày tạo note
                 */
                AdapterView.AdapterContextMenuInfo info1 = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                // Cursor cursor1 = noteAdapter.fetchTime(info1.id);
                long id1 = info1.id;    // lấy số id của ntoe trong cơ sở dữ liệu
                cursor1 = noteAdapter.fetchNote(info1.id);
                time = cursor1.getString(cursor1.getColumnIndexOrThrow(NoteAdapter.KEY_DATE));
                Toast.makeText(getApplicationContext(), time.toString(), Toast.LENGTH_SHORT).show();

            }
        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}

