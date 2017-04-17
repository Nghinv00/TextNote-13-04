package com.nghinv.textnote.ui;

import android.app.Activity;
import android.content.Intent;
import android.nfc.tech.TagTechnology;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.nghinv.textnote.R;
import com.nghinv.textnote.Sqlite.MainActivity;
import com.nghinv.textnote.data.TextNote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends Activity {

    String YOUR_APP_ID = "8FE516AE-6168-DD32-FF82-DA1D16824900";
    String YOUR_SECRET_KEY = "2DC074FA-794B-618D-FF99-0509C7525C00";
    String appVersion = "v1";
    public static List<TextNote> noteList;

    public static String username;
    LoginButton loginbutton;
    EditText editEmail, editPass;
    String email = "", pass = "";
    private CallbackManager callbackManager;
    Button btnLogin123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp(getApplicationContext(), YOUR_APP_ID, YOUR_SECRET_KEY, appVersion);

        facebookSDKInitialize();
        setContentView(R.layout.activity_login);
        getDataObject();
        editEmail = (EditText) findViewById(R.id.editEEEEEEEE);
        editPass = (EditText) findViewById(R.id.editPPPPPPPP);
        btnLogin123= (Button) findViewById(R.id.LoginEmail);
        btnLogin123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmail();
            }
        });
        loginbutton = (LoginButton) findViewById(R.id.loginbuton);
        loginbutton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends","user_likes"));

        loginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                /**
                 * Lấy toàn bộ thông tin của người dùng ứng dụng
                 */
                GraphRequest request = GraphRequest.newMeRequest( accessToken ,
                        new GraphRequest.GraphJSONObjectCallback(){
                            @Override
                            public void onCompleted( JSONObject object, GraphResponse reponse){
                                try{
                                    Log.d("TAG", object.toString());
                                    Log.e("TAG", reponse.toString());
                                    username = object.getString("name");
                                    /*btnLogin.setText(username);*/
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle param = new Bundle();
                param.putString("fields", "name");
                request.setParameters(param);
                request.executeAsync();

                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Log.e("TAG","Cancel");
            }
            @Override
            public void onError(FacebookException error) {
                Log.e(error.getMessage() + "", "aaa");
               /* btnLogin.setText("Đăng nhập thất bại");*/
            }
        });
    }
    private void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

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
    private void loginEmail() {
        email = editEmail.getText() + "";
        pass = editPass.getText() + "";

        if (  ( email.equals("mai@gmail.com") && pass.equals("123456") )
                ||  ( email.equals("nghi@gmail.com") && pass.equals("123456") )
                ||  ( email.equals("nghinv1310@gmail.com") && pass.equals("123456") )  ) {
            Toast.makeText(getApplicationContext(), "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();

        }
    }

}
