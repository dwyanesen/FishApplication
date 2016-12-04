package com.example.michael.fishapplication.GameView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.example.michael.fishapplication.Constant.ConstantUtil;
import com.example.michael.fishapplication.Factory.GameObjectFactory;
import com.example.michael.fishapplication.Object.BigFish;
import com.example.michael.fishapplication.Object.BossFish;
import com.example.michael.fishapplication.Object.BubbleGoods;
import com.example.michael.fishapplication.Object.EnemyFish;
import com.example.michael.fishapplication.Object.GameObject;
import com.example.michael.fishapplication.Object.HugeFish;
import com.example.michael.fishapplication.Object.LargeFish;
import com.example.michael.fishapplication.Object.LobsterGoods;
import com.example.michael.fishapplication.Object.MiddleFish;
import com.example.michael.fishapplication.Object.MyFish;
import com.example.michael.fishapplication.Object.SmallFish;
import com.example.michael.fishapplication.Object.StarGoods;
import com.example.michael.fishapplication.R;
import com.example.michael.fishapplication.Sounds.GameSoundPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Michael on 2016/11/13.
 */
public class MainView extends BaseView {

    private int grade;
    private int middlePlaneScore;	// 中型敌机的积分
    private int bigPlaneScore;		// 大型敌机的积分
    private int bossPlaneScore;		// boss型敌机的积分
    private int missileScore;		// 导弹的积分
    private int bulletScore;		// 子弹的积分
    private int sumScore;
    private float bg_y;				// 图片的坐标
    private float bg_y1;
    private float bg_y2;
    private float play_bt_w;
    private float play_bt_h;
    private Bitmap background; 		// 背景图片
    private Bitmap background2; 	// 背景图片

    private Bitmap background1; 	// 背景图片
    private Bitmap playButton; 		// 开始/暂停游戏的按钮图片

    private int speedTime;			// 游戏速度的倍数
    private GameObjectFactory factory;
    private List<EnemyFish> enemyFishs;
    private MyFish myFish;		// 玩家的飞机

    private BubbleGoods bubbleGoods;
    private StarGoods starGoods;
    private LobsterGoods lobsterGoods;


    private MediaPlayer mp = new MediaPlayer();

    private boolean isPlay;			// 标记游戏运行状态
    private boolean isTouchPlane;	// 判断玩家是否按下屏幕


    Timer timer2 = new Timer();//LobsterGoods
    Timer timer1 = new Timer();//吃了海星结束加速时用
    Timer timer = new Timer();//定时器，海星物品一段时间(2秒)出现
    TimerTask tt,tt1;


