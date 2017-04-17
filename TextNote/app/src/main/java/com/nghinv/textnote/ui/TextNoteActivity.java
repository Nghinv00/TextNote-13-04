package com.nghinv.textnote.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.nghinv.textnote.R;
import com.nghinv.textnote.adapter.CRUD;
import com.nghinv.textnote.data.TextNote;

public class TextNoteActivity extends Activity {

    Context context;
    EditText editTitie, editNote;
    public String objectId;
    CRUD crud = new CRUD();
    TextNote textNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);
        setTitle(R.string.app_name);
        editTitie = (EditText) findViewById(R.id.editTitie);
        editNote = (EditText) findViewById(R.id.editNote);



    }

    private void saveState() {

        textNote= new TextNote();
        String title = editTitie.getText() +"";
        String note = editNote.getText() + "";
        textNote.setTitle(title);
        textNote.setNote(note);



        if( title.equals("") &&  note.equals("") ){
               TextNoteActivity.this.finish();
        }
        if ( objectId == null){
            crud.saveContact(title, note);

            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("aaaa", textNote);
            intent.putExtra("bbbb", bundle);

            setResult(MenuActivity.THEM_NOTEACTIVITY_THANHCONG, intent);

        } else {
            crud.updateContact();

        }
        /*MenuActivity.textnoteAdapter.notifyDataSetChanged();*/
        this.finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    /**
     * Người dùng click nút quay lại trên điện thoại
     */
    @Override
    public void onBackPressed() {
        // your code.
        saveState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();


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
                AlertDialog.Builder dialog = new AlertDialog.Builder(TextNoteActivity.this);
                dialog.setTitle("Hướng Dẫn");
                dialog.setMessage("Phần mềm có sử dụng công cụ hỗ trợ: https://backendless.com");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                return true;
            case R.id.menuDelete:
                    this.finish();
                return true;

            case R.id.menuSave:
                saveState();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }

    private void HelpMe() {
        AlertDialog alertDialog = new AlertDialog.Builder(TextNoteActivity.this).create();
        alertDialog.setTitle("Thông Báo");
        alertDialog.setMessage("Ghi chú của bạn đang trống");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }




    public static class LineEditText extends EditText {
        // We need this constructor for LayoutInflater
        private Paint mPaint = new Paint();

        public LineEditText(Context context) {
            super(context);
            initPaint();
        }

        private void initPaint() {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.BLACK);
        }
        public LineEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            initPaint();
        }

        public LineEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initPaint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int left = getLeft();
            int right = getRight();
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int height = getHeight();
            int lineHeight = getLineHeight();
            int count = (height-paddingTop-paddingBottom) / lineHeight;

            if ( count < 30)    count = 30;
            for (int i = 0; i < count; i++) {
                int baseline = lineHeight * (i+1) + paddingTop;
                canvas.drawLine(left+paddingLeft, baseline, right-paddingRight, baseline, mPaint);
            }
            super.onDraw(canvas);
        }
    }

}
