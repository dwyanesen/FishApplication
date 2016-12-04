package com.example.michael.fishapplication.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.michael.fishapplication.Constant.ConstantUtil;
import com.example.michael.fishapplication.GameView.BaseView;
import com.example.michael.fishapplication.GameView.EndView;
import com.example.michael.fishapplication.GameView.MainView;
import com.example.michael.fishapplication.GameView.ReadyView;
import com.example.michael.fishapplication.Sounds.GameSoundPool;

public class MainActivity extends AppCompatActivity {

    private EndView endView;
    private MainView mainView;
    private ReadyView readyView;
    private GameSoundPool sounds;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == ConstantUtil.TO_MAIN_VIEW){
                toMainView();
            }
            else  if(msg.what == ConstantUtil.TO_END_VIEW){
                toEndView(msg.arg1);
            }
            else  if(msg.what == ConstantUtil.END_GAME){
                endGame();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sounds = new GameSoundPool(this);
        sounds.initGameSound();
        setContentView(new ReadyView(this,sounds));

    }

    public void toMainView(){
        if(mainView == null){
            //mainView = new MainView(this,sounds);
            mainView = new MainView(this,sounds);
        }
        setContentView(mainView);
        readyView = null;
        endView = null;
    }

    public void toEndView(int score){
        if(endView == null){
            //endView = new EndView(this,sounds);
            endView = new EndView(this,sounds);
            endView.setScore(score);
        }
        setContentView(endView);
        mainView = null;
    }

    public void endGame(){
        if(readyView != null){
            readyView.setThreadFlag(false);
        }
        else if(mainView != null){
            mainView.setThreadFlag(false);
        }
        else if(endView != null){
            endView.setThreadFlag(false);
        }
        this.finish();
    }

    public Handler getHandler() {
        return handler;
    }
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
