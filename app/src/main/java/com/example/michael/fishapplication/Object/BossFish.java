package com.example.michael.fishapplication.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.michael.fishapplication.R;

import java.util.Random;

/**
 * Created by Michael on 2016/11/13.
 */
public class BossFish extends EnemyFish {
    private static int currentCount = 0;	 //	对象当前的数量
    private Bitmap bossFish; // 对象图片
    private Bitmap bossFish2; // 对象图片
    public static int sumCount = 2;	 	 	 //	对象总的数量

    int a;
    public BossFish(Resources resources) {
        super(resources);
        this.score = 20000;		// 为对象设置分数
    }

    //初始化数据
    @Override
    public void initial(int arg0,float arg1,float arg2){
        isAlive = true;
        bloodVolume = 100;
        blood = bloodVolume;
        Random ran = new Random();
        speed = ran.nextInt(4) + 10 * arg0;
//        object_x = ran.nextInt((int)(screen_width - object_width));
//        object_y = -object_height * (currentCount*2 + 1);
        a = (int)arg1%2;
        if(arg1%2 == 0)
            object_x = 0 - object_width;
        else
            object_x = screen_width + object_width ;
        object_y = ran.nextInt((int)(screen_height - object_height));
        currentCount++;
        if(currentCount >= sumCount){
            currentCount = 0;
        }
    }

    // 初始化图片资源
    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub
        bossFish = BitmapFactory.decodeResource(resources, R.drawable.bossfish3);
        object_width = bossFish.getWidth();			//获得每一帧位图的宽
        object_height = bossFish.getHeight();		//获得每一帧位图的高
        Matrix m = new Matrix();
        m.postScale(-1, 1);   //镜像水平翻转
        bossFish2 = Bitmap.createBitmap(bossFish, 0, 0, (int)object_width, (int)object_height, m, true);
    }

    // 对象的绘图函数
    @Override
    public void drawSelf(Canvas canvas) {
        // TODO Auto-generated method stub
        //判是否死亡状态
        if(isAlive){
            if(a == 0) {
                if (!isEatten) {
                    if (isVisible) {

                        canvas.save();
                        canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
                        canvas.drawBitmap(bossFish, object_x, object_y, paint);
                        canvas.restore();
                    }
                    logic();
                } else {
//                int y = (int) (currentFrame * object_height); // 获得当前帧相对于位图的Y坐标
//                canvas.save();
//                canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
//                canvas.drawBitmap(smallPlane, object_x, object_y - y,paint);
//                canvas.restore();
//                currentFrame++;
//                if(currentFrame >= 3){
//                    currentFrame = 0;
//                    isExplosion = false;
//                    isAlive = false;
//                }
                    isAlive = false;
                    isEatten = false;
                    isVisible = false;
                }
            }else{
                if (!isEatten) {
                    if (isVisible) {
                        canvas.save();
                        canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
                        canvas.drawBitmap(bossFish2, object_x, object_y, paint);
                        canvas.restore();
                    }
                    if (object_x > 0 - object_width) {
                        object_x -= speed;
                    } else {
                        isAlive = false;
                    }
                    if (object_y + object_height > 0) {
                        isVisible = true;
                    } else {
                        isVisible = false;
                    }
                    if (blood <= 0) {
                        isEatten = true;
                    } else {
                        isEatten = false;
                    }
                } else {
//                int y = (int) (currentFrame * object_height); // 获得当前帧相对于位图的Y坐标
//                canvas.save();
//                canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
//                canvas.drawBitmap(smallPlane, object_x, object_y - y,paint);
//                canvas.restore();
//                currentFrame++;
//                if(currentFrame >= 3){
//                    currentFrame = 0;
//                    isExplosion = false;
//                    isAlive = false;
//                }
                    isAlive = false;
                    isEatten = false;
                    isVisible = false;
                }
            }
        }
    }


    // 释放资源
    @Override
    public void release() {
        // TODO Auto-generated method stub
        if(!bossFish.isRecycled()){
            bossFish.recycle();
        }
    }
}
