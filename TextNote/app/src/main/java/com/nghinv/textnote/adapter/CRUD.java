package com.nghinv.textnote.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.property.ObjectProperty;
import com.nghinv.textnote.data.TextNote;

import java.util.List;

/**
 * Created by NghiNV on 13/04/2017.
 */

public class CRUD {

    public static List<TextNote> noteList;
    List<String>   listString;
    TextNote textNote;
    Context context;


    public List<String> ConvertObjectToString(List<TextNote> textNotes){

        /**
         *
         */
        textNotes = noteList;

        /**
         *
         */

        for( TextNote txt : textNotes ){
            listString.add(txt.getTitle());
        }
        return listString;
    }

    public void getDataObject() {

        Backendless.Persistence.of( TextNote.class ).find(new AsyncCallback<BackendlessCollection<TextNote>>(){
            @Override
            public void handleResponse( BackendlessCollection<TextNote> foundTextNote )
            {
                Log.e("TAG", foundTextNote.toString());
                Toast.makeText(context, "Kết nối thành công", Toast.LENGTH_SHORT).show();
                noteList = foundTextNote.getCurrentPage();  // Lấy ra list danh sách của đối tượng
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Log.e("TAG", fault.toString());
                Toast.makeText(context , "Kết nối thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPropertiesTable()
    {
       TextNote textNote = new TextNote();
        textNote.setTitle("ABc");
        textNote.setNote("ABC");

        // asynchronous call
        Backendless.Persistence.of( TextNote.class ).save( textNote, new AsyncCallback<TextNote>()
        {
            @Override
            public void handleResponse( TextNote txtxt )
            {
                /**
                 * Sẽ vào đến đây và lấy dudojc person vừa add
                 */
                Toast.makeText(context, "11111111", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
                /**
                 * Đã vào dến đây vào sẽ láy dược các thuộc tính của bảng....
                 */
                Toast.makeText(context, "222222222", Toast.LENGTH_SHORT).show();
                Log.e("TAG", backendlessFault.toString());
            }
        } );

        Backendless.Persistence.describe( "Person", new AsyncCallback<List<ObjectProperty>>()
        {
            @Override
            public void handleResponse( List<ObjectProperty> objectProperties )
            {
                Toast.makeText(context , "3333333333", Toast.LENGTH_SHORT).show();
                Log.e("TAG", objectProperties.toString());
            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
                Toast.makeText(context, "44444444", Toast.LENGTH_SHORT).show();
                Log.e("TAG", backendlessFault.toString());
            }
        } );

    }

    public void deleteContact()
    {
        TextNote textNote = new TextNote();
        textNote.setTitle("ABCABC");
        textNote.setNote("ABCABC");

        Backendless.Persistence.save( textNote, new AsyncCallback<TextNote>()
        {
            public void handleResponse( TextNote savedContact111 )
            {
                Backendless.Persistence.of( TextNote.class ).remove( savedContact111,
                        new AsyncCallback<Long>()
                        {
                            public void handleResponse( Long response )
                            {
                                Log.e("TAG", response.toString());
                                Toast.makeText( context , "Xóa bản ghi thành công!!!", Toast.LENGTH_SHORT).show();
                                String aaaa = response.toString();
                            }
                            public void handleFault( BackendlessFault fault )
                            {
                                Log.e("TAG", fault.toString());
                                Toast.makeText(context , "Thao tác thất bại. Xin mời bạn thao tác lại", Toast.LENGTH_SHORT).show();
                                String aaaa = fault.toString();

                            }
                        } );
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Log.e("TAG", fault.toString());
            }
        });
    }

    public void saveContact()
    {
        TextNote text = new TextNote();
        text.setTitle("Thứ 5");
        text.setNote("Nghỉ Nghỉ");

        // save object asynchronously
        Backendless.Persistence.save( text, new AsyncCallback<TextNote>() {
            public void handleResponse( TextNote response )
            {
                /**
                 * response sẽ trả về : thông tin của bản ghi vừa được chèn vào... bao gồm đầy đủ các trường trên server
                 */
                // new Contact instance has been saved
                Toast.makeText( context , "Thêm bản ghi thành công!!!", Toast.LENGTH_SHORT).show();
                Log.e("TAG", response.toString());
            }
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.
                Toast.makeText(context , "Thao tác thất bại. Xin mời bạn thao tác lại", Toast.LENGTH_SHORT).show();
                Log.e("TAG", fault.toString());
            }
        });
    }

    public void updateContact()
    {
        TextNote textNote = new TextNote();
        textNote.setTitle("AAAA");
        textNote.setNote("BBBB");

        Backendless.Persistence.save( textNote, new AsyncCallback<TextNote>() {
            public void handleResponse( TextNote saveText )
            {
                saveText.setTitle( "CCCC" );
                saveText.setNote( "DDDD" );
                Backendless.Persistence.save( saveText , new  AsyncCallback<TextNote>() {

                    @Override
                    public void handleResponse( TextNote response )
                    {
                        // Contact instance has been updated
                        Toast.makeText( context , "Thêm bản ghi thành công!!!", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", response.toString());
                        String aaaaa = response.toString();
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with
                        Toast.makeText( context , "Thao tác thất bại. Xin mời bạn thao tác lại", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", fault.toString());
                        String aaaaa = fault.toString();
                    }
                } );
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.
                Toast.makeText(context , "Thao tác thất bại. Xin mời bạn thao tác lại", Toast.LENGTH_SHORT).show();
                Log.e("TAG", fault.toString());
                String aaaaa = fault.toString();
            }
        });
    }

}
