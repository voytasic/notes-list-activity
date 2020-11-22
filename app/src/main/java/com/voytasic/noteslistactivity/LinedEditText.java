package com.voytasic.noteslistactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {
    private Rect mRect;
    private Paint mPaint;

    public LinedEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(0xFFFFD966);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = ((View)this.getParent()).getHeight();
        int lineHeight = getLineHeight();
        int numberOfHLines = height/lineHeight;

        int width = ((View)this.getParent()).getWidth();
        int lineGap = lineHeight;
        int numberOfVLines = width/lineHeight;

        Rect rect = mRect;
        Paint paint = mPaint;
        final String TAG="LineEditText";

        int baseLine = getLineBounds(0, rect);

        //Log.d(TAG, "onDraw: start base line: "+baseLine);
        for(int i=0; i < numberOfHLines; i++){
           canvas.drawLine(rect.left, baseLine+1 , rect.right, baseLine+1 , paint );
           baseLine+=lineHeight;
        }
        //Log.d(TAG, "onDraw: stop base line: "+baseLine);
        int h=rect.left+lineGap/2;
        for(int i=0; i < numberOfVLines; i++){
            canvas.drawLine(h, rect.top , h, rect.top+height, paint );
            h+=lineGap;
        }
        super.onDraw(canvas);
    }
}