    public MainView(Context context,GameSoundPool sounds) {
        super(context,sounds);
        isPlay = true;
        speedTime = 1;
        grade = 1;
        factory = new GameObjectFactory();						  //工厂类
        enemyFishs = new ArrayList<EnemyFish>();
        myFish = (MyFish) factory.createMyFish(getResources());//生产玩家的飞机
        myFish.setMainView(this);
        for(int i = 0; i < SmallFish.sumCount; i++){
            //生产smallfish
            SmallFish smallFish = (SmallFish) factory.createSmallFish(getResources());
            enemyFishs.add(smallFish);
        }
        for(int i = 0;i < MiddleFish.sumCount;i++){
            //生产middlefish
            MiddleFish middleFish = (MiddleFish) factory.createMiddleFish(getResources());
            enemyFishs.add(middleFish);
        }
        for(int i = 0; i < BigFish.sumCount; i++){
            //生产bigfish
            BigFish bigFish = (BigFish) factory.createBigFish(getResources());
            enemyFishs.add(bigFish);
        }
        for(int i = 0; i < LargeFish.sumCount; i++){
            //生产largefish
            LargeFish largeFish = (LargeFish) factory.createLargeFish(getResources());
            enemyFishs.add(largeFish);
        }
        for(int i = 0; i < HugeFish.sumCount; i++){
            //生产hugefish
            HugeFish hugeFish = (HugeFish) factory.createHugeFish(getResources());
            enemyFishs.add(hugeFish);
        }
        for(int i = 0; i < BossFish.sumCount; i++){
            //生产bossfish
            BossFish bossFish = (BossFish) factory.createBossFish(getResources());
            enemyFishs.add(bossFish);
        }
        for(int i = 0; i < BubbleGoods.sumCount; i++){
            //生产bubblegoods
            bubbleGoods = (BubbleGoods) factory.createBubbleGoods(getResources());
        }
        for(int i = 0; i < StarGoods.sumCount; i++){
            //生产stargoods
            starGoods = (StarGoods) factory.createStarGoods(getResources());
        }
        for(int i = 0; i < LobsterGoods.sumCount; i++){
            //生产stargoods
            lobsterGoods = (LobsterGoods) factory.createLobesterGoods(getResources());
        }

        thread = new Thread(this);



    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
        initBitmap(); // 初始化图片资源
        mp = MediaPlayer.create(mainActivity, R.raw.bgm);
        mp.setLooping(true);
        mp.start();
//        sounds.playSound(4,-1);
        for(GameObject obj:enemyFishs){
            obj.setScreenWH(screen_width,screen_height);
        }
        myFish.setScreenWH(screen_width,screen_height);
        bubbleGoods.setScreenWH(screen_width,screen_height);
        bubbleGoods.setAlive(true);
        starGoods.setScreenWH(screen_width,screen_height);
        lobsterGoods.setScreenWH(screen_width,screen_height);

        myFish.setAlive(true);

        if(thread.isAlive()){

            thread.start();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(sumScore > 2000) {
                        if (!lobsterGoods.isAlive()) {
                            lobsterGoods.initial(0, 0, 0);
                        }
                    }
                }
            },2000, 50000);
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    if (!starGoods.isAlive()) {
                        starGoods.initial(0, 0, 0);
                    }
                }
            },0 , 10000);
        }
        else{
            thread = new Thread(this);
            thread.start();
//            tt1 = new TimerTask() {
//                @Override
//                public void run() {
//                    if(sumScore > 2000) {
//                        if (!lobsterGoods.isAlive()) {
//                            lobsterGoods.initial(0, 0, 0);
//                        }
//                    }
//                }
//            };
//            tt =;
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(sumScore > 2000) {
                        if (!lobsterGoods.isAlive()) {
                            lobsterGoods.initial(0, 0, 0);
                        }
                    }
                }
            },2000, 50000);
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    if (!starGoods.isAlive()) {
                        starGoods.initial(0, 0, 0);
                    }
                }
            },0, 10000);
        }
    }


    //视图销毁方法
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        release();
    }

    @Override
    public void initBitmap() {
        playButton = BitmapFactory.decodeResource(getResources(),R.drawable.play);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        background2 = BitmapFactory.decodeResource(getResources(), R.drawable.background4);
        background1 = BitmapFactory.decodeResource(getResources(), R.drawable.background4);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        play_bt_w = playButton.getWidth();
        play_bt_h = playButton.getHeight()/2;
        bg_y = 0;
        bg_y1 = 0;
        bg_y2 = bg_y1 - screen_width;

    }

    // 增加游戏分数的方法
    public void addGameScore(int score){
//        middlePlaneScore += score;	// 中型敌机的积分
//        bigPlaneScore += score;		// 大型敌机的积分
//        bossPlaneScore += score;	// boss型敌机的积分
//        missileScore += score;		// 导弹的积分
//        bulletScore += score;		// 子弹的积分
        sumScore += score;			// 游戏总得分
    }

    //初始化游戏对象
    public void initObject() {
        int a = 1;
        for (EnemyFish obj : enemyFishs) {
            //初始化smallfish
            if (obj instanceof SmallFish) {
                if (!obj.isAlive()) {
                    obj.initial(speedTime, a, 0);
                    break;
                }
            }
            //初始化middlefish
            else if (obj instanceof MiddleFish) {
//                if(middlePlaneScore > 10000){
                if (!obj.isAlive()) {
                    obj.initial(speedTime, a, 0);
                    break;
                }
//                }
            }
            //初始化bigfish
            else if (obj instanceof BigFish) {
                if (sumScore >= 1000) {
                    if (!obj.isAlive()) {
                        obj.initial(speedTime, a, 0);
                        break;
                    }
                }
            }
            //初始化largefish
            else if (obj instanceof LargeFish) {
                if (sumScore >= 8000) {
                    if (!obj.isAlive()) {
                        obj.initial(speedTime, a, 0);
                        break;
                    }
                }
            }
            //初始化hugefish
            else if (obj instanceof HugeFish) {
                if (sumScore >= 15000) {
                    if (!obj.isAlive()) {
                        obj.initial(speedTime, a, 0);
                        break;
                    }
                }
            }
            //初始化bossfish
            else {
                if (sumScore >= 80000) {
                    if (!obj.isAlive()) {
                        obj.initial(speedTime, a, 0);
                        break;
                    }
                }
            }
            a++;
        }
//        if (BigFish.currentCount <=8 && MiddleFish.currentCount < 10)
        if ( (sumScore >= 2000 && sumScore <= 3000) || (sumScore > 12000 && sumScore <14000)){
            if (!bubbleGoods.isAlive()) {
                bubbleGoods.initial(0, 0, 0);
            }
        }

//        Random random = new Random();
//        if ( sumScore >= random.nextInt() && (sumScore >= random.nextInt()+2000)){
//            if (!starGoods.isAlive()) {
//                starGoods.initial(0, 0, 0);
//            }
//        }
//        //提升等级
//        if(sumScore >= speedTime*100000 && speedTime < 6){
////            speedTime++;
//            grade++;
//            myFish.setBlood(myFish.getBlood()+5);
//        }

        //提升等级
        if(sumScore >= grade*8000 && grade < 20){
//            speedTime++;
            sounds.playSound(5,0);
            grade++;
            myFish.setBlood(myFish.getBlood()+5);
            myFish.setSpeed(myFish.getSpeed()+2);
            myFish.initBitmap(grade);
    }
    }

    // 释放图片资源的方法
    @Override
    public void release() {
        // TODO Auto-generated method stub
        for(GameObject obj:enemyFishs){
            obj.release();
        }
        myFish.release();
        if(!playButton.isRecycled()){
            playButton.recycle();
        }
        if(!background.isRecycled()){
            background.recycle();
        }
        if(!background2.isRecycled()){
            background2.recycle();
        }
        timer1.cancel();
        timer2.cancel();
        timer.cancel();
        mp.release();
    }

    // 响应触屏事件的方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            isTouchPlane = false;
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h) {
                if (isPlay) {
                    isPlay = false;
                    mp.pause();
                } else {
                    isPlay = true;
                    mp.start();
                    synchronized (thread) {
                        thread.notify();
                    }
                }
                return true;
            }
            //判断玩家的鱼是否被按下
            else if (x > myFish.getObject_x() && x < myFish.getObject_x() + myFish.getObject_width()
                    && y > myFish.getObject_y() && y < myFish.getObject_y() + myFish.getObject_height()) {
                if (isPlay) {
                    isTouchPlane = true;
                }
                return true;
            }
        }
        //响应手指在屏幕移动的事件
        else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){
            //判断触摸点是否为玩家的飞机
            if(isTouchPlane){
                float x = event.getX();
                float y = event.getY();
                if(x > myFish.getMiddle_x() + 20){
                    if(myFish.getMiddle_x() + myFish.getSpeed() <= screen_width){
                        myFish.redirection(grade);
                        myFish.setMiddle_x(myFish.getMiddle_x() + myFish.getSpeed());
                    }
                }
                else if(x < myFish.getMiddle_x() - 20){
                    if(myFish.getMiddle_x() - myFish.getSpeed() >= 0){
                        myFish.direction(grade);
                        myFish.setMiddle_x(myFish.getMiddle_x() - myFish.getSpeed());
                    }
                }
                if(y > myFish.getMiddle_y() + 20){
                    if(myFish.getMiddle_y() + myFish.getSpeed() <= screen_height){
                        myFish.setMiddle_y(myFish.getMiddle_y() + myFish.getSpeed());
                    }
                }
                else if(y < myFish.getMiddle_y() - 20){
                    if(myFish.getMiddle_y() - myFish.getSpeed() >= 0){
                        myFish.setMiddle_y(myFish.getMiddle_y() - myFish.getSpeed());
                    }
                }
                return true;
            }
        }
        return false;
    }




    // 绘图方法
    @Override
    public void drawSelf() {
        // TODO Auto-generated method stub
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK); // 绘制背景色
            canvas.save();
            // 计算背景图片与屏幕的比例
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, bg_y, 0, paint);   // 绘制背景图
            canvas.drawBitmap(background1, bg_y1, 0, paint); // 绘制背景图
            canvas.drawBitmap(background2, bg_y2, 0, paint); // 绘制背景图
            canvas.restore();
            //绘制按钮
            canvas.save();
            canvas.clipRect(10, 10, 10 + play_bt_w,10 + play_bt_h);
            if(isPlay){
                canvas.drawBitmap(playButton, 10, 10, paint);
            }
            else{
                canvas.drawBitmap(playButton, 10, 10 - play_bt_h, paint);
            }
            canvas.restore();
            //绘制bubblegoods物品
            if(bubbleGoods.isAlive()){
                if(bubbleGoods.isCollide(myFish)){
                    bubbleGoods.setAlive(false);
                    addGameScore(5000);
                    sounds.playSound(3, 0);
                }
                else
                    bubbleGoods.drawSelf(canvas);
            }
            //绘制stargoods物品
            if(starGoods.isAlive()){
                if(starGoods.isCollide(myFish)){
                    starGoods.setAlive(false);
                    myFish.setSpeed(myFish.getSpeed()+10);
//                    addGameScore(5000);
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            myFish.setSpeed(myFish.getSpeed()-10);
                            Log.i("APP.TAG","speedown");
                        }
                    };

                    timer1.schedule(task,5000);

                    sounds.playSound(3, 0);
                }
                else
                    starGoods.drawSelf(canvas);
            }
            //绘制bubblegoods物品
            if(lobsterGoods.isAlive()){
                if(lobsterGoods.isCollide(myFish)){
                    lobsterGoods.setAlive(false);
                    addGameScore(8000);
                    sounds.playSound(3, 0);
                }
                else
                    lobsterGoods.drawSelf(canvas);
            }
            //绘制enemyfish
            for(EnemyFish obj:enemyFishs){
                if(obj.isAlive()){
                    obj.drawSelf(canvas);
                    //检测敌机是否与玩家的飞机碰撞
                    if(obj.isCanCollide() && myFish.isAlive()){
                        if(obj.isCollide(myFish)){
                            if((obj.getBlood() )>( myFish.getBlood())) {

//                                sounds.stop(4);
                                mp.stop();
                                sounds.playSound(2, 0);
                                myFish.setAlive(false);
                                Log.i("APP.TAG","比较判断错误");
                                Log.i("APP.TAG",Integer.toString(obj.getBlood()));
                                Log.i("APP.TAG",Integer.toString(myFish.getBlood()));
                            }
                            else{
                                sounds.playSound(3, 0);
                                obj.setBlood(0);
                                obj.setAlive(false);
                                addGameScore(obj.getScore());
                            }
                        }
                    }
                }
            }
            if(!myFish.isAlive()){
                threadFlag = false;
//                sounds.playSound(4, 0);
            }
            myFish.drawSelf(canvas);	//绘制myfish

