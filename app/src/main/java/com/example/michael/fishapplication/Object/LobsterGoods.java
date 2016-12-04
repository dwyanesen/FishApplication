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
public class LobsterGoods extends GameGoods {


    private Bitmap lobstergoods;
    private Bitmap lobstergoods2;
    private static int currentCount = 0;	 //	对象当前的数量
    //    public static int currentCount = 0;	 //	对象当前的数量
    public static int sumCount = 1;	 	 	 //	对象总的数量

    public LobsterGoods(Resources resources) {
        super(resources);
        Random ran = new Random();
        this.speed = ran.nextInt(10) + 10;
        initBitmap();
    }

    @Override
    protected void initBitmap() {
        lobstergoods = BitmapFactory.decodeResource(resources, R.drawable.lobestergoods);

        Matrix matrix = new Matrix();
        matrix.postScale((float)1/7,(float) 1/7); //长和宽放大缩小的比例
        lobstergoods = Bitmap.createBitmap(lobstergoods, 0, 0, lobstergoods.getWidth() , lobstergoods.getHeight(), matrix, true);

        object_height = lobstergoods.getHeight();
        object_width = lobstergoods.getWidth();

        Matrix m = new Matrix();
        m.postScale(-1, 1);   //镜像水平翻转
        lobstergoods2 = Bitmap.createBitmap(lobstergoods, 0, 0, (int)object_width, (int)object_height, m, true);
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        Random ran = new Random();
        Log.i("APP.TAG","Lobster"+Float.toString(screen_height));
        object_x = 0 - object_width;
//        object_x = 0;
        object_y = screen_height - object_height;
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
            canvas.drawBitmap(lobstergoods2, object_x, object_y, paint);
            canvas.restore();
            logic();
        }

    }

    @Override
    public void logic() {
        if (object_x < (screen_width + object_width)){
            object_x += speed;
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
        if(!lobstergoods.isRecycled()){
            lobstergoods.recycle();
        }
    }
}
