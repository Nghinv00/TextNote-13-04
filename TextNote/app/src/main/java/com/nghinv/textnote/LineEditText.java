package com.nghinv.textnote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
        * Created by NghiNV on 24/03/2017.
        */

public class LineEditText extends EditText {
    // We need this constructor for LayoutInflater
    private Paint mPaint = new Paint();

    public LineEditText(Context context) {
        super(context);
        initPaint();
    }


    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LineEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int left = getLeft();
        Log.e("left", "left");
        int right = getRight();
        Log.d("right", "right");

        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        int width = getWidth();
        int height = getHeight();
        Log.e("height", "height");

        int lineHeight = getLineHeight();
        Log.e("line_height","line_height");

        int count = (height-paddingTop-paddingBottom) / lineHeight;

        for (int i = 0; i < count; i++) {
            int baseline = lineHeight * (i+1) + paddingTop;
            canvas.drawLine(left+paddingLeft, baseline, right-paddingRight, baseline, mPaint);
        }
        super.onDraw(canvas);
    }
}