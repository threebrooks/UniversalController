package com.example.me.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Me on 2017-03-12.
 */

public class MySurfaceView extends SurfaceView {

        private SurfaceHolder surfaceHolder;

        public MySurfaceView(Context context) {
            super(context);
            init();
        }

        public MySurfaceView(Context context,
                             AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public MySurfaceView(Context context,
                             AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        private void init(){
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(new SurfaceHolder.Callback(){

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    Canvas canvas = holder.lockCanvas(null);
                    drawSomething(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder,
                                           int format, int width, int height) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    // TODO Auto-generated method stub

                }});
        }

        protected void drawSomething(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAlpha(128);
            canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2,paint);
        }

    }
