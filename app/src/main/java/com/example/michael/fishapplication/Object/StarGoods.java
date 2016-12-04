package com.example.michael.fishapplication.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import com.example.michael.fishapplication.R;

import java.util.Random;

/**
 * Created by Michael on 2016/12/4.
 */
public class StarGoods extends GameGoods {

    private Bitmap stargoods;
    private static int currentCount = 0;	 //	对象当前的数量
    //    public static int currentCount = 0;	 //	对象当前的数量
    public static int sumCount = 1;	 	 	 //	对象总的数量

    public StarGoods(Resources resources) {
        super(resources);
        Random ran = new Random();
        this.speed = ran.nextInt(40) +10;
        initBitmap();
    }

    @Override
    protected void initBitmap() {
        stargoods = BitmapFactory.decodeResource(resources, R.drawable.stargoods);

        Matrix matrix = new Matrix();
        matrix.postScale((float)1/6,(float) 1/6); //长和宽放大缩小的比例
        stargoods = Bitmap.createBitmap(stargoods, 0, 0, stargoods.getWidth() , stargoods.getHeight(), matrix, true);

        object_height = stargoods.getHeight()/3;
        object_width = stargoods.getWidth();
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        Random ran = new Random();
        Log.i("APP.TAG",Float.toString(screen_width));
//        object_x = ran.nextInt((int)(screen_width - object_width));
////        object_x = 0;
//        object_y = screen_height + object_height;;
        object_x = ran.nextInt((int)(screen_width - object_width));
        object_y = - object_height;;
        currentCount++;
        if(currentCount >= sumCount){
            currentCount = 0;
        }
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            canvas.drawBitmap(stargoods, object_x, object_y, paint);
            canvas.restore();
            logic();
        }

    }

    @Override
    public void logic() {
        if (object_y < screen_height) {
            object_y += speed;
        }
        else {
            isAlive = false;
        }
//        if (object_y < screen_height) {
//            object_y += speed;
//        }
//        else {
//            isAlive = false;
//        }
//        if(object_y + object_height > 0){
//            isVisible = true;
//        }
//        else{
//            isVisible = false;
//        }
    }



    @Override
    public boolean isCollide(GameObject obj) {
        // 矩形1位于矩形2的左侧
        if (object_x <= obj.getObject_x()
                && object_x + object_width <= obj.getObject_x()) {
            return false;
        }
        // 矩形1位于矩形2的右侧
        else if (obj.getObject_x() <= object_x
                && obj.getObject_x() + obj.getObject_width() <= object_x) {
            return false;
        }
        // 矩形1位于矩形2的上方
        else if (object_y <= obj.getObject_y()
                && object_y + object_height <= obj.getObject_y()) {
            return false;
        }
        // 矩形1位于矩形2的下方
        else if (obj.getObject_y() <= object_y
                && obj.getObject_y() + obj.getObject_height() <= object_y) {
            return false;
        }
        isAlive = false;
        return true;
    }

    @Override
    public void release() {
        if(!stargoods.isRecycled()){
            stargoods.recycle();
        }
    }
}
