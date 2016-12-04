package com.example.michael.fishapplication.Factory;

import android.content.res.Resources;

import com.example.michael.fishapplication.Object.BigFish;
import com.example.michael.fishapplication.Object.BossFish;
import com.example.michael.fishapplication.Object.BubbleGoods;
import com.example.michael.fishapplication.Object.GameObject;
import com.example.michael.fishapplication.Object.HugeFish;
import com.example.michael.fishapplication.Object.LargeFish;
import com.example.michael.fishapplication.Object.LobsterGoods;
import com.example.michael.fishapplication.Object.MiddleFish;
import com.example.michael.fishapplication.Object.MyFish;
import com.example.michael.fishapplication.Object.SmallFish;
import com.example.michael.fishapplication.Object.StarGoods;

/**
 * Created by Michael on 2016/11/13.
 */
public class GameObjectFactory {
    //创建smallfish的方法
    public GameObject createSmallFish(Resources resources){
        return new SmallFish(resources);
    }
    //创建middlefish的方法
    public GameObject createMiddleFish(Resources resources){
        return new MiddleFish(resources);
    }
    //创建bigfish的方法
    public GameObject createBigFish(Resources resources){
        return new BigFish(resources);
    }
    //创建largefish的方法
    public GameObject createLargeFish(Resources resources){
        return new LargeFish(resources);
    }
    //创建hugefish敌机的方法
    public GameObject createHugeFish(Resources resources){
        return new HugeFish(resources);
    }
    //创建bossfish的方法
    public GameObject createBossFish(Resources resources){
        return new BossFish(resources);
    }
    //创建玩家fish的方法
    public GameObject createMyFish(Resources resources){
        return new MyFish(resources);
    }
    //创建气泡物品
    public GameObject createBubbleGoods(Resources resources){
        return new BubbleGoods(resources);
    }
    //创建海星物品
    public GameObject createStarGoods(Resources resources){
        return new StarGoods(resources);
    }
    //创建龙虾物品
    public GameObject createLobesterGoods(Resources resources){
        return new LobsterGoods(resources);
    }
//    //创建导弹物品
//    public GameObject createMissileGoods(Resources resources){
//        return new MissileGoods(resources);
//    }
}
