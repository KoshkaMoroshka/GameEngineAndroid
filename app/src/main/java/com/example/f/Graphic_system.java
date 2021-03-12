package com.example.f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;


public class Graphic_system extends View {
    public static TextView no_sex;
    public static Paint paint = new Paint();

    public static int width, height, w, h;
    public static int scale = 4;


    public static boolean touch = false;
    public static float tx=0, ty=0;


    public static float x=0, y=0;
    public static int dx=0, dy=0, act=0, prevact=0, timer=0;
    public static boolean action_button_click = false;



    public static Dialogue dialogue;
    public static Camera camera;
    public static Player player;
    public static Keyboard keyboard;


    public static Overlay overlay;
    public static Overlay.HP_Bar hp_bar;


    public static Chapter_scenes chapter_1;


    public static int activateControllers(boolean active){
        int r=0;
        if(activateActionButton(active))
            r++;
        if(activateArrows(active))
            r++;
        return r;
    }
    public static boolean activateArrows(boolean active){
        boolean res=MainActivity.arrows;
        MainActivity.activateArrows(active);
        return res;
    }
    public static boolean activateActionButton(boolean active){
        boolean res=MainActivity.act;
        MainActivity.activateActionButton(active);
        return res;
    }


    public void update(Canvas canvas) {
        canvas.translate(width /2, height /2);
        canvas.scale(scale, scale);
        timer++;


        x=camera.x;
        y=camera.y;


        tx-=x;  ty-=y;
        overlay.update();
        overlay.render(canvas, paint, 0, 0);
        tx+=x;  ty+=y;
        return;
    }


    private boolean initialized=false;
    public void init(){
        initialized=true;
        player=new Player();


        overlay=new Overlay();
        hp_bar=overlay.hp_bar;
        camera=overlay.camera;
        dialogue=overlay.dialogue;
        keyboard=overlay.keyboard;



        chapter_1 = new Chapter_1_scenes();
        chapter_1.start();
    }


    public Graphic_system(Context context) {
        super(context);
        Game_object.attach(getResources(), getContext());



        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        return;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w = Graphic_system.width /scale/2;
        h = Graphic_system.height /scale/2;

        if(!initialized)
            init();
        else
        {
            if((prevact==1)&&(act==0))
                action_button_click=true;
            update(canvas);
            touch=false;
            prevact=act;
            act=0;
        }

        action_button_click=false;
        invalidate();
    }

}
