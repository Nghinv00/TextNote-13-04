package com.nghinv.textnote.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nghinv.textnote.R;
import com.nghinv.textnote.adapter.CRUD;
import com.nghinv.textnote.adapter.TextNoteAdapter;
import com.nghinv.textnote.data.TextNote;
import java.util.List;

public class MenuActivity extends Activity {

    String YOUR_APP_ID = "8FE516AE-6168-DD32-FF82-DA1D16824900";
    String YOUR_SECRET_KEY = "2DC074FA-794B-618D-FF99-0509C7525C00";
    String appVersion = "v1";

    private static final int DELETE_ID = Menu.FIRST;
    private  static final int NgayTao = 2;
    static final int TAO_NOTEACTIVITY_THANHCONG = 0;
    static final int MO_NOTEACTIVITY_THANHCONG = 1;
    static final int TRA_VE_KQ_NOTEACTIVITY_THANHCONG = 2;

    Button btnAdd;
    ListView listView;

    CRUD crud= new CRUD();
    List<TextNote> noteList;
    TextNoteAdapter textnoteAdapter= null;
    TextNote textNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Backendless.initApp(this, YOUR_APP_ID, YOUR_SECRET_KEY, appVersion);
        setContentView(R.layout.activity_menu);

        noteList = LoginActivity.noteList;

        if ( noteList == null){
            Toast.makeText(getApplicationContext(), "Kết nối thất bại", Toast.LENGTH_SHORT).show();
        }
        getFormWidget();
        addEvent();

        textnoteAdapter = new TextNoteAdapter(getApplicationContext(), noteList);
        listView.setAdapter(textnoteAdapter);
    }

    public void getDataObject() {

        Backendless.Persistence.of(TextNote.class).find(new AsyncCallback<BackendlessCollection<TextNote>>() {
            @Override
            public void handleResponse(BackendlessCollection<TextNote> foundTextNote) {
                textNote = new TextNote();
                Log.e("TAG", foundTextNote.toString());
                Toast.makeText(getApplicationContext(), "Kết nối thành công", Toast.LENGTH_SHORT).show();

                noteList = foundTextNote.getCurrentPage();  // Lấy ra list danh sách của đối tượng
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("TAG", fault.toString());
                Toast.makeText(getApplicationContext(), "Kết nối thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TextNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getFormWidget() {
        btnAdd = (Button) findViewById(R.id.btnAdd);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        menu.add(1, NgayTao, 0, R.string.menu_ngaytao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
            case R.id.menu4:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
