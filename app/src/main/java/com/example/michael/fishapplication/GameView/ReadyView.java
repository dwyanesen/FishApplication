package com.example.michael.fishapplication.GameView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.michael.fishapplication.Constant.ConstantUtil;
import com.example.michael.fishapplication.R;
import com.example.michael.fishapplication.Sounds.GameSoundPool;

/**
 * Created by Michael on 2016/11/12.
 */
public class ReadyView extends BaseView{

    private float fly_x;					// 图片的坐标
    private float fly_y;
    private float fly_height;
    private float text_x;
    private float text_y;
    private float button_x;
    private float button_y;
    private float button_y2;
    private float strwid;
    private float strhei;
    private boolean isBtChange;				// 按钮图片改变的标记
    private boolean isBtChange2;
    private String startGame = "开始游戏";	// 按钮的文字
    private String exitGame = "退出游戏";
    private Bitmap text;					// 文字图片
    private Bitmap button;					// 按钮图片
    private Bitmap button2;					// 按钮图片
    private Bitmap fishswim;				// 飞机飞行的图片
    private Bitmap background;				// 背景图片
    private Rect rect;						// 绘制文字的区域

    public ReadyView(Context context,GameSoundPool sounds) {
        super(context,sounds);
        paint.setTextSize(40);
        rect = new Rect();
        thread = new Thread(this);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
        initBitmap();
        if(thread.isAlive()){
            thread.start();
        }
        else{
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        while(threadFlag){
            long startTime = System.currentTimeMillis();
            drawSelf();
            long endTime = System.currentTimeMillis();
            try{
                if(endTime - startTime < 400)
                    Thread.sleep(400 - (endTime - startTime));
            }catch (InterruptedException err){
                err.printStackTrace();
            }
        }
    }

    @Override
    public void initBitmap() {
        super.initBitmap();

        background = BitmapFactory.decodeResource(getResources(),R.drawable.background1);
        text = BitmapFactory.decodeResource(getResources(), R.drawable.text3);
        fishswim = BitmapFactory.decodeResource(getResources(), R.drawable.swim);
        button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        button2 = BitmapFactory.decodeResource(getResources(),R.drawable.button2);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        text_x = screen_width / 2 - text.getWidth() / 2;
        text_y = screen_height / 2 - text.getHeight();
        fly_x = screen_width / 2 - fishswim.getWidth() / 2;
        fly_height = fishswim.getHeight() / 3;
        fly_y = text_y - fly_height - 20;
        button_x = screen_width / 2 - button.getWidth() / 2;
        button_y = screen_height / 2 + button.getHeight();
        button_y2 = button_y + button.getHeight() + 40;
        // 返回包围整个字符串的最小的一个Rect区域
        paint.getTextBounds(startGame, 0, startGame.length(), rect);
        strwid = rect.width();
        strhei = rect.height();
    }

    @Override
    public void release() {
        super.release();
        if (!text.isRecycled()) {
            text.recycle();
        }
        if (!button.isRecycled()) {
            button.recycle();
        }
        if (!button2.isRecycled()) {
            button2.recycle();
        }
        if (!fishswim.isRecycled()) {
            fishswim.recycle();
        }
        if (!background.isRecycled()) {
            background.recycle();
        }
    }

    @Override
    public void drawSelf() {
        super.drawSelf();
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK); 						// 绘制背景色
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);					// 计算背景图片与屏幕的比例
            canvas.drawBitmap(background, 0, 0, paint); 		// 绘制背景图
            canvas.restore();
            canvas.drawBitmap(text, text_x, text_y, paint);		// 绘制文字图片
            //当手指滑过按钮时变换图片
            if (isBtChange) {
                canvas.drawBitmap(button2, button_x, button_y, paint);
            }
            else {
                canvas.drawBitmap(button, button_x, button_y, paint);
            }
            if (isBtChange2) {
                canvas.drawBitmap(button2, button_x, button_y2, paint);
            }
            else {
                canvas.drawBitmap(button, button_x, button_y2, paint);
            }
            //开始游戏的按钮
            canvas.drawText(startGame, screen_width / 2 - strwid / 2, button_y
                    + button.getHeight() / 2 + strhei / 2, paint);
            //退出游戏的按钮
            canvas.drawText(exitGame, screen_width / 2 - strwid / 2, button_y2
                    + button.getHeight() / 2 + strhei / 2, paint);
            //飞机飞行的动画
            canvas.save();
            canvas.clipRect(fly_x, fly_y, fly_x + fishswim.getWidth(), fly_y + fly_height);
            canvas.drawBitmap(fishswim, fly_x, fly_y - currentFrame * fly_height,paint);
            currentFrame++;
            if (currentFrame >= 3) {
                currentFrame = 0;
            }
            canvas.restore();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();
            //判断第一个按钮是否被按下
            if (x > button_x && x < button_x + button.getWidth() && y > button_y && y < button_y + button.getHeight()) {
                sounds.playSound(1, 0);
                isBtChange = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);//调用主活动的函数来传递相关的与游戏进程有关的信息，已经在ConstantUtil中对所有的的信息加以说明
                //这里是传递一个去到活动主界面的信息
            }
            //判断第二个按钮是否被按下
            else if (x > button_x && x < button_x + button.getWidth() && y > button_y2 && y < button_y2 + button.getHeight()) {
                //sounds.playSound(7, 0);
                isBtChange2 = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);//调用主活动的函数来传递相关的与游戏进程有关的信息，已经在ConstantUtil中对所有的的信息加以说明
                //这里是传递一个结束游戏的信息
            }
            return true;
        }
        //响应屏幕单点移动的消息
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight()) {
                isBtChange = true;
            } else {
                isBtChange = false;
            }
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight()) {
                isBtChange2 = true;
            } else {
                isBtChange2 = false;
            }
            return true;
        }
        //响应手指离开屏幕的消息
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChange = false;
            isBtChange2 = false;
            return true;
        }
        return false;
    }
}
