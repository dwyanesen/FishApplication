package com.example.michael.fishapplication.Object;

import android.content.res.Resources;
import android.graphics.Canvas;

/**
 * Created by Michael on 2016/11/13.
 */
public class EnemyFish extends GameObject {


    protected int score;						 // 对象的分值
    protected int blood; 						 // 对象的当前血量
    protected int bloodVolume; 					 // 对象总的血量
    protected boolean isEatten;   			 // 判断是否被吃掉
    protected boolean isVisible;		 		 //	 对象是否为可见状态

    public EnemyFish(Resources resources) {
        super(resources);
        initBitmap();			// 初始化图片资源
    }

    //对象的绘图函数
    @Override
    public void drawSelf(Canvas canvas) {

    }

    //初始化图片资源
    @Override
    protected void initBitmap() {

    }

    //释放资源
    @Override
    public void release() {

    }

//    //逻辑方法
//    @Override
//    public void logic() {
//
//        // TODO Auto-generated method stub
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
//        if (blood <= 0) {
//            isEatten = true;
//        }
//        else{
//            isEatten = false;
//        }
//    }

    //逻辑方法
    @Override
    public void logic() {

        // TODO Auto-generated method stub
        if (object_x < screen_width) {
            object_x += speed;
        }
        else {
            isAlive = false;
        }
        if(object_y + object_height > 0){
            isVisible = true;
        }
        else{
            isVisible = false;
        }
        if (blood <= 0) {
            isEatten = true;
        }
        else{
            isEatten = false;
        }
    }

    //检测碰撞
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
        return true;
    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }

    //初始化数据
    @Override
    public void initial(int arg0, float arg1, float arg2) {
        super.initial(arg0, arg1, arg2);
    }

    @Override
    public int getSpeed() {
        return super.getSpeed();
    }

    // 判断能否被检测碰撞
    public boolean isCanCollide() {
        // TODO Auto-generated method stub
        return isAlive && !isEatten && isVisible;
    }

    //getter和setter方法
    public int getScore() {
        // TODO Auto-generated method stub
        return score;
    }
    public void setScore(int score) {
        // TODO Auto-generated method stub
        this.score = score;
    }


    public int getBlood() {
        // TODO Auto-generated method stub
        return blood;
    }
    public void setBlood(int blood) {
        // TODO Auto-generated method stub
        this.blood = blood;
    }


    public int getBloodVolume() {
        // TODO Auto-generated method stub
        return bloodVolume;
    }
    public void setBloodVolume(int bloodVolume) {
        // TODO Auto-generated method stub
        this.bloodVolume = bloodVolume;
    }


    public boolean isEatten() {
        // TODO Auto-generated method stub
        return isEatten;
    }

}