//            myPlane.shoot(canvas,enemyPlanes);
//            sounds.playSound(1, 0);	  //子弹音效
//            //绘制导弹按钮
//            if(missileCount > 0){
//                paint.setTextSize(40);
//                paint.setColor(Color.BLACK);
//                canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
//                canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);//绘制文字
//            }
            //绘制积分文字
//            paint.setTextSize(30);
//            paint.setColor(Color.rgb(235, 161, 1));
            paint.setTextSize(50);
            paint.setColor(Color.rgb(0, 0, 200));
            canvas.drawText("积分:"+String.valueOf(sumScore), 30 + play_bt_w, 40, paint);		//绘制文字
            canvas.drawText("等级:"+String.valueOf(grade), screen_width - 200, 40, paint); //绘制文字
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    // 背景移动的逻辑函数
    public void viewLogic(){

//        if(bg_y2 > -background2.getWidth()/2 && bg_y2 <= 0){
//            bg_y2 -=30;
//        }
//        else if((bg_y2 < -background2.getWidth()/2 && bg_y2 > -background2.getWidth() ) )
//        {
//            bg_y2 += 30;
//        }
        if(bg_y1 > bg_y2){
            bg_y1 += 20;
            bg_y2 = bg_y1 - background1.getWidth();
        }
        else{
            bg_y2 += 20;
            bg_y1 = bg_y2 - background1.getWidth();
        }
        if(bg_y1 >= background1.getWidth()){
            bg_y1 = bg_y2 - background1.getWidth();
        }
        else if(bg_y2 >= background1.getHeight()){
            bg_y2 = bg_y1 - background1.getWidth();
        }
    }

    public void playSound(int key){
        sounds.playSound(key, 0);
    }
    // 线程运行的方法

    // 线程运行的方法
    @Override
    public void run() {
        // TODO Auto-generated method stub


//        while(threadFlag){
//            if(!starGoods.isAlive()) {
//                starGoods.initial(0,0,0);
//            }
//        }
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            initObject();
            drawSelf();
            viewLogic();		//背景移动的逻辑
            long endTime = System.currentTimeMillis();
            if(!isPlay){
                synchronized (thread) {
                    try {
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                if (endTime - startTime < 100)
                    Thread.sleep(100 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Message message = new Message();
        message.what = 	ConstantUtil.TO_END_VIEW;
        message.arg1 = Integer.valueOf(sumScore);
        mainActivity.getHandler().sendMessage(message);
    }

}
