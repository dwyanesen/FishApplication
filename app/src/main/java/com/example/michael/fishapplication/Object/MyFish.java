package com.example.michael.fishapplication.Object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.michael.fishapplication.Factory.GameObjectFactory;
import com.example.michael.fishapplication.GameView.MainView;
import com.example.michael.fishapplication.Interfaces.InMyFish;
import com.example.michael.fishapplication.R;

import java.util.List;

/**
 * Created by Michael on 2016/11/13.
 */
public class MyFish extends GameObject implements InMyFish{

    private float middle_x;			 // 飞机的中心坐标
    private float middle_y;
    private long startTime;	 	 	 // 开始的时间
    private long endTime;	 	 	 // 结束的时间
    private Bitmap myfish;			 // myfish初始的图片
    private Bitmap resizeBmp;			 // myfish变化的图片
    private MainView mainView;
    private GameObjectFactory factory;
    protected int blood; 						 // 对象的当前血量
    protected int bloodVolume; 					 // 对象总的血量
    protected boolean isEatten;   			 // 判断是否被吃掉
    protected boolean isVisible;		 		 //	 对象是否为可见状态


    public MyFish(Resources resources) {
        super(resources);
        initBitmap(1);
        this.speed = 8;
        this.blood = 15;
    }

    @Override
    public void setScreenWH(float screen_width, float screen_height) {
        super.setScreenWH(screen_width, screen_height);
        object_x = screen_width/2 - object_width/2;
        object_y = screen_height/2 - object_height/2;
        //myfish位置

        middle_x = object_x + object_width/2;
        middle_y = object_y + object_height/2;
    }


    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
    // 设置屏幕宽度和高度

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    protected void initBitmap() {

    }

    @Override
    public void drawSelf(Canvas canvas) {
        if(isAlive){
            int x = (int) (currentFrame * object_width); // 获得当前帧相对于位图的X坐标
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            canvas.drawBitmap(resizeBmp, object_x - x, object_y, paint);
            canvas.restore();
            currentFrame++;
            if (currentFrame >= 1) {
                currentFrame = 0;
            }
        }
        else{
            isAlive = false;
            isEatten = false;
            isVisible = false;
        }
    }


