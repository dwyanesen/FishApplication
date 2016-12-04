package com.example.michael.fishapplication.Object;

import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Michael on 2016/11/23.
 */
public class GameGoods extends GameObject {

    public GameGoods(Resources resources) {
        super(resources);
        initBitmap();
    }

    @Override
    public void release() {

    }

    @Override
    public void logic() {
        super.logic();
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
        return true;
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        super.initial(arg0, arg1, arg2);
    }

    @Override
    protected void initBitmap() {

    }

    @Override
    public void drawSelf(Canvas canvas) {

    }
}
