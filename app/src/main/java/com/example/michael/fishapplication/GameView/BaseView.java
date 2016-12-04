package com.example.michael.fishapplication.GameView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.michael.fishapplication.Activity.MainActivity;
import com.example.michael.fishapplication.Sounds.GameSoundPool;

/**
 * Created by Michael on 2016/11/12.
 */
public class BaseView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    protected int currentFrame;				// 当前动画帧
    protected float scalex;					// 背景图片的缩放比例
    protected float scaley;
    protected float screen_width;			// 视图的宽度
    protected float screen_height;			// 视图的高度
    protected boolean threadFlag;			// 线程运行的标记
    protected Paint paint; 					// 画笔对象
    protected Canvas canvas; 				// 画布对象
    protected Thread thread; 				// 绘图线程
    protected SurfaceHolder sfh;
    protected GameSoundPool sounds;
    protected MainActivity mainActivity;

    //构造方法
    public BaseView(Context context,GameSoundPool sounds) {
        super(context);

        this.sounds = sounds;
        this.mainActivity = (MainActivity)context;
        sfh = this.getHolder();
        sfh.addCallback(this);//注册回调方法
        paint = new Paint();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //界面销毁的时候
        Log.i("APP.TAG","surfaceDestroyed...");
        threadFlag = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //界面改变的时候（宽高德改变：横屏竖屏）
        Log.i("APP.TAG","surfaceChanged...");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //视图创建的时候
        Log.i("APP.TAG","surfaceCreated...");
        screen_height = this.getHeight();
        screen_width = this.getWidth();
        threadFlag = true;
    }

    //线程运行的方法
    @Override
    public void run() {

    }

    //初始化图片资源的方法
    public void initBitmap(){

    }

    //释放图片资源的方法
    public void release(){

    }

    //绘图方法
    public void drawSelf(){

    }

    public void setThreadFlag(boolean threadFlag){
        this.threadFlag = threadFlag;
    }

}
