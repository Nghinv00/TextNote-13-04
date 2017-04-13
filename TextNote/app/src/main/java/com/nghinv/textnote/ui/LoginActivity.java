package com.nghinv.textnote.ui;

import android.app.Activity;
import android.content.Intent;
import android.nfc.tech.TagTechnology;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.nghinv.textnote.R;
import com.nghinv.textnote.Sqlite.MainActivity;
import com.nghinv.textnote.data.TextNote;

import java.util.List;

public class LoginActivity extends Activity {

    String YOUR_APP_ID = "8FE516AE-6168-DD32-FF82-DA1D16824900";
    String YOUR_SECRET_KEY = "2DC074FA-794B-618D-FF99-0509C7525C00";
    String appVersion = "v1";
    public static List<TextNote> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Backendless.initApp(getApplicationContext(), YOUR_APP_ID, YOUR_SECRET_KEY, appVersion);
        /*
        new Thread(new Runnable() {
            public void run() {
                // synchronous backendless API call here:

            }
        }).start();
*/
         getDataObject();

        setContentView(R.layout.activity_login);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  getDataObject();*/
                List<TextNote> aaaa  = noteList;

                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getDataObject() {

        new Thread(new Runnable() {
            public void run() {
                // synchronous backendless API call here:
                Backendless.Persistence.of( TextNote.class).find( new AsyncCallback<BackendlessCollection<TextNote>>(){
                    @Override
                    public void handleResponse( BackendlessCollection<TextNote> foundTextNote )
                    {
                        Log.e("TAG", foundTextNote.toString());
                        Toast.makeText(getApplicationContext(), "Kết nối thành công", Toast.LENGTH_LONG).show();
                        noteList = foundTextNote.getCurrentPage();  // Lấy ra list danh sách của đối tượng
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Log.e("TAG", fault.toString());
                        Toast.makeText(getApplicationContext(), "Kết nối thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();

//        List<TextNote> t = nodeList;

    }

}