    public void direction(int grade){
        myfish = BitmapFactory.decodeResource(resources, R.drawable.myfish2);

//        if(grade <= 4) {
//            Matrix matrix = new Matrix();
//            matrix.postScale((float) 0.67 + grade / 3, (float) 0.67 + grade / 3); //长和宽放大缩小的比例
//            resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() / 3, myfish.getHeight(), matrix, true);
//        }else{
//            if(grade <=8 && grade >4) {
//                Matrix matrix = new Matrix();
//                matrix.postScale((float) 2.5 + grade / 13, (float) 2.5 + grade / 13); //长和宽放大缩小的比例
//                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() / 3, myfish.getHeight(), matrix, true);
//            }
//            else{
//                Matrix matrix = new Matrix();
//                matrix.postScale((float) 3.2 + grade / 15, (float) 3.2 + grade / 15); //长和宽放大缩小的比例
//                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() / 3, myfish.getHeight(), matrix, true);
//            }
//        }
        if(grade <= 4) {
            Matrix matrix = new Matrix();
            matrix.postScale((float)grade/10,(float) grade/10); //长和宽放大缩小的比例
            resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() , myfish.getHeight(), matrix, true);
        }else{
            if(grade <=8 && grade >4) {
                Matrix matrix = new Matrix();
                matrix.postScale((float) grade / 12, (float) grade / 12); //长和宽放大缩小的比例
                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth(), myfish.getHeight(), matrix, true);
            }
            else{
                Matrix matrix = new Matrix();
                matrix.postScale((float) grade / 13, (float)  grade / 13); //长和宽放大缩小的比例
                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth(), myfish.getHeight(), matrix, true);
            }
        }


    }

    public void redirection(int grade){
        myfish = BitmapFactory.decodeResource(resources, R.drawable.myfish2);

//        if(grade <= 4) {
//            Matrix matrix = new Matrix();
//            matrix.postScale((float) -(0.67 + grade / 3), (float) 0.67 + grade / 3); //长和宽放大缩小的比例
//            resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() / 3, myfish.getHeight(), matrix, true);
//        }else{
//            if(grade <=8 && grade >4) {
//                Matrix matrix = new Matrix();
//                matrix.postScale((float) -(2.5 + grade / 13), (float) 2.5 + grade / 13); //长和宽放大缩小的比例
//                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() / 3, myfish.getHeight(), matrix, true);
//            }
//            else{
//                Matrix matrix = new Matrix();
//                matrix.postScale((float) -(3.2 + grade / 15), (float) 3.2 + grade / 15); //长和宽放大缩小的比例
//                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() / 3, myfish.getHeight(), matrix, true);
//            }
//        }
        if(grade <= 4) {
            Matrix matrix = new Matrix();
            matrix.postScale((float)-grade/10,(float) grade/10); //长和宽放大缩小的比例
            resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() , myfish.getHeight(), matrix, true);
        }else{
            if(grade <=8 && grade >4) {
                Matrix matrix = new Matrix();
                matrix.postScale((float) -grade / 12, (float) grade / 12); //长和宽放大缩小的比例
                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth(), myfish.getHeight(), matrix, true);
            }
            else{
                Matrix matrix = new Matrix();
                matrix.postScale((float) -grade / 13, (float)  grade / 13); //长和宽放大缩小的比例
                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth(), myfish.getHeight(), matrix, true);
            }
        }

    }


    public void initBitmap(float grade) {
        myfish = BitmapFactory.decodeResource(resources, R.drawable.myfish2);

        if(grade <= 4) {
            Matrix matrix = new Matrix();
            matrix.postScale((float)grade/10,(float) grade/10); //长和宽放大缩小的比例
            resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth() , myfish.getHeight(), matrix, true);
        }else{
            if(grade <8 && grade >4) {
                Matrix matrix = new Matrix();
                matrix.postScale((float) grade / 12, (float) grade / 12); //长和宽放大缩小的比例
                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth(), myfish.getHeight(), matrix, true);
            }else if(grade == 8) {
                Matrix matrix = new Matrix();
                matrix.postScale((float) grade / 8, (float) grade / 8); //长和宽放大缩小的比例
                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth(), myfish.getHeight(), matrix, true);
            }
            else{
                Matrix matrix = new Matrix();
                matrix.postScale((float) grade / 13, (float)  grade / 13); //长和宽放大缩小的比例
                resizeBmp = Bitmap.createBitmap(myfish, 0, 0, myfish.getWidth(), myfish.getHeight(), matrix, true);
            }
        }

        object_width = resizeBmp.getWidth() ; // 获得每一帧位图的宽
        object_height = resizeBmp.getHeight(); 	// 获得每一帧位图的高

//        object_width = myfish.getWidth() / 3; // 获得每一帧位图的宽
//        object_height = myfish.getHeight(); 	// 获得每一帧位图的高
    }

//    @Override
//    public void initial(int arg0, float arg1, float arg2) {
//        isAlive = true;
//        bloodVolume = 15;
//        blood = bloodVolume;
//    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }

    @Override
    public void logic() {
        super.logic();
    }

    @Override
    public void release() {
        if(!myfish.isRecycled()){
            myfish.recycle();
        }
    }

    @Override
    public void setAlive(boolean isAlive) {
        super.setAlive(isAlive);
    }

    @Override
    public float getMiddle_x() {
        return middle_x;
    }
    @Override
    public void setMiddle_x(float middle_x) {
        this.middle_x = middle_x;
        this.object_x = middle_x - object_width/2;
    }
    @Override
    public float getMiddle_y() {
        return middle_y;
    }
    @Override
    public void setMiddle_y(float middle_y) {
        this.middle_y = middle_y;
        this.object_y = middle_y - object_height/2;
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

//    @Override
//    public void eat(Canvas canvas, List<EnemyFish> fishs) {
//        if(MyFish.this.isAlive())
//        {
//            for(EnemyFish pobj:fishs)
//            {
//                if(pobj.isCanCollide())
//                {
//                    if(this.isCollide((GameObject)pobj)){
//                        if(this.blood < pobj.blood)
//                        {
//                            this.setAlive(!isAlive);
//                        }else{
//                            mainView.addGameScore(pobj.getScore());
//                        }
//                    }
//                }
//            }
//        }
//    }


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

}
